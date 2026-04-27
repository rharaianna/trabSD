/*
 * Template:
 * Estado IDLE da entidade MEIO
 */
package meio;

import framework.Entidade;
import framework.Estado;
import framework.Evento;

public class Meio_idle extends Estado{
    public Meio_idle (Entidade _e){
        super(_e);
        ((Meio)ent).gui.EscreveLog("Meio Pronto"); // tirei a chance de perda
    }
    @Override
    public void transicao(Evento _ev){
        Meio p =(Meio) ent;

        switch(_ev.code){
            case Meio.CONVITE:
                if (p.portaConvidante != -1) {
                    // já tem sessão ativa — rejeita
                    p.gui.EscreveLog("Sessão em andamento, convite rejeitado.");
                    Evento rej = new Evento(Meio.REJEITAR, "rejeitar", "ocupado", null);
                    ent.msg.conecta("localhost", Integer.parseInt(_ev.C1));
                    ent.msg.envia(rej.toString());
                    ent.msg.termina();
                }
                else {
                    p.portaConvidante = Integer.parseInt(_ev.C1); // porta real do User
                    p.portaConvidado  = Integer.parseInt(_ev.C2); // destino informado pelo user

                    p.gui.EscreveLog("Convite de porta " + p.portaConvidante + " para " + p.portaConvidado);
                    Evento fwd = new Evento(Meio.CONVITE, "convite", _ev.C1, null);

                    ent.msg.conecta("localhost", p.portaConvidado);
                    ent.msg.envia(fwd.toString());
                    ent.msg.termina();
                }

                break;

            case Meio.ACEITAR:
                p.gui.EscreveLog("Aceito — notificando " + p.portaConvidante);
                Evento ace = new Evento(Meio.ACEITAR, "aceitar", "ok", null);
                ent.msg.conecta("localhost", p.portaConvidante);
                ent.msg.envia(ace.toString());
                ent.msg.termina();
                break;

            case Meio.REJEITAR:
                p.gui.EscreveLog("Rejeitado, notificando " + p.portaConvidante);
                Evento rej2 = new Evento(Meio.REJEITAR, "rejeitar", "no", null);
                ent.msg.conecta("localhost", p.portaConvidante);
                ent.msg.envia(rej2.toString());
                ent.msg.termina();
                p.portaConvidante = -1;
                p.portaConvidado  = -1;
                break;

            case Meio.MSG:
                // roteia para o outro lado
                int destino = (Integer.parseInt(_ev.C1) == p.portaConvidante)
                        ? p.portaConvidado : p.portaConvidante;
                p.gui.EscreveLog("Mensagem roteada para " + destino);
                Evento msg = new Evento(Meio.MSG, "msg", _ev.C2, null);
                ent.msg.conecta("localhost", destino);
                ent.msg.envia(msg.toString());
                ent.msg.termina();
                break;

            case Meio.DESCONECTAR:
                int outro = (Integer.parseInt(_ev.C1) == p.portaConvidante)
                        ? p.portaConvidado : p.portaConvidante;
                p.gui.EscreveLog("Desconexão de " + _ev.C1 + ", notificando " + outro);
                Evento desc = new Evento(Meio.DESCONECTAR, "desconectar", "bye", null);
                ent.msg.conecta("localhost", outro);
                ent.msg.envia(desc.toString());
                ent.msg.termina();
                p.portaConvidante = -1;
                p.portaConvidado  = -1;
                break;

            default:// evento inesperado
                ((Meio)ent).gui.EscreveLog("MEIO descartou evento : "+_ev.code + " em IDLE");
                //System.out.println("MEIO descartou evento : "+_ev.code + " em IDLE");
        }
    }
}

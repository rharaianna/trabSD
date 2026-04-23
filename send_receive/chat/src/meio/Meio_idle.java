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
        ((Meio)ent).gui.EscreveLog("Probabilidade de perda: 20%");
    }
    @Override
    public void transicao(Evento _ev){
        switch(_ev.code){
            case Meio.ENVIA:
                // guarda msg
                ((Meio)ent).ms = _ev.C2;
                // decide se perde ou se entrega
                if ((1 + (int)(Math.random() * 100)) > 20){
                    // Evento de Saida ENTREGA (entrega msg para RECEIVER)
                    Evento e = new Evento(Meio.ENTREGA,"entrega",((Meio)ent).ms,null);
                        ent.msg.conecta("localhost", ((Meio)ent).portaUsuario2);
                        ent.msg.envia(e.toString());
                        ent.msg.termina();
                    }
                    else{
                        ((Meio)ent).gui.EscreveLog("Perdeu msg");
                        // Evento de saída PERDA2 (perde a msg)
                        ((Meio)ent).ms="";
                    }
                break;
            case Meio.RESPONDE:
                // decide se perde ou se confirma
                if ((1 + (int)(Math.random() * 100)) > 10){
                    // Evento de saida CONFIRMA (entrega ack para SENDER)                                  
                    Evento e = new Evento(Meio.CONFIRMA,"confirma","ack",null);
                    ent.msg.conecta("localhost", ((Meio)ent).portaUsuario1);
                    ent.msg.envia(e.toString());
                    ent.msg.termina();
                }
                else{
                    ((Meio)ent).gui.EscreveLog("Perdeu ack");
                    // Evento de Saida PERDA1 (nao entrega o ack para sender)
                }
                break;
            case Meio.CONVITE:
                Evento e = new Evento(Meio.CONVITE, "convite", _ev.C2, null);
                ent.msg.conecta("localhost", ((Meio)ent).portaUsuario2);
                ent.msg.envia(e.toString());
                ent.msg.termina();
                break;
            case Meio.ACEITAR:
                Evento e1 = new Evento(Meio.ACEITAR, "aceitar", "ok", null);
                ent.msg.conecta("localhost", ((Meio)ent).portaUsuario1);
                ent.msg.envia(e1.toString());
                ent.msg.termina();
                break;

            case Meio.REJEITAR:
                Evento e2 = new Evento(Meio.REJEITAR, "rejeitar", "no", null);
                ent.msg.conecta("localhost", ((Meio)ent).portaUsuario1);
                ent.msg.envia(e2.toString());
                ent.msg.termina();
                break;
            default:// evento inesperado
                ((Meio)ent).gui.EscreveLog("MEIO descartou evento : "+_ev.code + " em IDLE");
                //System.out.println("MEIO descartou evento : "+_ev.code + " em IDLE");
        }
    }
}

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
                p.portaConvidante = Integer.parseInt(_ev.C1); // porta real do User
                p.portaConvidante = Integer.parseInt(_ev.C3);//Origem
                p.portaConvidado  = Integer.parseInt(_ev.C2); // destino informado pelo user

                p.gui.EscreveLog("Convite de porta " + p.portaConvidante + " para " + p.portaConvidado);
                Evento fwd = new Evento(Meio.CONVITE, "convite", _ev.C1, null);

                ent.msg.conecta("localhost", p.portaConvidado);
                ent.msg.envia(fwd.toString());
                ent.msg.termina();
                break;

            default:// evento inesperado
                ((Meio)ent).gui.EscreveLog("MEIO descartou evento : "+_ev.code + " em IDLE");
                //System.out.println("MEIO descartou evento : "+_ev.code + " em IDLE");
        }
    }
}

/*
 * Template:
 * Estado IDLE da entidade RECEIVER
 */
package receiver;

import framework.Entidade;
import framework.Estado;
import framework.Evento;
import meio.Meio;

public class Receiver_idle extends Estado{
    public Receiver_idle (Entidade _e){
        super(_e);   
    }
    @Override
    public void transicao(Evento _ev){
        switch(_ev.code){
            case Meio.ENTREGA: // Evento ENTREGA
                // guarda msg
                ((Receiver)ent).ms = _ev.C2;
                // Evento de saida RECEBE
                    // entrega msg para o USUARIO
                    ((Receiver)ent).g.EscreveUi("> "+_ev.C2);
                // System.out.print("Mensagem: "+_ev.C2+"\n");
                // Evento de saida RESPONDE (entrega ack para o MEIO)
                    Evento e = new Evento(Meio.RESPONDE,"responde","ack",null);
                    ent.msg.conecta("localhost", ((Receiver)ent).m); 
                    ent.msg.envia(e.toString());
                    ent.msg.termina();
                break;
            default: // evento inesperado
                ((Receiver)ent).g.EscreveLog("RECEIVER descartou evento : "+_ev.code + " em IDLE");
                //System.out.println("RECEIVER descartou evento : "+_ev.code + " em IDLE");
        }
    }
}

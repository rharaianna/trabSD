/*
 * Template:
 * Estado SENDING da entidade SENDER
 */
package sender;

import framework.Entidade;
import framework.Estado;
import framework.Evento;
import meio.Meio;

public class Sender_sending extends Estado{
    public Sender_sending(Entidade _e){
        super(_e);
    }
    @Override
    public void transicao(Evento _ev){
        switch(_ev.code){ 
            case Meio.TIMEOUT: // Evento TIMEOUT
                // Informa Timeout
                ((Sender)ent).g.EscreveLog("Timeout");
                //System.out.println("Timeout");
                ent.mudaEstado(this);
               break;
            case Meio.CONFIRMA: // Evento CONFIRMA
                 // Para timer
                 ((Sender)ent).t1.paraTimer();                    
                 // Muda estado para IDLE
                  ((Sender)ent).g.defEstado("IDLE");
                 ent.mudaEstado(((Sender)ent)._idle);
                break;
            default:
               ((Sender)ent).g.EscreveLog("SENDER descartou evento : "+_ev.code + " em SENDING");
               // System.out.println("SENDER descartou evento : "+_ev.code + " em SENDING");
        }
    }
    @Override
    public void acao(){
        // Evento de saida ENVIA (mensagem para o meio)
        Evento e = new Evento(Meio.ENVIA,"envia",((Sender)ent).ms,null);
        ent.msg.conecta("localhost", ((Sender)ent).m); 
        ent.msg.envia(e.toString());
        ent.msg.termina();
        // Dispara o timer
        ((Sender)ent).thread3 = new Thread(((Sender)ent).t1);
        ((Sender)ent).thread3.start();  
    }
}

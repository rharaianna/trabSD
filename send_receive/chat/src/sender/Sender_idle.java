/*
 * Template:
 * Estado IDLE da entidade SENDER
 */
package sender;

import framework.Entidade;
import framework.Estado;
import framework.Evento;
import meio.Meio;

public class Sender_idle extends Estado{
    public Sender_idle (Entidade _e){
        super(_e);   
    }
    @Override
    public void transicao(Evento _ev){
        switch(_ev.code){ 
            case Meio.MSG:
                // muda estado para SENDING
                ((Sender)ent).g.defEstado("WAITING");
                ent.mudaEstado(((Sender)ent)._sending);
                break;                
            default: // evento inesperado
                //System.out.println("SENDER descartou evento : "+_ev.code + " em IDLE");
                ((Sender)ent).g.EscreveLog("SENDER descartou evento : "+_ev.code + " em IDLE");
        }
    }
    @Override
    public void acao(){
        ((Sender)ent).g.trocaEnvio();
        // Le dado do usuário 
        //((Sender)ent).ms = ((Sender)ent).le();
        // gera evento MSG
        //ent.colocaEvento(new Evento(Meio.MSG,"msg",((Sender)ent).ms,null));   
    }
}

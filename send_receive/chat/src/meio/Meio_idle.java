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
        ((Meio)ent).g.EscreveLog("Probabilidade de perda: 20%");
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
                        ent.msg.conecta("localhost", ((Meio)ent).r); 
                        ent.msg.envia(e.toString());
                        ent.msg.termina();
                    }
                    else{
                        ((Meio)ent).g.EscreveLog("Perdeu msg");
                        // Evento de saída PERDA2 (perde a msg)
                        ((Meio)ent).ms="";
                    }
                break;
            case Meio.RESPONDE:
                // decide se perde ou se confirma
                if ((1 + (int)(Math.random() * 100)) > 10){
                    // Evento de saida CONFIRMA (entrega ack para SENDER)                                  
                    Evento e = new Evento(Meio.CONFIRMA,"confirma","ack",null);
                    ent.msg.conecta("localhost", ((Meio)ent).s); 
                    ent.msg.envia(e.toString());
                    ent.msg.termina();
                }
                else{
                    ((Meio)ent).g.EscreveLog("Perdeu ack");
                    // Evento de Saida PERDA1 (nao entrega o ack para sender)
                }
                break;
            default:// evento inesperado
                ((Meio)ent).g.EscreveLog("MEIO descartou evento : "+_ev.code + " em IDLE");
                //System.out.println("MEIO descartou evento : "+_ev.code + " em IDLE");
        }
    }
}

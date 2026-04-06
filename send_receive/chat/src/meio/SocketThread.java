/*
 * Sub-classe para desempacotar PDUs destinadas 
 * a entidade MEIO.
 */
package meio;

import framework.Entidade;
import framework.Evento;
import framework.Msg;

public class SocketThread extends framework.SocketThread {
    String n;
    String m;
    int code;
    public SocketThread(Msg _m, Entidade _u){
        super(_m, _u);
    }
    @Override
    public void desempacota(){
                    String [] split;
                    // Desempacota mensagem
                    split=tmp.split(",");
                    code= Integer.valueOf(split[0]);
                    n=split[1];
                    m=split[2];
                   // Cria Evento
                    Evento e = new Evento(code, n, m,null);
                   // Coloca no buffer da entidade
                   ent.colocaEvento(e); 
    }
}

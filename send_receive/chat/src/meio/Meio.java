/*
 * Template:
 * Essa classe implementa as características específicas
 * da entidade de protocolo MEIO
 */
package meio;
import Code.Gui;
import framework.Entidade;
import framework.Estado;

/**
 * @author Ciro
 */
public class Meio extends Entidade{
 public String ms;  
    // informacoes especificas do protocolo
    public int s;
    public int r;
    public Gui g;
    // definicao dos codigos das PDUs
    public static final int MSG  = 1;
    public static final int ENVIA  = 2;
    public static final int TIMEOUT = 3;
    public static final int CONFIRMA = 4;
    public static final int RESPONDE = 5;
    public static final int PERDE1 = 6;
    public static final int PERDE2 = 7;
    public static final int ENTREGA = 8;
    public static final int RECEBE = 9;
    // estados desse protocolo
    public Estado _idle;
    // thread para tratamento de socket
    framework.SocketThread xthread; 
    Thread thread2;
   
    public Meio(int _pl, int _ps, int _pr){
        super();
        // cria interface
        g = new Gui(this);        
        // inicializa informacoes do protocolo
        s=_ps;
        r=_pr;
        // inicializacao dos estados
        _idle = new Meio_idle(this);
        // definicao do estado inicial
        g.defEstado("IDLE");
        mudaEstado(_idle);
     
        this.defPortaLocal(_pl);
        // Inicia thread de leitura do socket
        xthread = new SocketThread(msg,this);
        thread2 = new Thread(xthread);
        thread2.start();
    
        g.EscreveLog("IP local: "+ msg.pegaHostLocal());
        g.EscreveLog("Porta local: "+ String.valueOf(_pl));
    }
    
    public static void main(String args[]) {
        Meio m = new Meio(7000,7001,7002); // porta local, porta do sender, porta do receiver
        m.g.EscreveLog("Entidade de protocolo inicializada");
    }
      
}

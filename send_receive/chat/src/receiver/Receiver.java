/*
 * Template:
 * Essa classe implementa as características específicas
 * da entidade de protocolo RECEIVER
 */
package receiver;
import Code.Gui;
import framework.Entidade;
import framework.Estado;

public class Receiver extends Entidade{
    
    // informacoes especificas do protocolo
    public String ms; // apontador para a entidade meio
    public int m;
    // estados dessa entidade de protocolo
    public Estado _idle;
    // thread para tratamento de socket
    framework.SocketThread xthread; 
    Thread thread2;
    public Gui g;
    
    public Receiver(int _pl, int _pr){
        super();
        // cria interface
        g = new Gui(this);  
        // define porta local
        defPortaLocal(_pl);
        g.EscreveLog("IP local: "+ msg.pegaHostLocal());
        g.EscreveLog("Porta local: "+ String.valueOf(_pl)); 
        
        m=_pr;
        g.defEstado("IDLE");
        _idle = new Receiver_idle(this);
        mudaEstado(_idle);
        // Inicia thread de leitura do socket
        xthread = new meio.SocketThread(msg,this);
        thread2 = new Thread(xthread);
        thread2.start();
    }
    
    public static void main(String args[]) {
        Receiver r = new Receiver(7002,7000); // porta local, porta do meio
        r.g.EscreveLog("Entidade de protocolo inicializada");
        //System.out.println("Entidade RECEIVER inicializada");
    }
}

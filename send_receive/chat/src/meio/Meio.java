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
    public int portaUsuario1;
    public int portaUsuario2;
    public Gui gui;
    // definicao dos codigos das PDUs
    public static final int MSG  = 1;
    public static final int ENVIA  = 2;
    public static final int TIMEOUT = 3;
    public static final int CONFIRMA = 4;
    public static final int RESPONDE = 5;
    public static final int ENTREGA = 8;

    public static final int CONVITE = 9;
    public static final int ACEITAR = 10;
    public static final int REJEITAR = 11;
    public static final int OCUPADO = 12;
    public static final int DESCONECTAR = 12;

    // estados desse protocolo
    public Estado _idle;
    // thread para tratamento de socket
    framework.SocketThread xthread; 
    Thread thread2;
   
    public Meio(int _portaLocal, int _portaUsuario1, int _portaUsuario2){
        super();
        // cria interface
        gui = new Gui(this);
        gui.desabilitaBotoesConexao();
        // inicializa informacoes do protocolo
        portaUsuario1= _portaUsuario1;
        portaUsuario2 = _portaUsuario2;

        // inicializacao dos estados
        _idle = new Meio_idle(this);
        // definicao do estado inicial
        gui.defEstado("IDLE");
        mudaEstado(_idle);
     
        this.defPortaLocal(_portaLocal);
        // Inicia thread de leitura do socket
        xthread = new SocketThread(msg,this);
        thread2 = new Thread(xthread);
        thread2.start();
    
        gui.EscreveLog("IP local: "+ msg.pegaHostLocal());
        gui.EscreveLog("Porta local: "+ String.valueOf(_portaLocal));
    }
    
    public static void main(String args[]) {
        Meio meio = new Meio(7000,7001,7002);
        meio.gui.EscreveLog("Entidade de protocolo inicializada");
    }
      
}

package user;

import Code.Gui;
import framework.Entidade;
import framework.Evento;
import framework.Timeout;
import meio.Meio;


public class User extends Entidade{
    public String ms;
    public int m;
    public int portaLocal;
    public int portaConvidante = -1; // guarda quem me convidou
    public Gui gui;

    User_ocioso _ocioso;
    User_aguarda_conexao _aguarda_conexao;
    User_recebe_pedido _recebe_pedido;
    User_conectado _conectado;

    Timeout t1;
    // thread para tratamento do timeout
    Thread thread3;
    // thread para tratamento de socket
    framework.SocketThread xthread;
    Thread thread2;


    public User(int _portaLocal, int _portaMeio){
        super();

        //Define porta no socket
        this.portaLocal = _portaLocal;
        this.defPortaLocal(_portaLocal);
        // cria interface
        gui = new Gui(this);

        // inicializa objeto timeout
        t1 = new Timeout(this, 10000);
        m= _portaMeio;


        // inicializacao dos estados
        _ocioso = new User_ocioso(this);;
        _aguarda_conexao = new User_aguarda_conexao(this);
        _recebe_pedido   = new User_recebe_pedido(this);
        _conectado = new User_conectado(this);

        // definicao do estado inicial
        gui.defEstado("OCIOSO");
        mudaEstado(_ocioso);

        // Inicia thread de leitura do socket
        xthread = new meio.SocketThread(msg,this);
        thread2 = new Thread(xthread);
        thread2.start();

        gui.EscreveLog("IP local: "+ msg.pegaHostLocal());
        gui.EscreveLog("Porta local: "+ String.valueOf(_portaLocal));
        gui.EscreveLog("Entidade de protocolo inicializada");
    }


    public static void main(String args[]) {
        User user1 = new User(7001, 7000);
        User user2 = new User(7002, 7000);
        User user3 = new User(7003, 7000);

    }
}

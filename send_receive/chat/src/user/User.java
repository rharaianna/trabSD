package user;

import Code.Gui;
import framework.Entidade;
import framework.Estado;
import framework.Timeout;
import sender.Sender;
import sender.Sender_idle;
import sender.Sender_sending;


public class User extends Entidade{
    public String ms;
    public int m;
    public Gui g;

    User_ocioso _ocioso;
    User_aguarda_conexao _aguarda_conexao;
    User_conectado _conectado;

    Timeout t1;
    // thread para tratamento do timeout
    Thread thread3;
    // thread para tratamento de socket
    framework.SocketThread xthread;
    Thread thread2;


    public User(int _lp, int _rp){
        super();
        // cria interface
        g = new Gui(this);

        // inicializa objeto timeout
        t1 = new Timeout(this, 10000);
        m=_rp;


        // inicializacao dos estados
        _ocioso = new User_ocioso(this);;
        _aguarda_conexao = new User_aguarda_conexao(this);
        _conectado = new User_conectado(this);

        // definicao do estado inicial
        g.defEstado("OCIOSO");
        mudaEstado(_ocioso);

        // Inicia thread de leitura do socket
        xthread = new meio.SocketThread(msg,this);
        thread2 = new Thread(xthread);
        thread2.start();
    }

    public static void main(String args[]) {
        User u = new User(7001,7000); // porta local, porta do meio
        u.g.EscreveLog("Timeout 10 segundos");
        u.g.EscreveLog("Entidade de protocolo inicializada");
        //System.out.println("Entidade SENDER inicializada");
    }
}

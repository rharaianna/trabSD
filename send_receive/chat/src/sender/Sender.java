/*
 * Template:
 * Essa classe implementa as características específicas
 * da entidade de protocolo SENDER
 */
package sender;

import Code.Gui;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import framework.Entidade;
import framework.Estado;
import framework.Timeout;

/**
 * @author Ciro Barbosa
 */
public class Sender extends Entidade{
    // informacoes especificas do protocolo
    public String ms;  
    public int m;
    public Gui g;
    // estados desse protocolo
    public Estado _idle;
    public Estado _sending;
    // objeto timer
    Timeout t1;
    // thread para tratamento do timeout
    Thread thread3;
    // thread para tratamento de socket
    framework.SocketThread xthread; 
    Thread thread2;
    
    public Sender(int _lp, int _rp){
        super();
        // cria interface
        g = new Gui(this);  
        // define porta local
        this.defPortaLocal(_lp);
        g.EscreveLog("IP local: "+ msg.pegaHostLocal());
        g.EscreveLog("Porta local: "+ String.valueOf(_lp)); 
  
        // inicializa objeto timeout
        t1 = new Timeout(this, 10000);
        m=_rp;       
        // inicializacao dos estados
        _idle = new Sender_idle(this);
        _sending = new Sender_sending(this);       

        // definicao do estado inicial
        g.defEstado("IDLE");
        mudaEstado(_idle);

        // Inicia thread de leitura do socket
        xthread = new meio.SocketThread(msg,this);
        thread2 = new Thread(xthread);
        thread2.start();      
    }
    
    public static void main(String args[]) {
        Sender s = new Sender(7001,7000);
        Sender s1 = new Sender(7002,7000);
        Sender s2 = new Sender(7003,7000); // porta local, porta do meio
        s.g.EscreveLog("Timeout 10 segundos");
        s.g.EscreveLog("Entidade de protocolo inicializada");
        //System.out.println("Entidade SENDER inicializada");
    } 
    
    public String le(){
        String aux;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
        g.EscreveUi("Informe a menssagem!");
        g.trocaEnvio();
        //System.out.print("Informe a menssagem: ");
        
        try {
            aux = in.readLine();
            return aux;
        } catch (IOException ex) {
            Logger.getLogger(Sender_idle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    } 
}

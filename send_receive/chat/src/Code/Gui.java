package Code;

import framework.Entidade;
import framework.Evento;
import meio.Meio;
import user.User;

public class Gui extends javax.swing.JFrame {

    Entidade ent;

    public Gui(Entidade _ent) {
        initComponents();
        ent = _ent;
        defCabecalho(ent.getClass().getName());
        java.awt.EventQueue.invokeLater(() -> setVisible(true));
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        lblEntidade = new javax.swing.JLabel("Entidade:");
        txtEntidade = new javax.swing.JTextField();
        lblEstado   = new javax.swing.JLabel("Estado:");
        txtEstado   = new javax.swing.JTextField();
        scrollLog   = new javax.swing.JScrollPane();
        txtLog      = new javax.swing.JTextArea();
        btnSair     = new javax.swing.JButton("Desconectar");
        lblPorta    = new javax.swing.JLabel("Porta destino:");
        txtPorta    = new javax.swing.JTextField();
        btnConvidar = new javax.swing.JButton("Conectar");
        btnAceitar  = new javax.swing.JButton("Aceitar");
        btnRejeitar = new javax.swing.JButton("Rejeitar");
        txtMsg      = new javax.swing.JTextField();
        btnEnviar   = new javax.swing.JButton("Enviar");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Entidade de Protocolo");
        setSize(420, 420);
        getContentPane().setLayout(null);

        lblEntidade.setBounds(10, 10, 80, 25);
        txtEntidade.setBounds(95, 10, 300, 25);
        txtEntidade.setEditable(false);

        lblEstado.setBounds(10, 45, 80, 25);
        txtEstado.setBounds(95, 45, 300, 25);
        txtEstado.setEditable(false);

        txtLog.setEditable(false);
        txtLog.setLineWrap(true);
        scrollLog.setViewportView(txtLog);
        scrollLog.setBounds(10, 80, 385, 220);

        lblPorta.setBounds(10, 315, 100, 25);
        txtPorta.setBounds(115, 315, 160, 25);
        btnConvidar.setBounds(285, 315, 110, 25);

        btnAceitar.setBounds(100, 315, 110, 25);
        btnRejeitar.setBounds(225, 315, 110, 25);

        txtMsg.setBounds(10, 315, 270, 25);
        btnEnviar.setBounds(290, 315, 105, 25);

        btnSair.setBounds(150, 355, 120, 25);

        getContentPane().add(lblEntidade);
        getContentPane().add(txtEntidade);
        getContentPane().add(lblEstado);
        getContentPane().add(txtEstado);
        getContentPane().add(scrollLog);
        getContentPane().add(lblPorta);
        getContentPane().add(txtPorta);
        getContentPane().add(btnConvidar);
        getContentPane().add(btnAceitar);
        getContentPane().add(btnRejeitar);
        getContentPane().add(txtMsg);
        getContentPane().add(btnEnviar);
        getContentPane().add(btnSair);

        btnSair.addActionListener(e -> {
            User p = (User) ent;
            // Criamos um evento de desconexão enviando nossa porta local
            // C2 enviamos "local" para saber que fomos nós que clicamos
            Evento ev = new Evento(Meio.DESCONECTAR, String.valueOf(p.portaLocal), "local", null);

            // Dispara para a FSM processar o envio da PDU de rede
            p.transicao(ev);
        });

        btnConvidar.addActionListener(e -> {
            String portaDestino = txtPorta.getText().trim();
            if (!portaDestino.isEmpty()) {
                String portaLocal = String.valueOf(((User) ent).portaLocal);
                ent.colocaEvento(new Evento(Meio.CONVITE, portaLocal, portaDestino, null));
            }
            txtPorta.setText("");
        });

        btnAceitar.addActionListener(e ->
                ent.colocaEvento(new Evento(Meio.ACEITAR, "aceitar", "ok", null))
        );

        btnRejeitar.addActionListener(e ->
                ent.colocaEvento(new Evento(Meio.REJEITAR, "rejeitar", "no", null))
        );

        btnEnviar.addActionListener(e -> {
            String texto = txtMsg.getText().trim();
            if (!texto.isEmpty()) {
                // C1: Enviamos a PORTA LOCAL deste usuário (ex: "7001")
                // Isso permite que o Meio identifique quem é o remetente.
                User p = (User) ent;
                Evento evento = new Evento(Meio.ENVIA, String.valueOf(p.portaLocal), texto, null);

                // Dispara para a máquina de estados processar
                p.transicao(evento);

                // Limpa o campo de texto
                txtMsg.setText("");
            }
        });

        modoMeio();
    }
    // </editor-fold>//GEN-END:initComponents

    public void modoMeio() {
        lblPorta.setVisible(false);
        txtPorta.setVisible(false);
        btnConvidar.setVisible(false);
        btnAceitar.setVisible(false);
        btnRejeitar.setVisible(false);
        txtMsg.setVisible(false);
        btnEnviar.setVisible(false);
    }

    public void modoOcioso() {
        lblPorta.setVisible(true);
        txtPorta.setVisible(true);
        btnConvidar.setVisible(true);
        btnAceitar.setVisible(false);
        btnRejeitar.setVisible(false);
        txtMsg.setVisible(false);
        btnEnviar.setVisible(false);
        btnSair.setVisible(false);
    }

    public void modoAguardando() {
        lblPorta.setVisible(false);
        txtPorta.setVisible(false);
        btnConvidar.setVisible(false);
        btnAceitar.setVisible(false);
        btnRejeitar.setVisible(false);
        txtMsg.setVisible(false);
        btnEnviar.setVisible(false);
    }

    public void modoRecebePedido() {
        lblPorta.setVisible(false);
        txtPorta.setVisible(false);
        btnConvidar.setVisible(false);
        btnAceitar.setVisible(true);
        btnRejeitar.setVisible(true);
        txtMsg.setVisible(false);
        btnEnviar.setVisible(false);
    }

    public void modoConectado() {
        lblPorta.setVisible(false);
        txtPorta.setVisible(false);
        btnConvidar.setVisible(false);
        btnAceitar.setVisible(false);
        btnRejeitar.setVisible(false);
        txtMsg.setVisible(true);
        btnEnviar.setVisible(true);
        btnSair.setVisible(true);
    }

    public void EscreveLog(String msg) {
        txtLog.append(" " + msg + "\n");
    }

    public void defEstado(String msg) {
        txtEstado.setText(" " + msg);
    }

    public void defCabecalho(String msg) {
        txtEntidade.setText(" " + msg);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblEntidade;
    private javax.swing.JTextField txtEntidade;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JScrollPane scrollLog;
    private javax.swing.JTextArea txtLog;
    private javax.swing.JButton btnSair;
    private javax.swing.JLabel lblPorta;
    private javax.swing.JTextField txtPorta;
    private javax.swing.JButton btnConvidar;
    private javax.swing.JButton btnAceitar;
    private javax.swing.JButton btnRejeitar;
    private javax.swing.JTextField txtMsg;
    private javax.swing.JButton btnEnviar;
    // End of variables declaration//GEN-END:variables
}
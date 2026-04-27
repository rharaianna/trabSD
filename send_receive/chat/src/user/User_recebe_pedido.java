package user;

import framework.Entidade;
import framework.Estado;
import framework.Evento;
import meio.Meio;

public class User_recebe_pedido extends Estado {
    public User_recebe_pedido(Entidade _e) { super(_e); }

    @Override
    public void transicao(Evento _evento) {
        User p = (User) ent;
        switch (_evento.code) {
            case Meio.ACEITAR:
                // usuário clicou Aceitar na GUI
                p.gui.EscreveLog("Conexão aceita.");
                Evento ace = new Evento(Meio.ACEITAR, String.valueOf(p.portaLocal), "ok", null);
                p.msg.conecta("localhost", p.m);
                p.msg.envia(ace.toString());
                p.msg.termina();
                p.mudaEstado(p._conectado);
                break;

            case Meio.REJEITAR:
                // usuário clicou Rejeitar na GUI
                p.gui.EscreveLog("Conexão rejeitada.");
                Evento rej = new Evento(Meio.REJEITAR, String.valueOf(p.portaLocal), "no", null);
                p.msg.conecta("localhost", p.m);
                p.msg.envia(rej.toString());
                p.msg.termina();
                p.portaConvidante = -1;
                p.mudaEstado(p._ocioso);
                break;

            default:
                p.gui.EscreveLog("Evento descartado em RECEBE_PEDIDO: " + _evento.code);
        }
    }

    @Override
    public void acao() {
        User p = (User) ent;
        p.gui.modoRecebePedido();
        p.gui.defEstado("RECEBE PEDIDO");
    }
}
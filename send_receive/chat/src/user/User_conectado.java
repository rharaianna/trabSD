package user;

import framework.Entidade;
import framework.Estado;
import framework.Evento;
import meio.Meio;

public class User_conectado extends Estado {
    public User_conectado(Entidade _e) {
        super(_e);
    }

    @Override
    public void transicao(Evento _evento) {
        User p = (User) ent;
        switch (_evento.code) {
            case Meio.MSG:
                p.gui.EscreveLog("Mensagem recebida: " + _evento.C2);
                break;
            case Meio.DESCONECTAR:
                p.gui.EscreveLog("Outro usuário desconectou.");
                p.mudaEstado(p._ocioso);
                break;
            default:
                p.gui.EscreveLog("Evento descartado em CONECTADO: " + _evento.code);
        }
    }

    @Override
    public void acao() {
        User p = (User) ent;
        p.gui.modoConectado();
        p.gui.defEstado("CONECTADO");
    }
}

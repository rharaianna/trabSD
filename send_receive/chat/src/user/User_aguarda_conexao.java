package user;

import framework.Entidade;
import framework.Estado;
import framework.Evento;
import meio.Meio;

public class User_aguarda_conexao extends Estado {
    public User_aguarda_conexao(Entidade _e) {
        super(_e);
    }

    @Override
    public void transicao(Evento _evento) {
        User p = (User) ent;
        switch (_evento.code) {
            case Meio.ACEITAR:
                p.gui.EscreveLog("Convite aceito! Conectado.");
                p.mudaEstado(p._conectado);
                break;
            case Meio.REJEITAR:
                p.gui.EscreveLog("Convite rejeitado.");
                p.mudaEstado(p._ocioso);
                break;
            case Meio.CONVITE:
                p.gui.EscreveLog("Ocupado, recusando convite extra.");
                // C1 = Minha porta (quem está rejeitando)
                Evento convRej = new Evento(Meio.REJEITAR, String.valueOf(p.portaLocal), "ocupado", null);
                p.msg.conecta("localhost", p.m);
                p.msg.envia(convRej.toString());
                p.msg.termina();
                // permanece no estado atual
                break;

            default:
                p.gui.EscreveLog("Evento descartado em AGUARDA: " + _evento.code);
        }
    }

    @Override
    public void acao() {
        ((User) ent).gui.modoAguardando();
        ((User) ent).gui.defEstado("AGUARDA CONEXÃO");
    }
}

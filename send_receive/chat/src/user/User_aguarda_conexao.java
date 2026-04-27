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
                // chegou convite de outro user enquanto aguarda — rejeita automaticamente
                p.gui.EscreveLog("Ocupado, convite ignorado de: " + _evento.C2);
                Evento rej = new Evento(Meio.REJEITAR, String.valueOf(p.portaLocal), _evento.C2, null);
                p.msg.conecta("localhost", p.m);
                p.msg.envia(rej.toString());
                p.msg.termina();
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

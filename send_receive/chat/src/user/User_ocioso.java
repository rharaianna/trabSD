package user;

import framework.Entidade;
import framework.Estado;
import framework.Evento;
import meio.Meio;

public class User_ocioso extends Estado {
    public User_ocioso(Entidade _e) {
        super(_e);
    }

    @Override
    public void transicao(Evento _evento) {
        User p = (User) ent;

        switch (_evento.code) {
            case Meio.CONVITE:
                if (_evento.C1.equals(String.valueOf(p.portaLocal))) {
                    // evento LOCAL — fui eu que cliquei em conectar
                    p.gui.EscreveLog("Enviando convite para: " + _evento.C2);
                    p.msg.conecta("localhost", p.m);
                    p.msg.envia(_evento.toString());
                    p.msg.termina();
                    p.mudaEstado(p._aguarda_conexao);
                } else {
                    // evento de REDE — recebi convite de alguém
                    // Se o Meio enviou o fwd com _ev.C1, aqui ele será recebido em _evento.C2 por causa do construtor.
                    p.portaConvidante = Integer.parseInt(_evento.C2);
                    p.gui.EscreveLog("Convite recebido de: " + p.portaConvidante);
                    p.mudaEstado(p._recebe_pedido);
                }
                break;
            default:
                // Lógica: Mostra na GUI botões de Aceitar/Recusar
                p.gui.EscreveLog("Recebeu convite de: " + _evento.C2);
                break;
        }

    }

    @Override
    public void acao() {
        ((User) ent).gui.modoOcioso();
        ((User) ent).gui.defEstado("OCIOSO");
    }
}

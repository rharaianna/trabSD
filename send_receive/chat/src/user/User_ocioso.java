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

        switch (_evento.code) {
            case Meio.CONVITE:
                // Lógica: Mostra na GUI botões de Aceitar/Recusar
                ((User) ent).gui.EscreveLog("Recebeu convite de: " + _evento.C2);
                break;

            case Meio.ACEITAR:
                // U1 recebe isso e muda para o estado CONECTADO
                ent.mudaEstado(((User) ent)._conectado);
                break;
            case Meio.REJEITAR:
                ((User) ent).gui.EscreveLog("Convite rejeitado!");
                break;

        }

    }

    @Override
    public void acao() {
        ((User) ent).gui.trocaEnvio();
    }
}

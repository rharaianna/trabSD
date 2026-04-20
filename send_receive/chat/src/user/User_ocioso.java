package user;

import framework.Entidade;
import framework.Estado;
import framework.Evento;
import sender.Sender;

public class User_ocioso extends Estado {
    public User_ocioso(Entidade _e) {
        super(_e);
    }

    @Override
    public void transicao(Evento _e) {
        super.transicao(_e);
    }

    @Override
    public void acao() {
        ((Sender)ent).g.trocaEnvio();
    }
}

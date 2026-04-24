package user;

import framework.Entidade;
import framework.Estado;
import framework.Evento;

public class User_aguarda_conexao extends Estado {
    public User_aguarda_conexao(Entidade _e) {
        super(_e);
    }

    @Override
    public void transicao(Evento _e) {
        super.transicao(_e);
    }

    @Override
    public void acao() {
        ((User) ent).gui.modoAguardando();
        ((User) ent).gui.defEstado("AGUARDA CONEXÃO");
    }
}

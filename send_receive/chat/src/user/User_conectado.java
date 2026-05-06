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
                if ("local".equals(_evento.C2)) {
                    // Eu cliquei no botão, então eu aviso o Meio
                    p.msg.conecta("localhost", p.m);
                    p.msg.envia(_evento.toString());
                    p.msg.termina();
                    p.gui.EscreveLog("Você saiu da conversa.");
                } else {
                    // Eu recebi o evento vindo do Meio, ou seja, o OUTRO saiu
                    p.gui.EscreveLog("O outro usuário encerrou a conexão.");
                }
                p.mudaEstado(p._ocioso);
                break;
            case Meio.ENVIA:
                // 1. Criar o evento formatado para o Meio (usando o código MSG = 1)
                Evento paraMeio = new Evento(Meio.MSG, _evento.C1, _evento.C2, null);

                // 2. Conectar no Meio (p.m que é a porta 7000) e enviar
                p.msg.conecta("localhost", p.m);
                p.msg.envia(paraMeio.toString());
                p.msg.termina();

                // 3. Mostrar no seu próprio log para você ver o que escreveu
                p.gui.EscreveLog("Você: " + _evento.C2);
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

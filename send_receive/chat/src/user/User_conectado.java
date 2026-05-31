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
                    // Força o envio da sua própria porta no C1 para o Meio saber quem está saindo
                    Evento descParaMeio = new Evento(Meio.DESCONECTAR, String.valueOf(p.portaLocal), "local", null);
                    p.msg.conecta("localhost", p.m);
                    p.msg.envia(descParaMeio.toString());
                    p.msg.termina();
                    p.gui.EscreveLog("Você saiu da conversa.");
                } else {
                    p.gui.EscreveLog("O outro usuário encerrou a conexão.");
                }
                // IMPORTANTE: Limpar variáveis de estado interno para nova conexão
                p.portaConvidante = -1;
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

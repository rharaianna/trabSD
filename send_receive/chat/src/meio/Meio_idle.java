/*
 * Estado IDLE da entidade MEIO
 *
 * Usa dois mapas:
 *   pendentes: convidado → convidante  (enquanto aguarda ACEITAR/REJEITAR)
 *   sessoes:   porta     → parceiro    (sessão confirmada, bidirecional)
 *
 * Isso evita sobrescrita quando um mesmo User recebe múltiplos convites
 * simultâneos — cada convite fica em sua própria entrada de pendentes.
 */
package meio;

import framework.Entidade;
import framework.Estado;
import framework.Evento;
import java.util.HashMap;
import java.util.Map;

public class Meio_idle extends Estado {

    // convites aguardando decisão: porta do convidado → porta do convidante
    private final Map<Integer,Integer> pendentes = new HashMap<>();
    // sessões ativas confirmadas (bidirecional): porta → porta do parceiro
    private final Map<Integer,Integer> sessoes   = new HashMap<>();

    public Meio_idle(Entidade _e) {
        super(_e);
        ((Meio) ent).gui.EscreveLog("Meio Pronto");
    }

    @Override
    public void transicao(Evento _ev) {
        Meio p = (Meio) ent;

        switch (_ev.code) {

            case Meio.CONVITE:
                // Apenas encaminha — registra como pendente (não sobrescreve sessão)
                int convidante = Integer.parseInt(_ev.C1);
                int convidado  = Integer.parseInt(_ev.C2);
                pendentes.put(convidado, convidante);   // convidado → quem chamou
                p.gui.EscreveLog("Convite pendente de " + convidante + " para " + convidado);
                Evento fwd = new Evento(Meio.CONVITE, "convite", _ev.C1, null);
                ent.msg.conecta("localhost", convidado);
                ent.msg.envia(fwd.toString());
                ent.msg.termina();
                break;

            case Meio.ACEITAR:
                // Promove o pendente para sessão ativa
                int aceitou    = Integer.parseInt(_ev.C1);
                int quemChamou = pendentes.remove(aceitou);   // remove do provisório
                if (quemChamou == 0) {
                    p.gui.EscreveLog("ACEITAR sem pendente de " + aceitou);
                    break;
                }
                sessoes.put(aceitou,    quemChamou);   // bidirecional
                sessoes.put(quemChamou, aceitou);
                p.gui.EscreveLog("Sessão estabelecida: " + quemChamou + " ↔ " + aceitou);
                Evento ace = new Evento(Meio.ACEITAR, "aceitar", "ok", null);
                ent.msg.conecta("localhost", quemChamou);
                ent.msg.envia(ace.toString());
                ent.msg.termina();
                break;

            case Meio.REJEITAR:
                // Remove o pendente e notifica o convidante
                int rejeitou    = Integer.parseInt(_ev.C1);
                Integer chamou  = pendentes.remove(rejeitou);
                if (chamou == null) {
                    p.gui.EscreveLog("REJEITAR sem pendente de " + rejeitou);
                    break;
                }
                p.gui.EscreveLog("Rejeitado — notificando " + chamou);
                Evento rej = new Evento(Meio.REJEITAR, "rejeitar", "no", null);
                ent.msg.conecta("localhost", chamou);
                ent.msg.envia(rej.toString());
                ent.msg.termina();
                break;

            case Meio.MSG:
                // Roteia para o parceiro da sessão ativa
                int remetente = Integer.parseInt(_ev.C1);
                Integer destMsg = sessoes.get(remetente);
                if (destMsg == null) {
                    p.gui.EscreveLog("MSG sem sessão ativa de " + remetente);
                    break;
                }
                p.gui.EscreveLog("MSG: " + remetente + " → " + destMsg);
                Evento msg = new Evento(Meio.MSG, "msg", _ev.C2, null);
                ent.msg.conecta("localhost", destMsg);
                ent.msg.envia(msg.toString());
                ent.msg.termina();
                break;

            case Meio.DESCONECTAR:
                // Remove a sessão e notifica o parceiro
                int quemSaiu  = Integer.parseInt(_ev.C1);
                Integer parceiro = sessoes.remove(quemSaiu);
                if (parceiro == null) {
                    p.gui.EscreveLog("DESCONECTAR sem sessão de " + quemSaiu);
                    break;
                }
                sessoes.remove(parceiro);
                p.gui.EscreveLog("Desconexão: " + quemSaiu + " saiu, notificando " + parceiro);
                Evento desc = new Evento(Meio.DESCONECTAR, "desconectar", "bye", null);
                ent.msg.conecta("localhost", parceiro);
                ent.msg.envia(desc.toString());
                ent.msg.termina();
                break;

            default:
                p.gui.EscreveLog("MEIO descartou evento: " + _ev.code + " em IDLE");
        }
    }
}
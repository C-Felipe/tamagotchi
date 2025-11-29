package br.com.tamagotchi.util;

import br.com.tamagotchi.servicos.TamagotchiService;
import br.com.tamagotchi.enums.Humor;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Utilitário responsável por controlar o relógio do jogo.
 * Executa ciclos automáticos de atualização (game loop).
 */
public class TempoUtil {

    private Timer timer;
    private TamagotchiService service;
    private boolean rodando;

    /** Configuração de velocidade (em milissegundos)
     * 1000ms = 1 segundo na vida real
     * Quanto menor, mais rápido o jogo passa
     */
    private static final int INTERVALO_ATUALIZACAO = 2000;

    public TempoUtil(TamagotchiService service) {
        this.service = service;
    }

    /**
     * Inicia a contagem do tempo.
     * O jogo começa a "rodar" sozinho a partir daqui.
     */
    public void iniciar() {
        if (timer != null ) return; //Evita criar dois timers

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //chama o serviço automaticamente
                service.processarPassagemDeTempo();

                // Se o digimon morreu, o relogio para
                if (service.getTamagotchi().getHumor() == Humor.MORTO) {
                    parar();
                }

                // (Futuramente aqui chamaremos a atualização da Janela também)
                // System.out.println("Tick... (Tempo passando)"); // Debug
            }
        }, 0, INTERVALO_ATUALIZACAO);
    }

    /**
     * Pausa o tempo (útil para menus ou pause).
     */
    public void parar() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        rodando = false;
        System.out.println("Tempo pausado.");
    }
}
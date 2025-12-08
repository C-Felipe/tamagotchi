package br.com.tamagotchi.util;

import br.com.tamagotchi.servicos.TamagotchiService;
import br.com.tamagotchi.enums.Humor;
import java.util.Timer;
import java.util.TimerTask;

public class TempoUtil {

    private Timer timer;
    private TamagotchiService service;

    // Atualiza o jogo a cada 5 segundos
    private static final int INTERVALO = 5000;

    public TempoUtil(TamagotchiService service) {
        this.service = service;
    }

    public void iniciar() {
        if (timer != null) return;

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (service.getTamagotchi() != null) {
                    service.processarPassagemDeTempo();

                    // Se morrer, para o loop para economizar recursos
                    if (service.getTamagotchi().getHumor() == Humor.MORTO) {
                        parar();
                    }
                }
            }
        }, 0, INTERVALO);
    }

    public void parar() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
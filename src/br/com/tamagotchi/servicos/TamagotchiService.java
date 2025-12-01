package br.com.tamagotchi.servicos;

import br.com.tamagotchi.entidades.*;
import br.com.tamagotchi.enums.Humor;
import java.util.Random;

import java.util.Scanner; // Para testes no console por enquanto

/**
 * Controla a lógica do jogo, gerenciando o Tamagotchi atual,
 * a passagem de tempo e as regras de evolução e morte.
 */
public class TamagotchiService {

    private Tamagotchi tamagotchi;

    private PersistenciaService persistencia = new PersistenciaService();

    /**
     * Métodos para salvar e carregar;
     */
    public boolean carregarJogoSalvo() {
        Tamagotchi salvo  = persistencia.carregar();
        if (salvo != null) {
            this.tamagotchi = salvo;
            System.out.println("Jogo Carregado com sucesso!");
            return true;
        }
        return false;
    }

    public void salvarJogo() {
        //Só salva se o digimon tiver vivo;
        if (this.tamagotchi != null && !this.tamagotchi.getHumor().equals("Morto")) {
            persistencia.salvar(this.tamagotchi);
        } else {
            System.out.println("Não é possível salvar, o Digimon não existe ou morreu.");
            persistencia.deletarSave();
        }
    }

    /**
     * Inicia o jogo criando um Digitama(ovo).
     * O Digimon é escolhido aleatoriamente.
     */
    public void incubarOvo() {
        System.out.println("O ovo está chocando...");
        try { Thread.sleep(1500); } catch (Exception e) {} // Suspense...
        System.out.println("CRAACK! O ovo rachou!");

        Random sorteio = new Random();
        int escolha = sorteio.nextInt(4) + 1; // Gera um número entre 1 e 4

        switch (escolha) {
            case 1:
                this.tamagotchi = new Koromon();
                System.out.println("Parabéns! Nasceu um KOROMON!");
                break;
            case 2:
                this.tamagotchi = new Tsunomon();
                System.out.println("Parabéns! Nasceu um TSUNOMON!");
                break;
            case 3:
                this.tamagotchi = new Pyocomon();
                System.out.println("Parabéns! Nasceu uma PYOCOMON!");
                break;
            case 4:
                this.tamagotchi = new Nyaromon();
                System.out.println("Parabéns! Nasceu um NYAROMON!");
                break;
        }
    }

    //Ações do Jogador

    public void alimentar() {
        if (verificarVivo()) {
            tamagotchi.alimentar();
            verificarEstadoGeral();
        }
    }

    public void brincar() {
        if (verificarVivo()) {
            tamagotchi.brincar();
            verificarEstadoGeral();
        }
    }

    public void dormir() {
        if (verificarVivo()) {
            tamagotchi.dormir();
            verificarEstadoGeral();
        }
    }

    public void exibirStatus() {
        if (tamagotchi == null) return;
        System.out.println("\n--- STATUS DE " + tamagotchi.getNome().toUpperCase() + " ---");
        System.out.println("Nível: " + tamagotchi.getNivel());
        // Mostra XP e quanto falta para 100
        System.out.println("XP: " + tamagotchi.getExperiencia() + " / 100");
        System.out.println("Fome: " + tamagotchi.getFome() + "/100");
        System.out.println("Energia: " + tamagotchi.getEnergia() + "/100");
        System.out.println("Felicidade: " + tamagotchi.getFelicidade() + "/100");
        System.out.println("Humor: " + tamagotchi.getHumor());
        System.out.println("------------------------------\n");
    }

    // Lógica de Jogo

    /**
     * Chamado pelo TempoUtil.
     */
    public void processarPassagemDeTempo() {
        if (!verificarVivo()) return;

        // Aumenta fome e diminui energia naturalmente com o tempo
        tamagotchi.setFome(tamagotchi.getFome() + 3);
        tamagotchi.setEnergia(tamagotchi.getEnergia() - 2);

        // Felicidade cai se estiver com fome ou sujo
        if (tamagotchi.getFome() > 70 || tamagotchi.getEnergia() < 30) {
            tamagotchi.setFelicidade(tamagotchi.getFelicidade() - 4);
        }

        verificarEstadoGeral();
    }

    private boolean verificarVivo() {
        if (tamagotchi == null) return false;
        if (tamagotchi.getHumor() == Humor.MORTO) {
            System.out.println("O " + tamagotchi.getNome() + " morreu. Fim de jogo.");
            return false;
        }
        return true;
    }

    /**
     * Verifica Morte e Evolução.
     */
    private void verificarEstadoGeral() {
        tamagotchi.atualizarHumor();

        if (tamagotchi.getHumor() == Humor.MORTO) return;

        if (tamagotchi.getFome() >= 100 || tamagotchi.getEnergia() <= 0) {
            tamagotchi.setHumor(Humor.MORTO);
        }

        if (tamagotchi.getHumor() == Humor.MORTO) {
            persistencia.deletarSave(); //Deleta o save, caso o digimon morra
            return;
        }

        // Regra de Evolução: Se for Bebê e chegar no nível 2
        if (tamagotchi.getNivel() >= 2) {
            tentarEvoluir();
        }
    }

    /**
     *Troca o objeto Bebê pelo objeto Criança.
     */
    private void tentarEvoluir() {
        Tamagotchi evolucao = null;

        // Verifica quem é o atual para saber quem será o próximo (instanceof)
        if (tamagotchi instanceof Koromon) {
            evolucao = new Agumon();
        } else if (tamagotchi instanceof Tsunomon) {
            evolucao = new Gabumon();
        } else if (tamagotchi instanceof Pyocomon) {
            evolucao = new Piyomon();
        } else if (tamagotchi instanceof Nyaromon) {
            evolucao = new Tailmon();
        }

        // Se houve uma evolução válida, faz a troca preservando dados
        if (evolucao != null) {
            System.out.println("\nO QUE? " + tamagotchi.getNome() + " ESTÁ EVOLUINDO!");

            evolucao.setFelicidade(tamagotchi.getFelicidade());

            this.tamagotchi = evolucao;

            System.out.println("PARABÉNS! Ele evoluiu para " + tamagotchi.getNome() + "!\n");
        }
    }

    public Tamagotchi getTamagotchi() {
        return tamagotchi;
    }
}
package br.com.tamagotchi.servicos;

import br.com.tamagotchi.entidades.*;
import br.com.tamagotchi.enums.Humor;
import br.com.tamagotchi.exceptions.AcaoNaoPermitidaException;
import java.util.Random;

public class TamagotchiService {

    private Tamagotchi tamagotchi;
    private final PersistenciaService persistencia = new PersistenciaService();

    public boolean carregarJogoSalvo() {
        Tamagotchi salvo = persistencia.carregar();
        if (salvo != null) {
            // Se o save estiver corrompido ou morto, reseta.
            if (salvo.getHumor() == Humor.MORTO) {
                persistencia.deletarSave();
                return false;
            }
            this.tamagotchi = salvo;
            return true;
        }
        return false;
    }

    public void salvarJogo() {
        if (this.tamagotchi != null && this.tamagotchi.getHumor() != Humor.MORTO) {
            persistencia.salvar(this.tamagotchi);
        } else {
            persistencia.deletarSave();
        }
    }

    public void exibirStatus() {
        if (tamagotchi != null) {
            System.out.println(tamagotchi.getNome() + " - Fome: " + tamagotchi.getFome());
        }
    }

    public void incubarOvo() {
        try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        Random sorteio = new Random();
        int escolha = sorteio.nextInt(4) + 1;

        switch (escolha) {
            case 1: this.tamagotchi = new Koromon(); break;
            case 2: this.tamagotchi = new Tsunomon(); break;
            case 3: this.tamagotchi = new Pyocomon(); break;
            case 4: this.tamagotchi = new Nyaromon(); break;
        }
    }

    // --- Ações do Jogador ---

    public void alimentar() throws AcaoNaoPermitidaException {
        verificarVivo();
        tamagotchi.alimentar();
        checarEvolucao();
        salvarJogo();
    }

    public void brincar() throws AcaoNaoPermitidaException {
        verificarVivo();
        tamagotchi.brincar();
        checarEvolucao();
        salvarJogo();
    }

    public void dormir() throws AcaoNaoPermitidaException {
        verificarVivo();
        tamagotchi.dormir();
        salvarJogo();
    }

    // --- Lógica Temporal ---

    public void processarPassagemDeTempo() {
        if (tamagotchi == null || tamagotchi.getHumor() == Humor.MORTO) return;

        tamagotchi.passarTempo();

        // Se morreu após o tick de tempo, limpa o save
        if (tamagotchi.getHumor() == Humor.MORTO) {
            System.out.println("O Digimon morreu de causas naturais.");
            persistencia.deletarSave();
        }
    }

    private void checarEvolucao() {
        if (tamagotchi == null) return;

        if (tamagotchi.getNivel() >= 2) {
            Tamagotchi evolucao = null;

            if (tamagotchi instanceof Koromon) evolucao = new Agumon();
            else if (tamagotchi instanceof Tsunomon) evolucao = new Gabumon();
            else if (tamagotchi instanceof Pyocomon) evolucao = new Piyomon();
            else if (tamagotchi instanceof Nyaromon) evolucao = new Tailmon();

            if (evolucao != null) {
                // Transfere atributos
                evolucao.setFelicidade(tamagotchi.getFelicidade());
                evolucao.setFome(tamagotchi.getFome());
                evolucao.setEnergia(tamagotchi.getEnergia());
                evolucao.setDiasVividos(tamagotchi.getDiasVividos());
                evolucao.setNivel(tamagotchi.getNivel());
                evolucao.setExperiencia(tamagotchi.getExperiencia());

                this.tamagotchi = evolucao;
                salvarJogo();
            }
        }
    }

    private void verificarVivo() throws AcaoNaoPermitidaException {
        if (tamagotchi == null) throw new AcaoNaoPermitidaException("Não há Digimon.");
        if (tamagotchi.getHumor() == Humor.MORTO) throw new AcaoNaoPermitidaException("Ele morreu.");
    }

    public Tamagotchi getTamagotchi() {
        return tamagotchi;
    }
}
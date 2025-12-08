package br.com.tamagotchi.entidades;

import br.com.tamagotchi.enums.Humor;
import br.com.tamagotchi.exceptions.AcaoNaoPermitidaException;
import br.com.tamagotchi.interfaces.IAlimentavel;
import br.com.tamagotchi.interfaces.IBrincavel;
import br.com.tamagotchi.interfaces.IDormivel;
import java.io.Serializable;

public abstract class Tamagotchi implements IAlimentavel, IBrincavel, IDormivel, Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private int fome;
    private int energia;
    private int felicidade;
    private int nivel;
    private int diasVividos;
    private Humor humor;
    private int experiencia = 0;

    //para contar os "ticks" para morte do digimon;
    private int tempoCritico = 0;

    public Tamagotchi(String nome) {
        this.nome = nome;
        this.energia = 100;
        this.felicidade = 75;
        this.fome = 30;
        this.diasVividos = 0;
        this.nivel = 1;
        this.experiencia = 0;
        this.humor = Humor.FELIZ;
    }

    @Override
    public void alimentar() throws AcaoNaoPermitidaException {
        if (humor == Humor.MORTO)
            throw new AcaoNaoPermitidaException("O " + nome + " morreu e não pode comer...");

        if (fome <= 0)
            throw new AcaoNaoPermitidaException("A barriga dele já está cheia!");

        setFome(getFome() - 15);
        this.tempoCritico = 0; //Recupera o estado crítico ao ser cuidado

        ganharExperiencia(5);
        atualizarHumor();
    }

    @Override
    public void brincar() throws AcaoNaoPermitidaException {
        if (humor == Humor.MORTO)
            throw new AcaoNaoPermitidaException("Não dá para brincar com fantasmas...");

        if (energia <= 10)
            throw new AcaoNaoPermitidaException("Ele está exausto! Deixe ele dormir um pouco.");

        if (fome >= 90)
            throw new AcaoNaoPermitidaException("Ele está faminto demais para brincar!");

        setFelicidade(getFelicidade() + 20);
        setEnergia(getEnergia() - 5);
        setFome(getFome() + 5);
        ganharExperiencia(10);
        atualizarHumor();
    }

    @Override
    public void dormir() throws AcaoNaoPermitidaException {
        if (humor == Humor.MORTO)
            throw new AcaoNaoPermitidaException("RIP " + nome);

        if (energia >= 95)
            throw new AcaoNaoPermitidaException("Ele está sem sono agora!");

        setEnergia(100);
        this.tempoCritico = 0; // Recupera o estado crítico

        setFome(getFome() + 10);
        setFelicidade(getFelicidade() + 5);
        atualizarHumor();
    }

    /**
     * Processa a redução de status com o tempo.
     * Também gerencia a regra de morte por negligência.
     */
    public void passarTempo() {
        if (humor == Humor.MORTO) return;

        setFome(getFome() + 2);
        setEnergia(getEnergia() - 1);
        setFelicidade(getFelicidade() - 1);

        // Verifica estado crítico, tipo Fome e cansaço
        if (fome >= 100 || energia <= 0) {
            tempoCritico++;
            System.out.println("Estado Crítico: " + tempoCritico + "/4");

            // Se permanecer 4 ciclos (aprox. 20s) nesse estado, morre
            if (tempoCritico >= 4) {
                this.humor = Humor.MORTO;
                System.out.println("O Digimon morreu.");
                return;
            }
        } else {
            tempoCritico = 0; // Reseta se os status voltarem ao normal
        }

        atualizarHumor();
    }

    public void atualizarHumor() {
        if (humor == Humor.MORTO) return;

        if (fome >= 70) {
            this.humor = Humor.COM_FOME;
        } else if (energia <= 30) {
            this.humor = Humor.CANSADO;
        } else if (felicidade <= 30) {
            this.humor = Humor.TRISTE;
        } else {
            this.humor = Humor.FELIZ;
        }
    }

    //Para Calcula XP necessário para o próximo nível
    public int getXpNecessario() {
        return 100 + (this.nivel - 1) * 50;
    }

    protected void ganharExperiencia(int pontos) {
        this.experiencia += pontos;
        int metaXp = getXpNecessario();

        System.out.println(getNome() + " ganhou " + pontos + " XP. Total: " + this.experiencia + "/" + metaXp);

        if (this.experiencia >= metaXp) {
            this.nivel++;
            this.experiencia = 0;

            //Para restaura status ao subir de nível
            setEnergia(100);
            setFelicidade(100);
            setFome(0);

            System.out.println("LEVEL UP! " + getNome() + " subiu para o nível " + this.nivel);
            verificarEvolucao();
        }
    }

    public void verificarEvolucao() {}

    // Getters e Setters com limites (0 a 100)
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getFome() { return fome; }
    public void setFome(int fome) { this.fome = Math.max(0, Math.min(100, fome)); }

    public int getEnergia() { return energia; }
    public void setEnergia(int energia) { this.energia = Math.max(0, Math.min(100, energia)); }

    public int getFelicidade() { return felicidade; }
    public void setFelicidade(int felicidade) { this.felicidade = Math.max(0, Math.min(100, felicidade)); }

    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }

    public int getDiasVividos() { return diasVividos; }
    public void setDiasVividos(int diasVividos) { this.diasVividos = diasVividos; }

    public Humor getHumor() { return humor; }
    public void setHumor(Humor humor) { this.humor = humor; }

    public int getExperiencia() { return experiencia; }
    public void setExperiencia(int experiencia) { this.experiencia = experiencia; }
}
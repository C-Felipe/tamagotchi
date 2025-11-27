package br.com.tamagotchi.entidades;

import br.com.tamagotchi.enums.Humor;
import br.com.tamagotchi.interfaces.IAlimentavel;
import br.com.tamagotchi.interfaces.IBrincavel;
import br.com.tamagotchi.interfaces.IDormivel;

/**
 * Classe feita para representar a estrutura básica dos Digimons e tambem define os atributos e comportamentos comuns.
 */

public abstract class Tamagotchi implements IAlimentavel, IBrincavel,  IDormivel {
    // Declaração dos atributos;
    private String nome;
    private int fome;
    private int energia;
    private int felicidade;
    private int nivel;
    private int diasVividos;
    private Humor humor;


    /**
     * CONSTRUTURO para iniciar o tamagotchi com valores padrão.
     */
    public Tamagotchi(String nome) {
        this .nome = nome;
        this.energia = 100;
        this.felicidade = 75;
        this.diasVividos = 0;
        this.nivel = 1;
        this.fome = 30;
        this.humor = Humor.FELIZ;
    }

    /**
     * MÉTODOS abstratos para definir os contratos.
     */

    @Override
    public abstract void alimentar();

    @Override
    public abstract void brincar();

    @Override
    public abstract void dormir();

    public abstract void atualizarHumor();

    public void passarTempo(){

    }

    public void verificarEvolucao(){

    }
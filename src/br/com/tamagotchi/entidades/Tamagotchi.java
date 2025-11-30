package br.com.tamagotchi.entidades;

import br.com.tamagotchi.enums.Humor;
import br.com.tamagotchi.interfaces.IAlimentavel;
import br.com.tamagotchi.interfaces.IBrincavel;
import br.com.tamagotchi.interfaces.IDormivel;
import java.io.Serializable; // <--- 1. 


/**
 * Classe feita para representar a estrutura básica dos Digimons e tambem define os atributos e comportamentos comuns.
 */

public abstract class Tamagotchi implements IAlimentavel, IBrincavel,  IDormivel, Serializable {
    // Declaração dos atributos;
    private String nome;
    private int fome;
    private int energia;
    private int felicidade;
    private int nivel;
    private int diasVividos;
    private Humor humor;
    private static final long serialVersionUID = 1L;

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


    /**
     * GETTERS e SETTERS
     */

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getFome() {
        return fome;
    }

    public void setFome(int fome) {
        this.fome = Math.max(0, Math.min(100, fome));
    }

    public int getEnergia() {
        return energia;
    }

    public void setEnergia(int energia) {
        this.energia = Math.max(0, Math.min(100, energia));
    }

    public int getFelicidade() {
        return felicidade;
    }

    public void setFelicidade(int felicidade) {
        this.felicidade = Math.max(0, Math.min(100, felicidade));
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getDiasVividos() {
        return diasVividos;
    }

    public void setDiasVividos(int diasVividos) {
        this.diasVividos = diasVividos;
    }

    public Humor getHumor() {
        return humor;
    }

    public void setHumor(Humor humor) {
        this.humor = humor;
    }

    public int getExperiencia() {
        return experiencia;
    }

    private int experiencia = 0;

    /**
     * Método auxiliar para ganhar XP
     */

    protected void ganharExperiencia(int pontos) {
        this.experiencia += pontos;
        System.out.println(getNome() + " ganhou " + pontos + " de experiência!");

        // Se passar de 100, sobe de nível
        if (this.experiencia >= 100) {
            this.nivel++;
            this.experiencia = 0;
            System.out.println("PARABÉNS! " + getNome() + " subiu para o nível " + this.nivel + "!");
            verificarEvolucao(); // Checa se já pode evoluir
        }
    }
}
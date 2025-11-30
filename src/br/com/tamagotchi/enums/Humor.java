package br.com.tamagotchi.enums;

/**
 Aqui defiinimos os estados emocionais do possiveis digimons.
 */

public enum Humor {
    FELIZ("Feliz"),
    TRISTE("Triste"),
    COM_FOME("Com fome"),
    CANSADO("Cansado"),
    MORTO("Morto");

    private String descricao;

    //Construtor do enum;
    Humor(String descricao) {
        this.descricao = descricao;
    }

    //Sobeescrever o toString para imprimir bonitinho no console;
    @Override
    public String toString() {
        return descricao;
    }
}
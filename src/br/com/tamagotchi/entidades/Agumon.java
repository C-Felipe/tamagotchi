Agumon.java

package br.com.tamagotchi.entidades;

import br.com.tamagotchi.enums.Humor;

public class Agumon extends Tamagotchi {

    public Agumon() {
        super("Agumon");
        setEnergia(100);
        setFome(40); // Já começa com um pouco de fome
    }
koromon.java

package br.com.tamagotchi.entidades;

public class Koromon extends Tamagotchi {

    public Koromon() {
        super("Koromon");
        // Bebês começam com status diferentes
        setEnergia(80);
        setFome(20);
    }
}
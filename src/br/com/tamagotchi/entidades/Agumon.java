agumon.java

package br.com.tamagotchi.entidades;

public class Agumon extends Tamagotchi {

    public Agumon() {
        super("Agumon");
        // Define status iniciais específicos do Agumon.
        setFome(30);
        setEnergia(100);
        setFelicidade(80);
    }
}
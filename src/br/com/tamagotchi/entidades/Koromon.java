Koromon.java

package br.com.tamagotchi.entidades;

import br.com.tamagotchi.enums.Humor;

public class Koromon extends Tamagotchi {

    public Koromon() {
        super("Koromon");
        setEnergia(80);
        setFome(20);
    }
    
@Override
    public void alimentar() {
        System.out.println(getNome() + " mordiscou um pedacinho de comida.");
        setFome(Math.max(0, getFome() - 5));
        ganharExperiencia(10);
    }

    @Override
    public void brincar() {
        System.out.println(getNome() + " soltou bolhas de sabão!");
        setFelicidade(Math.min(100, getFelicidade() + 10));
        setEnergia(getEnergia() - 5);
        setFome(getFome() + 5);
        ganharExperiencia(15);
    }

    @Override
    public void dormir() {
        System.out.println(getNome() + " fechou os olhinhos e dormiu.");
        setEnergia(100);
        setFome(getFome() + 2);
    }

    @Override
    public void atualizarHumor() {
        if (getFome() > 50 || getEnergia() < 40) {
            setHumor(Humor.TRISTE);
        } else {
            setHumor(Humor.FELIZ);
        }
    }
}
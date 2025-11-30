package br.com.tamagotchi.entidades;

import br.com.tamagotchi.enums.Humor;

public class Nyaromon extends Tamagotchi {

    public Nyaromon() {
        super("Nyaromon");
    }

    @Override
    public void alimentar() {
        System.out.println(getNome() + " bebeu leite.");
        setFome(Math.max(0, getFome() - 6));
        ganharExperiencia(10);
    }

    @Override
    public void brincar() {
        System.out.println(getNome() + " balançou a cauda.");
        setFelicidade(Math.min(100, getFelicidade() + 10));
        setEnergia(getEnergia() - 6);
        setFome(getFome() + 6);
        ganharExperiencia(15);
    }

    @Override
    public void dormir() {
        System.out.println(getNome() + " dormiu ronronando.");
        setEnergia(100);
        setFelicidade(Math.min(100, getFelicidade() + 5));
        setFome(getFome() + 2);
    }

    @Override
    public void atualizarHumor() {
        if (getEnergia() < 50) setHumor(Humor.CANSADO);
        else setHumor(Humor.FELIZ);
    }
}
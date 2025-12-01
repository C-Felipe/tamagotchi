package br.com.tamagotchi.entidades;

import br.com.tamagotchi.enums.Humor;

public class Tsunomon extends Tamagotchi {

    public Tsunomon() {
        super("Tsunomon");
    }

    @Override
    public void alimentar() {
        System.out.println(getNome() + " comeu um pouco.");
        setFome(Math.max(0, getFome() - 8));
        ganharExperiencia(10);
    }

    @Override
    public void brincar() {
        System.out.println(getNome() + " tentou dar uma chifrada de brincadeira.");
        setFelicidade(Math.min(100, getFelicidade() + 10));
        setEnergia(getEnergia() - 8);
        setFome(getFome() + 8);
        ganharExperiencia(15);
    }

    @Override
    public void dormir() {
        System.out.println(getNome() + " dormiu encolhido.");
        setEnergia(100);
        setFome(getFome() + 4);
    }

    @Override
    public void atualizarHumor() {
        if (getFome() > 60) setHumor(Humor.COM_FOME);
        else setHumor(Humor.FELIZ);
    }
}
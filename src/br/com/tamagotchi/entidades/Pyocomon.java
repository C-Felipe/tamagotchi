//o flamengo é seleção !!! 4 libertadores

package br.com.tamagotchi.entidades;

import br.com.tamagotchi.enums.Humor;

public class Pyocomon extends Tamagotchi {

    public Pyocomon() {
        super("Pyocomon");
    }

    @Override
    public void alimentar() {
        System.out.println(getNome() + " cheirou a comida e comeu.");
        setFome(Math.max(0, getFome() - 5));
        ganharExperiencia(10);
    }

    @Override
    public void brincar() {
        System.out.println(getNome() + " dançou com suas pétalas.");
        setFelicidade(Math.min(100, getFelicidade() + 15)); // Fica feliz fácil
        setEnergia(getEnergia() - 5);
        setFome(getFome() + 5);
        ganharExperiencia(15);
    }

    @Override
    public void dormir() {
        System.out.println(getNome() + " plantou as raízes e dormiu.");
        setEnergia(100);
        setFome(getFome() + 2);
    }

    @Override
    public void atualizarHumor() {
        if (getFelicidade() < 50) setHumor(Humor.TRISTE);
        else setHumor(Humor.FELIZ);
    }
}
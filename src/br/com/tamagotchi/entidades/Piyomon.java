e Piyomon.java

package br.com.tamagotchi.entidades;

import br.com.tamagotchi.enums.Humor;

public class Piyomon extends Tamagotchi {

    public Piyomon() {
        super("Piyomon");
    }

    @Override
    public void alimentar() {
        System.out.println(getNome() + " bicou algumas sementes.");
        setFome(Math.max(0, getFome() - 10)); // Padrão
        ganharExperiencia(5);
    }

    @Override
    public void brincar() {
        // BÔNUS: Gasta menos energia ao brincar
        System.out.println(getNome() + " voou pelo jardim!");

        setFelicidade(Math.min(100, getFelicidade() + 10));

        // Custo reduzido: Apenas 5 de energia (Padrão é 10)
        setEnergia(getEnergia() - 5);

        setFome(getFome() + 10);
        ganharExperiencia(10);
    }

    @Override
    public void dormir() {
        System.out.println(getNome() + " dormiu empoleirada.");
        setEnergia(100);
        setFome(getFome() + 5);
    }

    @Override
    public void atualizarHumor() {
        if (getFelicidade() < 40) setHumor(Humor.TRISTE);
        else if (getEnergia() < 10) setHumor(Humor.CANSADO);
        else setHumor(Humor.FELIZ);
    }
}
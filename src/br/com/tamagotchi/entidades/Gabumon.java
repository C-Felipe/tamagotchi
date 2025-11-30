package br.com.tamagotchi.entidades;

import br.com.tamagotchi.enums.Humor;

public class Gabumon extends Tamagotchi {

    public Gabumon() {
        super("Gabumon");
    }

    @Override
    public void alimentar() {
        // BÔNUS: Aumenta força (fome)
        System.out.println(getNome() + " comeu carne e ficou mais forte!");

        // Mata fome um pouco mais que o padrão (-15)
        setFome(Math.max(0, getFome() - 15));
        setEnergia(Math.min(100, getEnergia() + 5));
        ganharExperiencia(5);
    }

    @Override
    public void brincar() {
        // BÔNUS: Aumenta experiência
        System.out.println(getNome() + " está treinando enquanto brinca.");

        setFelicidade(Math.min(100, getFelicidade() + 10));
        setEnergia(getEnergia() - 10);
        setFome(getFome() + 10);

        // XP Dobrado: Aprende mais rápido
        ganharExperiencia(20);
    }

    @Override
    public void dormir() {
        System.out.println(getNome() + " se enrolou na pele e dormiu.");
        setEnergia(100);
        setFome(getFome() + 5);
    }

    @Override
    public void atualizarHumor() {
        if (getFome() > 85) setHumor(Humor.COM_FOME);
        else setHumor(Humor.FELIZ);
    }
}
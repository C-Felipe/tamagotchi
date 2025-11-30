package br.com.tamagotchi.entidades;

import br.com.tamagotchi.enums.Humor;

public class Tailmon extends Tamagotchi {

    public Tailmon() {
        super("Tailmon");
    }

    @Override
    public void alimentar() {
        System.out.println(getNome() + " comeu peixe fresco.");
        setFome(Math.max(0, getFome() - 10)); // Padrão
        ganharExperiencia(5);
    }

    @Override
    public void brincar() {
        System.out.println(getNome() + " brincou com o anel sagrado.");
        setFelicidade(Math.min(100, getFelicidade() + 10)); // Padrão
        setEnergia(getEnergia() - 10);
        setFome(getFome() + 10);
        ganharExperiencia(10);
    }

    @Override
    public void dormir() {
        // BÔNUS: Recupera mais energia/status dormindo
        System.out.println(getNome() + " dormiu e teve ótimos sonhos.");

        setEnergia(100);

        // Bônus extra: Fica feliz ao dormir
        setFelicidade(Math.min(100, getFelicidade() + 10));

        // Bônus extra: Acorda com MENOS fome que os outros
        setFome(getFome() + 2);
    }

    @Override
    public void atualizarHumor() {
        if (getEnergia() < 40) setHumor(Humor.CANSADO);
        else if (getFome() > 80) setHumor(Humor.COM_FOME);
        else setHumor(Humor.FELIZ);
    }
}
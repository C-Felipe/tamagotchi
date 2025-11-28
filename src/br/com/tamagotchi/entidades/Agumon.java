Agumon.java

package br.com.tamagotchi.entidades;

import br.com.tamagotchi.enums.Humor;

public class Agumon extends Tamagotchi {

    public Agumon() {
        super("Agumon");
        setEnergia(100);
        setFome(40); // Já começa com um pouco de fome
    }
    
    @Override
    public void alimentar() {
        // BÔNUS: Ganha "mais força" (mata mais a fome)
        System.out.println(getNome() + " devorou um pernil gigante!");

        // Diminui fome em 20 (Padrão é 10)
        setFome(Math.max(0, getFome() - 20));

        // Recupera um pouco de energia comendo
        setEnergia(Math.min(100, getEnergia() + 5));

        ganharExperiencia(5); // XP Padrão
    }

    @Override
    public void brincar() {
        // BÔNUS: Ganha "mais felicidade"
        System.out.println(getNome() + " soltou uma Chama Neném de alegria!");

        // Aumenta felicidade em 20 (Padrão é 10)
        setFelicidade(Math.min(100, getFelicidade() + 20));

        // Custo: Gasta um pouco mais de energia por ser agitado (-15)
        setEnergia(getEnergia() - 15);
        setFome(getFome() + 10);

        ganharExperiencia(10); // XP Padrão
    }

    @Override
    public void dormir() {
        System.out.println(getNome() + " caiu no sono no meio da sala.");
        setEnergia(100);
        setFome(getFome() + 5); // Fome ao acordar (Padrão)
    }

    @Override
    public void atualizarHumor() {
        if (getFome() > 70) setHumor(Humor.COM_FOME);
        else if (getEnergia() < 20) setHumor(Humor.CANSADO);
        else setHumor(Humor.FELIZ);

        // Regra extra de morte
        if (getFome() >= 100) {
            setHumor(Humor.MORTO);
            System.out.println("Agumon não resistiu à fome...");
        }
    }
}
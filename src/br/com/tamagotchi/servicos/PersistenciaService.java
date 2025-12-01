package br.com.tamagotchi.servicos;

import br.com.tamagotchi.entidades.Tamagotchi;
import br.com.tamagotchi.enums.Humor;

import java.io.*;

public class PersistenciaService {
    private static final String NOME_ARQUIVO = "savegame.dat";

    public void salvar(Tamagotchi tamagotchi) {
        // Para tentar criar um fluxo de saida de para o arquivo;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO))) {
            oos.writeObject(tamagotchi);
            System.out.println("Jogo salvo com sucesso em: " + NOME_ARQUIVO);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o jogo: " + e.getMessage());
        }
    }

    public Tamagotchi carregar() {
        File arquivo = new File(NOME_ARQUIVO);

        if (!arquivo.exists()) {
            return null;  //Retorna nulo se não existir arquivo de save anterior;
        }

        //Para tentar ler o objeto do arquivo;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (Tamagotchi) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar o jogo: " + e.getMessage());
            return null;
        }
    }

    public void deletarSave() {
        File arquivo = new File(NOME_ARQUIVO);
        if (arquivo.exists()) {
            arquivo.delete();
        }
    }

}
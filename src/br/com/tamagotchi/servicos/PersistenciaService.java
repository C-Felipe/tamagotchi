package br.com.tamagotchi.servicos;

import br.com.tamagotchi.entidades.Tamagotchi;
import java.io.*;

public class PersistenciaService {

    private static final String NOME_ARQUIVO = "savegame.dat";

    public void salvar(Tamagotchi tamagotchi) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO))) {
            oos.writeObject(tamagotchi);
            System.out.println("Jogo salvo em: " + NOME_ARQUIVO);
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public Tamagotchi carregar() {
        File arquivo = new File(NOME_ARQUIVO);

        if (!arquivo.exists()) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (Tamagotchi) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar save (arquivo corrompido ou versão antiga).");
            return null;
        }
    }

    public void deletarSave() {
        File arquivo = new File(NOME_ARQUIVO);
        if (arquivo.exists()) {
            arquivo.delete();
            System.out.println("Save anterior deletado.");
        }
    }
}
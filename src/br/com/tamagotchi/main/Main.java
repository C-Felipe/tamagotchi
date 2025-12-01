package br.com.tamagotchi.main;

import br.com.tamagotchi.servicos.TamagotchiService;
import br.com.tamagotchi.util.TempoUtil;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TamagotchiService service = new TamagotchiService();
        TempoUtil tempoUtil = new TempoUtil(service);

        System.out.println("=== TAMAGOTCHI ===");

        //Para carregar
        boolean carregou = service.carregarJogoSalvo();

        //Se não carregou (não tinha save), começa um novo
        if (!carregou) {
            System.out.println("Nenhum jogo salvo encontrado.");
            System.out.println("Você encontrou um Digi-Ovo. Pressione ENTER para chocar.");
            scanner.nextLine();
            service.incubarOvo();
        }

        //Para iniciar o loop
        tempoUtil.iniciar();

        while (true) {
            // verificação de morte;
            if (service.getTamagotchi().getHumor().toString().equals("Morto")) {
                System.out.println("\n=================================");
                System.out.println("    GAME OVER    ");
                System.out.println("Seu Digimon partiu para um lugar melhor.");
                System.out.println("=================================\n");

                tempoUtil.parar();
                scanner.close();
                break;
            }

            service.exibirStatus();

            System.out.println("1 - Alimentar");
            System.out.println("2 - Brincar");
            System.out.println("3 - Dormir");
            System.out.println("0 - Salvar e Sair"); // Nova opção
            System.out.print("Opção: ");

            int acao = scanner.nextInt();

            switch (acao) {
                case 1: service.alimentar(); break;
                case 2: service.brincar(); break;
                case 3: service.dormir(); break;
                case 0:
                    System.out.println("Salvando progresso...");
                    service.salvarJogo(); // <--- CHAMA O SAVE
                    tempoUtil.parar();
                    System.out.println("Até logo!");
                    scanner.close();
                    System.exit(0);
                    break;
                default: System.out.println("Opção inválida!");
            }
        }
    }
}
package br.com.tamagotchi.main;

import br.com.tamagotchi.servicos.TamagotchiService;
import br.com.tamagotchi.util.TempoUtil;
import br.com.tamagotchi.exceptions.AcaoNaoPermitidaException;
import br.com.tamagotchi.enums.Humor;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TamagotchiService service = new TamagotchiService();
        TempoUtil tempoUtil = new TempoUtil(service);

        System.out.println("=== TAMAGOTCHI ===");

        // Tenta carregar save existente, senão inicia novo jogo
        if (!service.carregarJogoSalvo()) {
            System.out.println("Nenhum jogo salvo encontrado.");
            System.out.println("Pressione ENTER para chocar o Digi-Ovo.");
            scanner.nextLine();
            service.incubarOvo();
        }

        tempoUtil.iniciar();

        while (true) {
            // Verifica Game Over
            if (service.getTamagotchi().getHumor() == Humor.MORTO) {
                System.out.println("\n=================================");
                System.out.println("    GAME OVER    ");
                System.out.println("Seu Digimon partiu para um lugar melhor.");
                System.out.println("=================================\n");
                tempoUtil.parar();
                break;
            }

            service.exibirStatus();

            System.out.println("1 - Alimentar");
            System.out.println("2 - Brincar");
            System.out.println("3 - Dormir");
            System.out.println("0 - Salvar e Sair");
            System.out.print("Opção: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Digite apenas números!");
                scanner.next();
                continue;
            }

            int acao = scanner.nextInt();

            try {
                switch (acao) {
                    case 1:
                        service.alimentar();
                        System.out.println("Nham nham!");
                        break;
                    case 2:
                        service.brincar();
                        System.out.println("Diversão!");
                        break;
                    case 3:
                        service.dormir();
                        System.out.println("Zzzzz...");
                        break;
                    case 0:
                        System.out.println("Salvando progresso...");
                        service.salvarJogo();
                        tempoUtil.parar();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (AcaoNaoPermitidaException e) {
                System.out.println("\n" + e.getMessage() + "\n");
            }
        }
        scanner.close();
    }
}
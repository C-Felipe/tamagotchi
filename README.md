# Projeto Tamagotchi (Digimon Edition)

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Status](https://img.shields.io/badge/Status-Finalizado-green?style=for-the-badge)

## Integrantes do Grupo

| Nome Completo | Matrícula | GitHub |
| :--- | :--- | :--- |
| Claudio Felipe Lopes da Silva | 2024010650 | [Perfil do GitHub](https://github.com/C-Felipe) |
| Davi Gabriel de Oliveira Pascoal  | 2023022913 | [Perfil do GitHub](https://github.com/Davi-GOP) |
| Iara Raquel de Almeida Fernandes | 2023023022 | [Perfil do GitHub](https://github.com/iararaquel) |
| Igor Cavalcante Rocha | 2023022954 | [Perfil do GitHub](https://github.com/Igor-C-Rocha) |
| João Carlos de Sousa Gurgel Rocha | 2024010452 | [Perfil do GitHub](https://github.com/Joaonuvem) |
| Pedro Henrique Duarte de Andrade | 2025022521 | [Perfil do GitHub](https://github.com/Jackveio) |
| Rafael Fernandes da Costa | 2023022805 | [Perfil do GitHub](https://github.com/RafaelFernaa) |

---

## Descrição Geral do Projeto

Este software foi desenvolvido como requisito avaliativo da disciplina de Programação Orientada a Objetos. Trata-se de um simulador de vida virtual (*Virtual Pet*) ambientado no universo Digimon, onde o usuário atua como cuidador de uma criatura digital.

O sistema foi projetado para aplicar na prática os pilares da POO e conceitos avançados de Java:
* **Herança e Polimorfismo:** Implementados através do sistema de evolução de espécies.
* **Encapsulamento:** Proteção rigorosa dos estados vitais (Fome, Energia, XP).
* **Multithreading:** Simulação de tempo real (envelhecimento automático) sem travar a interface.
* **Persistência de Dados:** O progresso é salvo automaticamente em arquivo binário.
* **Tratamento de Exceções:** Regras de negócio robustas.

### Documentação Técnica
Para acessar a descrição detalhada das entidades, atributos, arquitetura e diagrama de classes, consulte o documento oficial na pasta `doc`:

**[CLIQUE AQUI PARA VER A DOCUMENTAÇÃO](doc/DOCUMENTACAO.md)**

---

## Pré-requisitos

Antes de começar, certifique-se de que sua máquina possui os seguintes requisitos:

1.  **Java JDK 25** ou superior instalado e configurado no PATH.
    * *Para verificar, digite no terminal:* `java -version`
2.  **Git** (para clonar o repositório).

---

## Como Baixar o Projeto

Para baixar o código fonte e o executável para sua máquina, você deve clonar este repositório.

### Passo a Passo (Git Clone)
1.  Abra o terminal (Git Bash, CMD ou Terminal do Linux/Mac).
2.  Copie e cole o comando abaixo:
    ```bash
    git clone https://github.com/C-Felipe/tamagotchi.git
    ```
3.  Entre na pasta do projeto:
    ```bash
    cd tamagotchi
    ```

---

## Como Executar

Este repositório já contém uma versão compilada pronta para uso, além do código fonte. Escolha uma das opções abaixo:

### Opção 1: Executável Pronto (.jar) - Rápido
Na raiz do projeto, você encontrará o arquivo `Tamagotchi.jar` (ou nome similar).

1.  **Importante:** Mantenha o arquivo `.jar` na mesma pasta que a pasta `src`. O jogo precisa carregar as imagens que estão lá.
2.  **Dê um duplo clique** no arquivo `.jar` para abrir.
3.  Caso não abra, use o terminal na pasta do projeto:
    ```bash
    java -jar Tamagotchi.jar
    ```

### Opção 2: IntelliJ IDEA
1.  Abra o IntelliJ e vá em **File > Open**.
2.  Selecione a pasta clonada do projeto.
3.  Aguarde a indexação.
4.  Navegue até: `src` > `br` > `com` > `tamagotchi` > `gui` > `JanelaPrincipal.java`.
5.  Clique com o botão direito e selecione **"Run 'JanelaPrincipal.main()'"**.

### Opção 3: VS Code
1.  Certifique-se de ter a extensão **"Extension Pack for Java"** instalada.
2.  Abra a pasta do projeto no VS Code (`File > Open Folder`).
3.  Abra o arquivo `src/br/com/tamagotchi/gui/JanelaPrincipal.java`.
4.  Clique no link **"Run"** que aparecerá logo acima da linha `public static void main`.

### Opção 4: Terminal (Compilação Manual)
Se preferir compilar manualmente sem IDE:

1.  Na pasta raiz, crie uma pasta para os binários: `mkdir bin`
2.  Compile o código:
    ```bash
    javac -d bin -sourcepath src src/br/com/tamagotchi/gui/JanelaPrincipal.java
    ```
3.  Execute:
    ```bash
    java -cp bin br.com.tamagotchi.gui.JanelaPrincipal
    ```

---

## Guia Rápido
* **Início:** Clique no Digitama (Ovo) para nascer.
* **Monitoramento:** Acompanhe as barras de Fome (Vermelha) e Energia (Azul).
* **Ações:** Use os botões para Comer, Brincar e Dormir.
* **Morte:** Se a Fome chegar a 100% ou Energia a 0%, você tem poucos segundos para salvar o Digimon.
* **Salvar:** O jogo salva automaticamente a cada ação e ao clicar no botão "Power/OFF".

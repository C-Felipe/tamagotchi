//Painel do tamagochi
package br.com.tamagotchi.gui;

import br.com.tamagotchi.entidades.Tamagotchi;
import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;

public class PainelTamagotchi extends JPanel {

    public enum AcaoVisual { NENHUMA, COMER, BRINCAR, DORMINDO }

    private Tamagotchi tamagotchi;

    // Imagens
    private Image imagemRenderizar;
    private Image imagemFundoFade;
    private Image imgMorphAntiga;
    private Image imgMorphNova;
    private String caminhoUltimaImagem = "";

    // Controle de Animação
    private Timer timerAnimacao;
    private float escala = 1.0f;
    private float alpha = 1.0f;

    // Sincronização
    private long tempoInicioAnimacao;
    private static final long DURACAO_EVOLUCAO = 4000;
    private static final long TEMPO_POR_FASE = 1000;
    private boolean animandoEvolucao = false;

    // Ações Temporárias
    private AcaoVisual acaoAtual = AcaoVisual.NENHUMA;
    private Timer timerAcao;

    // GIF e Som
    private Image gifEvolucao;
    private boolean mostrandoGifFundo = false;
    private static final String CAMINHO_GIF = "src/br/com/tamagotchi/recursos/imagens/evolucao.gif";
    private static final String CAMINHO_SOM = "src/br/com/tamagotchi/recursos/sons/evolucao.wav";

    public PainelTamagotchi(Tamagotchi t) {
        this.tamagotchi = t;
        setOpaque(false);

        File f = new File(CAMINHO_GIF);
        if (f.exists()) gifEvolucao = new ImageIcon(CAMINHO_GIF).getImage();

        atualizarImagem(false);
    }

    public void setTamagotchi(Tamagotchi novoTamagotchi) {
        boolean houveEvolucao = false;

        // Detecta Evolução
        if (this.tamagotchi != null && novoTamagotchi != null &&
                !this.tamagotchi.getClass().equals(novoTamagotchi.getClass())) {

            houveEvolucao = true;

            // Prepara a imagem antiga para o efeito de "trasnsformçaão"
            String nomeAntigo = this.tamagotchi.getNome().toLowerCase();
            String caminhoNormalAntigo = "src/br/com/tamagotchi/recursos/imagens/"
                    + nomeAntigo + "/" + nomeAntigo + "_normal.png";
            File fNormal = new File(caminhoNormalAntigo);

            if (fNormal.exists()) {
                imagemRenderizar = new ImageIcon(caminhoNormalAntigo).getImage();
                imagemFundoFade = null;
                alpha = 1.0f;
                escala = 1.0f;
                repaint();
            }

            new Thread(() -> tocarSom(CAMINHO_SOM)).start();
            ativarGifFundo();
        }

        this.tamagotchi = novoTamagotchi;

        if (acaoAtual == AcaoVisual.NENHUMA) {
            atualizarImagem(houveEvolucao);
        }
    }

    /**
     * Executa uma ação visual (Comer, Brincar, Dormir) e roda um callback ao terminar.
     */
    public void executarAcao(AcaoVisual acao, int duracaoMs, Runnable aoTerminar) {
        if (tamagotchi == null || animandoEvolucao) return;

        String nome = tamagotchi.getNome().toLowerCase();
        String sufixo = "normal";

        if (acao == AcaoVisual.COMER) sufixo = "comer";
        else if (acao == AcaoVisual.BRINCAR) sufixo = "brincar";
        else if (acao == AcaoVisual.DORMINDO) sufixo = "dormindo";

        String caminhoAcao = "src/br/com/tamagotchi/recursos/imagens/" + nome + "/" + nome + "_" + sufixo + ".png";
        File f = new File(caminhoAcao);

        if (f.exists()) {
            acaoAtual = acao;

            // Fade para a imagem de ação
            imagemFundoFade = imagemRenderizar;
            imagemRenderizar = new ImageIcon(caminhoAcao).getImage();
            iniciarFadeSimples();

            if (timerAcao != null && timerAcao.isRunning()) timerAcao.stop();
            timerAcao = new Timer(duracaoMs, e -> {
                if (aoTerminar != null) aoTerminar.run();

                acaoAtual = AcaoVisual.NENHUMA;
                caminhoUltimaImagem = "";
                atualizarImagem(false);
                ((Timer)e.getSource()).stop();
            });
            timerAcao.setRepeats(false);
            timerAcao.start();
        }
    }

    private void atualizarImagem(boolean animarEvolucao) {
        if (tamagotchi == null) return;
        if (acaoAtual != AcaoVisual.NENHUMA) return;

        String nome = tamagotchi.getNome().toLowerCase();
        String humor = tamagotchi.getHumor().toString().toLowerCase();
        String sufixo = "normal";

        if (humor.contains("fome")) sufixo = "fome";
        else if (humor.contains("sono") || humor.contains("cansado")) sufixo = "sono";
        else if (humor.contains("triste")) sufixo = "triste";
        else if (humor.contains("morto")) sufixo = "morto";
        else if (humor.contains("feliz")) sufixo = "normal";

        String novoCaminho = "src/br/com/tamagotchi/recursos/imagens/"
                + nome + "/" + nome + "_" + sufixo + ".png";

        if (novoCaminho.equals(caminhoUltimaImagem) && !animarEvolucao) return;

        caminhoUltimaImagem = novoCaminho;
        File arquivo = new File(novoCaminho);

        if (arquivo.exists()) {
            if (animarEvolucao) {
                // Configura Morph
                imgMorphAntiga = imagemRenderizar;
                imgMorphNova = new ImageIcon(novoCaminho).getImage();
                iniciarEvolucaoMorph();
            } else {
                // Configura Fade
                imagemFundoFade = imagemRenderizar;
                imagemRenderizar = new ImageIcon(novoCaminho).getImage();
                iniciarFadeSimples();
            }
        }
    }

    // --- ANIMAÇÕES ---

    private void iniciarFadeSimples() {
        if (timerAnimacao != null && timerAnimacao.isRunning()) timerAnimacao.stop();

        alpha = 0.0f;
        escala = 1.0f;
        animandoEvolucao = false;

        timerAnimacao = new Timer(20, e -> {
            alpha += 0.05f;

            if (alpha >= 1.0f) {
                alpha = 1.0f;
                imagemFundoFade = null;
                ((Timer)e.getSource()).stop();
            }
            repaint();
        });
        timerAnimacao.start();
    }

    private void iniciarEvolucaoMorph() {
        if (timerAnimacao != null && timerAnimacao.isRunning()) timerAnimacao.stop();
        animandoEvolucao = true;
        tempoInicioAnimacao = System.currentTimeMillis();

        timerAnimacao = new Timer(10, e -> {
            long agora = System.currentTimeMillis();
            long tempoDecorrido = agora - tempoInicioAnimacao;

            if (tempoDecorrido >= DURACAO_EVOLUCAO) {
                animandoEvolucao = false;
                escala = 1.0f; alpha = 1.0f;
                imagemRenderizar = imgMorphNova;
                ((Timer)e.getSource()).stop();
                repaint();
                return;
            }

            long faseAtual = tempoDecorrido / TEMPO_POR_FASE;
            long tempoDentroDaFase = tempoDecorrido % TEMPO_POR_FASE;
            float progresso = (float) tempoDentroDaFase / TEMPO_POR_FASE;

            // Alternância (Ping-Pong)
            if (faseAtual % 2 == 0) {
                imagemRenderizar = imgMorphAntiga;
                escala = 1.0f - progresso;
                alpha = 1.0f - progresso;
            } else {
                imagemRenderizar = imgMorphNova;
                escala = progresso;
                alpha = progresso;
            }

            if (alpha < 0f) alpha = 0f; if (alpha > 1f) alpha = 1f;
            if (escala < 0f) escala = 0f;
            repaint();
        });
        timerAnimacao.start();
    }

    private void ativarGifFundo() {
        if (gifEvolucao == null) return;
        mostrandoGifFundo = true;
        Timer timerGif = new Timer(4250, e -> {
            mostrandoGifFundo = false;
            repaint();
            ((Timer)e.getSource()).stop();
        });
        timerGif.setRepeats(false);
        timerGif.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int painelW = getWidth();
        int painelH = getHeight();
        int baseSize = 150;

        //GIF de Evolução
        if (mostrandoGifFundo && gifEvolucao != null) {
            int gifW = 380; int gifH = 310;
            g.drawImage(gifEvolucao, (painelW - gifW)/2, (painelH - gifH)/2, gifW, gifH, this);
        }

        //Personagem
        int w = (int) (baseSize * escala);
        int h = (int) (baseSize * escala);
        int x = (painelW - w) / 2;
        int y = (painelH - h) / 2;

        //Cross-Dissolve para Fade
        if (!animandoEvolucao && imagemFundoFade != null && alpha < 1.0f) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            g2.drawImage(imagemFundoFade, (painelW - baseSize)/2, (painelH - baseSize)/2, baseSize, baseSize, this);
        }

        if (imagemRenderizar != null) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.drawImage(imagemRenderizar, x, y, w, h, this);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
    }

    private void tocarSom(String caminho) {
        try {
            File arquivoSom = new File(caminho);
            if (arquivoSom.exists()) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(arquivoSom);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            }
        } catch (Exception e) { }
    }
}
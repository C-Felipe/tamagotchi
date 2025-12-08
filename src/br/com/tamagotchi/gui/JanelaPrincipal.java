package br.com.tamagotchi.gui;

import br.com.tamagotchi.servicos.TamagotchiService;
import br.com.tamagotchi.util.TempoUtil;
import br.com.tamagotchi.exceptions.AcaoNaoPermitidaException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class JanelaPrincipal extends JFrame {

    private TamagotchiService service;
    private TempoUtil tempoUtil;
    private Point pontoInicialMouse;

    private JPanel painelFundo;

    // Componentes Visuais
    private PainelTamagotchi painelImagem;
    private JLabel lblCarcaca;
    private BarraStatus barFome, barEnergia, barFelicidade, barXP;

    private JLabel lblMensagem;
    private Timer timerMensagem;

    private JLabel lblDigitama;
    private JLabel lblMensagemOvo;

    private String cenarioAtual = "cenario_dia.png";
    private boolean estaDormindo = false;
    private boolean jogoAcabou = false;

    // Caminhos
    private static final String CAMINHO_BOTOES = "src/br/com/tamagotchi/recursos/imagens/botoes/";
    private static final String CAMINHO_FUNDO = "src/br/com/tamagotchi/recursos/imagens/fundo_tamagotchi.png";
    private static final String CAMINHO_CENARIOS = "src/br/com/tamagotchi/recursos/imagens/cenarios/";
    private static final String CAMINHO_DIGITAMA = "src/br/com/tamagotchi/recursos/imagens/digitama.png";

    // Dimensões
    private int larguraBarra = 119; private int alturaBarra = 45;
    private int tamanhoBotao = 90; private int tamanhoPower = 55;
    private int tamanhoDigimon = 350;
    private int larguraCenario = 300; private int alturaCenario = 300;

    // Coordenadas
    private int digimonX = 50, digimonY = 70;
    private int cenarioX = 85, cenarioY = 120;
    private int fomeX = 98, fomeY = 381;
    private int energiaX = 234, energiaY = 381;
    private int felizX = 98, felizY = 426;
    private int xpX = 234, xpY = 426;
    private int btnComerX = 182, btnComerY = 486;
    private int btnBrincarX = 87, btnBrincarY = 474;
    private int btnDormirX = 277, btnDormirY = 474;
    private int btnSairX = 346, btnSairY = 70;

    public JanelaPrincipal() {
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setSize(450, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        service = new TamagotchiService();
        tempoUtil = new TempoUtil(service);

        painelFundo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                String pathCenario = CAMINHO_CENARIOS + cenarioAtual;
                File fCenario = new File(pathCenario);
                if (fCenario.exists()) {
                    g2.drawImage(new ImageIcon(pathCenario).getImage(), cenarioX, cenarioY, larguraCenario, alturaCenario, this);
                } else {
                    g2.setColor(cenarioAtual.contains("noite") ? Color.BLACK : new Color(135, 206, 235));
                    g2.fillRect(cenarioX, cenarioY, larguraCenario, alturaCenario);
                }
            }
        };
        painelFundo.setLayout(null);
        painelFundo.setOpaque(false);
        setContentPane(painelFundo);

        montarInterfaceFixa();

        if (service.carregarJogoSalvo()) {
            iniciarLoopDoJogo();
        } else {
            mostrarDigitama();
        }
    }

    private void montarInterfaceFixa() {
        //Label para mensagens com fundo semi-transparente
        lblMensagem = new JLabel("", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                if (!getText().isEmpty()) {
                    g.setColor(new Color(0, 0, 0, 160));
                    g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                }
                super.paintComponent(g);
            }
        };
        lblMensagem.setFont(new Font("Arial", Font.BOLD, 14));
        lblMensagem.setForeground(Color.WHITE);
        lblMensagem.setBounds(88, 345, 275, 30);
        lblMensagem.setVisible(false);
        painelFundo.add(lblMensagem);

        //Botão Power (Salvar e Sair / Deletar se morto)
        JButton btnSair = criarBotao(CAMINHO_BOTOES + "power.png", "OFF", tamanhoPower, () -> {
            if (service.getTamagotchi() != null &&
                    service.getTamagotchi().getHumor().toString().equalsIgnoreCase("Morto")) {
                service.salvarJogo();
            } else {
                service.salvarJogo();
            }
            System.exit(0);
        });
        btnSair.setBounds(btnSairX, btnSairY, tamanhoPower, tamanhoPower);
        painelFundo.add(btnSair);

        //Botão Comer
        JButton btnComer = criarBotao(CAMINHO_BOTOES + "alimentar.png", "Comer", tamanhoBotao, () -> {
            if (jogoAcabou || service.getTamagotchi() == null) return;

            if (service.getTamagotchi().getFome() == 0) {
                mostrarMensagem("A barriga está cheia!", 2000);
                return;
            }
            mostrarMensagem("Nham nham...", 2000);
            painelImagem.executarAcao(PainelTamagotchi.AcaoVisual.COMER, 2000, () -> {
                try { service.alimentar(); } catch (AcaoNaoPermitidaException e) { mostrarMensagem(e.getMessage(), 2000); }
            });
        });
        btnComer.setBounds(btnComerX, btnComerY, tamanhoBotao, tamanhoBotao);
        painelFundo.add(btnComer);

        //Botão Brincar
        JButton btnBrincar = criarBotao(CAMINHO_BOTOES + "brincar.png", "Brincar", tamanhoBotao,  () -> {
            if (jogoAcabou || service.getTamagotchi() == null) return;

            if (service.getTamagotchi().getEnergia() <= 10) {
                mostrarMensagem("Muito cansado...", 2000);
                return;
            }
            if (service.getTamagotchi().getFome() >= 90) {
                mostrarMensagem("Muita fome para brincar!", 2000);
                return;
            }
            mostrarMensagem("Brincando!", 2000);
            painelImagem.executarAcao(PainelTamagotchi.AcaoVisual.BRINCAR, 2000, () -> {
                try { service.brincar(); } catch (AcaoNaoPermitidaException e) { mostrarMensagem(e.getMessage(), 2000); }
            });
        });
        btnBrincar.setBounds(btnBrincarX, btnBrincarY, tamanhoBotao, tamanhoBotao);
        painelFundo.add(btnBrincar);

        //Botão Dormir
        JButton btnDormir = criarBotao(CAMINHO_BOTOES + "dormir.png", "Dormir", tamanhoBotao, () -> {
            if (jogoAcabou || service.getTamagotchi() == null) return;

            if (service.getTamagotchi().getEnergia() >= 95) {
                mostrarMensagem("Sem sono!", 2000);
                return;
            }

            mostrarMensagem("Zzz... Dormindo...", 10000);
            iniciarAnimacaoSono(10000);
            painelImagem.executarAcao(PainelTamagotchi.AcaoVisual.DORMINDO, 10000, () -> {
                try { service.dormir(); mostrarMensagem("Bom dia! Energia recuperada.", 3000); }
                catch (AcaoNaoPermitidaException e) { mostrarMensagem(e.getMessage(), 3000); }
            });
        });
        btnDormir.setBounds(btnDormirX, btnDormirY, tamanhoBotao, tamanhoBotao);
        painelFundo.add(btnDormir);

        //Barras de Status
        barFome = adicionarBarra(painelFundo, "Fome", Color.RED, fomeX, fomeY);
        barEnergia = adicionarBarra(painelFundo, "Energia", Color.BLUE, energiaX, energiaY);
        barFelicidade = adicionarBarra(painelFundo, "Felicidade", Color.MAGENTA, felizX, felizY);
        barXP = adicionarBarra(painelFundo, "XP", Color.GREEN, xpX, xpY);

        adicionarCarcaca();

        //Painel do Digimon
        painelImagem = new PainelTamagotchi(service.getTamagotchi());
        painelImagem.setBounds(digimonX, digimonY, tamanhoDigimon, tamanhoDigimon);
        painelFundo.add(painelImagem);

        painelFundo.setComponentZOrder(lblMensagem, 0);
    }

    private void mostrarMensagem(String texto, int tempoMs) {
        // Reseta a cor se não for mensagem de emergência
        if (!texto.contains("SOCORRO")) {
            lblMensagem.setForeground(Color.WHITE);
        }

        lblMensagem.setText(texto);
        lblMensagem.setVisible(true);
        lblMensagem.repaint();

        if (timerMensagem != null && timerMensagem.isRunning()) {
            timerMensagem.stop();
        }

        timerMensagem = new Timer(tempoMs, e -> {
            lblMensagem.setVisible(false);
            lblMensagem.setText("");
            ((Timer)e.getSource()).stop();
        });
        timerMensagem.setRepeats(false);
        timerMensagem.start();
    }

    private void mostrarDigitama() {
        lblMensagemOvo = new JLabel("<html><center>Toque no Digitama<br>para nascer!</center></html>", SwingConstants.CENTER);
        lblMensagemOvo.setFont(new Font("Arial", Font.BOLD, 14));
        lblMensagemOvo.setForeground(Color.WHITE);
        lblMensagemOvo.setBounds(135, 140, 200, 40);
        painelFundo.add(lblMensagemOvo);
        painelFundo.setComponentZOrder(lblMensagemOvo, 0);

        ImageIcon iconOvo = new ImageIcon(CAMINHO_DIGITAMA);
        Image imgOvo = iconOvo.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        lblDigitama = new JLabel(new ImageIcon(imgOvo));
        lblDigitama.setBounds(135, 200, 180, 180);
        lblDigitama.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        lblDigitama.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                painelFundo.remove(lblDigitama);
                painelFundo.remove(lblMensagemOvo);
                lblDigitama = null;

                service.incubarOvo();
                jogoAcabou = false;
                iniciarLoopDoJogo();
                painelFundo.repaint();
            }
        });

        painelFundo.add(lblDigitama);
        painelFundo.setComponentZOrder(lblDigitama, 0);

        painelImagem.setVisible(false);
        painelFundo.repaint();
    }

    private void iniciarLoopDoJogo() {
        painelImagem.setVisible(true);
        tempoUtil.iniciar();
        new Timer(100, e -> atualizarTela()).start();
        mostrarMensagem("Olá " + service.getTamagotchi().getNome() + "!", 3000);
    }

    private void adicionarCarcaca() {
        if (lblCarcaca != null) return;
        lblCarcaca = new JLabel();
        File fCarcaca = new File(CAMINHO_FUNDO);
        if (fCarcaca.exists()) {
            ImageIcon iconCarcaca = new ImageIcon(new ImageIcon(CAMINHO_FUNDO).getImage()
                    .getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH));
            lblCarcaca.setIcon(iconCarcaca);
        }
        lblCarcaca.setBounds(0, 0, getWidth(), getHeight());
        painelFundo.add(lblCarcaca);

        // Mantive apenas o movimento da janela, removendo a edição do cenário (Ctrl+Click)
        MouseAdapter adaptadorFundo = new MouseAdapter() {
            public void mousePressed(MouseEvent e) { pontoInicialMouse = e.getPoint(); }
            public void mouseDragged(MouseEvent e) {
                Point atual = e.getLocationOnScreen();
                setLocation(atual.x - pontoInicialMouse.x, atual.y - pontoInicialMouse.y);
            }
        };
        lblCarcaca.addMouseListener(adaptadorFundo);
        lblCarcaca.addMouseMotionListener(adaptadorFundo);
        painelFundo.addMouseListener(adaptadorFundo);
        painelFundo.addMouseMotionListener(adaptadorFundo);
    }

    private void iniciarAnimacaoSono(int duracaoMs) {
        if (estaDormindo) return;
        estaDormindo = true;
        cenarioAtual = "cenario_noite.png";
        repaint();
        Timer timerAcordar = new Timer(duracaoMs, e -> {
            estaDormindo = false;
            cenarioAtual = "cenario_dia.png";
            ((Timer)e.getSource()).stop();
            repaint();
        });
        timerAcordar.setRepeats(false);
        timerAcordar.start();
    }

    private BarraStatus adicionarBarra(JPanel painel, String tipo, Color cor, int x, int y) {
        BarraStatus b = new BarraStatus(service.getTamagotchi(), tipo, cor);
        b.setBounds(x, y, larguraBarra, alturaBarra);
        painel.add(b);
        return b;
    }

    private JButton criarBotao(String icone, String texto, int tamanho, Runnable acao) {
        JButton btn = new JButton();
        File f = new File(icone);
        if (f.exists()) {
            Image img = new ImageIcon(icone).getImage().getScaledInstance(tamanho, tamanho, Image.SCALE_SMOOTH);
            btn.setIcon(new ImageIcon(img));
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);
        } else {
            btn.setText(texto);
            btn.setBackground(Color.WHITE);
            if (tamanho < 50) btn.setFont(new Font("Arial", Font.BOLD, 10));
        }
        btn.addActionListener(e -> {
            if (!estaDormindo) { acao.run(); atualizarTela(); }
        });
        return btn;
    }

    private void atualizarTela() {
        if (painelImagem == null || service.getTamagotchi() == null) return;

        painelImagem.setTamagotchi(service.getTamagotchi());
        barFome.setTamagotchi(service.getTamagotchi());
        barEnergia.setTamagotchi(service.getTamagotchi());
        barFelicidade.setTamagotchi(service.getTamagotchi());
        barXP.setTamagotchi(service.getTamagotchi());

        // Avisos Automáticos (Fome/Sono/Emergência)
        if (lblMensagem.getText().isEmpty()) {
            if (service.getTamagotchi().getFome() >= 100) {
                lblMensagem.setForeground(Color.RED);
                mostrarMensagem("SOCORRO! VOU MORRER DE FOME!", 4000);
            }
            else if (service.getTamagotchi().getFome() >= 80) {
                lblMensagem.setForeground(Color.WHITE);
                mostrarMensagem("Estou com muita fome!", 3000);
            }
            else if (service.getTamagotchi().getEnergia() <= 20) {
                lblMensagem.setForeground(Color.WHITE);
                mostrarMensagem("Estou com muito sono...", 3000);
            }
        }

        // Verificação de Morte
        if (!jogoAcabou && service.getTamagotchi().getHumor().toString().equalsIgnoreCase("Morto")) {
            jogoAcabou = true;
            tempoUtil.parar();
            mostrarMensagem("GAME OVER...", 999999);

            JOptionPane.showMessageDialog(this,
                    "O seu Digimon morreu...\nO jogo continuará mostrando ele até você desligar.",
                    "Game Over",
                    JOptionPane.ERROR_MESSAGE);
        }
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JanelaPrincipal().setVisible(true));
    }
}
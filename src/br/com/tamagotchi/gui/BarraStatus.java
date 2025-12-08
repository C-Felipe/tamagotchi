package br.com.tamagotchi.gui;

import br.com.tamagotchi.entidades.Tamagotchi;
import javax.swing.*;
import java.awt.*;

public class BarraStatus extends JPanel {

    private Tamagotchi tamagotchi;
    private String tipo;
    private Color cor;

    public BarraStatus(Tamagotchi t, String tipo, Color cor) {
        this.tamagotchi = t;
        this.tipo = tipo;
        this.cor = cor;
        setOpaque(false);
    }

    public void setTamagotchi(Tamagotchi t) {
        this.tamagotchi = t;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int valor = 0;

        // Se o tamagotchi existir, busca os valores reais
        if (tamagotchi != null) {
            switch (tipo) {
                case "Fome": valor = tamagotchi.getFome(); break;
                case "Energia": valor = tamagotchi.getEnergia(); break;
                case "Felicidade": valor = tamagotchi.getFelicidade(); break;
                case "XP":
                    // Calcula a porcentagem baseada na meta do nível atual
                    long xpAtual = tamagotchi.getExperiencia();
                    long xpMeta = tamagotchi.getXpNecessario();

                    if (xpMeta > 0) {
                        valor = (int) ((xpAtual * 100) / xpMeta);
                    }
                    break;
            }
        }

        //Desenha o Texto (Label)
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Segoe UI", Font.BOLD, 12));

        String texto = tipo;
        if (tipo.equals("XP")) {
            if (tamagotchi != null) texto = "Lvl " + tamagotchi.getNivel();
            else texto = "Lvl --";
        }

        int alturaTexto = 15;
        g2.drawString(texto, 2, 12);

        //Desenha a Barra
        int yBarra = alturaTexto;
        int larguraTotal = getWidth() - 2;
        int alturaBarra = getHeight() - alturaTexto - 2;

        if (alturaBarra < 5) alturaBarra = 5;

        // Fundo (Cinza Claro)
        g2.setColor(new Color(220, 220, 220));
        g2.fillRoundRect(0, yBarra, larguraTotal, alturaBarra, 8, 8);

        // Preenchimento (Colorido)
        g2.setColor(cor);
        int larguraPreenchida = (int) ((valor / 100.0) * larguraTotal);
        g2.fillRoundRect(0, yBarra, larguraPreenchida, alturaBarra, 8, 8);

        // Borda (Cinza Escuro)
        g2.setColor(new Color(80, 80, 80));
        g2.setStroke(new BasicStroke(1));
        g2.drawRoundRect(0, yBarra, larguraTotal, alturaBarra,8,8);
    }
}
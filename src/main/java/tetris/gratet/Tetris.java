package main.java.tetris.gratet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

public class Tetris extends Canvas {

    private Game game = new Game();
    private final BufferStrategy strategy;

    private final int BOARD_CORNER_X = 300;
    private final int BOARD_CORNER_Y = 50;

    private final InputKlawiatury keyboard = new InputKlawiatury();
    private long ostatniaIteracja = System.currentTimeMillis();

    private static final int PIECE_WIDTH = 20;


    public Tetris() {
        JFrame container = new JFrame("Tetris");
        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(800, 600));
        panel.setLayout(null);

        setBounds(0, 0, 800, 600);
        panel.add(this);
        setIgnoreRepaint(true);

        container.pack();
        container.setResizable(false);
        container.setVisible(true);

        container.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        addKeyListener(keyboard);
        requestFocus();

        createBufferStrategy(2);
        strategy = getBufferStrategy();
    }

    void petlaGry() {
        while (true) {
            if (keyboard.nowaGra()) {
                game = new Game();
                game.startGame();
            }
            if (game.czyGranie()) {

                if (!game.czyPaused()) {
                    petlaTetrisa();
                }
                if (keyboard.pauzaGry()) {
                    game.pauzaGry();
                }
            }
            try {
                Thread.sleep(20);
            } catch (Exception e) { }
            rysuj();
        }
    }

    void petlaTetrisa() {
        if (game.czyOpada()) {
            game.przesunDol();
        } else if (System.currentTimeMillis() - ostatniaIteracja >= game.getOpoznienie()) {
            game.przesunDol();
            ostatniaIteracja = System.currentTimeMillis();
        }
        if (keyboard.obroc()) {
            game.obroc();
        } else if (keyboard.left()) {
            game.przesunLewo();
        } else if (keyboard.right()) {
            game.przesunPrawo();
        } else if (keyboard.opadanie()) {
            game.upusc();
        }
    }

    public void rysuj() {
        Graphics2D g = getGrafika();
        rysujpustaKomorke(g);
        drawHelpBox(g);
        drawPiecePreviewBox(g);

        if (game.czyGranie()) {
            rysujKomorke(g);
            drawPiecePreview(g, game.getNastepnyKlocek().getTypKlocka());

            if (game.czyPaused()) {
                piszStop(g);
            }
        }

        if (game.czyKoniecGry()) {
            rysujKomorke(g);
            piszKonieGry(g);
        }

        piszStatus(g);
        piszZagraj(g);

        g.dispose();
        strategy.show();
    }

    private Graphics2D getGrafika() {
        return (Graphics2D) strategy.getDrawGraphics();
    }

    private void rysujKomorke(Graphics2D g) {
        komorkaPlanszy[][] cells = game.getKomorkiPlanszy();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                rysujKlocka(g, BOARD_CORNER_X + i * 20, BOARD_CORNER_Y + (19 - j) * 20, getKolorKomorki(cells[i][j]));
            }
        }
    }

    private void rysujpustaKomorke(Graphics2D g) {
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, 800, 600);
        g.setColor(Color.GRAY);
        g.drawRect(BOARD_CORNER_X - 1, BOARD_CORNER_Y - 1, 10 * PIECE_WIDTH + 2, 20 * PIECE_WIDTH + 2);
    }

    private void piszStatus(Graphics2D g) {
        g.setFont(new Font("Dialog", Font.PLAIN, 16));
        g.setColor(Color.RED);
        g.drawString(getLevel(), 10, 20);
        g.drawString(getLines(), 10, 40);
        g.drawString(getScore(), 20, 80);
    }

    private void piszKonieGry(Graphics2D g) {
        Font font = new Font("Dialog", Font.PLAIN, 16);
        g.setFont(font);
        g.setColor(Color.RED);
        g.drawString("Koniec Gry", 350, 550);
    }

    private void piszStop(Graphics2D g) {
        Font font = new Font("Dialog", Font.PLAIN, 16);
        g.setFont(font);
        g.setColor(Color.YELLOW);
        g.drawString("Gra zatrzymana", 350, 550);
    }


    private void piszZagraj(Graphics2D g) {
        Font font = new Font("Dialog", Font.PLAIN, 16);
        g.setFont(font);
        g.setColor(Color.RED);
        g.drawString("Zagraj runde", 350, 500);
    }

    private String getLevel() {
        return String.format("Your level: %1s", game.getLevel());
    }

    private String getLines() {
        return String.format("Full lines: %1s", game.getLinia());
    }

    private String getScore() {
        return String.format("Score     %1s", game.getOstatecznyWynik());
    }

    private void drawPiecePreviewBox(Graphics2D g) {
        g.setFont(new Font("Dialog", Font.PLAIN, 16));
        g.setColor(Color.RED);
        g.drawString("Next:", 50, 420);
    }

    private void drawHelpBox(Graphics2D g) {
        g.setFont(new Font("Dialog", Font.PLAIN, 16));
        g.setColor(Color.RED);
        g.drawString("H E L P", 50, 140);
        g.drawString("F1: Pause Game", 10, 160);
        g.drawString("F2: New Game", 10, 180);
        g.drawString("UP: Rotate", 10, 200);
        g.drawString("ARROWS: Move left/right", 10, 220);
        g.drawString("SPACE: Drop", 10, 240);
    }

    private void drawPiecePreview(Graphics2D g, TypKlocka type) {
        for (Point p : type.getPunkt()) {
            rysujKlocka(g, 60 + p.x * PIECE_WIDTH, 380 + (3 - p.y) * 20, getKolorKlocka(type));
        }
    }

    private Color getKolorKomorki(komorkaPlanszy komorkaPlanszy) {
        if (komorkaPlanszy.czyPusta()) {
            return Color.BLACK;
        }
        return getKolorKlocka(komorkaPlanszy.getTypKlocka());
    }

    private Color getKolorKlocka(TypKlocka typKlocka) {
        switch (typKlocka) {
            case I:
                return Color.RED;
            case J:
                return Color.GRAY;
            case L:
                return Color.CYAN;
            case O:
                return Color.BLUE;
            case S:
                return Color.GREEN;
            case Z:
                return Color.ORANGE;
            default:
                return Color.MAGENTA;
        }
    }

    private void rysujKlocka(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        g.fillRect(x, y, PIECE_WIDTH, PIECE_WIDTH);
        g.drawRect(x, y, PIECE_WIDTH, PIECE_WIDTH);
    }

    public static void main(String[] args) {
        new Tetris().petlaGry();
    }

}

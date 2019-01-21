package main.java.tetris.gratet;

import java.awt.*;

/**
 * 20 [ ][ ][ ][X][X][X][X][ ][ ][ ]
 * 19 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 18 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 17 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 16 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 15 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 14 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 13 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 12 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 11 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 10 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 9  [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 8  [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 7  [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 6  [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 5  [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 4  [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 3  [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 2  [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 1  [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 *     1  2  3  4  5  6  7  8  9 10
 */
public class Plansza {

    private static final int D_X = 5;
    private static final int D_Y = 19;

    private static final int SZEROKOSC = 10;
    private static final int WYSOKOSC = 20;

    private Point srodekKlocka = new Point(D_X, D_Y);

    private Klocek aktywnyKlocek;

    private komorkaPlanszy[][] board = new komorkaPlanszy[SZEROKOSC][WYSOKOSC];

    private int pelnaLinia = 0;

    public Plansza() {
        board = stworzPustaTablice();
    }

    public int getSzerokosc() {
        return SZEROKOSC;
    }

    public int getWysokosc() {
        return WYSOKOSC;
    }

    public int getPelnaLinia() {
        return pelnaLinia;
    }

    public komorkaPlanszy getBoardAt(int x, int y) {
        return board[x][y];
    }

    private boolean czyRzadjestpelny(int y) {
        for (int x = 0; x < SZEROKOSC; x++) {
            if (getBoardAt(x, y).czyPusta()) {
                return false;
            }
        }
        return true;
    }

    public void usunPelnyRzad() {
        komorkaPlanszy[][] boardX = stworzPustaTablice();

        int full = 0;
        for (int y = 0; y < WYSOKOSC; y++) {
            if (czyRzadjestpelny(y)) {
                full++;
            } else {
                kopiujRzad(boardX, y, y-full);
            }
        }

        pelnaLinia += full;
        board = boardX;
    }

    private void kopiujRzad(komorkaPlanszy[][] to, int y, int toy) {
        for (int x = 0; x < SZEROKOSC; x++) {
            to[x][toy] = board[x][y];
        }
    }

    private komorkaPlanszy[][] stworzPustaTablice() {
        komorkaPlanszy[][] boardX = new komorkaPlanszy[SZEROKOSC][WYSOKOSC];

        for (int x = 0; x < SZEROKOSC; x++) {
            boardX[x] = komorkaPlanszy.getPustaTablica(WYSOKOSC);
        }
        return boardX;
    }

    public void obroc() {
        Klocek rot = aktywnyKlocek.obroc();
        if (pasuje(rot.getPunkty(), 0, 0)) {

            aktywnyKlocek = rot;
        }
    }

    public void przesunLewo() {
        if (pasuje(aktywnyKlocek.getPunkty(), -1, 0)) {
            mv( -1, 0);
        }
    }

    public void przesunPrawo() {
        if (pasuje(aktywnyKlocek.getPunkty(), 1, 0)) {
            mv(1, 0);
        }
    }

    public boolean czyAktywnyKlocekmozespadac() {
        return pasuje(aktywnyKlocek.getPunkty(), 0, -1);
    }

    public void przesunDol() {
        if (czyAktywnyKlocekmozespadac()) {
            mv(0, -1);
            usunPelnyRzad();
        }
    }

    public boolean pasuje(Point[] points, int moveX, int moveY) {
        for (Point point : points) {
            int x = srodekKlocka.x + point.x + moveX;
            int y = srodekKlocka.y + point.y + moveY;

            if (x < 0 || x >= getSzerokosc() || y >= getWysokosc() || y < 0) {
                return false;
            }

            if (!board[x][y].czyPusta()) {
                return false;
            }
        }

        return true;
    }

    public komorkaPlanszy[][] getBoardWithPiece() {
        komorkaPlanszy[][] dest = new komorkaPlanszy[SZEROKOSC][WYSOKOSC];

        for (int y = 0; y < SZEROKOSC; y++) {
            System.arraycopy(board[y], 0, dest[y], 0, board[0].length);
        }

        // add piece
        for (Point point : aktywnyKlocek.getPunkty()) {
            int x = point.x + srodekKlocka.x;
            int y = point.y + srodekKlocka.y;
            dest[x][y] = komorkaPlanszy.getKomorka(aktywnyKlocek.getTypKlocka());
        }

        return dest;
    }

    private void dodajKlocekdoPlanszy() {
        for (Point point : aktywnyKlocek.getPunkty()) {
            int x = srodekKlocka.x + point.x;
            int y = srodekKlocka.y + point.y;
            board[x][y] = komorkaPlanszy.getKomorka(aktywnyKlocek.getTypKlocka());
        }
    }

    private void mv(int moveX, int moveY) {
        srodekKlocka = new Point(srodekKlocka.x + moveX, srodekKlocka.y + moveY);
    }

    public void setAktywnyKlocek(Klocek klocek) {
        if (aktywnyKlocek != null) {
            dodajKlocekdoPlanszy();
        }
        aktywnyKlocek = klocek;
        resetSrodkaKlocka();
    }

    private void resetSrodkaKlocka() {
        srodekKlocka.x = D_X;
        srodekKlocka.y = D_Y;
    }

}

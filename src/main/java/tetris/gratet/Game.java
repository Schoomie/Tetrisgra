package main.java.tetris.gratet;

public class Game {

    private final Plansza board;
    private Klocek nastepnyKlocek;

    private boolean granie = false;
    private boolean paused = false;
    private boolean opadanie = false;
    private boolean koniecGry = false;

    private int liczbaSwobodnychSpadniec;
    private int OstatecznyWynik;

    public Game() {
        board = new Plansza();
    }

    public komorkaPlanszy[][] getKomorkiPlanszy() {
        return board.getBoardWithPiece();
    }

    public Klocek getNastepnyKlocek() {
        return nastepnyKlocek;
    }

    public long getOpoznienie() {
        return (long) (((11 - getLevel()) * 0.05) * 1000);
    }

    public int getWynik() {
        return ((21 + (3 * getLevel())) - liczbaSwobodnychSpadniec);
    }

    public int getOstatecznyWynik() {
        return OstatecznyWynik;
    }

    public int getLinia() {
        return board.getPelnaLinia();
    }

    public int getLevel() {
        if ((board.getPelnaLinia() >= 1) && (board.getPelnaLinia() <= 90)) {
            return 1 + ((board.getPelnaLinia() - 1) / 10);
        } else if (board.getPelnaLinia() >= 91) {
            return 10;
        } else {
            return 1;
        }
    }

    public void startGame() {
        paused = false;
        opadanie = false;
        nastepnyKlocek = Klocek.getLosowyKlocek();
        board.setAktywnyKlocek(Klocek.getLosowyKlocek());
        granie = true;
    }

    public boolean czyGranie() {
        return granie;
    }

    public boolean czyPaused() {
        return paused;
    }

    public boolean czyKoniecGry() {return koniecGry;}

    public void pauzaGry() {
        paused = !paused;
    }

    public void przesunLewo() {
        board.przesunLewo();
    }

    public void przesunPrawo() {
        board.przesunPrawo();
    }

    public void przesunDol() {
        if (!board.czyAktywnyKlocekmozespadac()) {

            if (liczbaSwobodnychSpadniec == 0) {
                granie = false;
                koniecGry = true;
            } else {
                opadanie = false;
                board.setAktywnyKlocek(nastepnyKlocek);
                nastepnyKlocek = Klocek.getLosowyKlocek();
                OstatecznyWynik += getWynik();
                liczbaSwobodnychSpadniec = 0;
            }
        } else {
            board.przesunDol();
            liczbaSwobodnychSpadniec++;
        }
    }

    public void obroc() {
        board.obroc();
    }

    public void upusc() {
        opadanie = true;
    }

    public boolean czyOpada() {
        return opadanie;
    }

}
package main.java.tetris.gratet;

import java.awt.*;

public class Klocek {

    private final Point punkty[];
    private final TypKlocka typKlocka;
    private final boolean wstepnaOrientacjaKlocka;

    private Klocek(TypKlocka typKlocka, Point[] punkty, boolean initial) {
        wstepnaOrientacjaKlocka = initial;
        this.punkty = punkty;
        this.typKlocka = typKlocka;
    }

    public static Klocek getLosowyKlocek() {
        TypKlocka typKlocka = TypKlocka.getLosowyKlocek();
        return new Klocek(typKlocka, typKlocka.getPunkt(), true);
    }

    public static Klocek getKlocek(TypKlocka typKlocka) {
        return new Klocek(typKlocka, typKlocka.getPunkt(), true);
    }

    public TypKlocka getTypKlocka() {
        return typKlocka;
    }

    public Point[] getPunkty() {
        return punkty;
    }

    public Klocek obroc() {
        if (typKlocka.getMaxOrientacje() == 0) {
            return this;
        } else if (typKlocka.getMaxOrientacje() == 2) {
            if (wstepnaOrientacjaKlocka) {
                return new Klocek(typKlocka, obrocPrawo(punkty), false);
            } else {
                return new Klocek(typKlocka, obrocLewo(punkty), true);
            }
        }
        return new Klocek(typKlocka, obrocPrawo(punkty), true);
    }

    private Point[] obrocLewo(Point toRotate[]) {
        return obroc(toRotate, 1, -1);
    }

    private Point[] obrocPrawo(Point toRotate[]) {
        return obroc(toRotate, -1, 1);
    }

    private Point[] obroc(Point toRotate[], int x, int y) {
        Point rotated[] = new Point[4];

        for (int i = 0; i < 4; i++) {
            int temp = toRotate[i].x;
            rotated[i] = new Point(x * toRotate[i].y, y * temp);
        }

        return rotated;
    }

}
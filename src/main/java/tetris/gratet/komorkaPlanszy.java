package main.java.tetris.gratet;

import java.util.Arrays;

public class komorkaPlanszy {

    private final TypKlocka typKlocka;

    private komorkaPlanszy() {
        typKlocka = null;
    }

    private komorkaPlanszy(TypKlocka type) {
        typKlocka = type;
    }

    public boolean czyPusta() {
        return typKlocka == null;
    }

    public TypKlocka getTypKlocka() {
        return typKlocka;
    }

    public static komorkaPlanszy getKomorka(TypKlocka typKlocka) {
        return new komorkaPlanszy(typKlocka);
    }

    public static komorkaPlanszy[] getPustaTablica(int size) {
        komorkaPlanszy[] cells = new komorkaPlanszy[size];
        Arrays.fill(cells, new komorkaPlanszy());
        return cells;
    }

}

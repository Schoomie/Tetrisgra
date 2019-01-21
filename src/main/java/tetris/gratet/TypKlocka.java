package main.java.tetris.gratet;

import java.awt.*;
import java.util.Random;

public enum TypKlocka {
    
    O(0, p(-1, 0), p(0, 0),  p(-1, -1), p(0, -1)),
    I(2, p(-2, 0), p(-1, 0), p(0, 0),   p(1, 0)),
    S(2, p(0, 0),  p(1, 0),  p(-1, -1), p(0, -1)),
    Z(2, p(-1, 0), p(0, 0),  p(0, -1),  p(1, -1)),
    L(4, p(-1, 0), p(0, 0),  p(1, 0),   p(-1, -1)),
    J(4, p(-1, 0), p(0, 0),  p(1, 0),   p(1, -1)),
    T(4, p(-1, 0), p(0, 0),  p(1, 0),   p(0, -1));

    private static final Random random = new Random();
    private final int maxOrientations;
    private final Point points[];

    TypKlocka(int maxOrientations, Point... points) {
        this.maxOrientations = maxOrientations;
        this.points = points;
    }

    public static TypKlocka getLosowyKlocek() {
        return TypKlocka.values()[random.nextInt(TypKlocka.values().length)];
    }

    public Point[] getPunkt() {
        return points;
    }

    public int getMaxOrientacje() {
        return maxOrientations;
    }

    private static Point p(int x, int y) {
        return new Point(x, y);
    }
}

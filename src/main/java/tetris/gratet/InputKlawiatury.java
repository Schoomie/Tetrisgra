package main.java.tetris.gratet;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InputKlawiatury implements KeyListener {

    private final Map<Integer, Boolean> currentStates = new ConcurrentHashMap<Integer, Boolean>();

    public InputKlawiatury() {
        currentStates.put(KeyEvent.VK_LEFT, Boolean.FALSE);
        currentStates.put(KeyEvent.VK_RIGHT, Boolean.FALSE);
        currentStates.put(KeyEvent.VK_UP, Boolean.FALSE);
        currentStates.put(KeyEvent.VK_SPACE, Boolean.FALSE);
        currentStates.put(KeyEvent.VK_F2, Boolean.FALSE);
        currentStates.put(KeyEvent.VK_F1, Boolean.FALSE);
    }

    public boolean left() {
        return dol(KeyEvent.VK_LEFT);
    }

    public boolean right() {
        return dol(KeyEvent.VK_RIGHT);
    }

    public boolean opadanie() {
        return dol(KeyEvent.VK_SPACE);
    }

    public boolean obroc() {
        return dol(KeyEvent.VK_UP);
    }

    public boolean pauzaGry() {
        return dol(KeyEvent.VK_F1);
    }

    public boolean nowaGra() {
        return dol(KeyEvent.VK_F2);
    }

    private boolean dol(int keyCode) {
        return currentStates.put(keyCode, Boolean.FALSE);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (currentStates.containsKey(keyEvent.getKeyCode())) {
            currentStates.put(keyEvent.getKeyCode(), Boolean.TRUE);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
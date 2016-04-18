package game;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Simon on 2016-04-16.
 */
public class Controller extends JComponent implements Runnable {
    private KeyListener inputListener;

    private Thread gameThread;

    private final View view = new View();

    int key;


    public Controller() {
        this.inputListener = new InputListener();
    }

    public class InputListener implements KeyListener {
        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            System.out.println(e.getKeyCode());
        }

        public void keyReleased(KeyEvent e) {

        }
    }

    public void startGame(){
        this.addKeyListener(this.inputListener);
        this.gameThread = new Thread(this);
        this.gameThread.start();

    }


    public void run(){
        while(true){

        }
    }

}

package game;



import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.*;
import com.jme3.input.InputManager;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Simon on 2016-04-16.
 */
public class Controller implements Runnable {
    private KeyListener inputListener;
    //InputManager inputManager = new InputManager();
    private Thread gameThread;


    private final View view = new View();

    private int key;


    public Controller() {


    }

    private void initKeys() {
        // You can map one or several inputs to one named action
        //inputManager.addMapping("Up",  new KeyTrigger(KeyInput.KEY_W));
        //inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        //inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        //inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        // Add the names to the action listener.
        //inputManager.addListener(analogListener,"Left", "Right", "Down", "Up");

    }

    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Pause") && !keyPressed) {

            }
        }
    };

    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float value, float tpf) {

                if (name.equals("Up")) {
                    System.out.println("up");
                }
                if (name.equals("Right")) {
                    System.out.println("right");
                }
                if (name.equals("Left")) {
                    System.out.println("left");
                }
                if (name.equals("Down")){
                    System.out.println("Down");
                }
            }

    };




    public void startGame() {

        this.gameThread = new Thread(this);
        this.gameThread.start();


    }


    public void run() {
        while (true) {

        }
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }
}

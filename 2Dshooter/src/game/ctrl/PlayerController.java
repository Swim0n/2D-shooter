package game.ctrl;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import game.core.Player;
import game.core.World;
import game.gameView.*;

/**
 * Created by Simon on 2016-04-26.
 */
public class PlayerController extends BetterCharacterControl implements ActionListener {

    protected final PlayerView view;
    protected final BulletView bulletView;
    private InputManager inputManager;
    private boolean left,right,up,down,gunLeft,gunRight;
    protected Player playerData;
    protected float speed;
    protected Vector3f lastDirection = new Vector3f(0f,0f,20f); //last direction this player moved, start value is a placeholder until real movement
    protected GUIView niftyView;

    private boolean paused = true;

    public PlayerController(PlayerView view, float radius, float height, float mass, GUIView niftyView, World world){
        super(radius, height, mass);
        this.view = view;
        this.inputManager = view.getInputManager();
        this.bulletView = new BulletView(view.getGameView(), view);

        if(view.getPlayerNode().equals(view.getGameView().getPlayer1Node())) {
            this.playerData = world.getPlayer1();
        } else if (view.getPlayerNode().equals(view.getGameView().getPlayer2Node())) {
            this.playerData = world.getPlayer2();
        }
        this.speed = playerData.getSpeed();
        this.niftyView = niftyView;
    }

    public void setupKeys() {
        inputManager.addMapping("resetGame",new KeyTrigger(KeyInput.KEY_F8));
        inputManager.addListener(this, "resetGame");
        if(view.getPlayerNode().equals(view.getGameView().getPlayer1Node())) {
            inputManager.addMapping("p1left", new KeyTrigger(KeyInput.KEY_LEFT));
            inputManager.addMapping("p1right", new KeyTrigger(KeyInput.KEY_RIGHT));
            inputManager.addMapping("p1up", new KeyTrigger(KeyInput.KEY_UP));
            inputManager.addMapping("p1down", new KeyTrigger(KeyInput.KEY_DOWN));
            inputManager.addMapping("p1shoot", new KeyTrigger(KeyInput.KEY_NUMPAD5), new KeyTrigger(KeyInput.KEY_RETURN));
            inputManager.addMapping("p1GunLeft", new KeyTrigger(KeyInput.KEY_O),new KeyTrigger(KeyInput.KEY_NUMPAD4));
            inputManager.addMapping("p1GunRight", new KeyTrigger(KeyInput.KEY_P),new KeyTrigger(KeyInput.KEY_NUMPAD6));

            inputManager.addListener(this, "p1left");
            inputManager.addListener(this, "p1right");
            inputManager.addListener(this, "p1up");
            inputManager.addListener(this, "p1down");
            inputManager.addListener(this, "p1shoot");
            inputManager.addListener(this, "p1GunLeft");
            inputManager.addListener(this, "p1GunRight");
        }
        if(view.getPlayerNode().equals(view.getGameView().getPlayer2Node())) {
            inputManager.addMapping("p2left", new KeyTrigger(KeyInput.KEY_A));
            inputManager.addMapping("p2right", new KeyTrigger(KeyInput.KEY_D));
            inputManager.addMapping("p2up", new KeyTrigger(KeyInput.KEY_W));
            inputManager.addMapping("p2down", new KeyTrigger(KeyInput.KEY_S));
            inputManager.addMapping("p2shoot", new KeyTrigger(KeyInput.KEY_SPACE),new KeyTrigger(KeyInput.KEY_H));
            inputManager.addMapping("p2GunLeft", new KeyTrigger(KeyInput.KEY_G));
            inputManager.addMapping("p2GunRight", new KeyTrigger(KeyInput.KEY_J));
            inputManager.addListener(this, "p2left");
            inputManager.addListener(this, "p2right");
            inputManager.addListener(this, "p2up");
            inputManager.addListener(this, "p2down");
            inputManager.addListener(this, "p2shoot");
            inputManager.addListener(this, "p2GunLeft");
            inputManager.addListener(this, "p2GunRight");
        }
    }

    @Override
    public void update(float tpf) {
        if(this.paused){
            setWalkDirection(lastDirection.set(0f,0f,0f));
            return;
        }
        super.update(tpf);
        bulletView.getGameView().updateGUI();
        speed = playerData.getSpeed();
        if (playerData.getHealth()==0){

            if(view.getGameView().getPlayer1Control().getPlayerData().getHealth()==0){
                view.getGameView().getPlayer2Control().getPlayerData().incWins();
            }else view.getGameView().getPlayer1Control().getPlayerData().incWins();

            view.getGameView().getPlayer1Control().resetPlayer();
            view.getGameView().getPlayer2Control().resetPlayer();
            System.out.println("P1 wins: "+view.getGameView().getPlayer1Control().getPlayerData().getWins()+
                    "\nP2 wins: "+view.getGameView().getPlayer2Control().getPlayerData().getWins());
        }
        if (!left && !right && !up && !down){
            setWalkDirection(new Vector3f(0f,0f,0f));
        }
        if (left){
            setWalkDirection(lastDirection.set(speed*-1f,0f,0f));
        }
        if (right){
            setWalkDirection(lastDirection.set(speed*1f,0f,0f));
        }
        if (up){
            setWalkDirection(lastDirection.set(0f,0f,speed*1f));
        }
        if (down){
            setWalkDirection(lastDirection.set(0f,0f,speed*-1f));
        }
        if (left && up){
            setWalkDirection(lastDirection.set(speed*-0.707f,0f,speed*0.707f));
        }
        if (left && down){
            setWalkDirection(lastDirection.set(speed*-0.707f,0f,speed*-0.707f));
        }
        if (right && up){
            setWalkDirection(lastDirection.set(speed*0.707f,0f,speed*0.707f));
        }
        if (right && down){
            setWalkDirection(lastDirection.set(speed*0.707f,0f,speed*-0.707f));
        }
        if (gunLeft){
            view.rotateGun(tpf*-140f);
        }
        if(gunRight){
            view.rotateGun(tpf*140f);
        }
            warp(new Vector3f(location.getX(),-2f, location.getZ()));
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        //resetting the game to its original state
        if (name.equals("resetGame") && !isPressed){
            this.resetPlayer();
        }
        //movement of player
        if(view.getPlayerNode().equals(view.getGameView().getPlayer1Node())){
            if (name.equals("p1left")) {
                left = isPressed;
            } else if (name.equals("p1right")) {
                right = isPressed;
            } else if (name.equals("p1up")) {
                up = isPressed;
            } else if (name.equals("p1down")) {
                down = isPressed;
            }
            if (name.equals("p1shoot") && isPressed){
                shootBullet();
            }
            if(name.equals("p1GunLeft")){
                gunLeft = isPressed;
            }else if(name.equals("p1GunRight")){
                gunRight = isPressed;
            }
        }
        else if(view.getPlayerNode().equals(view.getGameView().getPlayer2Node())){
            if (name.equals("p2left")) {
                left = isPressed;
            } else if (name.equals("p2right")) {
                right = isPressed;
            } else if (name.equals("p2up")) {
                up = isPressed;
            } else if (name.equals("p2down")) {
                down = isPressed;
            }
            if (name.equals("p2shoot") && isPressed){
                shootBullet();
            }
            if(name.equals("p2GunLeft")){
                gunLeft = isPressed;
            }else if(name.equals("p2GunRight")){
                gunRight = isPressed;
            }
        }
    }

    public void takeDamage(float damage){
        playerData.setHealth(playerData.getHealth() - damage);
        view.setHealthBar(playerData.getHealth());
        niftyView.updateText();
    }

    public void removeMappings(){
        inputManager.reset();
    }

    public void pause(){
        this.paused = true;
    }

    public void unpause(){
        this.paused = false;
    }
    public void resetPlayer(){
        this.warp(new Vector3f(view.getStartPos()));
        this.view.setHealthBar(100);
        this.playerData.setStandard();
        this.niftyView.updateText();
    }
    public Player getPlayerData(){
        return this.playerData;
    }

    //creates a new bullet specific to the player who fired it
    public void shootBullet(){
        bulletView.createBullet();
    }
}


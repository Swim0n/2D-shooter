package game.ctrl;


import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import game.core.Player;
import game.gameView.*;


/**
 * Created by Simon on 2016-04-26.
 */
public class PlayerController extends BetterCharacterControl implements ActionListener {

    private final PlayerView view;
    private final BulletView bulletView;
    private InputManager inputManager;
    private boolean left,right,up,down,gunLeft,gunRight;
    private Player playerData = new Player();
    private float speed;
    private Vector3f lastDirection = new Vector3f(0f,0f,20f); //last direction this player moved, start value is a placeholder until real movement
    private float lastRotation;
    private Quaternion gunRot = new Quaternion();
    private Vector3f axisY=Vector3f.UNIT_Y;//for rotation around Y-axis

    public PlayerController(PlayerView view, float radius, float height, float mass){
        super(radius, height, mass);
        this.view = view;
        this.inputManager = view.getInputManager();
        this.bulletView = new BulletView(view.getGameView(), this, view);
        setupKeys();
        this.playerData.setStandard();
        speed = playerData.getSpeed();
    }

    private void setupKeys() {
        if(view instanceof Player1View) {
            inputManager.addMapping("p1left", new KeyTrigger(KeyInput.KEY_LEFT));
            inputManager.addMapping("p1right", new KeyTrigger(KeyInput.KEY_RIGHT));
            inputManager.addMapping("p1up", new KeyTrigger(KeyInput.KEY_UP));
            inputManager.addMapping("p1down", new KeyTrigger(KeyInput.KEY_DOWN));
            inputManager.addMapping("p1shoot", new KeyTrigger(KeyInput.KEY_NUMPAD0), new KeyTrigger(KeyInput.KEY_RETURN));
            inputManager.addMapping("p1GunLeft", new KeyTrigger(KeyInput.KEY_O));
            inputManager.addMapping("p1GunRight", new KeyTrigger(KeyInput.KEY_P));

            inputManager.addListener(this, "p1left");
            inputManager.addListener(this, "p1right");
            inputManager.addListener(this, "p1up");
            inputManager.addListener(this, "p1down");
            inputManager.addListener(this, "p1shoot");
            inputManager.addListener(this, "p1GunLeft");
            inputManager.addListener(this, "p1GunRight");
        }
        if(view instanceof Player2View) {
            inputManager.addMapping("p2left", new KeyTrigger(KeyInput.KEY_A));
            inputManager.addMapping("p2right", new KeyTrigger(KeyInput.KEY_D));
            inputManager.addMapping("p2up", new KeyTrigger(KeyInput.KEY_W));
            inputManager.addMapping("p2down", new KeyTrigger(KeyInput.KEY_S));
            inputManager.addMapping("p2shoot", new KeyTrigger(KeyInput.KEY_SPACE));
            inputManager.addMapping("p2GunLeft", new KeyTrigger(KeyInput.KEY_G));
            inputManager.addMapping("p2GunRight", new KeyTrigger(KeyInput.KEY_H));
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
        super.update(tpf);
        speed = playerData.getSpeed();
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

            //for some reason, everything gets reset when releasing the rotation button. Everrything else works fine
            gunRot = spatial.getLocalRotation();
            Vector3f direction = gunRot.getRotationColumn(2);
            gunRot.getRotationColumn(2);
            spatial.setLocalRotation(gunRot);
            spatial.updateModelBound();
            gunRot.fromAngleAxis(FastMath.PI*lastRotation/180, axisY);

            view.getPlayer().updateGeometricState();


           // spatial.rotate(0, FastMath.PI*lastRotation/180, 0);
            //spatial.rotate(0, lastRotation-0.1f, 0);
            lastRotation -= 2f;
        }
        if(gunRight){
            gunRot = spatial.getLocalRotation();
            gunRot.fromAngleAxis(FastMath.PI*lastRotation/180, axisY);
            spatial.setLocalRotation(gunRot);

            //  Vector3f translation = new Vector3f(2,0,0);

            //spatial.setLocalTranslation(gunRot.mult(translation));
            //spatial.setLocalRotation(gunRot);
            //spatial.rotate(0, FastMath.PI*lastRotation/180, 0);
            //spatial.rotate(0, lastRotation+0.1f, 0);
            lastRotation += 2f;
            view.getPlayer().updateGeometricState();
            spatial.updateModelBound();

        }

            warp(new Vector3f(location.getX(),-2f, location.getZ()));
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        //movement of player
        if(view instanceof Player1View){
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
        else if(view instanceof Player2View){
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
        playerData.setHeath(playerData.getHealth() - damage);

    }

    public Player getPlayerData(){
        return this.playerData;
    }



    //creates a new bullet specific to the player who fired it
    public void shootBullet(){
        bulletView.createBullet();
    }

    //last direction the player moved, used by bullets
    public Vector3f getLastDirection(){return this.lastDirection;}
    //last rotation of the player
    public Quaternion getGunRotation(){return gunRot;}
    public float getLastRotation(){return FastMath.PI*lastRotation/180;}
    public BulletView getBulletView(){return this.bulletView;}
}


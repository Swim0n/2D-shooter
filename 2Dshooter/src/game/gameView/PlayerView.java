package game.gameView;

import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.BillboardControl;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import game.utils.Utils;

/**
 * Created by Simon on 2016-04-26.
 */
public class PlayerView {
    private final Geometry pipe;
    private final Node pipeNode = new Node("pipeNode");
    private Geometry player;
    private Geometry gun;
    private Geometry backgroundBar;
    private Geometry healthBar;
    private AssetManager assetManager;
    private GameView gameView;
    private Node playerNode;
    private Node gunNode = new Node("gun");
    private InputManager inputManager;
    private float lastRotation;
    private Quaternion gunRot = new Quaternion();
    private Vector3f axisY=Vector3f.UNIT_Y;//for rotation around Y-axis

    //colorRGBA is just a placeholder until textures are in place
    public PlayerView(AssetManager assetManager, Node playerNode, GameView gameView, ColorRGBA colorRGBA, Vector3f startPos){
        this.assetManager = assetManager;
        this.gameView = gameView;
        this.playerNode = playerNode;
        this.inputManager = gameView.getInputManager();

        //creating player
        player = Utils.getBox(1f,1f,1f);
        player.setMaterial(Utils.getMaterial(assetManager,colorRGBA));
        playerNode.setLocalTranslation(startPos);
        playerNode.attachChild(player);

        //creating gun attached to player
        gun = Utils.getBox(0.3f,0.3f,1.5f);
        gun.setMaterial(Utils.getMaterial(assetManager,colorRGBA.DarkGray));
        gun.setLocalTranslation(0,0,0.75f);
        gunNode.attachChild(gun);
        playerNode.attachChild(gunNode);

        //creating pipe for bullet exit
        pipe = Utils.getBox(0.1f,0.1f,0.2f);
        pipe.setMaterial(Utils.getMaterial(assetManager,colorRGBA.Green));
        pipeNode.setLocalTranslation(0f,0.3f,3f);
        pipe.setLocalTranslation(0f,0.3f,-0.4f);
        pipeNode.attachChild(pipe);
        gunNode.attachChild(pipeNode);

        //creating a health bar
        BillboardControl backCtrl = new BillboardControl();
        BillboardControl healthCtrl = new BillboardControl();
        backgroundBar = new Geometry("backgroundBar", new Quad(4f, 0.6f));
        healthBar = new Geometry("healthBar", new Quad(4f, 0.6f));
        backgroundBar.setMaterial(Utils.getMaterial(assetManager,colorRGBA.Red));
        healthBar.setMaterial(Utils.getMaterial(assetManager,colorRGBA.Green));
        backgroundBar.rotate(FastMath.HALF_PI,0,0);
        healthBar.rotate(FastMath.HALF_PI,0,0);
        backgroundBar.center();
        healthBar.center();
        backgroundBar.move(0,0,3f);
        healthBar.move(0,0,3f);
        backgroundBar.addControl(backCtrl);
        healthBar.addControl(healthCtrl);
        playerNode.attachChild(backgroundBar);
        playerNode.attachChild(healthBar);
    }

    public void rotateGun(float step){
        lastRotation += step;
        gunRot.fromAngleAxis(FastMath.PI*lastRotation/180, axisY);
        gunNode.setLocalRotation(gunRot);
    }

    public void setHealthBar(float percent){
        if(percent != 0){
            healthBar.setLocalScale(percent/100, 1f, 1f);
        } else {
            healthBar.setLocalScale(-0.1f, 1f, 1f);
        }

    }

    public InputManager getInputManager(){return this.inputManager;}
    public Node getPlayerNode(){return this.playerNode;}
    public Vector3f getPipePos(){return this.pipeNode.getWorldTranslation();}
    public GameView getGameView(){return this.gameView;}
    public Quaternion getGunRotation(){return gunRot;}


}

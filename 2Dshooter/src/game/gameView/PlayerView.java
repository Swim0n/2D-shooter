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
import com.jme3.scene.shape.Box;
import game.utils.Utils;

/**
 * Created by Simon on 2016-04-26.
 */
public class PlayerView {
    private final Geometry pipe;
    private final Node pipeNode = new Node("pipeNode");
    private Geometry player;
    private Geometry gun;
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
    }

    public void rotateGun(float step){
        lastRotation += step;
        gunRot.fromAngleAxis(FastMath.PI*lastRotation/180, axisY);
        gunNode.setLocalRotation(gunRot);
    }

    public InputManager getInputManager(){return this.inputManager;}
    public Node getPlayerNode(){return this.playerNode;}
    public Vector3f getPipePos(){return this.pipeNode.getWorldTranslation();}
    public GameView getGameView(){return this.gameView;}
    public Quaternion getGunRotation(){return gunRot;}


}

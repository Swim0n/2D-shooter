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

/**
 * Created by Simon on 2016-04-26.
 */
public class PlayerView {
    private final Box pipeShape;
    private final Geometry pipe;
    private final Material pipeMaterial;
    private final Node pipeNode = new Node("pipeNode");
    private Node gunPipe = new Node("gunPipe");
    private Geometry player;
    private Geometry gun;
    private Box playerShape;
    private Box gunShape;
    private Material playerMaterial;
    private Material gunMaterial;
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
        playerShape = new Box(1f,1f,1f);

        player = new Geometry("Box", playerShape);
        playerMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        playerNode.setLocalTranslation(startPos);
        playerMaterial.setColor("Color", colorRGBA);
        player.setMaterial(playerMaterial);
        playerNode.attachChild(player);

        //creating gun attached to player
        gunShape = new Box(0.3f,0.3f,1.5f);

        gun = new Geometry("Box", gunShape);
        gunMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        gun.setLocalTranslation(0,0,0.75f);

        gunMaterial.setColor("Color", ColorRGBA.DarkGray);
        gun.setMaterial(gunMaterial);
        gunNode.attachChild(gun);
        playerNode.attachChild(gunNode);

        //creating pipe for bullet exit
        pipeShape = new Box(0.1f,0.1f,0.2f);

        pipe = new Geometry("Box", pipeShape);
        pipeMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

        pipeMaterial.setColor("Color", ColorRGBA.Green);
        pipe.setMaterial(pipeMaterial);
        pipe.setLocalTranslation(0f,0.3f,-0.4f);
        pipeNode.setLocalTranslation(0,0.3f,3f);

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

package game.gameView;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.input.InputManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/**
 * Created by Simon on 2016-04-26.
 */
public class PlayerView {
    protected Geometry player;
    protected Geometry gun;
    protected Box playerShape;
    protected Box gunShape;
    protected Material playerMaterial;
    protected Material gunMaterial;
    protected AssetManager assetManager;
    protected GameView gameView;
    protected Node playerNode;
    protected Node gunNode = new Node("guns");
    protected InputManager inputManager;
    protected float lastRotation;
    protected Quaternion gunRot = new Quaternion();
    protected Vector3f axisY=Vector3f.UNIT_Y;//for rotation around Y-axis

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
        gunShape = new Box(0.3f,0.1f,1.5f);

        gun = new Geometry("Box", gunShape);
        gunMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        gun.setLocalTranslation(0,0,0.75f);

        gunMaterial.setColor("Color", ColorRGBA.DarkGray);
        gun.setMaterial(gunMaterial);
        gunNode.attachChild(gun);
        playerNode.attachChild(gunNode);
    }

    public void rotateGun(float step){
        lastRotation += step;
        gunNode.setModelBound(new BoundingBox());
        gunRot.fromAngleAxis(FastMath.PI*lastRotation/180, axisY);
        gunNode.setLocalRotation(gunRot);
        gunNode.updateModelBound();
    }

    public InputManager getInputManager(){return this.inputManager;}
    public Geometry getPlayer(){return this.player;}
    public Spatial getPlayerNode(){return this.playerNode;}
    public GameView getGameView(){return this.gameView;}
    public Quaternion getGunRotation(){return gunRot;}


}

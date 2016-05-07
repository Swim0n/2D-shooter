package game.gameView;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.input.InputManager;
import com.jme3.material.Material;
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
public abstract class PlayerView {
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

    public PlayerView(AssetManager assetManager, Node playerNode, GameView gameView){
        this.assetManager = assetManager;
        this.gameView = gameView;
        this.playerNode = playerNode;
        this.inputManager = gameView.getInputManager();
    }

    public void rotateGun(float step){
        lastRotation += step;
        gunNode.setModelBound(new BoundingBox());
        gunRot.fromAngleAxis(FastMath.PI*lastRotation/180, axisY);
        gunNode.setLocalRotation(gunRot);
        gunNode.updateModelBound();
    }
    public void createPlayer(){}
    public void createGun(){}

    public InputManager getInputManager(){
        return this.inputManager;
    }
    public Geometry getPlayer(){
        return this.player;
    }
    public Spatial getPlayerNode(){return this.playerNode;}
    public GameView getGameView(){return this.gameView;}
    public Quaternion getGunRotation(){return gunRot;}


}

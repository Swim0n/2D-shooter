package gameView;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import core.Ground;

/**
 * Created by Hannes on 24/04/2016.
 */
public class GroundView {

    private Geometry groundGeom;
    private Node stageNode;
    private AssetManager assetManager;
    private Quad groundShape;
    private BulletAppState bulletAppState;
    private Ground groundModel = new Ground();

    GroundView(GameView gameView){
        this.stageNode = gameView.getStageNode();
        this.assetManager = gameView.getAssetManager();
        this.bulletAppState = gameView.getBulletAppState();
        createGround();
        applyPhysics();
    }
    private void createGround(){

        groundShape = new Quad(groundModel.getWidth(), groundModel.getHeight()); //quad to represent ground in java
        groundGeom= new Geometry("Ground",groundShape); //geometry to represent ground
        groundGeom.setMaterial(assetManager.loadMaterial("Materials/block2mat.j3m"));
        groundGeom.rotate(FastMath.HALF_PI,0,0);
        groundGeom.setLocalTranslation(-groundShape.getWidth()/2, 0, -groundShape.getHeight()/2);
        stageNode.attachChild(groundGeom);
    }
    private void applyPhysics(){
        RigidBodyControl groundPhy = new RigidBodyControl(0);
        groundGeom.addControl(groundPhy);
        bulletAppState.getPhysicsSpace().add(groundPhy);
    }
    Geometry getGroundGeom(){return this.groundGeom;}
    Quad getGroundShape(){return this.groundShape;}
}

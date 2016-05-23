package gameView;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import core.Ground;
import utils.ApplicationAssets;

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

    GroundView(ApplicationAssets appAssets){
        this.stageNode = appAssets.getStageNode();
        this.assetManager = appAssets.getAssetManager();
        this.bulletAppState = appAssets.getBulletAppState();
        createGround();
        applyPhysics();
    }
    private void createGround(){

        groundShape = new Quad(groundModel.getWidth(), groundModel.getHeight()); //quad to represent ground in java
        groundGeom= new Geometry("Ground",groundShape); //geometry to represent ground
        Material groundMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"); //material for ground
        Texture dirt = assetManager.loadTexture("Textures/dirt.jpg");
        dirt.setWrap(Texture.WrapMode.Repeat);
        groundMat.setTexture("ColorMap", dirt);
        groundGeom.setMaterial(groundMat);
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

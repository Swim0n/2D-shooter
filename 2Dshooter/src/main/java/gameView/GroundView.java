package gameView;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.BillboardControl;
import com.jme3.scene.shape.Quad;
import core.World;

/**
 * Created by Hannes on 24/04/2016.
 */
public class GroundView {

    private Geometry groundGeom;
    private Geometry backgroundGeom;
    private Material backgroundMaterial;
    private Node stageNode;
    private Node backgroundNode = new Node("background");
    private AssetManager assetManager;
    private Quad groundShape;
    private Quad backgroundShape;
    private BulletAppState bulletAppState;
    private World world;

    GroundView(GameView gameView){
        this.stageNode = gameView.getStageNode();
        this.assetManager = gameView.getAssetManager();
        this.bulletAppState = gameView.getBulletAppState();
        this.world = gameView.getWorld();
        createGround();
        createBackground();
        applyPhysics();
    }
    private void createGround(){

        groundShape = new Quad(world.getWidth(), world.getHeight()); //quad to represent ground in java
        groundGeom= new Geometry("Ground",groundShape); //geometry to represent ground
        groundGeom.setMaterial(assetManager.loadMaterial("Materials/block2mat.j3m"));
        groundGeom.rotate(FastMath.HALF_PI,0,0);
        groundGeom.setLocalTranslation(-groundShape.getWidth()/2, 0, -groundShape.getHeight()/2);
        stageNode.attachChild(groundGeom);
    }

    private void createBackground(){
        backgroundShape = new Quad(world.getWidth()*4, world.getHeight()*4);
        backgroundGeom = new Geometry("Background", backgroundShape);
        backgroundMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        backgroundMaterial.setTexture("ColorMap", assetManager.loadTexture("Textures/background.jpg"));
        backgroundGeom.setMaterial(backgroundMaterial);
        backgroundGeom.center();
        backgroundNode.attachChild(backgroundGeom);
        backgroundNode.setShadowMode(RenderQueue.ShadowMode.Off);
        BillboardControl backgroundCtrl = new BillboardControl();
        backgroundNode.move(0,70,30);
        backgroundNode.addControl(backgroundCtrl);
        stageNode.attachChild(backgroundNode);
    }
    private void applyPhysics(){
        RigidBodyControl groundPhy = new RigidBodyControl(0);
        groundGeom.addControl(groundPhy);
        bulletAppState.getPhysicsSpace().add(groundPhy);
    }
    Geometry getGroundGeom(){return this.groundGeom;}
    Quad getGroundShape(){return this.groundShape;}
}

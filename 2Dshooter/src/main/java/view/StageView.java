package view;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.BillboardControl;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import core.World;

/**
 * Initializes the platform and the background space picture
 */
public class StageView {
    private Node stageNode;
    private AssetManager assetManager;
    private BulletAppState bulletAppState;
    private World world;

    public StageView(WorldView worldView){
        this.stageNode = worldView.getStageNode();
        this.assetManager = worldView.getAssetManager();
        this.bulletAppState = worldView.getBulletAppState();
        this.world = worldView.getWorld();
        createGround();
        createWalls();
        createBackground();
    }
    private void createGround(){
        Quad groundShape = new Quad(world.getWidth()+2, world.getHeight()+2); //quad to represent ground in java
        Geometry groundGeom= new Geometry("Ground",groundShape); //geometry to represent ground
        groundGeom.setMaterial(assetManager.loadMaterial("Materials/block2mat.j3m"));
        groundGeom.rotate(FastMath.HALF_PI,0,0);
        groundGeom.setLocalTranslation(-groundShape.getWidth()/2, 0, -groundShape.getHeight()/2);
        RigidBodyControl groundPhy = new RigidBodyControl(0);
        groundGeom.addControl(groundPhy);
        bulletAppState.getPhysicsSpace().add(groundPhy);
        stageNode.attachChild(groundGeom);
    }

    private void createWalls(){
        Box verticalWallShape = new Box(0.5f,3f,27f);
        Box horizontalWallShape = new Box(36f, 3f,0.5f);
        Material wallMaterial = assetManager.loadMaterial("Materials/block2mat.j3m");
        Geometry[] walls = new Geometry[4];
        walls[0] = new Geometry("westWall", verticalWallShape);
        walls[1] = new Geometry("eastWall", verticalWallShape);
        walls[2] = new Geometry("northWall", horizontalWallShape);
        walls[3] = new Geometry("southWall", horizontalWallShape);
        walls[0].setLocalTranslation(-world.getWidth()/2-1,-2f,0);
        walls[1].setLocalTranslation(world.getWidth()/2+1,-2f,0);
        walls[2].setLocalTranslation(0,-2f,world.getHeight()/2);
        walls[3].setLocalTranslation(0,-2f,-world.getHeight()/2);
        for(int i = 0; i < walls.length; i++){
            walls[i].setMaterial(wallMaterial);
            RigidBodyControl wallPhy = new RigidBodyControl(0);
            walls[i].addControl(wallPhy);
            bulletAppState.getPhysicsSpace().add(wallPhy);
            stageNode.attachChild(walls[i]);
        }
    }

    private void createBackground(){
        Quad backgroundShape = new Quad(world.getWidth()*4, world.getHeight()*4);
        Geometry backgroundGeom = new Geometry("Background", backgroundShape);
        Material backgroundMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        backgroundMaterial.setTexture("ColorMap", assetManager.loadTexture("Textures/background.jpg"));
        backgroundGeom.setMaterial(backgroundMaterial);
        backgroundGeom.center();
        Node backgroundNode = new Node("background");
        backgroundNode.attachChild(backgroundGeom);
        backgroundNode.setShadowMode(RenderQueue.ShadowMode.Off);
        BillboardControl backgroundCtrl = new BillboardControl();
        backgroundNode.move(0,70,30);
        backgroundNode.addControl(backgroundCtrl);
        stageNode.attachChild(backgroundNode);
    }
}

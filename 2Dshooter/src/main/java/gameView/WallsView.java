package gameView;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import core.World;

/**
 * Created by Hannes on 24/04/2016.
 */
public class WallsView {
    private Box verticalWallShape;
    private Box horizontalWallShape;
    private Material wallMaterial;


    private Geometry westWall;
    private Geometry eastWall;
    private Geometry northWall;
    private Geometry southWall;
    private final AssetManager assetManager;
    private final Node stageNode;

    private World world;

    private BulletAppState bulletAppState;

    public WallsView (GameView gameView){
        this.assetManager = gameView.getAssetManager();
        this.stageNode = gameView.getStageNode();
        this.bulletAppState = gameView.getBulletAppState();
        this.world = gameView.getWorld();
        createWalls();
        applyPhysics();
    }

    private void createWalls(){
        verticalWallShape = new Box(0.5f,3f,27f);
        horizontalWallShape = new Box(36f, 3f,0.5f);
        wallMaterial = assetManager.loadMaterial("Materials/block2mat.j3m");
        westWall = new Geometry("westWall", verticalWallShape);
        eastWall = new Geometry("eastWall", verticalWallShape);
        northWall = new Geometry("northWall", horizontalWallShape);
        southWall = new Geometry("southWall", horizontalWallShape);
        westWall.setLocalTranslation(-world.getWidth()/2-1,-2f,0);
        eastWall.setLocalTranslation(world.getWidth()/2+1,-2f,0);
        northWall.setLocalTranslation(0,-2f,world.getHeight()/2);
        southWall.setLocalTranslation(0,-2f,-world.getHeight()/2);

        westWall.setMaterial(wallMaterial);
        eastWall.setMaterial(wallMaterial);
        northWall.setMaterial(wallMaterial);
        southWall.setMaterial(wallMaterial);

        stageNode.attachChild(eastWall);
        stageNode.attachChild(northWall);
        stageNode.attachChild(southWall);
        stageNode.attachChild(westWall);
    }
    private void applyPhysics(){
        RigidBodyControl eWallPhy = new RigidBodyControl(0);
        RigidBodyControl wWallPhy = new RigidBodyControl(0);
        RigidBodyControl nWallPhy = new RigidBodyControl(0);
        RigidBodyControl sWallPhy = new RigidBodyControl(0);
        westWall.addControl(wWallPhy);
        northWall.addControl(nWallPhy);
        eastWall.addControl(eWallPhy);
        southWall.addControl(sWallPhy);
        bulletAppState.getPhysicsSpace().add(eWallPhy);
        bulletAppState.getPhysicsSpace().add(wWallPhy);
        bulletAppState.getPhysicsSpace().add(nWallPhy);
        bulletAppState.getPhysicsSpace().add(sWallPhy);
    }
}

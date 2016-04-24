package game.gameView;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import jme3tools.optimize.GeometryBatchFactory;

/**
 * Created by Hannes on 24/04/2016.
 */
public class WallsView {
    private Box verticalWallShape;
    private Box horizontalWallShape;
    private Material wallMaterial;

    private Spatial walls;
    private Geometry westWall;
    private Geometry eastWall;
    private Geometry northWall;
    private Geometry southWall;
    private final AssetManager assetManager;
    private final Node rootNode;
    private final Geometry groundGeom;

    public WallsView (AssetManager assetManager, Node rootNode, Geometry groundGeom){
        this.assetManager = assetManager;
        this.rootNode = rootNode;
        this.groundGeom = groundGeom;
    }



    public void createWalls(){
        verticalWallShape = new Box(0.5f,2,27f);
        horizontalWallShape = new Box(36f, 2,0.5f);
        wallMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture brick = assetManager.loadTexture("Textures/BrickWall.jpg");
        brick.setWrap(Texture.WrapMode.Repeat);
        wallMaterial.setTexture("ColorMap", brick);
        westWall = new Geometry("westWall", verticalWallShape);
        eastWall = new Geometry("eastWall", verticalWallShape);
        northWall = new Geometry("northWall", horizontalWallShape);
        southWall = new Geometry("southWall", horizontalWallShape);
        westWall.setLocalTranslation(-groundGeom.getLocalTranslation().x,0.5f,0);
        eastWall.setLocalTranslation(groundGeom.getLocalTranslation().x,0.5f,0);
        northWall.setLocalTranslation(0,0.5f,groundGeom.getLocalTranslation().z);
        southWall.setLocalTranslation(0,0.5f,-groundGeom.getLocalTranslation().z);

        westWall.setMaterial(wallMaterial);
        eastWall.setMaterial(wallMaterial);
        northWall.setMaterial(wallMaterial);
        southWall.setMaterial(wallMaterial);

        Node wallNode = new Node();
        wallNode.attachChild(westWall);
        wallNode.attachChild(eastWall);
        wallNode.attachChild(northWall);
        wallNode.attachChild(southWall);
        walls = GeometryBatchFactory.optimize(wallNode);
        rootNode.attachChild(walls);

    }

    public Spatial getWalls(){
        return this.walls;
    }



}

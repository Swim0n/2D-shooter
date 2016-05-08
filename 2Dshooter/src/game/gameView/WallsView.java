package game.gameView;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

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
    private final Geometry groundGeom;

    public WallsView (AssetManager assetManager, Node stageNode, Geometry groundGeom){
        this.assetManager = assetManager;
        this.stageNode = stageNode;
        this.groundGeom = groundGeom;
    }



    public void createWalls(){
        verticalWallShape = new Box(0.5f,5f,27f);
        horizontalWallShape = new Box(36f, 5f,0.5f);
        wallMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture brick = assetManager.loadTexture("Textures/BrickWall.jpg");
        brick.setWrap(Texture.WrapMode.Repeat);
        wallMaterial.setTexture("ColorMap", brick);
        westWall = new Geometry("westWall", verticalWallShape);
        eastWall = new Geometry("eastWall", verticalWallShape);
        northWall = new Geometry("northWall", horizontalWallShape);
        southWall = new Geometry("southWall", horizontalWallShape);
        westWall.setLocalTranslation(-groundGeom.getLocalTranslation().x+1f,-2f,0);
        eastWall.setLocalTranslation(groundGeom.getLocalTranslation().x-1f,-2f,0);
        northWall.setLocalTranslation(0,-2f,groundGeom.getLocalTranslation().z-1f);
        southWall.setLocalTranslation(0,-2f,-groundGeom.getLocalTranslation().z+1f);

        westWall.setMaterial(wallMaterial);
        eastWall.setMaterial(wallMaterial);
        northWall.setMaterial(wallMaterial);
        southWall.setMaterial(wallMaterial);



        stageNode.attachChild(eastWall);
        stageNode.attachChild(northWall);
        stageNode.attachChild(southWall);
        stageNode.attachChild(westWall);

    }


    public Spatial getWest(){
        return this.westWall;
    }
    public Spatial getEast(){
        return this.eastWall;
    }
    public Spatial getNorth(){
        return this.northWall;
    }
    public Spatial getSouth(){ return this.southWall; }



}

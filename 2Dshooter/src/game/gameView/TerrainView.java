package game.gameView;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

import java.util.Random;

/**
 * Created by Simon on 2016-05-03.
 */
public class TerrainView {
    private AssetManager assetManager;
    private GroundView groundView;
    private Node stageNode;

    private float groundX;
    private float groundZ;

    private Box rockShape;
    private Geometry rock;
    private Material rockMaterial;
    private Box treeShape;
    private Geometry tree;
    private Material treeMaterial;

    private Geometry[][] terrainGrid;

    private final static Random randomGenerator = new Random();

    public TerrainView(Application application, Node stageNode, GroundView groundView) {
        this.assetManager = application.getAssetManager();
        this.stageNode = stageNode;
        this.groundView = groundView;
        this.groundX = groundView.getGroundShape().getWidth();
        this.groundZ = groundView.getGroundShape().getHeight();
        terrainGrid = new Geometry[(int)groundX][(int)groundZ];
    }

    public void createTerrain(int rocks, int trees){
        rockShape = new Box(2f,2f,2f);
        rockMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        rockMaterial.setColor("Color", ColorRGBA.DarkGray);

        for (int i = 0; i < rocks; i++){
            int x = randomGenerator.nextInt((int) (groundX-4)/4+2);
            int z = randomGenerator.nextInt((int) (groundZ-4)/4+1);
            while(true){
                if(terrainGrid[x][z] != null){
                    x = randomGenerator.nextInt((int) (groundX-4)/4+2);
                    z = randomGenerator.nextInt((int) (groundZ-4)/4+1);
                } else {
                    break;
                }
            }
            rock = new Geometry("Rock", rockShape);
            rock.setMaterial(rockMaterial);
            terrainGrid[x][z] = rock;
            rock.setLocalTranslation(-groundX/2+rockShape.getXExtent()+x*4,-2, -groundZ/2+rockShape.getZExtent()+z*4);
            stageNode.attachChild(rock);
        }

        treeShape = new Box(2f,2f,2f);
        treeMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        treeMaterial.setColor("Color", ColorRGBA.Green);

        for (int i = 0; i < trees; i++){
            int x = randomGenerator.nextInt((int) (groundX-4)/4);
            int z = randomGenerator.nextInt((int) (groundZ-4)/4);
            while(true){
                if(terrainGrid[x][z] != null){
                    x = randomGenerator.nextInt((int) (groundX-4)/4);
                    z = randomGenerator.nextInt((int) (groundZ-4)/4);
                } else {
                    break;
                }
            }
            tree = new Geometry("Tree", treeShape);
            tree.setMaterial(treeMaterial);
            terrainGrid[x][z] = tree;
            tree.setLocalTranslation(-groundX/2+treeShape.getXExtent()+x*4,-2, -groundZ/2+treeShape.getZExtent()+z*4);
            stageNode.attachChild(tree);
        }



    }

    public Geometry[][] getTerrainGrid(){
        return this.terrainGrid;
    }
}

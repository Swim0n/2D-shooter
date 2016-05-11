package game.gameView;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import game.core.World;

import java.util.Random;

/**
 * Created by Simon on 2016-05-03.
 */
public class TerrainView {
    private AssetManager assetManager;
    private GroundView groundView;
    private Node terrainNode;

    private float groundX;
    private float groundZ;

    private Box rockShape;
    private Geometry rock;
    private Material rockMaterial;
    private Box treeShape;
    private Geometry tree;
    private Material treeMaterial;
    private World world;

    private Geometry[][] terrainGrid;

    private final static Random randomGenerator = new Random();

    public TerrainView(Application application, Node terrainNode, GroundView groundView, World world) {
        this.assetManager = application.getAssetManager();
        this.terrainNode = terrainNode;
        this.groundView = groundView;
        this.groundX = groundView.getGroundShape().getWidth();
        this.groundZ = groundView.getGroundShape().getHeight();
        this.world = world;
        terrainGrid = new Geometry[(int)groundX][(int)groundZ];
        world.getTerrain().setPositionsAmount((int)groundX,(int)groundZ);
    }

    public void createTerrain(){
        rockShape = new Box(2f,2f,2f);
        rockMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        rockMaterial.setColor("Color", ColorRGBA.DarkGray);

        for (int i = 0; i < world.getTerrain().getRocksAmount(); i++){
            int[] position = world.getTerrain().getRandomPos(groundX, groundZ, 4, 4);
            rock = new Geometry("Rock", rockShape);
            rock.setMaterial(rockMaterial);
            terrainGrid[position[0]][position[1]] = rock;
            rock.setLocalTranslation(-groundX/2+rockShape.getXExtent()+position[0]*4,-2, -groundZ/2+rockShape.getZExtent()+position[1]*4+0.5f);
            terrainNode.attachChild(rock);
        }

        treeShape = new Box(2f,2f,2f);
        treeMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        treeMaterial.setColor("Color", ColorRGBA.Green);

        for (int i = 0; i < world.getTerrain().getTreesAmount(); i++){
            int[] position = world.getTerrain().getRandomPos(groundX, groundZ, 4, 4);
            tree = new Geometry("Tree", treeShape);
            tree.setMaterial(treeMaterial);
            terrainGrid[position[0]][position[1]] = tree;
            tree.setLocalTranslation(-groundX/2+treeShape.getXExtent()+position[0]*4,-2, -groundZ/2+treeShape.getZExtent()+position[1]*4+0.5f);
            terrainNode.attachChild(tree);
        }



    }

    public Geometry[][] getTerrainGrid(){
        return this.terrainGrid;
    }
}

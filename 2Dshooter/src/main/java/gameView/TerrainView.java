package gameView;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import core.World;
import utils.ApplicationAssets;
import utils.Utils;

import java.util.ArrayList;

/**
 * Created by Simon on 2016-05-03.
 */
public class TerrainView {
    private AssetManager assetManager;
    private Node terrainNode;

    private float groundX;
    private float groundZ;
    private float tileWidth;
    private float tileHeight;

    private Box rockShape;
    private Geometry rock;
    private Material rockMaterial;
    private Box treeShape;
    private Geometry tree;
    private Material treeMaterial;
    private World world;

    private BulletAppState bulletAppState;

    private ArrayList<Geometry> terrainGrid;


    public TerrainView(ApplicationAssets appAssets, float tileWidth, float tileHeight) {
        this.assetManager = appAssets.getAssetManager();
        this.terrainNode = appAssets.getTerrainNode();
        this.groundX = appAssets.getGameView().getGroundSize().getWidth();
        this.groundZ = appAssets.getGameView().getGroundSize().getHeight();
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.world = appAssets.getWorld();
        this.bulletAppState = appAssets.getBulletAppState();
        this.terrainGrid = new ArrayList<Geometry>();
        world.getTerrain().setPositions(groundX, groundZ, tileWidth, tileHeight);
        createTerrain();
        applyPhysics();
    }

    private void createTerrain(){
        rockShape = new Box(tileWidth/2,2f,tileHeight/2);
        rockMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        rockMaterial.setColor("Color", ColorRGBA.DarkGray);

        for (int i = 0; i < world.getTerrain().getRocksAmount(); i++){
            Vector3f position = Utils.vecMathToJMEVector3f(world.getTerrain().getRandomPos());
            rock = new Geometry("Rock", rockShape);
            rock.setMaterial(rockMaterial);
            terrainGrid.add(rock);
            rock.setLocalTranslation(position.getX(),-2, position.getZ());
            terrainNode.attachChild(rock);
        }

        treeShape = new Box(tileWidth/2,2f,tileHeight/2);
        treeMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        treeMaterial.setColor("Color", ColorRGBA.Green);

        for (int i = 0; i < world.getTerrain().getTreesAmount(); i++){
            Vector3f position = Utils.vecMathToJMEVector3f(world.getTerrain().getRandomPos());
            tree = new Geometry("Tree", treeShape);
            tree.setMaterial(treeMaterial);
            terrainGrid.add(tree);
            tree.setLocalTranslation(position.getX(),-2, position.getZ());
            terrainNode.attachChild(tree);
        }
    }

    private void applyPhysics(){
        for (int i = 0; i < terrainGrid.size(); i++){
            RigidBodyControl terrainPhy = new RigidBodyControl(0);
            terrainGrid.get(i).addControl(terrainPhy);
            bulletAppState.getPhysicsSpace().add(terrainPhy);
        }
    }
}

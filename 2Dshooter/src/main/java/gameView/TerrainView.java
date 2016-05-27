package gameView;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
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

    private World world;

    private BulletAppState bulletAppState;

    private ArrayList<Spatial> terrainGrid;


    public TerrainView(ApplicationAssets appAssets, float tileWidth, float tileHeight) {
        this.assetManager = appAssets.getAssetManager();
        this.terrainNode = appAssets.getTerrainNode();
        this.groundX = appAssets.getGameView().getGroundSize().getWidth();
        this.groundZ = appAssets.getGameView().getGroundSize().getHeight();
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.world = appAssets.getWorld();
        this.bulletAppState = appAssets.getBulletAppState();
        this.terrainGrid = new ArrayList<Spatial>();
        world.getTerrain().setPositions(groundX, groundZ, tileWidth, tileHeight);
        createTerrain();
        applyPhysics();
    }

    private void createTerrain(){
        for (int i = 0; i < world.getTerrain().getRocksAmount(); i++){
            Vector3f position = Utils.vecMathToJMEVector3f(world.getTerrain().getRandomPos());
            Spatial rock = assetManager.loadModel("Models/block.mesh.xml");
            rock.setMaterial(assetManager.loadMaterial("Materials/block1mat.j3m"));
            terrainGrid.add(rock);
            rock.setLocalTranslation(position.getX(),-2, position.getZ());
            rock.rotate(FastMath.PI,0,0);
            rock.scale(2);
            rock.move(-4,0,0);
            terrainNode.attachChild(rock);
        }
        for (int i = 0; i < world.getTerrain().getTreesAmount(); i++){
            Vector3f position = Utils.vecMathToJMEVector3f(world.getTerrain().getRandomPos());
            Spatial tree = assetManager.loadModel("Models/block.mesh.xml");
            tree.setMaterial(assetManager.loadMaterial("Materials/block2mat.j3m"));
            terrainGrid.add(tree);
            tree.setLocalTranslation(position.getX(),-2, position.getZ());
            tree.rotate(FastMath.PI,0,0);
            tree.scale(2);
            tree.move(-4,0,0);
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
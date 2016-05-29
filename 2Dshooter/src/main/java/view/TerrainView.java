package view;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import core.World;
import jME3.utils.Utils;
import java.util.ArrayList;

/**
 * Initializes randomly generated terrain
 */
public class TerrainView {

    private float groundX;
    private float groundZ;
    private float tileWidth;
    private float tileHeight;

    private World world;
    private Node terrainNode;
    private ArrayList<Spatial> terrainGrid;
    private BulletAppState bulletAppState;
    private AssetManager assetManager;

    public TerrainView(WorldView worldView, float tileWidth, float tileHeight) {
        this.assetManager = worldView.getAssetManager();
        this.terrainNode = worldView.getTerrainNode();
        this.world = worldView.getWorld();
        this.bulletAppState = worldView.getBulletAppState();
        this.terrainGrid = new ArrayList<>();
        world.getTerrain().setPositions(world.getWidth(), world.getHeight(), tileWidth, tileHeight);
        this.groundX = worldView.getWorld().getWidth();
        this.groundZ = worldView.getWorld().getHeight();
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        world.getTerrain().setPositions(groundX, groundZ, tileWidth, tileHeight);
        createTerrain();
    }

    private void createTerrain(){
        for (int i = 0; i < world.getTerrain().getRocksAmount(); i++){
            Vector3f position = Utils.vecMathToJMEVector3f(world.getTerrain().getRandomPos(true));
            world.getTerrain().getTileByCoords((int) position.getX(), (int) position.getZ()).setBlocked(true);
            Spatial rock = assetManager.loadModel("Models/block2.mesh.xml");
            rock.setMaterial(assetManager.loadMaterial("Materials/block1mat.j3m"));
            terrainGrid.add(rock);
            rock.setLocalTranslation(position.getX(),-2, position.getZ());
            rock.rotate(FastMath.PI,0,0);
            rock.scale(2);
            rock.move(1,0,0);
            terrainNode.attachChild(rock);

        }
        for (int i = 0; i < world.getTerrain().getTreesAmount(); i++){
            Vector3f position = Utils.vecMathToJMEVector3f(world.getTerrain().getRandomPos(true));
            world.getTerrain().getTileByCoords((int) position.getX(), (int) position.getZ()).setBlocked(true);
            Spatial tree = assetManager.loadModel("Models/block2.mesh.xml");
            tree.setMaterial(assetManager.loadMaterial("Materials/block2mat.j3m"));
            terrainGrid.add(tree);
            tree.setLocalTranslation(position.getX(),-2, position.getZ());
            tree.rotate(FastMath.PI,0,0);
            tree.scale(2);
            tree.move(1,0,0);
            terrainNode.attachChild(tree);
        }
        for (int i = 0; i < terrainGrid.size(); i++){
            RigidBodyControl terrainPhy = new RigidBodyControl(0);
            terrainGrid.get(i).addControl(terrainPhy);
            bulletAppState.getPhysicsSpace().add(terrainPhy);
        }
    }
}

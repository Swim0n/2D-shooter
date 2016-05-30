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
        createTerrain();
    }

    private void createTerrain(){
        for (int i = 0; i < world.getTerrain().getRocksAmount(); i++){
            Vector3f position = Utils.vecMathToJMEVector3f(world.getTerrain().getRandomPos(true));
            world.getTerrain().getTileByCoords((int) position.getX(), (int) position.getZ()).setBlocked(true);
            Spatial block1 = assetManager.loadModel("Models/block1.mesh.xml");
            block1.setMaterial(assetManager.loadMaterial("Materials/block1mat.j3m"));
            terrainGrid.add(block1);
            block1.setLocalTranslation(position.getX(),-2, position.getZ());
            block1.rotate(FastMath.PI,0,0);
            block1.scale(2);
            block1.move(1,0,0);
            terrainNode.attachChild(block1);

        }
        for (int i = 0; i < world.getTerrain().getTreesAmount(); i++){
            Vector3f position = Utils.vecMathToJMEVector3f(world.getTerrain().getRandomPos(true));
            world.getTerrain().getTileByCoords((int) position.getX(), (int) position.getZ()).setBlocked(true);
            Spatial block2 = assetManager.loadModel("Models/block2.mesh.xml");
            block2.setMaterial(assetManager.loadMaterial("Materials/block2mat.j3m"));
            terrainGrid.add(block2);
            block2.setLocalTranslation(position.getX(),-2, position.getZ());
            block2.rotate(FastMath.PI,0,0);
            block2.scale(2);
            block2.move(1,0,0);
            terrainNode.attachChild(block2);
        }
        for (int i = 0; i < terrainGrid.size(); i++){
            RigidBodyControl terrainPhy = new RigidBodyControl(0);
            terrainGrid.get(i).addControl(terrainPhy);
            bulletAppState.getPhysicsSpace().add(terrainPhy);
        }
    }
}

package game.gameView;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import game.ctrl.PlayerController;
import jme3tools.optimize.GeometryBatchFactory;


/**
 * Created by David on 2016-04-18.
 */
public class GameView extends SimpleApplication {

    //variables for physics control
    private RigidBodyControl wallsPhy;
    private RigidBodyControl groundPhy;
    private PlayerController playerControl;
    private BulletAppState bulletAppState;


    //variables for viewer classes
    private WallsView wallsView;
    private GroundView groundView;
    private PlayerView playerView;

    //private World world = new World();

    public void simpleInitApp() {

        //camera settings
        cam.setLocation(new Vector3f(0,-65f,0));
        cam.lookAtDirection(new Vector3f(0,1,0), new Vector3f(0,0,1));
        getFlyByCamera().setEnabled(false);
        getFlyByCamera().setMoveSpeed(50);

        //turn off stats gameView (you can leave it on, if you want)
        setDisplayStatView(true);
        setDisplayFps(true);

        //set up physics
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        bulletAppState.setDebugEnabled(true);

        //creating a "ground floor" for levels
        groundView = new GroundView(getAssetManager(),getRootNode());
        groundView.createGround();

        //adding walls for the surface
        wallsView = new WallsView(getAssetManager(), getRootNode(), groundView.getGroundGeom());
        wallsView.createWalls();

        //spawning player1
        playerView = new PlayerView(getAssetManager(), getRootNode(), this);
        playerView.createPlayer();

        //adding collision-detection to map walls, not working properly <--- still?
        wallCollisionControl();

        //adding collision-detection to ground
        groundCollisionControl();

        //adding collision-detection to player.
        playerCollisionControl();

        //nullify gravity
        bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0, 0, 0));
    }


    public void groundCollisionControl(){
        groundPhy = new RigidBodyControl(0);
        groundView.getGroundGeom().addControl(groundPhy);
        bulletAppState.getPhysicsSpace().add(groundPhy);
    }

    public void wallCollisionControl(){
        wallsPhy = new RigidBodyControl(0);
        wallsView.getWalls().addControl(wallsPhy);
        bulletAppState.getPhysicsSpace().add(wallsPhy);
    }
    public void playerCollisionControl(){
        playerControl = new PlayerController(playerView,1f,4f,1f);
        playerView.getPlayer().addControl(playerControl);
        bulletAppState.getPhysicsSpace().add(playerControl);
    }

    public void simpleUpdate(float tpf){

    }
}

package game.gameView;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import game.ctrl.BulletController;
import game.ctrl.Player1Controller;
import game.ctrl.Player2Controller;
import game.ctrl.PlayerController;


/**
 * Created by David on 2016-04-18.
 */
public class GameView extends SimpleApplication {

    //variables for physics control
    private RigidBodyControl wallsPhy;
    private RigidBodyControl groundPhy;
    private RigidBodyControl bulletPhy;
    private PlayerController player1Control;
    private PlayerController player2Control;
    private BulletAppState bulletAppState;


    //variables for viewer classes
    private WallsView wallsView;
    private GroundView groundView;
    private Player1View player1View;
    private Player2View player2View;


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
        player1View = new Player1View(getAssetManager(), getRootNode(), this);
        player1View.createPlayer();

        //spawning player2
        player2View = new Player2View(getAssetManager(), getRootNode(), this);
        player2View.createPlayer();

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

    public void bulletCollisionControl(Geometry bullet, PlayerController playerController){
        bulletPhy = new BulletController();
        bullet.addControl(bulletPhy);

        bulletPhy.setLinearVelocity(playerController.getLastDirection().mult(speed*2));
        bulletAppState.getPhysicsSpace().add(bulletPhy);
    }

    public void playerCollisionControl(){
        player1Control = new Player1Controller(player1View,1f,4f,1f);
        player2Control = new Player2Controller(player2View,1f,4f,1f);
        player1View.getPlayer().addControl(player1Control);
        bulletAppState.getPhysicsSpace().add(player1Control);
        player2View.getPlayer().addControl(player2Control);
        bulletAppState.getPhysicsSpace().add(player2Control);
    }

    public void simpleUpdate(float tpf){

    }
}

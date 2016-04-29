package game.gameView;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import game.ctrl.BulletController;
import game.ctrl.PlayerController;


/**
 * Created by David on 2016-04-18.
 */
public class GameView extends SimpleApplication {

    //variables for physics control
    private RigidBodyControl eWallPhy;
    private RigidBodyControl wWallPhy;
    private RigidBodyControl nWallPhy;
    private RigidBodyControl sWallPhy;
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
    private PowerupView powerupView;

    private Node bulletNode;
    private Node stageNode;


    //private World world = new World();

    public void simpleInitApp() {

        //camera settings
        cam.setLocation(new Vector3f(0,-65f,0));
        cam.lookAtDirection(new Vector3f(0,1,0), new Vector3f(0,0,1));
        getFlyByCamera().setEnabled(false);
        getFlyByCamera().setMoveSpeed(50);

        bulletNode = new Node("bullets");
        stageNode = new Node("stage");
        rootNode.attachChild(bulletNode);
        rootNode.attachChild(stageNode);

        //turn off stats gameView (you can leave it on, if you want)
        setDisplayStatView(true);
        setDisplayFps(true);

        //set up physics
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        bulletAppState.setDebugEnabled(true);

        //creating a "ground floor" for levels
        groundView = new GroundView(getAssetManager(),stageNode);
        groundView.createGround();



        //adding walls for the surface
        wallsView = new WallsView(getAssetManager(), stageNode, groundView.getGroundGeom());
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

        //adding collision control to the world


        //nullify gravity
        bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0, 0, 0));

        //spawn a power-up of type speed
        powerupView = new PowerupView(this,stageNode,groundView);
        powerupView.createPowerup("speed");

    }

    public Quad getGroundSize(){
        return groundView.getGroundShape();
    }




    //CollisionControl methods to give objects physic bodies and properties
    public void groundCollisionControl(){
        groundPhy = new RigidBodyControl(0);
        groundView.getGroundGeom().addControl(groundPhy);
        bulletAppState.getPhysicsSpace().add(groundPhy);
    }

    public void wallCollisionControl(){
        eWallPhy = new RigidBodyControl(0);
        wWallPhy = new RigidBodyControl(0);
        nWallPhy = new RigidBodyControl(0);
        sWallPhy = new RigidBodyControl(0);
        wallsView.getWest().addControl(wWallPhy);
        wallsView.getNorth().addControl(nWallPhy);
        wallsView.getEast().addControl(eWallPhy);
        wallsView.getSouth().addControl(sWallPhy);
        bulletAppState.getPhysicsSpace().add(eWallPhy);
        bulletAppState.getPhysicsSpace().add(wWallPhy);
        bulletAppState.getPhysicsSpace().add(nWallPhy);
        bulletAppState.getPhysicsSpace().add(sWallPhy);
    }

    public void bulletCollisionControl(BulletView bulletView, Spatial bullet){
        bulletPhy = new BulletController(bulletView);
        bullet.addControl(bulletPhy);
        bulletPhy.setLinearVelocity(bulletView.getPlayerController().getLastDirection().mult(speed*2));
        bulletAppState.getPhysicsSpace().add(bulletPhy);
    }

    public void playerCollisionControl(){
        player1Control = new PlayerController(player1View,1f,4f,1f);
        player2Control = new PlayerController(player2View,1f,4f,1f);
        player1View.getPlayer().addControl(player1Control);
        bulletAppState.getPhysicsSpace().add(player1Control);
        player2View.getPlayer().addControl(player2Control);
        bulletAppState.getPhysicsSpace().add(player2Control);
    }

    public Node getBulletNode(){
        return this.bulletNode;
    }

    public void simpleUpdate(float tpf){

    }

    public Node getStageNode(){
        return stageNode;
    }

    public BulletAppState getBulletAppState(){
        return this.bulletAppState;
    }

    public WallsView getWallsView(){
        return wallsView;
    }
}

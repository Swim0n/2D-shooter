package game.gameView;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import game.core.World;
import game.ctrl.AIPlayerController;
import game.ctrl.BulletController;
import game.ctrl.PlayerController;

/**
 * Created by David on 2016-04-18.
 */
public class GameView extends SimpleApplication implements ScreenController{

    //variables for physics control
    private RigidBodyControl eWallPhy;
    private RigidBodyControl wWallPhy;
    private RigidBodyControl nWallPhy;
    private RigidBodyControl sWallPhy;
    private RigidBodyControl groundPhy;
    private RigidBodyControl bulletPhy;
    private PlayerController player1Control;
    private PlayerController player2Control;
    private PlayerController player2ControlSave;
    private AIPlayerController player2AIControl;
    private BulletAppState bulletAppState;

    //variables for viewer classes
    private WallsView wallsView;
    private GroundView groundView;
    private PlayerView player1View;
    private PlayerView player2View;
    private PowerupView powerupView;
    private TerrainView terrainView;

    private Node bulletNode;
    private Node stageNode;
    private Node terrainNode;
    private Node player1Node;
    private Node player2Node;

    private World world;

    //variables for gui
    private NiftyJmeDisplay niftyDisplay;
    private Nifty nifty;
    private GUIView niftyView;

    private boolean ai = true;
    private boolean paused = true;


    //private World world = new World();

    public void simpleInitApp() {
        //gui initialization
        niftyDisplay = new NiftyJmeDisplay(assetManager,
                inputManager,
                audioRenderer,
                guiViewPort);
        nifty = niftyDisplay.getNifty();

        nifty.fromXml("Interface/screen.xml", "start", this);
        guiViewPort.addProcessor(niftyDisplay);

        niftyView = (GUIView) nifty.getCurrentScreen().getScreenController();
        niftyView.setNiftyDisp(niftyDisplay);
        niftyView.setGameView(this);

        //camera settings
        flyCam.setEnabled(false);
        cam.setLocation(new Vector3f(0f,-80f,0));
        cam.lookAtDirection(new Vector3f(0,1,0), new Vector3f(0,0,1));
        getFlyByCamera().setEnabled(false);
        getFlyByCamera().setMoveSpeed(50);

        //init nodes
        bulletNode = new Node("bullets");
        stageNode = new Node("stage");
        terrainNode = new Node("terrain");
        player1Node = new Node("player1");
        player2Node = new Node("player2");
        rootNode.attachChild(player1Node);
        rootNode.attachChild(player2Node);
        rootNode.attachChild(bulletNode);
        rootNode.attachChild(stageNode);
        rootNode.attachChild(terrainNode);

        world = new World(20, 12);

        //turn off stats gameView (you can leave it on, if you want)
        setDisplayStatView(true);
        setDisplayFps(true);

        //set up physics
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        bulletAppState.setDebugEnabled(false);

        //creating a "ground floor" for levels
        groundView = new GroundView(getAssetManager(),stageNode);
        groundView.createGround();

        //adding walls for the surface
        wallsView = new WallsView(getAssetManager(), stageNode, groundView.getGroundGeom());
        wallsView.createWalls();

        //spawning player1
        player1View = new PlayerView(getAssetManager(), player1Node, this, ColorRGBA.Red, new Vector3f(-4f,-2f,0f));

        //spawning player2
        player2View = new PlayerView(getAssetManager(), player2Node, this, ColorRGBA.Black, new Vector3f(4f,-2f,0f));

        terrainView = new TerrainView(this, terrainNode, groundView, world);
        terrainView.createTerrain();

        player1Control = new PlayerController(player1View,1f,2f,1f, niftyView, world);
        player1Control.setupKeys();

        player2AIControl = new AIPlayerController(player2View,1f,2f,1f, niftyView, world);
        player2ControlSave = new PlayerController(player2View,1f,2f,1f, niftyView, world);

        if(this.ai == true){
            player2Control = player2AIControl;
        } else {
            player2Control = player2ControlSave;
        }
        player2Control.setupKeys();



        niftyView.setP1ctr(player1Control);
        niftyView.setP2ctr(player2Control);




        //adding collision-detection to map walls, not working properly <--- still?
        wallCollisionControl();

        //adding collision-detection to ground
        groundCollisionControl();

        //adding collision-detection to player.
        playerCollisionControl();

        //adding collision-detection to terrain.
        terrainCollisionControl();

        //nullify gravity
        bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0, 0, 0));

        //spawn a power-up of type speed
        powerupView = new PowerupView(this,stageNode,groundView);
        //powerUpView.createPowerUp("speed");
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

    public void terrainCollisionControl(){
        Geometry[][] terrainGrid = terrainView.getTerrainGrid();
        for (int i = 0; i < terrainGrid.length; i++){
            for (int j = 0; j<terrainGrid[i].length; j++){
                if (terrainGrid[i][j] != null) {
                    RigidBodyControl terrainPhy = new RigidBodyControl(0);
                    terrainGrid[i][j].addControl(terrainPhy);
                    bulletAppState.getPhysicsSpace().add(terrainPhy);
                }
            }
        }
    }

    public void bulletCollisionControl(BulletView bulletView, Spatial bullet){
        bulletPhy = new BulletController(bulletView, player1Control, player2Control);
        bullet.addControl(bulletPhy);

        bulletPhy.setLinearVelocity(bulletView.getPlayerView().getGunRotation().getRotationColumn(2).mult(50));
        bulletAppState.getPhysicsSpace().add(bulletPhy);
    }

    public void playerCollisionControl(){
        player1View.getPlayerNode().addControl(player1Control);
        bulletAppState.getPhysicsSpace().add(player1Control);
        player2View.getPlayerNode().addControl(player2Control);
        bulletAppState.getPhysicsSpace().add(player2Control);
    }

    //"pauses the game"
    public void pauseGame(){
        this.paused = true;
        this.player1Control.pause();
        this.player2Control.pause();
    }

    //"unpauses the game"
    public void unpauseGame(){
        this.paused = false;
        this.player1Control.unpause();
        this.player2Control.unpause();
    }

    public void updateGUI(){
        niftyView.updateText();
    }

    public boolean getPaused(){
        return this.paused;
    }

    public void setAI(boolean state){
        this.ai = state;
    }
    public boolean getAI(){
        return this.ai;
    }

    public Node getBulletNode(){
        return this.bulletNode;
    }

    public void simpleUpdate(float tpf){}

    public Node getStageNode(){
        return stageNode;
    }

    public Node getTerrainNode() {return terrainNode;}

    public BulletAppState getBulletAppState(){
        return this.bulletAppState;
    }

    public WallsView getWallsView(){
        return wallsView;
    }

    public Node getPlayer1Node() {return player1Node;}

    public Node getPlayer2Node() {return player2Node;}

    public void onEndScreen(){}
    public void onStartScreen(){}
    public void bind(Nifty nifty, Screen screen){}
}

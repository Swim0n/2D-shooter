package gameView;

import com.jme3.app.SimpleApplication;

import com.jme3.bullet.BulletAppState;
import com.jme3.input.KeyInput;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl;
import com.jme3.scene.shape.Quad;
import core.HealthPowerUp;
import core.SpeedPowerUp;
import core.World;
import ctrl.*;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import utils.ApplicationAssets;
import utils.KeyMappings;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by David on 2016-04-18.
 */
public class GameView extends SimpleApplication implements ScreenController{

    //variables for controls
    private PlayerController player1Control;
    private PlayerController player2Control;
    private PlayerController player2ControlSave;
    private AIPlayerController player2AIControl;
    private BulletAppState bulletAppState;
    private List<PowerUpController> PowerUpControllerList = new ArrayList<PowerUpController>();
    private CameraController camControl;

    //variables for viewer classes
    private WallsView wallsView;
    private GroundView groundView;
    private PlayerView player1View;
    private PlayerView player2View;
    private PowerupView powerUpView;
    private TerrainView terrainView;
    private CameraView cameraView;


    private Node bulletNode;
    private Node stageNode;
    private CameraNode camNode;
    private Node terrainNode;
    private Node player1Node;
    private Node player2Node;

    private World world;

    private ApplicationAssets appAssets;

    //variables for gui
    private NiftyJmeDisplay niftyDisplay;
    private Nifty nifty;
    private GUIView niftyView;

    private boolean ai = false;
    private boolean paused = true;


    public void simpleInitApp() {
        initiateNodes();
        initiatePhysics();

        world = new World(15, 10, false);
        appAssets = new ApplicationAssets(this, world, assetManager, inputManager, bulletAppState, stageNode, terrainNode);

        initiateCamera();
        initiateGUI();
        initiateStage();
        initiatePlayers();

        bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0,0,0));

        niftyView.setP1ctr(player1Control);
        niftyView.setP2ctr(player2Control);

        DirectionalLight dl = new DirectionalLight();
        dl.setColor(ColorRGBA.White);
        dl.setDirection(new Vector3f(1,1,1).normalize());
        rootNode.addLight(dl);

        DirectionalLight dl1 = new DirectionalLight();
        dl1.setColor(ColorRGBA.White);
        dl1.setDirection(new Vector3f(-1,1,1).normalize());
        rootNode.addLight(dl1);

        DirectionalLight dl2 = new DirectionalLight();
        dl2.setColor(ColorRGBA.White);
        dl2.setDirection(new Vector3f(-1,1,-1).normalize());
        rootNode.addLight(dl2);

        DirectionalLight dl3 = new DirectionalLight();
        dl3.setColor(ColorRGBA.White);
        dl3.setDirection(new Vector3f(1,1,-1).normalize());
        rootNode.addLight(dl3);

        //for developing purposes only, remove before release to the waiting masses
        setDisplayStatView(true);
        setDisplayFps(true);
    }

    private void initiateStage(){
        //creating a "ground floor" for levels
        groundView = new GroundView(appAssets);
        //adding walls for the surface
        wallsView = new WallsView(appAssets);
        //generate terrain
        terrainView = new TerrainView(appAssets, 4, 4);
        //Creates power ups, spawns two new every 15sec
        powerUpView = new PowerupView(appAssets);

        //spawning player1
        player1View = new PlayerView(appAssets, player1Node, ColorRGBA.Red, new Vector3f(-4f,-2f,0f));
        //spawning player2
        player2View = new PlayerView(appAssets, player2Node, ColorRGBA.Black, new Vector3f(4f,-2f,0f));
        new PowerUpController(new HealthPowerUp(appAssets),appAssets,powerUpView);
        new PowerUpController(new SpeedPowerUp(appAssets), appAssets, powerUpView);
    }

    private void initiatePlayers(){
        player1Control = new HumanPlayerController(player1View,world.getPlayer1(), niftyView, appAssets, new KeyMappings(KeyInput.KEY_LEFT, KeyInput.KEY_RIGHT, KeyInput.KEY_UP,
                KeyInput.KEY_DOWN, KeyInput.KEY_NUMPAD5, KeyInput.KEY_NUMPAD4, KeyInput.KEY_NUMPAD6, KeyInput.KEY_NUMPAD0));
        player2AIControl = new AIPlayerController(player2View,world.getPlayer2(), niftyView);
        player2ControlSave = new HumanPlayerController(player2View,world.getPlayer2(), niftyView, appAssets, new KeyMappings(KeyInput.KEY_A, KeyInput.KEY_D, KeyInput.KEY_W,
                KeyInput.KEY_S, KeyInput.KEY_J, KeyInput.KEY_H, KeyInput.KEY_K, KeyInput.KEY_SPACE));
        if(this.ai == true){
            player2Control = player2AIControl;
        } else {
            player2Control = player2ControlSave;
        }
        player1Node.addControl(player1Control);
        bulletAppState.getPhysicsSpace().add(player1Control);
        player2Node.addControl(player2Control);
        bulletAppState.getPhysicsSpace().add(player2Control);
    }

    private void initiateGUI(){
        //gui initialization
        niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/screen.xml", "start", this);
        guiViewPort.addProcessor(niftyDisplay);
        niftyView = (GUIView) nifty.getCurrentScreen().getScreenController();
        niftyView.setNiftyDisp(niftyDisplay);
        niftyView.setGameView(this);

    }

    private void initiatePhysics(){
        //set up physics
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        bulletAppState.setDebugEnabled(false);
    }

    private void initiateCamera(){
        //camera settings
        camControl = new CameraController(appAssets);
        cameraView = new CameraView(appAssets);
    }

    private void initiateNodes(){
        //init nodes
        bulletNode = new Node("bullets");
        stageNode = new Node("stage");
        camNode = new CameraNode("cameraNode",cam);
        terrainNode = new Node("terrain");
        player1Node = new Node("player1");
        player2Node = new Node("player2");
        rootNode.attachChild(player1Node);
        rootNode.attachChild(player2Node);
        rootNode.attachChild(bulletNode);
        rootNode.attachChild(stageNode);
        rootNode.attachChild(terrainNode);
        rootNode.attachChild(camNode);
    }

    //"pauses the java"
    public void pauseGame(){
        this.paused = true;
        this.player1Control.pause();
        this.player2Control.pause();
    }

    //"unpauses the java"
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
    public Node getPlayer1Node() {return player1Node;}
    public Node getPlayer2Node() {return player2Node;}
    public PlayerController getPlayer1Control() {return player1Control;}
    public PlayerController getPlayer2Control() {return player2Control;}
    public Quad getGroundSize(){
        return groundView.getGroundShape();
    }
    public Geometry getGroundGeom() {return groundView.getGroundGeom();}
    public void onEndScreen(){}
    public void onStartScreen(){}
    public void bind(Nifty nifty, Screen screen){}
    public CameraView getCameraView() {return cameraView;}
    public CameraNode getCameraNode() {return camNode;}
    public CameraControl getCameraControl() {return camControl;}



    //automatically called on app exit, notifies other threads
    @Override
    public void destroy() {
        super.destroy();
        world.setRunning(false);
        powerUpView.stopTimer();
    }
    //called on window close
    @Override
    public void requestClose(boolean esc) {
        super.requestClose(esc);
        world.setRunning(false);
        powerUpView.stopTimer();
    }
}
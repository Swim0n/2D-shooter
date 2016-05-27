package gameView;

import com.jme3.app.SimpleApplication;

import com.jme3.bullet.BulletAppState;
import com.jme3.input.KeyInput;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.shadow.PointLightShadowRenderer;
import core.HealthPowerUp;
import core.SpeedPowerUp;
import core.World;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import utils.ApplicationAssets;


/**
 * Created by David on 2016-04-18.
 */
public class GameView extends SimpleApplication implements ScreenController{

    private BulletAppState bulletAppState;

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
    //all views initiated
    private boolean initiated;


    public void simpleInitApp() {
        initiateNodes();
        initiatePhysics();

        world = new World(40, 30, true);
        appAssets = new ApplicationAssets(this, world, assetManager, inputManager, bulletAppState, stageNode, terrainNode);

        initiateCamera();
        initiateGUI();
        initiateStage();
        PointLight lamp_light = new PointLight();
        lamp_light.setColor(ColorRGBA.White.mult(2));
        lamp_light.setRadius(150f);
        lamp_light.setPosition(new Vector3f(0,-20,0));
        rootNode.addLight(lamp_light);


        final int SHADOWMAP_SIZE=1024;
       PointLightShadowRenderer dlsr = new PointLightShadowRenderer(assetManager, SHADOWMAP_SIZE);
        dlsr.setLight(lamp_light);
        viewPort.addProcessor(dlsr);

        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.Magenta.mult(0.4f));
        rootNode.addLight(al);

        rootNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

        initiated = true;
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
        player1View = new PlayerView(appAssets, player1Node, "Materials/p1headmat.j3m","Materials/p1bodymat.j3m",
                ColorRGBA.Magenta, ColorRGBA.Cyan, new Vector3f(-4f,-2f,0f),appAssets.getWorld().getPlayer1());
        //spawning player2
        player2View = new PlayerView(appAssets, player2Node, "Materials/p2headmat.j3m","Materials/p2bodymat.j3m",
                ColorRGBA.Cyan, ColorRGBA.Magenta, new Vector3f(4f,-2f,0f),appAssets.getWorld().getPlayer2());
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



    //automatically called on app exit, notifies other threads
    @Override
    public void destroy() {
        super.destroy();
        powerUpView.stopTimer();
    }
    //called on window close
    @Override
    public void requestClose(boolean esc) {
        super.requestClose(esc);
        powerUpView.stopTimer();
    }

    public ApplicationAssets getAppAssets() {
        return appAssets;
    }

    public PlayerView getPlayer1View() {
        return player1View;
    }

    public GUIView getNiftyView() {
        return niftyView;
    }

    public PlayerView getPlayer2View() {
        return player2View;
    }

    public PowerupView getPowerUpView() {
        return powerUpView;
    }

    public boolean isInitiated() {
        return initiated;
    }
    public void setPaused(boolean paused) {
        this.paused = paused;
    }
    public boolean isPaused() {
        return paused;
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
    public Quad getGroundSize(){
        return groundView.getGroundShape();
    }
    public Geometry getGroundGeom() {return groundView.getGroundGeom();}
    public void onEndScreen(){}
    public void onStartScreen(){}
    public void bind(Nifty nifty, Screen screen){}
    public CameraNode getCameraNode() {return camNode;}
}
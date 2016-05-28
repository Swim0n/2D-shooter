package gameView;

import com.jme3.app.SimpleApplication;

import com.jme3.bullet.BulletAppState;
import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.shadow.PointLightShadowRenderer;
import core.World;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 * Created by David on 2016-04-18.
 */
public class GameView extends SimpleApplication implements ScreenController{

    private BulletAppState bulletAppState;

    private WallsView wallsView;
    private GroundView groundView;
    private PlayerView player1View;
    private PlayerView player2View;
    private TerrainView terrainView;
    private CameraView cameraView;
    private BulletView bulletView;
    private PowerUpView powerUpView;

    private Node bulletNode;
    private Node stageNode;
    private CameraNode camNode;
    private Node terrainNode;
    private Node player1Node;
    private Node player2Node;

    private World world;

    private NiftyJmeDisplay niftyDisplay;
    private Nifty nifty;
    private GUIView niftyView;

    private boolean ai = false;
    private boolean paused = true;

    //all views initialized, set by startButton in nifty start menu
    private boolean initialized;


    public void simpleInitApp() {
        initNodes();
        initPhysics();
        initCamera();

        //creates the model of the game
        world = new World(20, 20, false);

        initGUI();
        initStage();
        initPlayers();
        initLights();
        initBullets();
        initPowerUps();

        //for developing purposes only, remove before release to the waiting masses
        setDisplayStatView(true);
        setDisplayFps(true);
    }

    private void initPowerUps() {
        powerUpView = new PowerUpView(this);
    }

    private void initBullets() {
       bulletView = new BulletView(this);
    }

    private void initStage(){
        //creating a "ground floor" for levels
        groundView = new GroundView(this);
        //adding walls for the surface
        wallsView = new WallsView(this);
        terrainView = new TerrainView(this, 4, 4);
    }

    private void initGUI(){
        niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/screen.xml", "start", this);
        guiViewPort.addProcessor(niftyDisplay);
        niftyView = (GUIView) nifty.getCurrentScreen().getScreenController();
        niftyView.setNiftyDisplay(niftyDisplay);
        niftyView.setGameView(this);
    }

    private void initPlayers(){
        player1View = new PlayerView(this, player1Node, "Materials/p1headmat.j3m","Materials/p1bodymat.j3m",
                ColorRGBA.Magenta, ColorRGBA.Cyan, new Vector3f(-29.5f,-2f,19.5f),world.getPlayer1());
        player2View = new PlayerView(this, player2Node, "Materials/p2headmat.j3m","Materials/p2bodymat.j3m",
                ColorRGBA.Cyan, ColorRGBA.Magenta, new Vector3f(29.5f,-2f,-21f),world.getPlayer2());
    }

    private void initLights(){
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
    }

    private void initPhysics(){
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        bulletAppState.setDebugEnabled(false);
    }

    private void initCamera(){
        cameraView = new CameraView(this);
    }

    private void initNodes(){
        rootNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        bulletNode = new Node("bullets");
        bulletNode.setShadowMode(RenderQueue.ShadowMode.Cast);
        stageNode = new Node("stage");
        camNode = new CameraNode("cameraNode",cam);
        terrainNode = new Node("terrain");
        terrainNode.setShadowMode(RenderQueue.ShadowMode.Cast);
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
        world.setShutDown();
    }
    //called on window close
    @Override
    public void requestClose(boolean esc) {
        super.requestClose(esc);
        world.setShutDown();
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

    public boolean isInitialized() {
        return initialized;
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

    public World getWorld() {
        return world;
    }
    public void setInitialized() {
        initialized = true;
    }

    public BulletView getBulletView() {return bulletView;}

    public PowerUpView getPowerUpView() {
        return powerUpView;
    }
}
package view;

import com.jme3.app.SimpleApplication;

import com.jme3.bullet.BulletAppState;
import com.jme3.input.KeyInput;
import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.shadow.PointLightShadowRenderer;
import core.World;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import jME3.utils.KeyMappings;

/**
 * Initializes the graphical world
 */
public class WorldView extends SimpleApplication implements ScreenController{
    private World world;
    private Node bulletNode;
    private Node stageNode;
    private Node terrainNode;
    private Node player1Node;
    private Node player2Node;
    private CameraNode camNode;
    private StageView stageView;
    private TerrainView terrainView;
    private BulletView bulletView;
    private PowerUpView powerUpView;
    private PlayerView player1View;
    private PlayerView player2View;
    private NiftyJmeDisplay niftyDisplay;
    private Nifty nifty;
    private GUIView niftyView;
    private BulletAppState bulletAppState;

    private KeyMappings player1Mappings = new KeyMappings(KeyInput.KEY_A, KeyInput.KEY_D, KeyInput.KEY_W,
            KeyInput.KEY_S, KeyInput.KEY_J, KeyInput.KEY_H, KeyInput.KEY_K, KeyInput.KEY_SPACE);
    private KeyMappings player2Mappings = new KeyMappings(KeyInput.KEY_LEFT, KeyInput.KEY_RIGHT, KeyInput.KEY_UP,
            KeyInput.KEY_DOWN, KeyInput.KEY_NUMPAD5, KeyInput.KEY_NUMPAD4, KeyInput.KEY_NUMPAD6, KeyInput.KEY_NUMPAD0);

    //all views initialized, set by startButton in nifty start menu
    private boolean initialized;

    public void simpleInitApp() {
        initNodes();
        initPhysics();
        //creates the model of the game
        world = new World(20, 20, false);
        initCamera();
        initGUI();
        initStage();
        initPlayers();
        initLights();
        initBullets();
        initPowerUps();
    }

    private void initPowerUps() {
        powerUpView = new PowerUpView(this);
    }

    private void initBullets() {
       bulletView = new BulletView(this);
    }

    private void initStage(){
        stageView = new StageView(this);
        terrainView = new TerrainView(this, 4, 4);
    }

    private void initGUI(){
        niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/screen.xml", "start", this);
        guiViewPort.addProcessor(niftyDisplay);
        niftyView = (GUIView) nifty.getCurrentScreen().getScreenController();
        niftyView.setNiftyDisplay(niftyDisplay);
        niftyView.setWorldView(this);
        setDisplayStatView(false);
        setDisplayFps(false);
    }

    private void initPlayers(){
        player1View = new PlayerView(this, player1Node, "Materials/p1headmat.j3m","Materials/p1bodymat.j3m",
                ColorRGBA.Magenta, ColorRGBA.Cyan, new Vector3f(-29.5f,-2f,19.5f));
        player2View = new PlayerView(this, player2Node, "Materials/p2headmat.j3m","Materials/p2bodymat.j3m",
                ColorRGBA.Cyan, ColorRGBA.Magenta, new Vector3f(29.5f,-2f,-21f));
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
        camNode.setLocalTranslation(jME3.utils.Utils.vecMathToJMEVector3f(world.getCameraData().getStaticPosition()));
        camNode.lookAt(jME3.utils.Utils.vecMathToJMEVector3f(world.getCameraData().getStaticLookAt()),Vector3f.UNIT_Z);
        flyCam.setEnabled(false);
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
        powerUpView.stopTimer();
    }
    //called on window close
    @Override
    public void requestClose(boolean esc) {
        super.requestClose(esc);
        world.setShutDown();
        powerUpView.stopTimer();
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

    public void updateGUI(){
        niftyView.updateText();
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
    public NiftyJmeDisplay getNiftyDisplay(){
        return this.niftyDisplay;
    }

    //to be used in controller remapping by user but not fully implemented at the moment
    public KeyMappings getPlayer1Mappings() {return player1Mappings;}
    public void setPlayer1Mappings(KeyMappings player1Mappings) {this.player1Mappings = player1Mappings;}
    public KeyMappings getPlayer2Mappings() {return player2Mappings;}
    public void setPlayer2Mappings(KeyMappings player2Mappings) {this.player2Mappings = player2Mappings;}
}
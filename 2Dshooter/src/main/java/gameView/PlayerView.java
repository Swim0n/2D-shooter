package gameView;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.effect.shapes.EmitterSphereShape;
import com.jme3.input.InputManager;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.BillboardControl;
import com.jme3.scene.control.LightControl;
import com.jme3.scene.shape.Quad;
import core.Player;
import utils.Utils;

/**
 * Created by Simon on 2016-04-26.
 */
public class PlayerView {
    private Geometry pipe;
    private final Node pipeNode = new Node("pipeNode");
    private final Vector3f startPos;
    private Spatial player;
    private Spatial gun;
    private Geometry healthBackgroundBar;
    private Geometry healthBar;
    private Geometry dashBackgroundBar;
    private Geometry dashBar;
    private AssetManager assetManager;
    private GameView gameView;
    private Node playerNode;
    private Node headNode = new Node("head");
    private Node bodyNode = new Node("body");
    private Node healthBarNode = new Node("healthbar");
    private Node dashBarNode = new Node("dashbar");
    private AudioNode powerUpAudio;
    private AudioNode shotAudio;
    private AudioNode dashAudio;
    private AudioNode hitAudio;
    private InputManager inputManager;
    private float lastRotation;
    private Quaternion gunRot = new Quaternion();
    private Vector3f axisY=Vector3f.UNIT_Y;//for rotation around Y-axis
    private String headMaterialPath;
    private String bodyMaterialPath;
    private ColorRGBA bodyColor;
    private ColorRGBA emitColor;
    private ParticleEmitter sparks;
    private Player playerData;

    //colorRGBA is just a placeholder until textures are in place
    public PlayerView(GameView gameView, Node playerNode, String headMaterialPath, String bodyMaterialPath, ColorRGBA bodyColor, ColorRGBA emitColor, Vector3f startPos, Player player){
        this.assetManager = gameView.getAssetManager();
        this.gameView = gameView;
        this.playerNode = playerNode;
        this.inputManager = gameView.getInputManager();
        this.startPos = startPos;
        this.headMaterialPath = headMaterialPath;
        this.bodyMaterialPath = bodyMaterialPath;
        this.bodyColor = bodyColor;
        this.emitColor = emitColor;
        this.playerData = player;
        createPlayer();
        createHealthBar();
        createDashBar();
        createParticleEmitter();
        initSounds();
    }

    private void createPlayer(){
        playerNode.setLocalTranslation(startPos);

        //creating gun attached to player
        gun = assetManager.loadModel("Models/p1head.mesh.xml");
        gun.setMaterial(assetManager.loadMaterial(headMaterialPath));
        gun.rotate(-FastMath.HALF_PI,FastMath.PI,0);
        headNode.setLocalTranslation(0,-1.6f,0);
        headNode.attachChild(gun);
        playerNode.attachChild(headNode);

        //creating player
        player = assetManager.loadModel("Models/p1body.mesh.xml");
        player.setMaterial(assetManager.loadMaterial(bodyMaterialPath));
        player.rotate(-FastMath.HALF_PI,FastMath.PI,0);
        bodyNode.move(0,0,0);
        bodyNode.attachChild(player);
        playerNode.attachChild(bodyNode);

        //creating light
        PointLight lamp_light = new PointLight();
        lamp_light.setColor(bodyColor.mult(5));
        lamp_light.setRadius(9f);
        gameView.getRootNode().addLight(lamp_light);
        LightControl lightControl = new LightControl(lamp_light);
        gun.addControl(lightControl);

    }

    private void createHealthBar(){
        //creating a health bar
        healthBackgroundBar = new Geometry("healthBackgroundBar", new Quad(3.5f, 0.4f));
        healthBar = new Geometry("healthBar", new Quad(3.5f, 0.4f));
        healthBackgroundBar.setMaterial(Utils.getMaterial(assetManager,ColorRGBA.Black));
        healthBar.setMaterial(Utils.getMaterial(assetManager,bodyColor));
        healthBackgroundBar.center();
        healthBar.center();
        healthBarNode.attachChild(healthBackgroundBar);
        healthBarNode.attachChild(healthBar);
        healthBarNode.move(0,-3.5f, 1);
        BillboardControl healthCtrl = new BillboardControl();
        healthBarNode.addControl(healthCtrl);
        healthBarNode.setShadowMode(RenderQueue.ShadowMode.Off);
        playerNode.attachChild(healthBarNode);
    }

    private void createDashBar(){
        dashBackgroundBar = new Geometry("dashBackgroundBar", new Quad(2.3f, 0.3f));
        dashBar = new Geometry("dashBar", new Quad(2.3f, 0.3f));
        dashBackgroundBar.setMaterial(Utils.getMaterial(assetManager,ColorRGBA.Black));
        dashBar.setMaterial(Utils.getMaterial(assetManager,ColorRGBA.Orange));
        dashBackgroundBar.rotate(0,0,FastMath.HALF_PI);
        dashBar.rotate(0,0,FastMath.HALF_PI);
        dashBackgroundBar.center();
        dashBar.center();
        dashBackgroundBar.move(0,0,-0.01f);
        dashBarNode.attachChild(dashBackgroundBar);
        dashBarNode.attachChild(dashBar);
        BillboardControl dashCtrl = new BillboardControl();
        dashBarNode.addControl(dashCtrl);
        dashBarNode.move(1.7f,-1,0);
        dashBarNode.setShadowMode(RenderQueue.ShadowMode.Off);
        playerNode.attachChild(dashBarNode);
    }

    private void createParticleEmitter(){
        sparks = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 15);
        Material sparkMaterial = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        sparkMaterial.setTexture("Texture", assetManager.loadTexture("Effects/flash.png"));
        sparks.setMaterial(sparkMaterial);
        sparks.setShape(new EmitterSphereShape(Vector3f.ZERO, 2f));
        sparks.setImagesX(2);
        sparks.setImagesY(2);
        sparks.setParticlesPerSec(0);
        sparks.setStartColor(emitColor);
        sparks.setEndColor(ColorRGBA.White);
        sparks.setStartSize(0.8f);
        sparks.setEndSize(0);
        sparks.setHighLife(0.5f);
        sparks.setLowLife(0.2f);
        sparks.setSelectRandomImage(true);
        sparks.setLocalTranslation(gun.getLocalTranslation());
        playerNode.attachChild(sparks);
    }

    private void initSounds(){
        powerUpAudio = new AudioNode(assetManager, "Sound/powerUpSound.wav");
        powerUpAudio.setPositional(false);
        powerUpAudio.setLooping(false);
        powerUpAudio.setVolume(2);
        playerNode.attachChild(powerUpAudio);
        shotAudio = new AudioNode(assetManager, "Sound/shot.wav");
        shotAudio.setPositional(false);
        shotAudio.setLooping(false);
        shotAudio.setVolume(0.6f);
        playerNode.attachChild(shotAudio);
        dashAudio = new AudioNode(assetManager, "Sound/dash.wav");
        dashAudio.setPositional(false);
        dashAudio.setLooping(false);
        dashAudio.setVolume(2);
        playerNode.attachChild(dashAudio);
        hitAudio = new AudioNode(assetManager, "Sound/playerHit.wav");
        hitAudio.setPositional(false);
        hitAudio.setLooping(false);
        hitAudio.setVolume(0.3f);
        playerNode.attachChild(hitAudio);
    }

    public void playPowerUpSound(){
        powerUpAudio.playInstance();
    }

    public void playShotSound(){
        shotAudio.playInstance();
    }

    public void playDashSound(){
        dashAudio.playInstance();
    }

    public void playPlayerHitSound(){
        hitAudio.playInstance();
    }

    public void rotateGun(float step){
        lastRotation += step;
        gunRot.fromAngleAxis(FastMath.PI*lastRotation/180, axisY);
        headNode.setLocalRotation(gunRot);
    }

    public void emitSparks(){
        sparks.emitAllParticles();
    }

    public void setHealthBar(float percent){
        if(percent != 0){
            healthBar.setLocalScale(percent/100, 1f, 1f);
        } else {
            healthBar.setLocalScale(-0.1f, 1f, 1f);
        }
    }

    public void setDashBar(float percent){
        if(percent != 0){
            dashBar.setLocalScale(percent/100, 1f, 1f);
        } else {
            dashBar.setLocalScale(-0.1f, 1f, 1f);
        }
    }
    public Node getPlayerNode(){return this.playerNode;}
    public Node getBodyNode(){
        return this.bodyNode;
    }
    public Vector3f getPipePos(){return this.headNode.getWorldTranslation();}
    public Vector3f getStartPos(){return startPos;}
    public GameView getGameView(){return this.gameView;}
    public Quaternion getGunRotation(){return gunRot;}
    public Vector3f getPosition(){return this.getPlayerNode().getLocalTranslation();}
    public ColorRGBA getBodyColor(){
        return this.bodyColor;
    }
    public Player getPlayerData() {
        return playerData;
    }
}

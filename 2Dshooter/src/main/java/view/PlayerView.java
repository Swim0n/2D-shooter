package view;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.effect.shapes.EmitterSphereShape;
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
import jME3.utils.Utils;

/**
 * Spawns a player with associated stat bars
 */
public class PlayerView {
    private Node playerNode;
    private Node rootNode;
    private Node headNode = new Node("head");
    private Node bodyNode = new Node("body");
    private Node healthBarNode = new Node("healthbar");
    private Node dashBarNode = new Node("dashbar");
    private Node shotBarNode = new Node ("shotbar");
    private AudioNode powerUpAudio;
    private AudioNode shotAudio;
    private AudioNode dashAudio;
    private AudioNode hitAudio;
    private AudioNode overloadAudio;
    private final Vector3f startPos;
    private Spatial playerBody;
    private Spatial playerHead;
    private Geometry healthBar;
    private Geometry dashBar;
    private Geometry shotBar;
    private ColorRGBA bodyColor;
    private ColorRGBA emitColor;
    private float lastRotation;
    private Quaternion gunRot = new Quaternion();
    private ParticleEmitter sparks;
    private String headMaterialPath;
    private String bodyMaterialPath;
    private AssetManager assetManager;

    public PlayerView(WorldView worldView, Node playerNode, String headMaterialPath, String bodyMaterialPath, ColorRGBA bodyColor, ColorRGBA emitColor, Vector3f startPos){
        this.assetManager = worldView.getAssetManager();
        this.playerNode = playerNode;
        this.startPos = startPos;
        this.headMaterialPath = headMaterialPath;
        this.bodyMaterialPath = bodyMaterialPath;
        this.bodyColor = bodyColor;
        this.emitColor = emitColor;
        this.rootNode = worldView.getRootNode();
        createPlayer();
        createHealthBar();
        createDashBar();
        createShotBar();
        createParticleEmitter();
        initSounds();
    }

    public void rotateGun(float step){
        lastRotation += step;
        gunRot.fromAngleAxis(FastMath.PI*lastRotation/180, Vector3f.UNIT_Y);
        headNode.setLocalRotation(gunRot);
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

    public void setShotBar(float percent){
        if(percent != 0){
            shotBar.setLocalScale(percent/100, 1f, 1f);
        } else {
            shotBar.setLocalScale(-0.1f, 1f, 1f);
        }
    }

    public void setShotBarColor(ColorRGBA color){
        shotBar.setMaterial(Utils.getMaterial(assetManager,color));
    }

    public void emitSparks(){
        sparks.emitAllParticles();
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

    public void playOverloadSound(){
        overloadAudio.playInstance();
    }

    private void createPlayer(){
        playerNode.setLocalTranslation(startPos);

        playerHead = assetManager.loadModel("Models/p1head.mesh.xml");
        playerHead.setMaterial(assetManager.loadMaterial(headMaterialPath));
        playerHead.rotate(-FastMath.HALF_PI,FastMath.PI,0);
        headNode.setLocalTranslation(0,-1.6f,0);
        headNode.attachChild(playerHead);
        playerNode.attachChild(headNode);

        playerBody = assetManager.loadModel("Models/p1body.mesh.xml");
        playerBody.setMaterial(assetManager.loadMaterial(bodyMaterialPath));
        playerBody.rotate(-FastMath.HALF_PI,FastMath.PI,0);
        bodyNode.attachChild(playerBody);
        playerNode.attachChild(bodyNode);

        PointLight headLight = new PointLight();
        headLight.setColor(bodyColor.mult(5));
        headLight.setRadius(9f);
        rootNode.addLight(headLight);
        LightControl lightControl = new LightControl(headLight);
        playerHead.addControl(lightControl);
    }

    private void createHealthBar(){
        Geometry healthBackgroundBar = new Geometry("healthBackgroundBar", new Quad(3.7f, 0.6f));
        healthBar = new Geometry("healthBar", new Quad(3.5f, 0.4f));
        healthBackgroundBar.setMaterial(Utils.getMaterial(assetManager,ColorRGBA.Black));
        healthBar.setMaterial(Utils.getMaterial(assetManager,bodyColor));
        healthBackgroundBar.center();
        healthBar.center();
        healthBackgroundBar.move(0,0,-0.05f);
        healthBarNode.attachChild(healthBackgroundBar);
        healthBarNode.attachChild(healthBar);
        healthBarNode.move(0,-3.5f, 1);
        BillboardControl healthCtrl = new BillboardControl();
        healthBarNode.addControl(healthCtrl);
        healthBarNode.setShadowMode(RenderQueue.ShadowMode.Off);
        playerNode.attachChild(healthBarNode);
    }

    private void createDashBar(){
        Geometry dashBackgroundBar = new Geometry("dashBackgroundBar", new Quad(2.5f, 0.45f));
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
        dashBarNode.move(2.15f,-1,0);
        dashBarNode.setShadowMode(RenderQueue.ShadowMode.Off);
        playerNode.attachChild(dashBarNode);
    }

    private void createShotBar(){
        Geometry shotBackgroundBar = new Geometry("shotBackgroundBar", new Quad(2.5f, 0.45f));
        shotBar = new Geometry("shotBar", new Quad(2.3f, 0.3f));
        shotBackgroundBar.setMaterial(Utils.getMaterial(assetManager,ColorRGBA.Black));
        shotBar.setMaterial(Utils.getMaterial(assetManager,ColorRGBA.Yellow));
        shotBackgroundBar.rotate(0,0,FastMath.HALF_PI);
        shotBar.rotate(0,0,FastMath.HALF_PI);
        shotBackgroundBar.center();
        shotBar.center();
        shotBackgroundBar.move(0,0,-0.01f);
        shotBarNode.attachChild(shotBackgroundBar);
        shotBarNode.attachChild(shotBar);
        BillboardControl shotCtrl = new BillboardControl();
        shotBarNode.addControl(shotCtrl);
        shotBarNode.move(1.7f,-1,0);
        shotBarNode.setShadowMode(RenderQueue.ShadowMode.Off);
        playerNode.attachChild(shotBarNode);
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
        sparks.setLocalTranslation(playerHead.getLocalTranslation());
        playerNode.attachChild(sparks);
    }

    private void initSounds(){
        powerUpAudio = new AudioNode(assetManager, "Sound/powerUpSound.wav");
        powerUpAudio.setPositional(false);
        powerUpAudio.setLooping(false);
        powerUpAudio.setVolume(1);
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
        hitAudio.setVolume(0.2f);
        playerNode.attachChild(hitAudio);
        overloadAudio = new AudioNode(assetManager, "Sound/overload.wav");
        overloadAudio.setPositional(false);
        overloadAudio.setLooping(false);
        overloadAudio.setVolume(0.3f);
        playerNode.attachChild(overloadAudio);
    }

    public Node getPlayerNode(){return this.playerNode;}
    public Node getBodyNode(){
        return this.bodyNode;
    }
    public Vector3f getPipePos(){return this.headNode.getWorldTranslation();}
    public Vector3f getStartPos(){return startPos;}
    public Quaternion getGunRotation(){return gunRot;}
    public Vector3f getPosition(){return this.getPlayerNode().getLocalTranslation();}
    public ColorRGBA getBodyColor(){
        return this.bodyColor;
    }
}

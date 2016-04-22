package game.gameView;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
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

    private Geometry player1;
    private Geometry player2;
    private Spatial walls;
    private Geometry westWall;
    private Geometry eastWall;
    private Geometry northWall;
    private Geometry southWall;
    private RigidBodyControl wallsPhy;
    private RigidBodyControl groundPhy;
    private PlayerController playerControl;
    private BulletAppState bulletAppState;
    private Geometry groundGeom;
    private Box verticalWallShape;
    private Box horizontalWallShape;
    private Material wallMaterial;
    private Material playerMaterial;
    //private World world = new World();

    public void simpleInitApp() {

        //camera settings
        cam.setLocation(new Vector3f(0,-65f,0));
        cam.lookAtDirection(new Vector3f(0,1,0), new Vector3f(0,0,1));
        getFlyByCamera().setEnabled(false);

        //turn off stats gameView (you can leave it on, if you want)
        setDisplayStatView(true);
        setDisplayFps(true);

        //set up physics
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        bulletAppState.setDebugEnabled(true);

        //creating a "ground floor" for levels
        createGround();

        //adding walls for the surface
        createWalls();

        //spawning player1
        createPlayer();

        //adding collision-detection to map walls, not working properly <--- still?
        wallCollisionControl();

        //adding collision-detection to ground
        groundCollisionControl();

        //nullify gravity
        bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0, 0, 0));
    }

    public void createGround(){
        Quad groundShape = new Quad(71f, 53f); //quad to represent ground in game
        groundGeom= new Geometry("Ground",groundShape); //geometry to represent ground
        Material groundMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"); //material for ground
        Texture dirt = assetManager.loadTexture("Textures/dirt.jpg");
        dirt.setWrap(Texture.WrapMode.Repeat);
        groundMat.setTexture("ColorMap", dirt);
        groundGeom.setMaterial(groundMat);
        groundGeom.rotate(FastMath.HALF_PI,0,0);
        groundGeom.setLocalTranslation(-groundShape.getWidth()/2, 0, -groundShape.getHeight()/2);

        rootNode.attachChild(groundGeom);
    }

    public void createWalls(){
        verticalWallShape = new Box(0.5f,2,27f);
        horizontalWallShape = new Box(36f, 2,0.5f);
        wallMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture brick = assetManager.loadTexture("Textures/BrickWall.jpg");
        brick.setWrap(Texture.WrapMode.Repeat);
        wallMaterial.setTexture("ColorMap", brick);
        westWall = new Geometry("westWall", verticalWallShape);
        eastWall = new Geometry("eastWall", verticalWallShape);
        northWall = new Geometry("northWall", horizontalWallShape);
        southWall = new Geometry("southWall", horizontalWallShape);
        westWall.setLocalTranslation(-groundGeom.getLocalTranslation().x,0.5f,0);
        eastWall.setLocalTranslation(groundGeom.getLocalTranslation().x,0.5f,0);
        northWall.setLocalTranslation(0,0.5f,groundGeom.getLocalTranslation().z);
        southWall.setLocalTranslation(0,0.5f,-groundGeom.getLocalTranslation().z);

        westWall.setMaterial(wallMaterial);
        eastWall.setMaterial(wallMaterial);
        northWall.setMaterial(wallMaterial);
        southWall.setMaterial(wallMaterial);

        Node wallNode = new Node();
        wallNode.attachChild(westWall);
        wallNode.attachChild(eastWall);
        wallNode.attachChild(northWall);
        wallNode.attachChild(southWall);
        walls = GeometryBatchFactory.optimize(wallNode);
        rootNode.attachChild(walls);
    }

    public void groundCollisionControl(){
        groundPhy = new RigidBodyControl(0.0f);
        groundGeom.addControl(groundPhy);
        bulletAppState.getPhysicsSpace().add(groundPhy);
    }

    public void wallCollisionControl(){
        wallsPhy = new RigidBodyControl(0.0f);
        walls.addControl(wallsPhy);
        bulletAppState.getPhysicsSpace().add(wallsPhy);
    }

    public void createPlayer(){
        Box playerShape = new Box(1,1,1);
        playerControl = new PlayerController(this, 1f, 1f, 1f);

        player1 = new Geometry("Box", playerShape);
        playerMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        player1.setLocalTranslation(0,0.5f,0);
        playerMaterial.setColor("Color", ColorRGBA.Red);
        player1.setMaterial(playerMaterial);
        rootNode.attachChild(player1);
        player1.addControl(playerControl);
        bulletAppState.getPhysicsSpace().add(playerControl);
    }

    public void simpleUpdate(float tpf){

    }
}

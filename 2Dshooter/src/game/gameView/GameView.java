package game.gameView;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.texture.Texture;
import game.ctrl.PlayerController;



/**
 * Created by David on 2016-04-18.
 */
public class GameView extends SimpleApplication {

    private Geometry player1;
    private Geometry player2;
    private Geometry westWall;
    private Geometry eastWall;
    private Geometry northWall;
    private Geometry southWall;
    private CharacterControl characterControl;
    private RigidBodyControl wallsControl;
    private BulletAppState bulletAppState;
    private Geometry groundGeom;
    private Box verticalWallShape;
    private Box horizontalWallShape;
    private Material wallMaterial;
    private Material playerMaterial;
    //private World world = new World();
    public void simpleInitApp() {

        //setup camera for 2D view
        cam.setParallelProjection(false);
        cam.setLocation(new Vector3f(0,0,70f));
        getFlyByCamera().setEnabled(false);

        //turn off stats gameView (you can leave it on, if you want)
        setDisplayStatView(false);
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


        //adding collision-detection to player
        playerCollisionControl();


        //adding collision-detection to map walls, not working properly
        wallCollisionControl();




        //attaching controllers, latest one is the one used. Can't have 2.
        player1.addControl(new PlayerController(this));

        //player1.addControl(characterControl);

        //player2.addControl();
    }


    public void createGround(){
        Quad groundShape = new Quad(50f, 50f); //quad to represent ground in game
        groundGeom= new Geometry("Ground",groundShape); //geometry to represent ground
        Material groundMat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md"); //material for ground
        Texture dirt = assetManager.loadTexture(
                "Textures/dirt.jpg");
        dirt.setWrap(Texture.WrapMode.Repeat);
        groundMat.setTexture("ColorMap", dirt);        groundGeom.setMaterial(groundMat);
        groundGeom.rotate(0,0,0);
        groundGeom.setLocalTranslation(groundShape.getWidth()/-2, groundShape.getHeight()/-2, 0);
        rootNode.attachChild(groundGeom);
    }

    public void createWalls(){
        verticalWallShape = new Box(0.5f,25f,1);
        horizontalWallShape = new Box(25f, 0.5f,1);
        wallMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture brick = assetManager.loadTexture("Textures/BrickWall.jpg");
        brick.setWrap(Texture.WrapMode.Repeat);
        wallMaterial.setTexture("ColorMap", brick);
        westWall = new Geometry("westWall", verticalWallShape);
        eastWall = new Geometry("eastWall", verticalWallShape);
        northWall = new Geometry("northWall", horizontalWallShape);
        southWall = new Geometry("southWall", horizontalWallShape);
        westWall.setLocalTranslation(-groundGeom.getLocalTranslation().x,0,0.5f);
        eastWall.setLocalTranslation(groundGeom.getLocalTranslation().x,0,0.5f);
        northWall.setLocalTranslation(0,groundGeom.getLocalTranslation().y,0.5f);
        southWall.setLocalTranslation(0,-groundGeom.getLocalTranslation().y,0.5f);
        westWall.setMaterial(wallMaterial);
        eastWall.setMaterial(wallMaterial);
        northWall.setMaterial(wallMaterial);
        southWall.setMaterial(wallMaterial);
        rootNode.attachChild(westWall);
        rootNode.attachChild(eastWall);
        rootNode.attachChild(northWall);
        rootNode.attachChild(southWall);
    }

    public void wallCollisionControl(){
        wallsControl = new RigidBodyControl(0);
        westWall.addControl(wallsControl);
        southWall.addControl(wallsControl);
        northWall.addControl(wallsControl);
        eastWall.addControl(wallsControl);
        bulletAppState.getPhysicsSpace().add(southWall);
        bulletAppState.getPhysicsSpace().add(westWall);
        bulletAppState.getPhysicsSpace().add(northWall);
        bulletAppState.getPhysicsSpace().add(eastWall);
    }
    public void playerCollisionControl(){
        CapsuleCollisionShape playerCollisionShape = new CapsuleCollisionShape(1f,1f,1);
        characterControl = new CharacterControl(playerCollisionShape, 0.05f);
        characterControl.setGravity(0);
        //enable character control to physics.
        bulletAppState.getPhysicsSpace().add(characterControl);
    }
    public void createPlayer(){
        Box playerShape = new Box(1,1,1);
        player1 = new Geometry("Box", playerShape);
        playerMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        player1.setLocalTranslation(0,0,0.5f);
        playerMaterial.setColor("Color", ColorRGBA.Red);
        player1.setMaterial(playerMaterial);
        rootNode.attachChild(player1);
    }
    public void simpleUpdate(float tpf){

    }
}

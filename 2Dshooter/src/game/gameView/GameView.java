package game.gameView;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import game.ctrl.PlayerController;

/**
 * Created by David on 2016-04-18.
 */
public class GameView extends SimpleApplication implements ActionListener {

    private Geometry player1;
    private Geometry player2;
    private Geometry westWall;
    private Geometry eastWall;
    private Geometry northWall;
    private Geometry southWall;

    //private World world = new World();
    public void simpleInitApp() {

        //        setup camera for 2D games
        cam.setParallelProjection(false);
        cam.setLocation(new Vector3f(0,0,70f));
        getFlyByCamera().setEnabled(false);

        //turn off stats gameView (you can leave it on, if you want)
        setDisplayStatView(true);
        setDisplayFps(true);


        //creating inputmappings and adding listeners
        inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("up", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("down", new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addListener(this, "left");
        inputManager.addListener(this, "right");
        inputManager.addListener(this, "up");
        inputManager.addListener(this, "down");

        //creating a "ground floor" for levels
        Quad groundShape = new Quad(50f, 50f); //quad to represent ground in game
        Geometry groundGeom = new Geometry("Ground",groundShape); //geometry to represent ground
        Material groundMat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md"); //material for ground
        groundMat.setColor("Color", ColorRGBA.Green);
        groundGeom.setMaterial(groundMat);
        groundGeom.rotate(0,0,0);
        groundGeom.setLocalTranslation(groundShape.getWidth()/-2, groundShape.getHeight()/-2, 0);
        rootNode.attachChild(groundGeom);

        //adding walls for the surface
        Box verticalWallShape = new Box(0.5f,25f,1);
        Box horizontalWallShape = new Box(25f, 0.5f,1);

        Material wallMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        wallMaterial.setColor("Color", ColorRGBA.Blue);

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

        //spawning player1
        Box playerShape = new Box(1,1,1);
        player1 = new Geometry("Box", playerShape);

        Material playerMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

        player1.setLocalTranslation(0,0,0.5f);
        playerMaterial.setColor("Color", ColorRGBA.Red);
        player1.setMaterial(playerMaterial);
        rootNode.attachChild(player1);

        //attaching controllers
        player1.addControl(new PlayerController());
        //player2.addControl();
    }

    public void simpleUpdate(float tpf){

    }

    public void onAction(String name, boolean isPressed, float tpf) {
        //movement of player
        if(name.equals("left")){
           player1.getControl(PlayerController.class).setLeft(isPressed);
        }else if(name.equals("right")){
            player1.getControl(PlayerController.class).setRight(isPressed);
        }else if(name.equals("up")){
            player1.getControl(PlayerController.class).setUp(isPressed);
        }else if(name.equals("down")){
            player1.getControl(PlayerController.class).setDown(isPressed);
        }
    }
}

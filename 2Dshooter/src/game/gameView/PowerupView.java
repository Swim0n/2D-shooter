package game.gameView;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import game.core.HealthPowerUp;
import game.core.SpeedPowerUp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PowerUpView {

    private Box powerBox;
    private Geometry powerupGeom;
    private Material boxMaterial;

    private AssetManager assetManager;
    private Node rootNode;

    private GroundView groundView;
    private GameView gameView;

    private float randomX;
    private float randomZ;

    private final static List<PowerUpView> POWER_UP_VIEW_LIST = new ArrayList<PowerUpView>();

    private final static Random randomGenerator = new Random();


    public PowerUpView(GameView gameView, Node rootNode, GroundView groundView){
        this.assetManager = gameView.getAssetManager();
        this.rootNode = rootNode;
        this.groundView =  groundView;
        this.gameView = gameView;
        powerBox = new Box(1f,1f,1f);
        powerupGeom = new Geometry("Power up", powerBox);



    }

    /**sets the possible x and z range for box.*/
    private void setRandomPos(){
        float maximumX = groundView.getGroundShape().getWidth();
        float maximumZ = groundView.getGroundShape().getHeight();

        randomX = randomGenerator.nextInt((int)(maximumX)) - maximumX/2;
        randomZ = randomGenerator.nextInt((int)(maximumZ)) - maximumZ/2;
    }

    /**creates a power up at random position. Have to set type of power up.*/
    public void createPowerUp(String powerUpType){
        setRandomPos();
        powerupGeom = new Geometry("Box", powerBox);
        boxMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        setType(powerUpType);
        powerupGeom.setMaterial(boxMaterial);
        powerupGeom.setLocalTranslation(randomX, 0, randomZ);
        rootNode.attachChild(powerupGeom);
        //gameView.powerUpCollisionControl(this,powerupGeom, powerUpType);
        if (powerUpType.equalsIgnoreCase("health")){
            gameView.powerUpCollisionControl(this, new HealthPowerUp(),powerupGeom);
        }
        if (powerUpType.equalsIgnoreCase("speed")){
            gameView.powerUpCollisionControl(this,new SpeedPowerUp(),powerupGeom);
        }
        System.out.println(powerUpType);

        POWER_UP_VIEW_LIST.add(this);


    }

    public void setType(String powerUpType){
        if (powerUpType.equalsIgnoreCase("speed")){

            boxMaterial.setColor("Color", ColorRGBA.Yellow);
        }
        if (powerUpType.equalsIgnoreCase("health")){
           boxMaterial.setColor("Color", ColorRGBA.Cyan);
        }
    }


    public GameView getGameView(){
        return this.gameView;
    }


}

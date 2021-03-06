package ctrl;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import core.*;
import view.PowerUpView;
import view.WorldView;

/**
 * Controls the power ups
 */
public class PowerUpController extends RigidBodyControl {

    private final WorldView worldView;
    private final Node targetNode;
    private final int nrOfPowerUpTypes = 3;
    private int placeOrder = 1;
    private PowerUpView powerUpView;

    /** initialize a PowerUpController to create and inits power ups*/
    public PowerUpController(Node targetNode, WorldView worldView){
        this.worldView = worldView;
        this.targetNode = targetNode;
        this.powerUpView = worldView.getPowerUpView();

        targetNode.addControl(this);
    }

    public void update(float tpf){
        placeOrder = FastMath.nextRandomInt(1,nrOfPowerUpTypes);
        Spatial powerUpMesh;
        PowerUp powerUp;
        if(powerUpView.isReadyToPlace()){
            if(placeOrder==1) {
                powerUpMesh = powerUpView.createPowerUp(targetNode, powerUp = new HealthPowerUp(worldView.getWorld().getTerrain()), "Models/plus.mesh.xml", "Materials/plusmat.j3m", ColorRGBA.Red);
                powerUpMesh.scale(1.4f);

            }
            else if(placeOrder==2) {
                powerUpMesh = powerUpView.createPowerUp(targetNode, powerUp = new WeaponPowerUp((worldView.getWorld().getTerrain())), "Models/cube.mesh.xml", "Materials/cubemat.j3m", ColorRGBA.White);
                powerUpMesh.scale(0.6f);

            }
            else {
                powerUpMesh = powerUpView.createPowerUp(targetNode, powerUp = new SpeedPowerUp(worldView.getWorld().getTerrain()), "Models/flash.mesh.xml", "Materials/flashmat.j3m", ColorRGBA.Yellow);
                powerUpMesh.scale(1.5f);

            }
            new PowerUpCollisionController(worldView,powerUpMesh,powerUp, powerUpView.getPowerLight());
            powerUpView.incActivePowerUps();
            powerUpView.setPlaced();
        }
    }
}
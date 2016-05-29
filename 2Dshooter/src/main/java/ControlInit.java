import com.jme3.bullet.BulletAppState;
import com.jme3.input.KeyInput;
import com.jme3.math.Vector3f;
import core.World;
import ctrl.*;
import view.WorldView;
import jME3.utils.KeyMappings;

/**
 * Initializes all the controllers
 */

public class ControlInit {
    private WorldView worldView;
    private World world;
    private BulletAppState bulletAppState;



    public ControlInit(ViewInit viewInit){
        this.worldView = viewInit.getWorldView();
        this.world = worldView.getWorld();
        this.bulletAppState = worldView.getBulletAppState();
        initiatePlayerControls();
        initiatePowerUpControls();
        initiateGUI();
        initiateCameraControls();
        world.setInGame(true);
   }

    private void initiateGUI() {
        worldView.getNiftyView().setPlayer1(worldView.getWorld().getPlayer1());
        worldView.getNiftyView().setPlayer2(worldView.getWorld().getPlayer2());
    }

    private void initiateCameraControls() {
        new CameraController(worldView);
    }

    private void initiatePlayerControls(){
        PlayerController player1Control = new HumanPlayerController(worldView.getPlayer1View(),
                world.getPlayer1(), worldView, worldView.getPlayer1Mappings());

        AIPlayerController player2AIControl = new AIPlayerController(worldView.getPlayer2View(),
                world.getPlayer2(), worldView);

        PlayerController player2ControlSave = new HumanPlayerController(worldView.getPlayer2View(),
                world.getPlayer2(), worldView, worldView.getPlayer2Mappings());

        PlayerController player2Control;
        if(worldView.getWorld().isAI()){
            player2Control = player2AIControl;
        } else {
            player2Control = player2ControlSave;
        }

        worldView.getPlayer1Node().addControl(player1Control);
        bulletAppState.getPhysicsSpace().add(player1Control);
        worldView.getPlayer2Node().addControl(player2Control);
        bulletAppState.getPhysicsSpace().add(player2Control);
        bulletAppState.getPhysicsSpace().setGravity(Vector3f.ZERO);
    }

    private void initiatePowerUpControls(){
        new PowerUpController(worldView.getTerrainNode(), worldView);
    }
}

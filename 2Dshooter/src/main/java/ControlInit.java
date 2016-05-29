import com.jme3.bullet.BulletAppState;
import com.jme3.input.KeyInput;
import com.jme3.math.Vector3f;
import core.World;
import ctrl.*;
import view.WorldView;
import jME3.utils.KeyMappings;

/**
 * Created by David on 2016-05-25.
 */

public class ControlInit {
    private WorldView worldView;
    private World world;
    private BulletAppState bulletAppState;

    private KeyMappings player1Mappings = new KeyMappings(KeyInput.KEY_A, KeyInput.KEY_D, KeyInput.KEY_W,
            KeyInput.KEY_S, KeyInput.KEY_J, KeyInput.KEY_H, KeyInput.KEY_K, KeyInput.KEY_SPACE);
    private KeyMappings player2Mappings = new KeyMappings(KeyInput.KEY_LEFT, KeyInput.KEY_RIGHT, KeyInput.KEY_UP,
            KeyInput.KEY_DOWN, KeyInput.KEY_NUMPAD5, KeyInput.KEY_NUMPAD4, KeyInput.KEY_NUMPAD6, KeyInput.KEY_NUMPAD0);

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
        PlayerController player1Control = new HumanPlayerController(worldView.getPlayer1View(), world.getPlayer1(), worldView, player1Mappings);

        AIPlayerController player2AIControl = new AIPlayerController(worldView.getPlayer2View(), world.getPlayer2(), worldView);

        PlayerController player2ControlSave = new HumanPlayerController(worldView.getPlayer2View(), world.getPlayer2(), worldView, player2Mappings);

        PlayerController player2Control;
        if(worldView.isAI()){
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

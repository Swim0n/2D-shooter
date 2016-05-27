import com.jme3.bullet.BulletAppState;
import com.jme3.input.KeyInput;
import com.jme3.math.Vector3f;
import com.sun.org.apache.xpath.internal.SourceTree;
import core.HealthPowerUp;
import core.SpeedPowerUp;
import core.World;
import ctrl.*;
import gameView.GameView;
import utils.KeyMappings;

/**
 * Created by David on 2016-05-25.
 */

public class ControlInit {
    private GameView gameView;
    private World world;
    private ViewInit viewInit;

    //variables for controls
    private PlayerController player1Control;
    private PlayerController player2Control;
    private BulletAppState bulletAppState;
    private CameraController camControl;
    private boolean ai = false;


    private KeyMappings player1Mappings = new KeyMappings(KeyInput.KEY_A, KeyInput.KEY_D, KeyInput.KEY_W,
            KeyInput.KEY_S, KeyInput.KEY_J, KeyInput.KEY_H, KeyInput.KEY_K, KeyInput.KEY_SPACE);
    private KeyMappings player2Mappings = new KeyMappings(KeyInput.KEY_LEFT, KeyInput.KEY_RIGHT, KeyInput.KEY_UP,
            KeyInput.KEY_DOWN, KeyInput.KEY_NUMPAD5, KeyInput.KEY_NUMPAD4, KeyInput.KEY_NUMPAD6, KeyInput.KEY_NUMPAD0);
    public ControlInit(ViewInit viewInit){
        this.viewInit = viewInit;
        this.gameView = viewInit.getGameView();
        this.world = gameView.getWorld();
        this.bulletAppState = gameView.getBulletAppState();
        initiatePowerUpControls();
        initiatePlayerControls();
        initiateGUI();

        //disable to use static cam, will be better implemented with settings
        initiateCameraControls();
   }

    private void initiateGUI() {
        gameView.getNiftyView().setPlayer1(gameView.getWorld().getPlayer1());
        gameView.getNiftyView().setPlayer2(gameView.getWorld().getPlayer2());
    }

    private void initiateCameraControls() {
        camControl = new CameraController(gameView);
    }

    private void initiatePlayerControls(){
        player1Control = new HumanPlayerController(gameView.getPlayer1View(), world.getPlayer1(), gameView, player1Mappings);

        AIPlayerController player2AIControl = new AIPlayerController(gameView.getPlayer2View(), world.getPlayer2(),gameView);

        PlayerController player2ControlSave = new HumanPlayerController(gameView.getPlayer2View(), world.getPlayer2(), gameView, player2Mappings);

        if(this.ai == true){
            player2Control = player2AIControl;
        } else {
            player2Control = player2ControlSave;
        }

        gameView.getPlayer1Node().addControl(player1Control);
        bulletAppState.getPhysicsSpace().add(player1Control);
        gameView.getPlayer2Node().addControl(player2Control);
        bulletAppState.getPhysicsSpace().add(player2Control);
        bulletAppState.getPhysicsSpace().setGravity(Vector3f.ZERO);


    }

    private void initiatePowerUpControls(){
        new PowerUpController(new HealthPowerUp(gameView.getWorld().getTerrain()),gameView,gameView.getPowerUpView());
        new PowerUpController(new SpeedPowerUp(gameView.getWorld().getTerrain()), gameView,gameView.getPowerUpView());
    }
}

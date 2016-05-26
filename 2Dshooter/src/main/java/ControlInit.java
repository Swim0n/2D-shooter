import com.jme3.bullet.BulletAppState;
import com.jme3.input.KeyInput;
import core.HealthPowerUp;
import core.SpeedPowerUp;
import core.World;
import ctrl.*;
import gameView.GameView;
import utils.ApplicationAssets;
import utils.KeyMappings;

/**
 * Created by David on 2016-05-25.
 */

public class ControlInit {
    private GameView gameView;
    private ApplicationAssets appAssets;
    private World world;
    private ViewInit viewInit;

    //variables for controls
    private PlayerController player1Control;
    private PlayerController player2Control;
    private BulletAppState bulletAppState;
    private CameraController camControl;
    private boolean paused;
    private boolean ai;

    private KeyMappings player1Mappings = new KeyMappings(KeyInput.KEY_LEFT, KeyInput.KEY_RIGHT, KeyInput.KEY_UP,
            KeyInput.KEY_DOWN, KeyInput.KEY_NUMPAD5, KeyInput.KEY_NUMPAD4, KeyInput.KEY_NUMPAD6, KeyInput.KEY_NUMPAD0);
    private KeyMappings player2Mappings = new KeyMappings(KeyInput.KEY_A, KeyInput.KEY_D, KeyInput.KEY_W,
            KeyInput.KEY_S, KeyInput.KEY_J, KeyInput.KEY_H, KeyInput.KEY_K, KeyInput.KEY_SPACE);

    public ControlInit(ViewInit viewInit){
       this.viewInit = viewInit;
       this.gameView = viewInit.getGameView();
       this.appAssets = gameView.getAppAssets();
       this.gameView = appAssets.getGameView();
       this.world = appAssets.getWorld();
       this.bulletAppState = appAssets.getGameView().getBulletAppState();

       initiatePowerUpControls();

       initiatePlayerControls();

       //initiateGUI();

       initiateCameraControls();
   }

    private void initiateGUI() {
        appAssets.getGameView().getNiftyView().setP1ctr(player1Control);
        appAssets.getGameView().getNiftyView().setP2ctr(player2Control);
    }

    private void initiateCameraControls() {
        camControl = new CameraController(appAssets);
    }

    private void initiatePlayerControls(){
        player1Control = new HumanPlayerController(gameView.getPlayer1View(),world.getPlayer1(), appAssets, player1Mappings);

        AIPlayerController player2AIControl = new AIPlayerController(gameView.getPlayer2View(), world.getPlayer2(),appAssets);

        PlayerController player2ControlSave = new HumanPlayerController(gameView.getPlayer2View(), world.getPlayer2(), appAssets, player2Mappings);

        if(this.ai == true){
            player2Control = player2AIControl;
        } else {
            player2Control = player2ControlSave;
        }

        gameView.getPlayer1Node().addControl(player1Control);
        bulletAppState.getPhysicsSpace().add(player1Control);
        gameView.getPlayer2Node().addControl(player2Control);
        bulletAppState.getPhysicsSpace().add(player2Control);
    }


    private void initiatePowerUpControls(){
        new PowerUpController(new HealthPowerUp(appAssets),appAssets, gameView.getPowerUpView());
        new PowerUpController(new SpeedPowerUp(appAssets), appAssets, gameView.getPowerUpView());
    }

    //"pauses the game"
    public void pauseGame(){
        this.paused = true;
        this.player1Control.pause();
        this.player2Control.pause();
    }

    //"unpauses the game"
    public void unpauseGame(){
        this.paused = false;
        this.player1Control.unpause();
        this.player2Control.unpause();
    }

    public PlayerController getPlayer1Control() {return player1Control;}
    public PlayerController getPlayer2Control() {return player2Control;}
}

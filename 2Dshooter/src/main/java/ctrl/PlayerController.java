package ctrl;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.light.PointLight;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.control.LightControl;
import core.Player;
import gameView.BulletView;
import gameView.GUIView;
import gameView.GameView;
import gameView.PlayerView;
import utils.Utils;

/**
 * Created by Simon on 2016-05-11.
 */
public abstract class PlayerController extends BetterCharacterControl {
    protected final PlayerView playerView;
    private final GameView gameView;
    protected float speed;
    protected Player playerData;
    protected GUIView niftyView;

    public PlayerController(PlayerView playerView, Player player, GameView gameView){
        super(player.getRadius(), player.getHeight(), player.getMass());
        this.gameView = gameView;
        this.niftyView = gameView.getNiftyView();
        this.playerData = player;
        this.playerView = playerView;
        this.speed = playerData.getSpeed();
        this.spatial = playerView.getPlayerNode();
        warp(new Vector3f(playerView.getStartPos()));
    }

    @Override
    public void update(float tpf){
        super.update(tpf);
        if(gameView.isPaused()){
            setWalkDirection(new Vector3f(0,0,0));
            playerData.setPosition(Utils.jMEToVecMathVector3f(playerView.getPosition()));
            this.warp(new Vector3f(location.getX(),-2f, location.getZ()));
            return;
        }

        gameView.updateGUI();

        speed = playerData.getSpeed();


        if(playerData.getNeedsReset()){
            this.resetPlayer();
            playerData.setNeedsResetFalse();

            System.out.println("P1 wins: "+ gameView.getWorld().getPlayer1().getWins()+
                    "\nP2 wins: "+ gameView.getWorld().getPlayer2().getWins());
        }

        if(playerData.getHealth()==0){
            gameView.getWorld().setGameOver();
        }

        this.warp(new Vector3f(location.getX(),-2f, location.getZ()));
    }

    public void resetPlayer(){
        warp(new Vector3f(playerView.getStartPos()));
        playerView.setHealthBar(100);
        playerData.setStandard();
        niftyView.updateText();
    }

    //creates a new bullet specific to the player who fired it
    public void shootBullet(){
        BulletView bullet = new BulletView(this.playerView,gameView);
        //set up light for bullet
        PointLight lamp_light = new PointLight();
        lamp_light.setColor(playerView.getBodyColor().mult(5));
        lamp_light.setRadius(3.5f);
        LightControl lightControl = new LightControl(lamp_light);
        playerView.getGameView().getRootNode().addLight(lamp_light);
        bullet.getBullet().addControl(lightControl);
        //setup control for bullet
        BulletController bulletPhy = new BulletController(bullet, gameView, lamp_light);
        bullet.getBullet().addControl(bulletPhy);
        bulletPhy.setLinearVelocity(playerView.getGunRotation().getRotationColumn(2).mult(playerData.getBulletSpeed()));
        playerView.getGameView().getBulletAppState().getPhysicsSpace().add(bulletPhy);

        playerView.playShotSound();
    }
}

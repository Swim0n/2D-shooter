package ctrl;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.light.PointLight;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.control.LightControl;
import core.Player;
import gameView.BulletView;
import gameView.GUIView;
import gameView.PlayerView;
import utils.ApplicationAssets;

/**
 * Created by Simon on 2016-05-11.
 */
public abstract class PlayerController extends BetterCharacterControl {
    protected final PlayerView playerView;
    //protected final BulletView bulletView;
    private final ApplicationAssets appAssets;
    protected float speed;
    protected Player playerData;
    protected GUIView niftyView;

    public PlayerController(PlayerView playerView, Player player, ApplicationAssets appAssets){
        super(player.getRadius(), player.getHeight(), player.getMass());
        this.appAssets = appAssets;
        this.niftyView = appAssets.getGameView().getNiftyView();
        this.playerData = player;
        this.playerView = playerView;
        //this.bulletView = new BulletView(playerView,appAssets);
        this.speed = playerData.getSpeed();
        this.spatial = playerView.getPlayerNode();
        spatial.setLocalRotation(new Quaternion(0f,0f,0f,1f));
    }

    @Override
    public void update(float tpf){
        super.update(tpf);
        if(appAssets.getGameView().isPaused()){
            setWalkDirection(new Vector3f(0,0,0));
            return;
        }

        appAssets.getGameView().updateGUI();

        speed = playerData.getSpeed();


        if(appAssets.getWorld().gameOver()){
            resetPlayer();
            appAssets.getWorld().setGameOver(false);
        }


        if (playerData.getHealth()<=0){
            appAssets.getWorld().setGameOver(true);
            if(appAssets.getWorld().getPlayer1().getHealth()==0){

                appAssets.getWorld().getPlayer2().incWins();

            }else appAssets.getWorld().getPlayer1().incWins();


            System.out.println("P1 wins: "+ appAssets.getWorld().getPlayer1().getWins()+
                    "\nP2 wins: "+ appAssets.getWorld().getPlayer2().getWins());

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
        BulletView bullet = new BulletView(this.playerView,appAssets);

        PointLight lamp_light = new PointLight();
        lamp_light.setColor(playerView.getBodyColor().mult(5));

        lamp_light.setRadius(3.5f);
        LightControl lightControl = new LightControl(lamp_light); //TBR
        playerView.getGameView().getRootNode().addLight(lamp_light);
        BulletController bulletPhy = new BulletController(bullet, appAssets, lamp_light);
        bullet.getBullet().addControl(bulletPhy);
        bulletPhy.setLinearVelocity(playerView.getGunRotation().getRotationColumn(2).mult(50));
        playerView.getGameView().getBulletAppState().getPhysicsSpace().add(bulletPhy);
        bullet.getBullet().addControl(lightControl);
        playerView.playShotSound();
    }
}

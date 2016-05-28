package ctrl;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.control.LightControl;
import core.Player;
import gameView.BulletView;
import gameView.GUIView;
import gameView.GameView;
import gameView.PlayerView;
import utils.Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Simon on 2016-05-11.
 */
public abstract class PlayerController extends BetterCharacterControl {
    protected final PlayerView playerView;
    private final GameView gameView;
    protected float speed;
    protected Player player;
    protected GUIView niftyView;

    public PlayerController(PlayerView playerView, Player player, GameView gameView){
        super(player.getRadius(), player.getHeight(), player.getMass());
        this.gameView = gameView;
        this.niftyView = gameView.getNiftyView();
        this.player = player;
        this.playerView = playerView;
        this.speed = this.player.getSpeed();
        this.spatial = playerView.getPlayerNode();
        warp(new Vector3f(playerView.getStartPos()));
    }

    @Override
    public void update(float tpf){
        super.update(tpf);
        if(gameView.isPaused()){
            setWalkDirection(new Vector3f(0,0,0));
            player.setPosition(Utils.jMEToVecMathVector3f(playerView.getPosition()));
            this.warp(new Vector3f(location.getX(),-2f, location.getZ()));
            return;
        }

        gameView.updateGUI();

        speed = player.getSpeed();


        if(player.getNeedsReset()){
            this.resetPlayer();
            player.setNeedsResetFalse();

            System.out.println("P1 wins: "+ gameView.getWorld().getPlayer1().getWins()+
                    "\nP2 wins: "+ gameView.getWorld().getPlayer2().getWins());
        }

        if(player.getHealth()==0){
            gameView.getWorld().setGameOver();
        }

        playerView.rotateGun(player.getGunRotation()*tpf);
        player.setDashMeter(player.getDashMeterPercent()+tpf* player.getDashMeterRegenRate());
        playerView.setDashBar(player.getDashMeterPercent());
        if(!player.isOverloaded()){
            player.setShotMeter(player.getShotMeterPercent()+tpf* player.getShotMeterRegenRate());
            playerView.setShotBarColor(ColorRGBA.Yellow);
        } else {
            playerView.setShotBarColor(ColorRGBA.Red);
        }
        playerView.setShotBar((player.getShotMeterPercent()));


        this.warp(new Vector3f(location.getX(),-2f, location.getZ()));
    }

    public void resetPlayer(){
        warp(new Vector3f(playerView.getStartPos()));
        playerView.setHealthBar(100);
        player.setStandard();
        niftyView.updateText();
    }

    //creates a new bullet specific to the player who fired it
    protected void shootBullet(){
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
        bulletPhy.setLinearVelocity(playerView.getGunRotation().getRotationColumn(2).mult(player.getBulletSpeed()));
        playerView.getGameView().getBulletAppState().getPhysicsSpace().add(bulletPhy);
        player.setShotMeter(player.getShotMeterPercent()- player.getShotThreshold());
        playerView.playShotSound();
    }

    protected void dash(){
        player.dashing = true;
        playerView.playDashSound();
        player.setDashMeter(player.getDashMeterPercent()- player.getDashThreshold());
        dashTimer(player.getDashMillis());
    }

    private void dashTimer(int millis){
        Timer timer = new Timer(millis, new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                player.dashing = false;
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}

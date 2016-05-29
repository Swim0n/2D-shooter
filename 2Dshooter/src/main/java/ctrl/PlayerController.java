package ctrl;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import core.Player;
import view.BulletView;
import view.GUIView;
import view.PlayerView;
import view.WorldView;
import utils.Utils;
import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Simon on 2016-05-11.
 */
public abstract class PlayerController extends BetterCharacterControl {
    protected final PlayerView playerView;
    private final WorldView worldView;
    private final BulletView bulletView;
    protected float speed;
    protected Player player;
    protected GUIView niftyView;

    public PlayerController(PlayerView playerView, Player player, WorldView worldView){
        super(player.getRadius(), player.getHeight(), player.getMass());
        this.worldView = worldView;
        this.niftyView = worldView.getNiftyView();
        this.bulletView = worldView.getBulletView();
        this.player = player;
        this.playerView = playerView;
        this.speed = this.player.getSpeed();
        this.spatial = playerView.getPlayerNode();
        warp(new Vector3f(playerView.getStartPos()));
    }

    @Override
    public void update(float tpf){
        super.update(tpf);
        if(worldView.isPaused()){
            setWalkDirection(new Vector3f(0,0,0));
            player.setPosition(Utils.jMEToVecMathVector3f(playerView.getPosition()));
            this.warp(new Vector3f(location.getX(),-2f, location.getZ()));
            return;
        }

        worldView.updateGUI();

        speed = player.getSpeed();


        if(player.getNeedsReset()){
            this.resetPlayer();
            player.setNeedsResetFalse();

            System.out.println("P1 wins: "+ worldView.getWorld().getPlayer1().getWins()+
                    "\nP2 wins: "+ worldView.getWorld().getPlayer2().getWins());
        }

        if(player.getHealth()==0){
            worldView.getWorld().setGameOver();
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

    protected void shootBullet(){
        Geometry bullet = bulletView.getNewBullet(playerView);
        new BulletController(playerView, player, bullet, worldView, bulletView.getBulletLight());
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

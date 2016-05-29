package ctrl;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
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
    private final BulletView bulletView;
    private final PointLight bulletLight;
    private final Node rootNode;
    protected float speed;
    protected Player player;
    protected GUIView niftyView;

    public PlayerController(PlayerView playerView, Player player, GameView gameView){
        super(player.getRadius(), player.getHeight(), player.getMass());
        this.gameView = gameView;
        this.niftyView = gameView.getNiftyView();
        this.bulletView = gameView.getBulletView();
        this.player = player;
        this.playerView = playerView;
        this.speed = this.player.getSpeed();
        this.spatial = playerView.getPlayerNode();
        this.rootNode = gameView.getRootNode();
        this.bulletLight = bulletView.getBulletLight(playerView);
        warp(new Vector3f(playerView.getStartPos()));
    }

    @Override
    public void update(float tpf){
        super.update(tpf);


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

    protected void shootBullet(){
        Geometry bullet = bulletView.getBullet(playerView);
        rootNode.addLight(bulletLight);
        bullet.addControl(new LightControl(bulletLight));
        new BulletController(playerView, bullet, gameView, bulletLight);
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

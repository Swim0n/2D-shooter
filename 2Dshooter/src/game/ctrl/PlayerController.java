package game.ctrl;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import game.core.Player;
import game.core.World;
import game.gameView.BulletView;
import game.gameView.GUIView;
import game.gameView.PlayerView;

/**
 * Created by Simon on 2016-05-11.
 */
public abstract class PlayerController extends BetterCharacterControl {
    protected final PlayerView playerView;
    protected final BulletView bulletView;
    protected float speed;
    protected Player playerData;
    protected GUIView niftyView;
    protected boolean paused = true;

    public PlayerController(PlayerView playerView, Player player, GUIView niftyView){
        super(player.getRadius(), player.getHeight(), player.getMass());
        this.playerData = player;
        this.playerView = playerView;
        this.bulletView = new BulletView(playerView);
        this.speed = playerData.getSpeed();
        this.niftyView = niftyView;
    }

    @Override
    public void update(float tpf){
        if(this.paused){
            setWalkDirection(new Vector3f(0,0,0));
            return;
        }
        super.update(tpf);
        playerView.getGameView().updateGUI();
        speed = playerData.getSpeed();
        if (playerData.getHealth()<=0){

            if(playerView.getGameView().getPlayer1Control().getPlayerData().getHealth()==0){
                playerView.getGameView().getPlayer2Control().getPlayerData().incWins();
            }else playerView.getGameView().getPlayer1Control().getPlayerData().incWins();

            playerView.getGameView().getPlayer1Control().resetPlayer();
            playerView.getGameView().getPlayer2Control().resetPlayer();
            System.out.println("P1 wins: "+ playerView.getGameView().getPlayer1Control().getPlayerData().getWins()+
                    "\nP2 wins: "+ playerView.getGameView().getPlayer2Control().getPlayerData().getWins());
        }
        warp(new Vector3f(location.getX(),-2f, location.getZ()));
    }

    public void takeDamage(float damage){
        playerData.setHealth(playerData.getHealth() - damage);
        playerView.setHealthBar(playerData.getHealth());
        niftyView.updateText();
    }

    public void resetPlayer(){
        this.warp(new Vector3f(playerView.getStartPos()));
        this.playerView.setHealthBar(100);
        this.playerData.setStandard();
        this.niftyView.updateText();
    }

    public PlayerView getPlayerView(){
        return this.playerView;
    }

    //creates a new bullet specific to the player who fired it
    public void shootBullet(){
        bulletView.createBullet();
    }

    public Player getPlayerData(){
        return this.playerData;
    }

    public void pause(){
        this.paused = true;
    }

    public void unpause(){
        this.paused = false;
    }
}

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
    protected Player playerData;
    protected float speed;
    protected Vector3f lastDirection = new Vector3f(0f,0f,20f); //last direction this player moved, start value is a placeholder until real movement
    protected GUIView niftyView;
    protected boolean paused = true;

    public PlayerController(PlayerView playerView, float radius, float height, float mass, GUIView niftyView, World world){
        super(radius, height, mass);
        if(playerView.getPlayerNode().equals(playerView.getGameView().getPlayer1Node())) {
            this.playerData = world.getPlayer1();
        } else if (playerView.getPlayerNode().equals(playerView.getGameView().getPlayer2Node())) {
            this.playerData = world.getPlayer2();
        }
        this.playerView = playerView;
        this.bulletView = new BulletView(playerView);
        this.speed = playerData.getSpeed();
        this.niftyView = niftyView;
    }

    @Override
    public void update(float tpf){
        if(this.paused){
            setWalkDirection(lastDirection.set(0f,0f,0f));
            return;
        }
        super.update(tpf);
        playerView.getGameView().updateGUI();
        speed = playerData.getSpeed();
        if (playerData.getHealth()==0){

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

    public Vector3f getDashDirection(boolean leftTrue){
        System.out.println("init");

        //direction of gun
        Vector3f gunRot = playerView.getGunRotation().getRotationColumn(2);

        //direction of dash
        Quaternion halfPi = new Quaternion();
        if(leftTrue) {
            halfPi.fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y);
        }else{
            halfPi.fromAngleNormalAxis(FastMath.HALF_PI, Vector3f.UNIT_Y);}
        Matrix3f rotation = halfPi.toRotationMatrix();
        Vector3f dashDirection = rotation.mult(gunRot.normalize());
        return dashDirection;
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

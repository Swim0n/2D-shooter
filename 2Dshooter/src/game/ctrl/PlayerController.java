package game.ctrl;

import com.jme3.bullet.control.BetterCharacterControl;
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
    protected final PlayerView view;
    protected final BulletView bulletView;
    protected Player playerData;
    protected float speed;
    protected Vector3f lastDirection = new Vector3f(0f,0f,20f); //last direction this player moved, start value is a placeholder until real movement
    protected GUIView niftyView;
    protected boolean paused = true;

    public PlayerController(PlayerView view, float radius, float height, float mass, GUIView niftyView, World world){
        super(radius, height, mass);
        if(view.getPlayerNode().equals(view.getGameView().getPlayer1Node())) {
            this.playerData = world.getPlayer1();
        } else if (view.getPlayerNode().equals(view.getGameView().getPlayer2Node())) {
            this.playerData = world.getPlayer2();
        }
        this.view = view;
        this.bulletView = new BulletView(view.getGameView(), view);
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
        bulletView.getGameView().updateGUI();
        speed = playerData.getSpeed();
        if (playerData.getHealth()==0){

            if(view.getGameView().getPlayer1Control().getPlayerData().getHealth()==0){
                view.getGameView().getPlayer2Control().getPlayerData().incWins();
            }else view.getGameView().getPlayer1Control().getPlayerData().incWins();

            view.getGameView().getPlayer1Control().resetPlayer();
            view.getGameView().getPlayer2Control().resetPlayer();
            System.out.println("P1 wins: "+view.getGameView().getPlayer1Control().getPlayerData().getWins()+
                    "\nP2 wins: "+view.getGameView().getPlayer2Control().getPlayerData().getWins());
        }
        warp(new Vector3f(location.getX(),-2f, location.getZ()));
    }

    public void takeDamage(float damage){
        playerData.setHealth(playerData.getHealth() - damage);
        view.setHealthBar(playerData.getHealth());
        niftyView.updateText();
    }

    public void pause(){
        this.paused = true;
    }

    public void unpause(){
        this.paused = false;
    }

    public void resetPlayer(){
        this.warp(new Vector3f(view.getStartPos()));
        this.view.setHealthBar(100);
        this.playerData.setStandard();
        this.niftyView.updateText();
    }
    public Player getPlayerData(){
        return this.playerData;
    }

    //creates a new bullet specific to the player who fired it
    public void shootBullet(){
        bulletView.createBullet();
    }
}

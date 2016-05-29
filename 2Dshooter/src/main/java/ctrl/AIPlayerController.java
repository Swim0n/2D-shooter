package ctrl;

import com.jme3.collision.CollisionResults;
import com.jme3.math.*;
import gameView.GameView;
import gameView.PlayerView;
import java.util.ArrayList;
import core.Player;

/**
 * Created by Simon on 2016-05-10.
 */
public class AIPlayerController extends PlayerController {

    private float bulletCooldown = 200f;
    private long lastShotTime = 0;
    private ArrayList currentPath = new ArrayList();
    private boolean paused = true;
    private GameView gameView;


    private int stepCount = 0;
    private int stepPause = 0;
    private ArrayList path;


    public AIPlayerController(PlayerView view, Player player, GameView gameView){
        super(view, player,gameView);
        this.gameView = gameView;
        this.niftyView = gameView.getNiftyView();

    }

    @Override
    public void update(float tpf){
        if(paused){
            return;
        }
        super.update(tpf);

        Vector3f directionToPlayer = gameView.getPlayer1Node().getWorldTranslation().subtract(spatial.getWorldTranslation());

        //logic for always rotating the gun to face the player
        if(!playerView.getGunRotation().getRotationColumn(2).equals(directionToPlayer.normalize())){
            Quaternion halfPi = new Quaternion();
            halfPi.fromAngleNormalAxis(FastMath.HALF_PI, Vector3f.UNIT_Y);
            Matrix3f rotation = halfPi.toRotationMatrix();
            Vector3f compareVector = rotation.mult(directionToPlayer.normalize());
            if(playerView.getGunRotation().getRotationColumn(2).angleBetween(compareVector) < FastMath.HALF_PI){
                playerView.rotateGun(tpf*-140f);
            } else {
                playerView.rotateGun(tpf*140f);
            }
        }

        //logic for only shooting when player is in line of sight
        Ray ray = new Ray(spatial.getWorldTranslation(),directionToPlayer.normalize());
        ray.setLimit(directionToPlayer.length());
        CollisionResults results = new CollisionResults();
        gameView.getTerrainNode().collideWith(ray, results);
        if(results.size() == 0 && System.currentTimeMillis() - lastShotTime > bulletCooldown) {
            shootBullet();
            lastShotTime = System.currentTimeMillis();
        }

        //logic for moving towards player
        if(results.size() == 0) {
            if(directionToPlayer.length() < 15f){
                setWalkDirection(new Vector3f(0f,0f,0f));
            } else {
                setWalkDirection(directionToPlayer.normalize().mult(speed));
            }
        } else {
            setWalkDirection(new Vector3f(0f,0f,0f));
        }
    }
    public void pause(){
        this.paused = true;
    }

    public void unpause(){
        this.paused = false;
    }
}

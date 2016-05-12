package game.ctrl;

import com.jme3.collision.CollisionResults;
import com.jme3.math.*;
import game.core.World;
import game.gameView.GUIView;
import game.gameView.PlayerView;

/**
 * Created by Simon on 2016-05-10.
 */
public class AIPlayerController extends PlayerController {

    private float bulletCooldown = 200f;
    private long lastShotTime = 0;

    public AIPlayerController(PlayerView view, float radius, float height, float mass, GUIView niftyView, World world){
        super(view,radius, height, mass, niftyView, world);

    }

    @Override
    public void update(float tpf){
        super.update(tpf);

        Vector3f directionToPlayer = view.getGameView().getPlayer1Node().getWorldTranslation().subtract(spatial.getWorldTranslation());


        //logic for always rotating the gun to face the player
        if(!view.getGunRotation().getRotationColumn(2).equals(directionToPlayer.normalize())){
            Quaternion halfPi = new Quaternion();
            halfPi.fromAngleNormalAxis(FastMath.HALF_PI, Vector3f.UNIT_Y);
            Matrix3f rotation = halfPi.toRotationMatrix();
            Vector3f compareVector = rotation.mult(directionToPlayer.normalize());
            if(view.getGunRotation().getRotationColumn(2).angleBetween(compareVector) < FastMath.HALF_PI){
                view.rotateGun(tpf*-140f);
            } else {
                view.rotateGun(tpf*140f);
            }
        }

        //logic for only shooting when player is in line of sight
        Ray ray = new Ray(spatial.getWorldTranslation(),directionToPlayer.normalize());
        ray.setLimit(directionToPlayer.length());
        CollisionResults results = new CollisionResults();
        view.getGameView().getTerrainNode().collideWith(ray, results);
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

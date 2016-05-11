package game.ctrl;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.material.Material;
import com.jme3.math.*;
import com.jme3.scene.Geometry;
import game.core.Player;
import game.core.World;
import game.gameView.BulletView;
import game.gameView.GUIView;
import game.gameView.PlayerView;

/**
 * Created by Simon on 2016-05-10.
 */
public class AIPlayerController extends PlayerController {


    private boolean paused = true;


    public AIPlayerController(PlayerView view, float radius, float height, float mass, GUIView niftyView, World world){
        super(view,radius, height, mass, niftyView, world);

    }

    @Override
    public void update(float tpf){
        if(this.paused){
            return;
        }
        super.update(tpf);
        bulletView.getGameView().updateGUI();
        speed = playerData.getSpeed();
        Vector3f directionToPlayer = view.getGameView().getPlayer1Node().getWorldTranslation().subtract(spatial.getWorldTranslation());
        directionToPlayer.normalize();


        if(!view.getGunRotation().getRotationColumn(2).equals(directionToPlayer)){
            Quaternion halfPi = new Quaternion();
            halfPi.fromAngleNormalAxis(FastMath.HALF_PI, Vector3f.UNIT_Y);
            Matrix3f rotation = halfPi.toRotationMatrix();
            Vector3f compareVector = rotation.mult(directionToPlayer);
            if(view.getGunRotation().getRotationColumn(2).angleBetween(compareVector) < FastMath.HALF_PI){
                view.rotateGun(tpf*-140f);
            } else {
                view.rotateGun(tpf*140f);
            }
        }

        directionToPlayer = view.getGameView().getPlayer1Node().getWorldTranslation().subtract(spatial.getWorldTranslation());
        Ray ray = new Ray(spatial.getWorldTranslation(),directionToPlayer.normalize());
        ray.setLimit(directionToPlayer.length());
        CollisionResults results = new CollisionResults();
        view.getGameView().getTerrainNode().collideWith(ray, results);
        if(results.size() == 0) {
            shootBullet();
        }
    }

    public void pause(){
        this.paused = true;
    }

    public void unpause(){
        this.paused = false;
    }
}

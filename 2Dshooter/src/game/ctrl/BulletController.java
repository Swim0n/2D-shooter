package game.ctrl;


import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import game.gameView.BulletView;

/**
 * Created by David on 2016-04-19.
 */
public class BulletController extends RigidBodyControl{
    private BulletView bulletView;
    private float bulletSpeed = 160;

    public BulletController(BulletView bulletView){
        this.bulletView = bulletView;

        //this.setLinearVelocity(this.bulletView.getPlayerController().getLastDirection().mult(bulletSpeed));
        //setDirection(this.bulletView.getPlayerController().getLastDirection());
    }

    public void update(float tpf){
        super.update(tpf);
    }

    public void setDirection(Vector3f direction){
       // this.setLinearVelocity(direction.mult(bulletSpeed));
    }
}

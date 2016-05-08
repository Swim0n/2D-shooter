package game.ctrl;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import game.gameView.BulletView;

/**
 * Created by David on 2016-04-19.
 */
public class BulletController extends RigidBodyControl{
    private BulletView bulletView;
    private PlayerController player1Control;
    private PlayerController player2Control;

    public BulletController(BulletView bulletView, PlayerController p1, PlayerController p2){
        this.bulletView = bulletView;
        this.player1Control = p1;
        this.player2Control = p2;
    }

    public void update(float tpf){
        super.update(tpf);
        CollisionResults results = new CollisionResults();

        bulletView.getGameView().getStageNode().collideWith(spatial.getWorldBound(), results);

        if (results.size() > 0){
            spatial.removeFromParent();
            bulletView.getGameView().getBulletAppState().getPhysicsSpace().remove(spatial.getControl(0));
        }

        results.clear();

        if (bulletView.getPlayerView().getPlayerNode().equals(bulletView.getGameView().getPlayer1Node())){
            bulletView.getGameView().getPlayer2Node().collideWith(spatial.getWorldBound(), results);
            if (results.size() > 0){
                spatial.removeFromParent();
                bulletView.getGameView().getBulletAppState().getPhysicsSpace().remove(spatial.getControl(0));

                player2Control.takeDamage(player1Control.getPlayerData().getDamage());//player taking damage, runs for every bullet, needs logic fi

                System.out.println("P2 HP: " + Float.toString(player2Control.getPlayerData().getHealth()));
            }
        } else if (bulletView.getPlayerView().getPlayerNode().equals(bulletView.getGameView().getPlayer2Node())) {
            bulletView.getGameView().getPlayer1Node().collideWith(spatial.getWorldBound(), results);

            if (results.size() > 0){
                spatial.removeFromParent();
                bulletView.getGameView().getBulletAppState().getPhysicsSpace().remove(spatial.getControl(0));

                player1Control.takeDamage(player2Control.getPlayerData().getDamage()); //player taking damage, runs for every bullet, needs logic fix

                System.out.println("P1 HP: " + Float.toString(player1Control.getPlayerData().getHealth()));
            }
        }

    }
}

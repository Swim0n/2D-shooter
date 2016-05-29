package ctrl;

import com.jme3.collision.CollisionResults;
import com.jme3.math.*;
import core.AI;
import core.Tile;
import core.World;

import view.PlayerView;
import java.util.ArrayList;
import core.Player;
import view.WorldView;

/**
 * Control for an AI controlled player
 */
public class AIPlayerController extends PlayerController {

    private float bulletCooldown = 200f;
    private long lastShotTime = 0;
    private WorldView worldView;


    private int stepCount = 0;
    private AI AI;
    private ArrayList path;
    private World world;


    public AIPlayerController(PlayerView view, Player player, WorldView worldView){
        super(view, player, worldView);
        this.world = worldView.getWorld();
        this.AI = new AI(world.getTerrain());
        this.worldView = worldView;
        this.niftyView = worldView.getNiftyView();
    }

    @Override
    public void update(float tpf){
        super.update(tpf);
        if(world.isPaused()){
            setWalkDirection(Vector3f.ZERO);
            return;}

        Vector3f directionToPlayer = worldView.getPlayer1Node().getWorldTranslation().subtract(spatial.getWorldTranslation());


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
        worldView.getTerrainNode().collideWith(ray, results);
        if(results.size() == 0 && System.currentTimeMillis() - lastShotTime > bulletCooldown) {
            shootBullet();
            lastShotTime = System.currentTimeMillis();
        }

        if(path != null) {
            for (int i = 0; i < path.size(); i++) {
                if (((Tile) path.get(i)).getBlocked()) {
                    resetPath();
                    return;
                }
            }
        }


        //if a path exists, set walkdirection toward next tile in path until the next tile has been reached. Repeat until at the last tile of path.
        if(path != null){
        if(stepCount < path.size() ){
            Vector3f directionNextTile = (new Vector3f((float) ((((Tile) path.get(stepCount)).getX())), -2f,
                    (float) (((Tile) path.get(stepCount)).getY()))).subtract(spatial.getWorldTranslation());

            setWalkDirection(directionNextTile.normalize().mult(speed));
            if(!directionNextTile.equals(Vector3f.ZERO)){
                playerView.getBodyNode().lookAt(playerView.getPlayerNode().getLocalTranslation().add(directionNextTile), Vector3f.UNIT_Y);
            }


            if (updateTookStep(directionNextTile)) {
                if (stepCount <= path.size()) {
                    stepCount++;
                } else {
                    stepCount = 0;
                }
            }
        }else {
            resetPath();
        }
        }else{
            resetPath();
        }


    }

    public void resetPath(){
        stepCount = 0;
        path = AI.findPath((int) spatial.getWorldTranslation().getX(),(int) spatial.getWorldTranslation().getZ(),
                (int) worldView.getPlayer1Node().getWorldTranslation().getX(),(int)worldView.getPlayer1Node().getWorldTranslation().getZ());
    }
    public boolean updateTookStep(Vector3f directionToNextTile){
        if(directionToNextTile.length() < 0.3f){
            return true;
        }
        return false;
    }



}

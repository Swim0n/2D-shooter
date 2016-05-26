package ctrl;

import com.jme3.collision.CollisionResults;
import com.jme3.math.*;
import game.core.PathFinder;
import game.core.Tile;
import game.core.World;
import game.gameView.GUIView;
import game.gameView.PlayerView;
import org.lwjgl.Sys;

import java.util.ArrayList;
import core.Player;
import gameView.GUIView;
import gameView.PlayerView;

/**
 * Created by Simon on 2016-05-10.
 */
public class AIPlayerController extends PlayerController {

    private float bulletCooldown = 200f;
    private long lastShotTime = 0;
    private ArrayList currentPath = new ArrayList();
    private boolean paused = true;


    private int stepCount = 0;
    private int stepPause = 0;
    private PathFinder pathFinder;
    private ArrayList path;



    public AIPlayerController(PlayerView view, float radius, float height, float mass, GUIView niftyView, World world){
        super(view,radius, height, mass, niftyView, world);
        this.pathFinder = new PathFinder(world.getTerrain());
        path = pathFinder.findPath(41,41,0,0);
        System.out.println("path:");
        for(int i=0; i<path.size(); i++){
            System.out.println("X: " + ((Tile) path.get(i)).getX() + "Y: " + ((Tile) path.get(i)).getY());
        }
        System.out.println("/path");
    public AIPlayerController(PlayerView view, Player player, GUIView niftyView){
        super(view, player, niftyView);

    }

    @Override
    public void update(float tpf){
        if(paused){
            return;
        }
        super.update(tpf);

        Vector3f directionToPlayer = playerView.getGameView().getPlayer1Node().getWorldTranslation().subtract(spatial.getWorldTranslation());

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
        playerView.getGameView().getTerrainNode().collideWith(ray, results);
        if(results.size() == 0 && System.currentTimeMillis() - lastShotTime > bulletCooldown) {
            shootBullet();
            lastShotTime = System.currentTimeMillis();
        }





        Vector3f directionNextTile = spatial.getWorldTranslation().subtract(new Vector3f((float) ((((Tile) path.get(stepCount)).getX()) - 35.5), (float) (((Tile) path.get(stepCount)).getY() - 35), 0f));
        if(tookStep(directionNextTile)) {
            setWalkDirection(directionNextTile);
            if (stepCount < path.size()) {
                stepCount++;
            } else {
                stepCount = 0;
            }
        }




        //logic for moving towards player
        /**if(results.size() == 0) {
            if(directionToPlayer.length() < 15f){
                setWalkDirection(new Vector3f(0f,0f,0f));
            } else {
                setWalkDirection(directionToPlayer.normalize().mult(speed));
            }
        } else {
            setWalkDirection(new Vector3f(0f,0f,0f));
        }**/
    }
    public boolean tookStep(Vector3f directionToNextTile){

        Tile target = (Tile) path.get(stepCount);

        if(directionToNextTile.length() < 1){
            return true;
        }
        return false;
    }
    public void pause(){
        this.paused = true;
    }

    public void unpause(){
        this.paused = false;
    }
}

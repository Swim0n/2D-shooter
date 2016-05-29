package ctrl;

import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.math.*;
import com.sun.xml.internal.ws.dump.LoggingDumpTube;
import core.PathFinder;
import core.Tile;
import core.World;
import gameView.GUIView;
import gameView.PlayerView;
import org.lwjgl.Sys;

import view.PlayerView;
import java.util.ArrayList;
import core.Player;
import gameView.GUIView;
import gameView.PlayerView;
import utils.ApplicationAssets;
import utils.Utils;
import view.WorldView;

/**
 * Created by Simon on 2016-05-10.
 */
public class AIPlayerController extends PlayerController {

    private float bulletCooldown = 200f;
    private long lastShotTime = 0;
    private ArrayList currentPath = new ArrayList();
    private boolean paused = true;
    private ApplicationAssets applicationAssets;
    private WorldView worldView;


    private int stepCount = 0;
    private int stepPause = 0;
    private PathFinder pathFinder;
    private ArrayList path;
    private World world;


    public AIPlayerController(PlayerView view, Player player, ApplicationAssets aa, AssetManager assetManager){
        super(view, player, worldView);
        this.world = aa.getWorld();
        this.applicationAssets = aa;
        this.pathFinder = new PathFinder(world.getTerrain(), aa, assetManager);
        this.worldView = worldView;
        this.niftyView = worldView.getNiftyView();

    }

    @Override
    public void update(float tpf){
        if(super.paused){
            return;
        }
        super.update(tpf);

        Vector3f directionToPlayer = worldView.getPlayer1Node().getWorldTranslation().subtract(spatial.getWorldTranslation());

        if (playerData.getHealth()<=0){
            this.stepCount = 0;
            path = null;
            setWalkDirection(new Vector3f(0f, -2f, 0f));
        }
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

        if(path != null){
        if(stepCount < path.size() ){
            Vector3f directionNextTile = (new Vector3f((float) ((((Tile) path.get(stepCount)).getX())), -2f, (float) (((Tile) path.get(stepCount)).getY()))).subtract(spatial.getWorldTranslation());
            setWalkDirection(directionNextTile.normalize().mult(speed));

            if (updateTookStep(directionNextTile)) {
                if (stepCount <= path.size()) {
                    stepCount++;
                } else {
                    stepCount = 0;
                }
            }
        }else {

            stepCount = 0;
            path = pathFinder.findPath((int) spatial.getWorldTranslation().getX(), (int) spatial.getWorldTranslation().getZ(), (int) playerView.getGameView().getPlayer1Node().getWorldTranslation().getX(), (int) playerView.getGameView().getPlayer1Node().getWorldTranslation().getZ());

        }
        }else{
            stepCount = 0;
            path = pathFinder.findPath((int) spatial.getWorldTranslation().getX(),(int) spatial.getWorldTranslation().getZ(),(int) playerView.getGameView().getPlayer1Node().getWorldTranslation().getX(),(int)playerView.getGameView().getPlayer1Node().getWorldTranslation().getZ());
        }



        /**
        //logic for moving towards player
        if(results.size() == 0) {
            if(directionToPlayer.length() < 15f){
                setWalkDirection(new Vector3f(0f,0f,0f));
            } else {
                setWalkDirection(directionToPlayer.normalize().mult(speed));
            }
        } else {
            setWalkDirection(new Vector3f(0f,0f,0f));
        }**/
    }


    public boolean updateTookStep(Vector3f directionToNextTile){
        if(directionToNextTile.length() < 0.5f){
            return true;
        }
        return false;
    }



}

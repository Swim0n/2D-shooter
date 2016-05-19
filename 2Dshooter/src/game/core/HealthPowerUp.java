package game.core;

import game.utils.ApplicationAssets;

/**
 * Created by Hannes on 10/05/2016.
 */
public class HealthPowerUp implements PowerUp {

    Ground groundModel = new Ground();
    private ApplicationAssets applicationAssets;

    private int [] position;
    private int x;
    private int z;

    public HealthPowerUp(ApplicationAssets applicationAssets){
        this.applicationAssets = applicationAssets;
        setPosition();
    }

    public void setPosition() {
        //position =
        //-groundX/2+treeShape.getXExtent()+position[0]*4,-2, -groundZ/2+treeShape.getZExtent()+position[1]*4+0.5f
        //position = applicationAssets.getWorld().getTerrain().getRandomPos(groundModel.getWidth(),groundModel.getHeight(),2,2);


        //x = position[0];
        //z = position[1];

    }

    public void setEffect(Player player) {
        player.setHealth(player.getHealth()+20);


    }
    public int getX(){
        return this.x;
    }
    public int getZ(){
        return this.z;
    }
}

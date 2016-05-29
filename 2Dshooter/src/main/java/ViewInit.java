import com.jme3.system.AppSettings;
import view.WorldView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by David on 2016-05-25.
 */

public class ViewInit {
    private final WorldView worldView;

    public ViewInit() throws InterruptedException, IOException {

        AppSettings settings = new AppSettings(false);
        settings.setSettingsDialogImage("Interface/voxelgalaxy.png");

        BufferedImage[] voxelIcons = new BufferedImage[] {
                ImageIO.read( Main.class.getResource( "Interface/voxel-128.png" ) ),
                ImageIO.read( Main.class.getResource( "Interface/voxel-32.png" ) ),
                ImageIO.read( Main.class.getResource( "Interface/voxel-16.png" ) )
        };
        settings.setIcons(voxelIcons);
        settings.setTitle("Voxel Galaxy: Arena");
        settings.setVSync(true);

        this.worldView = new WorldView();

        this.worldView.setSettings(settings);
        this.worldView.start();


        //appAssets in worldView is null since simpleInitApp hasn't been called yet, thus the wait
        while (!worldView.isInitialized()){
            Thread.currentThread().sleep(100);
        }
    }

    public WorldView getWorldView(){
        return worldView;
    }
}

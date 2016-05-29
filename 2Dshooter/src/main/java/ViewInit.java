import com.jme3.system.AppSettings;
import view.WorldView;

/**
 * Created by David on 2016-05-25.
 */

public class ViewInit {
    private final WorldView worldView;

    public ViewInit() throws InterruptedException {

        AppSettings settings = new AppSettings(false);
        settings.setSettingsDialogImage("Interface/voxelgalaxy.png");
        settings.setTitle("Voxel Galaxy: Arena");
        settings.setVSync(true);

        this.worldView = new WorldView();

        this.worldView.setSettings(settings);
        this.worldView.start();

        //appAssets in worldView is null since simpleInitApp hasn't been called yet, thus the wait
        while (!worldView.isInitialized()){
            Thread.currentThread().sleep(1000);
        }
    }

    public WorldView getWorldView(){
        return worldView;
    }
}

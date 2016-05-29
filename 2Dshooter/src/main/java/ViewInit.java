import com.jme3.system.AppSettings;
import gameView.GameView;

/**
 * Created by David on 2016-05-25.
 */

public class ViewInit {
    private final GameView gameView;

    public ViewInit() throws InterruptedException {

        AppSettings settings = new AppSettings(false);
        settings.setSettingsDialogImage("Interface/voxelgalaxy.png");
        settings.setTitle("Voxel Galaxy: Arena");
        settings.setVSync(true);

        this.gameView = new GameView();

        this.gameView.setSettings(settings);
        this.gameView.start();

        //appAssets in gameView is null since simpleInitApp hasn't been called yet, thus the wait
        while (!gameView.isInitialized()){
            Thread.currentThread().sleep(100);
        }
    }

    public GameView getGameView(){
        return gameView;
    }
}

import com.jme3.system.AppSettings;
import gameView.GameView;
import utils.ApplicationAssets;

/**
 * Created by David on 2016-05-25.
 */

public class ViewInit {
    private ApplicationAssets appAssets;
    private final GameView gameView;

    public ViewInit() throws InterruptedException {

        AppSettings settings = new AppSettings(false);
        settings.setSettingsDialogImage("Interface/theteam.png");
        settings.setTitle("Epic 2D-shooter");
        settings.setVSync(true);

        this.gameView = new GameView();

        this.gameView.setSettings(settings);
        this.gameView.start();

        //appAssets in gameView is null since simpleInitApp hasn't been called yet, thus the wait
        while (!gameView.isInitiated()){
            Thread.currentThread().sleep(1000);
        }
        this.appAssets = gameView.getAppAssets();
    }

    public ApplicationAssets getAppAssets() {
        return appAssets;
    }

    public void setAppAssets(ApplicationAssets appAssets){
        this.appAssets = appAssets;
    }

    public GameView getGameView(){
        return gameView;
    }
}

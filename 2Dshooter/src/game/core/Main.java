package game.core;

import com.jme3.system.AppSettings;
import game.gameView.GameView;

/**
 * Created by David on 2016-04-18.
 */
public class Main {
    public static void main(String[] args) {
        AppSettings settings = new AppSettings(false);
        settings.setSettingsDialogImage("Interface/theteam.png");
        settings.setTitle("Epic 2D-shooter");
        settings.setVSync(true);
        GameView gameView = new GameView();
        gameView.setSettings(settings);
        gameView.start();
    }
}

package game;

/**
 * Created by David on 2016-04-18.
 */
public class PlayerController implements Runnable{
    private final World world = new World();
    public void run() {

        world.gameUpdate();
    }
}

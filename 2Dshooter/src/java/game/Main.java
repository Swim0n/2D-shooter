package game;

/**
 * Created by David on 2016-04-18.
 */
public class Main {
    public static void main(String[] args) {
        View view = new View();
        view.start();
        Controller controller = new Controller();
        controller.startGame();


    }
}

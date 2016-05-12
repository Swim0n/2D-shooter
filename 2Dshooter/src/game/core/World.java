package game.core;


/**
 * Created by David on 2016-04-15.
 */
public class World {



    private final Player player1;
    private final Player player2;
    private final Environment terrain;

    public World(int rocks, int trees){
        this.player1 = new Player();
        this.player2 = new Player();
        this.terrain = new Environment(rocks, trees);
    }

    public Player getPlayer1() {
        return player1;
    }
    public Player getPlayer2() {
        return player2;
    }
    public Environment getTerrain(){
        return terrain;
    }
}

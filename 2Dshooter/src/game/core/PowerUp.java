package game.core;

/**
 * Created by Hannes on 10/05/2016.
 */
public interface PowerUp {

    void setPosition();

    void setEffect(Player player);

    int getX();
    int getZ();

}

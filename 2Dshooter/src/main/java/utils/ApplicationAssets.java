package utils;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.InputManager;
import com.jme3.scene.Node;
import core.World;
import gameView.GameView;

/**
 * Created by Simon on 2016-05-14.
 */
public class ApplicationAssets {
    private GameView gameView;

    private World world;
    private AssetManager assetManager;
    private InputManager inputManager;
    private BulletAppState bulletAppState;

    private Node stageNode;
    private Node terrainNode;

    public ApplicationAssets(GameView gameView, World world, AssetManager assetManager, InputManager inputManager, BulletAppState bulletAppState, Node stageNode, Node terrainNode){
        this.assetManager = assetManager;
        this.inputManager = inputManager;
        this.bulletAppState = bulletAppState;
        this.gameView = gameView;

        this.world = world;
        this.stageNode = stageNode;
        this.terrainNode = terrainNode;
    }

    public GameView getGameView() {
        return gameView;
    }
    public World getWorld() {
        return world;
    }
    public AssetManager getAssetManager() {
        return assetManager;
    }
    public InputManager getInputManager() {
        return inputManager;
    }
    public BulletAppState getBulletAppState() {return bulletAppState;}
    public Node getStageNode() {
        return stageNode;
    }
    public Node getTerrainNode() {
        return terrainNode;
    }
}

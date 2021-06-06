package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.assets.Explosion;
import se2.ticktackbumm.core.assets.Flame;
import se2.ticktackbumm.core.assets.Lamb;
import se2.ticktackbumm.core.data.GameData;


public class WinnerScreen extends ScreenAdapter {
    private final TickTackBummGame game;
    private final AssetManager assetManager;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final Skin skin;
    private final GameData gameData;
    private final Texture podium;
    private final Image podiumImage;
    private final Lamb lamb;
    private final Explosion explosion;
    private final SpriteBatch spriteBatch;
    private final Flame flame;

    public WinnerScreen(Table[] tables) {
        game = TickTackBummGame.getTickTackBummGame();
        this.assetManager = game.getManager();
        camera = TickTackBummGame.getGameCamera();
        gameData = game.getGameData();

        lamb = new Lamb();
        spriteBatch = new SpriteBatch();
        explosion = new Explosion();
        flame = new Flame();

        stage = new Stage(new FitViewport(TickTackBummGame.WIDTH, TickTackBummGame.HEIGHT));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        skin.getFont("default-font").getData().setScale(3);

        podium = assetManager.get("winnerScreen/podium.png", Texture.class);
        podiumImage = new Image(podium);
        podiumImage.setPosition(TickTackBummGame.WIDTH / 2.0f -390f, TickTackBummGame.HEIGHT /2f-400f);

        tables[0].setPosition(TickTackBummGame.WIDTH/2f-100f,TickTackBummGame.HEIGHT/2f);
        tables[1].setPosition(TickTackBummGame.WIDTH/2f-350f,TickTackBummGame.HEIGHT/2f-200f);
        tables[2].setPosition(TickTackBummGame.WIDTH/2f+150f,TickTackBummGame.HEIGHT/2f-200f);

        stage.addActor(tables[0]);
        stage.addActor(tables[1]);
        stage.addActor(tables[2]);
        stage.addActor(podiumImage);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(.18f, .21f, .32f, 1);
        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();
        stage.draw();
        flame.render(delta, spriteBatch);
        lamb.render(delta, spriteBatch);
        spriteBatch.end();
    }
}

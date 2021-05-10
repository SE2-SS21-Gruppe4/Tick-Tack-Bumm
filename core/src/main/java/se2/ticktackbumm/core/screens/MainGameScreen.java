package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.client.NetworkClient;
import se2.ticktackbumm.core.gamelogic.TextfieldInputListener;

public class MainGameScreen extends ScreenAdapter {
    private final TickTackBummGame game;
    private final OrthographicCamera camera;
    private final AssetManager assetManager;
    private final NetworkClient networkClient;
    private final BitmapFont font;
    private BitmapFont ttfBitmapFont;
    private final SpriteBatch batch;

    // scene2d UI
    private final Stage stage;
    private final Skin skin;
    private final Table textFieldTable;
    private final TextField textField;
    private final TextButton checkButton;


    public MainGameScreen() {
        game = TickTackBummGame.getTickTackBummGame();
        camera = TickTackBummGame.getGameCamera();
        batch = game.getBatch();
        font = game.getFont();
        assetManager = game.getManager();
        networkClient = game.getNetworkClient();

        // scene2d UI
        stage = new Stage(new FitViewport(TickTackBummGame.WIDTH, TickTackBummGame.HEIGHT));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        skin.getFont("default-font").getData().setScale(3);
//        skin.getFont("button").getData().setScale(2);
//        skin.getFont("font").getData().setScale(4);

        textField = new TextField("", skin);
        checkButton = new TextButton("CHECK", skin);

        textFieldTable = setupTextfieldTable();

        stage.addActor(textFieldTable);
    }

    private Table setupTextfieldTable() {
        final Table textFieldTable;
        textFieldTable = new Table();
        textFieldTable.setWidth(stage.getWidth());
        textFieldTable.align(Align.center | Align.bottom);

        textField.setAlignment(Align.center);
        checkButton.addListener(new TextfieldInputListener(textField));

        textFieldTable.add(textField).padBottom(20f).width(600f).height(125f);
        textFieldTable.row();
        textFieldTable.add(checkButton).padBottom(20f).width(350f).height(100f);

        return textFieldTable;
    }

    private void createTTF() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/JetBrainsMono-Medium.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 200;

        ttfBitmapFont = generator.generateFont(parameter);
        generator.dispose();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        stage.draw();
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        skin.dispose();
        font.dispose();
    }
}

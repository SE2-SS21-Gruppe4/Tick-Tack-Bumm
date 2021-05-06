package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.client.NetworkClient;

public class MainGameScreen extends ScreenAdapter {
    private final TickTackBummGame game;
    private final OrthographicCamera camera;
    private final AssetManager assetManager;
    private final NetworkClient networkClient;
    private final BitmapFont font;
    private final SpriteBatch batch;

    // scene2d UI
    private final Stage stage;
    private final Skin skin;
    private final Group textFieldGroup;
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

        textFieldGroup = new Group();
//        textFieldGroup.setBounds(0, 0, TickTackBummGame.WIDTH, TickTackBummGame.HEIGHT);

        textField = new TextField("", skin);
        checkButton = new TextButton("Prüfen", skin);

        textField.setAlignment(Align.center);
        checkButton.addListener(new TextfieldInputListener(textField));

        setTextFieldGroupPositions();


        textFieldGroup.addActor(textField);
        textFieldGroup.addActor(checkButton);

        stage.addActor(textFieldGroup);
//        stage.getCamera().position.set(TickTackBummGame.WIDTH / 2.0f, TickTackBummGame.HEIGHT / 2.0f, 0);
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
    }

    private void setTextFieldGroupPositions() {
        int TEMP_CARD_HEIGHT = 250;

        checkButton.setWidth(200);
        checkButton.setHeight(40);

        textField.setWidth(300);
        textField.setHeight(50);

        textField.setPosition(
                (TickTackBummGame.WIDTH / 2.0f - textField.getWidth() / 2.0f),
                (TickTackBummGame.HEIGHT / 2.0f - textField.getHeight() / 2.0f) - (TEMP_CARD_HEIGHT + 10)
        );

        checkButton.setPosition(
                TickTackBummGame.WIDTH / 2.0f - checkButton.getWidth() / 2.0f,
                textField.getY() - (textField.getHeight() + 10)
        );
    }
}

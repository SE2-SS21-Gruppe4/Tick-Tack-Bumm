package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.data.GameData;
import se2.ticktackbumm.core.data.GameMode;
import se2.ticktackbumm.core.models.WordPosition;

import java.security.SecureRandom;

public class SpinWheelScreen extends ScreenAdapter {
    private static final String CHALLENGE_STRING = "Your challenge: ";

    private final TickTackBummGame game;
    private final OrthographicCamera camera;
    private final GameData gameData;

    private final SpriteBatch batch;
    private final Stage stage;
    private final Skin skin;

    // scene 2d ui
    private final Table spinWheelTable;
    private final Label challengeLabel;
    private final Label gameModeLabel;
    private final Label descriptionLabel;
    private final TextButton spinButton;
    private final TextButton gameButton;

    private final SecureRandom randomNumb;

    public SpinWheelScreen() {
        game = TickTackBummGame.getTickTackBummGame();
        camera = TickTackBummGame.getGameCamera();
        gameData = game.getGameData();

        batch = new SpriteBatch();
        stage = new Stage(new FitViewport(TickTackBummGame.WIDTH, TickTackBummGame.HEIGHT));
        Gdx.input.setInputProcessor(stage);

        randomNumb = new SecureRandom();

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        skin.getFont("default-font").getData().setScale(3);

        challengeLabel = new Label(CHALLENGE_STRING, skin);
        gameModeLabel = new Label("", skin);
        descriptionLabel = new Label("", skin);

        spinButton = new TextButton("SPIN", skin);
        gameButton = new TextButton("GAME", skin);

        spinWheelTable = new Table();

        setupTextLabel(challengeLabel, 4);
        setupTextLabel(gameModeLabel, 5);
        setupTextLabel(descriptionLabel, 3);
        setupSpinButton();
        setupGameButton();
        setupSpinWheelTable();

        stage.addActor(spinWheelTable);
    }

    private void setupTextLabel(Label label, int textScale) {
        label.setAlignment(Align.center);
        label.setWrap(true);
        label.setFontScale(textScale);
    }

    public void setupSpinButton() {
        spinButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                setRandomGameMode();
                spinWheelTable.removeActor(spinButton);
            }
        });
    }

    public void setupGameButton() {
        gameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainGameScreen());
            }
        });

        gameButton.setDisabled(true);
        gameButton.setVisible(false);
    }

    private void setupSpinWheelTable() {
        spinWheelTable.setWidth(stage.getWidth());
        spinWheelTable.setHeight(stage.getHeight());
        spinWheelTable.align(Align.center);

        spinWheelTable.add(challengeLabel).width(500f).padBottom(200f);
        spinWheelTable.row();
        spinWheelTable.add(gameModeLabel).width(500f).padBottom(50f);
        spinWheelTable.row();
        spinWheelTable.add(descriptionLabel).width(800f).padBottom(300f);
        spinWheelTable.row();
        spinWheelTable.add(spinButton).width(300f);
        spinWheelTable.row();
        spinWheelTable.add(gameButton).width(300f);
    }

    private void setRandomGameMode() {
        switch (randomNumb.nextInt(3)) { // TODO: set to number of game modes
            case 0:
                gameModeLabel.setText(WordPosition.TICK.toString()); // TODO: replace String with icon
                descriptionLabel.setText("Diese Silbe muss am Anfang deines Wortes stehen.");
                gameData.setCurrentGameMode(GameMode.PREFIX);
                break;
            case 1:
                gameModeLabel.setText(WordPosition.TICK_TACK.toString());
                descriptionLabel.setText("Diese Silbe muss in der Mitte deines Wortes zu finden sein.");
                gameData.setCurrentGameMode(GameMode.INFIX);
                break;
            case 2:
                gameModeLabel.setText(WordPosition.BOMBE.toString());
                descriptionLabel.setText("Diese Silbe muss am Ende deines Wortes stehen.");
                gameData.setCurrentGameMode(GameMode.POSTFIX);
                break;
        }

        // TODO: testing only
        gameData.setCurrentGameMode(GameMode.POSTFIX); // set game mode always to postfix

        // show game button
        gameButton.setDisabled(false);
        gameButton.setVisible(true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(.18f, .21f, .32f, 1);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        stage.draw();
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        skin.dispose();
        stage.dispose();
    }
}

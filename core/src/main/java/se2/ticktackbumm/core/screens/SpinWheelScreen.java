package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.data.GameData;
import se2.ticktackbumm.core.data.GameMode;

import java.security.SecureRandom;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * SpinWheelScreen is for getting challenge which will be used on cards in the game
 *
 * @author NikolaMaNa
 */

public class SpinWheelScreen extends ScreenAdapter {

    /**
     * The challenge string is used to explain what to do
     */
    private static final String CHALLENGE_STRING = "Drueck SPIN, um das Drehrad zu starten!";
    private static final String SPIN_WHEEL_ATLAS_PATH = "ui/spin_wheel_ui.atlas";

    private final TickTackBummGame game;
    private final OrthographicCamera camera;
    private final AssetManager assetManager;
    private final GameData gameData;
    private final SpriteBatch batch;
    private final Stage stage;
    private final Skin skin;
    private final TextureAtlas atlas;
    private MainGameScreen mainGameScreen;

    /**
     * Scene 2D UI
     */
    private final Table spinWheelTable;
    private final Label challengeLabel;
    private final Label descriptionLabel;
    private final TextButton gameButton;
    private final Image wheelImage;
    private final Image needleImage;
    private final Image spinButtonImage;
    private final Texture background;
    private final Sprite sprite;

    /**
     * Variables used for functionality
     */
    private final SecureRandom randomNumb;
    private final Timer timer;
    private final Color color;
    private GameMode gameMode;
    private Timer.Task task;
    private float rotationAmount;
    private float spinSpeed;
    private float degree;
    private boolean isStart;

    /**
     * Test constructor
     * Set all variables on null to enable testing
     *
     * @param str - will always be empty.
     */
    public SpinWheelScreen(String str) {
        game = null;
        camera = null;
        assetManager = null;
        gameData = null;
        batch = null;
        stage = null;
        skin = null;
        atlas = null;
        spinWheelTable = null;
        challengeLabel = null;
        descriptionLabel = null;
        gameButton = null;
        wheelImage = null;
        needleImage = null;
        spinButtonImage = null;
        randomNumb = null;
        timer = null;
        color = null;
        sprite = null;
        background = null;
    }

    /**
     * Class constructor
     * init variables, load skin, load images from assets
     * set labels and table
     * methods call
     */
    public SpinWheelScreen() {
        game = TickTackBummGame.getTickTackBummGame();
        camera = TickTackBummGame.getGameCamera();
        assetManager = game.getManager();
        gameData = game.getGameData();
        gameMode = GameMode.NONE;
        batch = new SpriteBatch();
        stage = new Stage(new FitViewport(TickTackBummGame.WIDTH, TickTackBummGame.HEIGHT));
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        skin.getFont("default-font").getData().setScale(3);

        challengeLabel = new Label(CHALLENGE_STRING, skin);
        descriptionLabel = new Label("", skin);

        gameButton = new TextButton("SPIELEN", skin);

        spinWheelTable = new Table();

        setupTextLabel(challengeLabel, 4);
        setupTextLabel(descriptionLabel, 3);

        atlas = new TextureAtlas(SPIN_WHEEL_ATLAS_PATH);
        wheelImage = setupSpinWheelImages("spin_wheel_image", TickTackBummGame.WIDTH / 4.9f, TickTackBummGame.HEIGHT / 2.5f);
        needleImage = setupSpinWheelImages("needle", (TickTackBummGame.WIDTH / 2.07f), (TickTackBummGame.HEIGHT / 1.53f));
        spinButtonImage = setupSpinWheelImages("spin", (TickTackBummGame.WIDTH / 2.31f), (TickTackBummGame.HEIGHT / 1.95f));

        stage.addActor(wheelImage);
        stage.addActor(needleImage);
        stage.addActor(spinButtonImage);
        stage.addActor(spinWheelTable);

        rotationAmount = 0;
        spinSpeed = 0;
        degree = 0;
        color = new Color();
        timer = new Timer();
        randomNumb = new SecureRandom();

        background = assetManager.get("spinWheelScreen/background.png",Texture.class);
        sprite = new Sprite(background);

        setupGameButton();
        setupSpinWheelTable();
        btnSpinListener();

        stage.addActor(spinWheelTable);
    }

    /**
     * set up all parts of spinning wheel for UI
     *
     * @param path   - path of atlas in assets
     * @param xWidth - x position on UI/Screen
     * @param yHeight - y position on UI/Screen
     */
    private Image setupSpinWheelImages(String path, float xWidth, float yHeight) {
        Image image = new Image(atlas.findRegion(path));
        image.setPosition(xWidth, yHeight);
        return image;

    }

    /**
     * Set listener for rotating on SPIN IMAGE in the center of wheel
     * Timer for setting background color and game mode when spinning wheel stops
     */
    public void btnSpinListener() {
        spinButtonImage.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                // set listener to unable next spin for same player
                spinButtonImage.clearListeners();

                gameButton.addAction(sequence(scaleTo(1.25F, 1.25F, 0.10F), scaleTo(1F, 1F, 0.10F)));

                // setting random degree between 1 und 1800 degree.
                rotationAmount = randomNumb.nextInt(1800);
                // rotationAmount modulo with 360
                degree = setDegree(rotationAmount);
                // setting the constant speed
                spinSpeed = getSpinSpeed(rotationAmount);

                wheelImage.addAction(Actions.parallel(rotateBy(rotationAmount, spinSpeed)));
                wheelImage.setOrigin(Align.center);

                //set timer to get descrption label and background color when wheel stops
                task = new Timer.Task() {
                    @Override
                    public void run() {

                        setBackgroundColor(degree);

                        setGameMode(degree);

                        // show game button
                        gameButton.setDisabled(false);
                        gameButton.setVisible(true);

                    }
                };
                timer.scheduleTask(task, spinSpeed);
            }
        });


        game.getNetworkClient().getClientMessageSender().spinWheelStarted(gameData.getCurrentGameMode());
    }


    /**
     * set up description label on UI
     *
     * @param label     - text
     * @param textScale - font size
     */
    private void setupTextLabel(Label label, int textScale) {
        label.setAlignment(Align.center);
        label.setWrap(true);
        label.setFontScale(textScale);
    }

    /**
     * set game button for going on next screen after getting challenge
     */
    public void setupGameButton() {
        gameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                game.setScreen(new MainGameScreen());

                game.getNetworkClient().getClientMessageSender().spinWheelFinished(gameData.getCurrentGameMode());
                game.getNetworkClient().getClientMessageSender().sendStartBomb();
                game.startNextTurn();
            }
        });

        gameButton.setDisabled(true);
        gameButton.setVisible(false);
    }

    // set up table
    private void setupSpinWheelTable() {
        spinWheelTable.setWidth(stage.getWidth());
        spinWheelTable.setHeight(stage.getHeight());
        spinWheelTable.align(Align.center);

        spinWheelTable.add(challengeLabel).width(1000f).padBottom(1000f);
        spinWheelTable.row();
        spinWheelTable.add(descriptionLabel).width(800f).padBottom(300f);
        spinWheelTable.row();
        spinWheelTable.add(gameButton).width(300f);
    }

    /**
     * set up descriptionLabel and current game mode, depending on received degrees value
     */
    public void setGameMode(float degree) {
        if ((degree > 120 && degree <= 180) || (degree >= 300 && degree < 360)) {
            descriptionLabel.setText("Die Silbe muss am Anfang deines Wortes stehen.");
            gameMode = GameMode.PREFIX;

        } else if ((degree > 0 && degree < 60) || (degree > 180 && degree <= 240)) {
            descriptionLabel.setText("Die Silbe muss in der Mitte deines Wortes zu finden sein.");
            gameMode = GameMode.INFIX;

        } else {
            descriptionLabel.setText("Die Silbe muss am Ende deines Wortes stehen.");
            gameMode = GameMode.POSTFIX;

        }
        gameData.setCurrentGameMode(gameMode);
    }

    /**
     * set up background color depending on received degrees value
     */
    private void setBackgroundColor(float degree) {
        if (degree < 61) {
            color.set(Color.valueOf("0070C0"));

        } else if (degree < 121) {
            color.set(Color.valueOf("FFC000"));

        } else if (degree < 181) {
            color.set(Color.valueOf("7030A0"));

        } else if (degree < 241) {
            color.set(Color.valueOf("F2CF00"));

        } else if (degree < 301) {
            color.set(Color.valueOf("FF0000"));

        } else {
            color.set(Color.valueOf("70AD47"));

        }
    }

    public float setDegree(float degree) {
        float retVal = degree;

        if (degree > 360) {
            retVal = degree % 360;
        }

        return retVal;
    }

    public float getSpinSpeed(float rotationAmount) {
        if (rotationAmount < 360) {
            return 1;

        } else {
            return rotationAmount / 360;

        }
    }


    @Override
    public void render(float delta) {

        if (!color.equals(Color.CLEAR)){
            ScreenUtils.clear(color.r, color.g, color.b, color.a);
        }else{
            game.getBatch().begin();
            sprite.draw(game.getBatch());
            game.getBatch().end();
        }

        batch.setProjectionMatrix(camera.combined);
        stage.act();

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

    public MainGameScreen getMainGameScreen(){
        return this.mainGameScreen;
    }
}

package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.data.GameData;
import se2.ticktackbumm.core.data.GameMode;

import java.security.SecureRandom;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.rotateBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class SpinWheelScreen extends ScreenAdapter {
    private static final String CHALLENGE_STRING = "Your challenge: ";

    private final TickTackBummGame game;
    private final OrthographicCamera camera;
    private final GameData gameData;

    private final SpriteBatch batch;
    private final Stage stage;
    private final Skin skin;
    private final TextureAtlas atlas;

    // scene 2d ui
    private final Table spinWheelTable;
    private final Label challengeLabel;
    private final Label descriptionLabel;
    private final TextButton gameButton;
    private final Image wheelImage;
    private final Image needleImage;
    private final Image spinButtonImage;


    private final SecureRandom randomNumb;
    private final Timer timer;
    private Timer.Task task;
    private final Color color;
    private float rotationAmount;
    private float spinSpeed;
    private float grade;
    private boolean againSpin;

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
        descriptionLabel = new Label("", skin);
        gameButton = new TextButton("GAME", skin);


        spinWheelTable = new Table();

        setupTextLabel(challengeLabel, 4);
        setupTextLabel(descriptionLabel, 3);


        final String pathOfAtlas = "spin_wheel_ui.atlas";
        atlas = new TextureAtlas(pathOfAtlas);
        wheelImage = setupSpinWheelImages("spin_wheel_image" , TickTackBummGame.WIDTH / 3.8F, TickTackBummGame.HEIGHT / 2f);
        needleImage = setupSpinWheelImages("needle", (TickTackBummGame.WIDTH / 2.1f) + 25f, (TickTackBummGame.HEIGHT / 1.5f) + 100f);
        spinButtonImage = setupSpinWheelImages("spin", (TickTackBummGame.WIDTH / 2.2f) + 15f, (TickTackBummGame.HEIGHT / 1.7f) + 22f);

        stage.addActor(wheelImage);
        stage.addActor(needleImage);
        stage.addActor(spinButtonImage);
        stage.addActor(spinWheelTable);

        rotationAmount = 0;
        spinSpeed = 0;
        grade = 0;
        color = new Color(.18f, .21f, .32f, 1);
        timer = new Timer();
        againSpin = false;


        setupGameButton();
        setupSpinWheelTable();
        btnSpinListener();

        stage.addActor(spinWheelTable);



    }

    private Image setupSpinWheelImages(String path, float xWidth, float yHeght) {
        Image image = new Image(atlas.findRegion(path));
        image.setPosition(xWidth, yHeght);
        return image;

    }

    public void btnSpinListener() {

        spinButtonImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameButton.addAction(sequence(scaleTo(1.25F, 1.25F, 0.10F), scaleTo(1F, 1F, 0.10F)));
                if (!againSpin) {
                    rotationAmount = randomNumb.nextInt(1800);
                    grade = getGrade(rotationAmount);
                }else{
                    rotationAmount = randomNumb.nextInt(1800);
                    grade = spinAgain(rotationAmount,grade);
                }
                spinSpeed = getSpinSpeed(rotationAmount);
                wheelImage.addAction(Actions.parallel(rotateBy(rotationAmount, spinSpeed)));
                wheelImage.setOrigin(Align.center);

                task = new Timer.Task() {
                    @Override
                    public void run() {
                        setBackgroundColor(grade);
                        setRandomGameMode(grade);
                        if (!descriptionLabel.getText().contains("Spin nochmal.")){
                            spinButtonImage.clearListeners();
                            // show game button
                            gameButton.setDisabled(false);
                            gameButton.setVisible(true);
                        }else{
                            againSpin = true;
                        }
                    }
                };
                timer.scheduleTask(task,spinSpeed);
            }
        });
    }



    private void setupTextLabel(Label label, int textScale) {
        label.setAlignment(Align.center);
        label.setWrap(true);
        label.setFontScale(textScale);
    }

    public void setupGameButton() {
        gameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainGameScreen());
                game.startNextTurn();
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
        spinWheelTable.add(descriptionLabel).width(800f).padBottom(300f);
        spinWheelTable.row();
        spinWheelTable.add(gameButton).width(300f);
    }

    private void setRandomGameMode(float value) {
        if ((value > 120 && value <= 180) || (value > 300 && value < 360)) {
            descriptionLabel.setText("Diese Silbe muss am Anfang deines Wortes stehen.");
            gameData.setCurrentGameMode(GameMode.PREFIX);
        } else if ((value > 0 && value < 60) || (value > 180 && value < 240)) {
            descriptionLabel.setText("Diese Silbe muss in der Mitte deines Wortes zu finden sein.");
            gameData.setCurrentGameMode(GameMode.INFIX);
        } else if ((value > 60 && value < 120) || (value > 240 && value < 300)) {
            descriptionLabel.setText("Diese Silbe muss am Ende deines Wortes stehen.");
            gameData.setCurrentGameMode(GameMode.POSTFIX);
        }else {
            descriptionLabel.setText("Spin nochmal.");
        }
        // TODO: testing only
        gameData.setCurrentGameMode(GameMode.POSTFIX); // set game mode always to postfix


    }

    // 121 - 181 - 240 -300 - - 360 - 0 pixel on white part of peg
    private void setBackgroundColor(float value) {
        if (value > 0 && value < 61) {
            color.set(Color.valueOf("0070C0"));
        } else if (value < 121) {
            color.set(Color.valueOf("FFC000"));
        } else if (value > 121 && value < 181) {
            color.set(Color.valueOf("7030A0"));
        } else if (value > 181 && value < 241) {
            color.set(Color.valueOf("F2CF00"));
        } else if (value > 240 && value < 300) {
            color.set(Color.valueOf("FF0000"));
        } else if (value > 300 && value < 360){
            color.set(Color.valueOf("70AD47"));
        } else{
            color.set(.18f, .21f, .32f, 1);
        }
    }

    private float getGrade(float value) {
        float retVal = value;
        if (value > 360) {
            retVal = value % 360;
        }
        return retVal;
    }


    private float getSpinSpeed(float rotationAmount) {

        if (rotationAmount < 360) {
            return 1;
        } else {
            return rotationAmount / 360;
        }
    }

    public float spinAgain(float val1 , float val2){
        System.out.println("Second spin");
        if ((val1+val2) > 1800){
            val1=(val1+val2)-1800;
        }
        float sum = 360;
        float count;
        count = sum - val2;
        val1 = getGrade(val1);

        return val1-count;
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

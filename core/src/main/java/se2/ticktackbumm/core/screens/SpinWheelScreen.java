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
    private boolean isStart;
    public SpinWheelScreen() {
        game = TickTackBummGame.getTickTackBummGame();
        camera = TickTackBummGame.getGameCamera();
        gameData = game.getGameData();

        batch = new SpriteBatch();
        stage = new Stage(new FitViewport(TickTackBummGame.WIDTH, TickTackBummGame.HEIGHT));
        Gdx.input.setInputProcessor(stage);


        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        skin.getFont("default-font").getData().setScale(3);

        challengeLabel = new Label(CHALLENGE_STRING, skin);
        descriptionLabel = new Label("", skin);
        gameButton = new TextButton("GAME", skin);

        spinWheelTable = new Table();

        setupTextLabel(challengeLabel, 4);
        setupTextLabel(descriptionLabel, 3);

        final String pathOfAtlas = "ui/spin_wheel_ui.atlas";
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
        randomNumb = new SecureRandom();
        isStart = false;


        setupGameButton();
        setupSpinWheelTable();
        btnSpinListener();

        stage.addActor(spinWheelTable);



    }
    // set up all parts of spinning wheel for UI
    private Image setupSpinWheelImages(String path, float xWidth, float yHeght) {
        Image image = new Image(atlas.findRegion(path));
        image.setPosition(xWidth, yHeght);
        return image;

    }

    // set listener for rotating on SPIN IMAGE in the center of wheel
    public void btnSpinListener() {
        spinButtonImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameButton.addAction(sequence(scaleTo(1.25F, 1.25F, 0.10F), scaleTo(1F, 1F, 0.10F)));
                // first time will always be false to check if we get needle on white point to repeat spinning
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

                //set timer to get descrption label and background color when wheel stops
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


    // set up label
    private void setupTextLabel(Label label, int textScale) {
        label.setAlignment(Align.center);
        label.setWrap(true);
        label.setFontScale(textScale);
    }

    // set game button for going on next screen after getting challenge
    public void setupGameButton() {
        gameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isStart = true;
                game.setScreen(new MainGameScreen());
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

        spinWheelTable.add(challengeLabel).width(500f).padBottom(800f);
        spinWheelTable.row();
        spinWheelTable.add(descriptionLabel).width(800f).padBottom(300f);
        spinWheelTable.row();
        spinWheelTable.add(gameButton).width(300f);
    }

    //set up descriptionLabel and current game mode, depending on received degrees value
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

    //set up background color depending on received degrees value
    // 121 - 181 - 240 -300 - - 360 - 0 pixels on white part of peg
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

    private float getGrade(float grade) {
        float retVal = grade;
        if (grade > 360) {
            retVal = grade % 360;
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

    public float spinAgain(float newRotationAmount , float oldGrade){
        if ((newRotationAmount+oldGrade) > 1800){
            newRotationAmount=(newRotationAmount+oldGrade)-1800;
        }
        float sum = 360;
        float count;
        count = sum - oldGrade;
        newRotationAmount = getGrade(newRotationAmount);

        return newRotationAmount-count;
    }





    @Override
    public void render(float delta) {
        ScreenUtils.clear(color.r, color.g, color.b, color.a);
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

    public void setStart(boolean isStart){
        this.isStart = isStart;
    }
    public boolean getStart(){
        return this.isStart;
    }
}

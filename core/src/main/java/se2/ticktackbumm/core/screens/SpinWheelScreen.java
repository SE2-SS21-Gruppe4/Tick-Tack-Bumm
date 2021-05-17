package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.data.GameMode;
import se2.ticktackbumm.core.models.WordPosition;

import java.security.SecureRandom;

public class SpinWheelScreen extends ScreenAdapter {
    private TickTackBummGame game;
    private final OrthographicCamera camera;

    private GameMode gameMode;

    private SpriteBatch batch;
    private TextButton textButton;
    private TextButton gameButton;
    private TextField textField;
    private TextField buttonBackTextField;
    private Skin skin;
    private Stage stage;
    private static WordPosition wordPosition;
    private BitmapFont wordPositionOutput;
    private BitmapFont challengeText;
    private BitmapFont descriptionText;
    private SecureRandom randomNumb;

    private int randomNumber;
    private static final String CHALLENGE_STRING = "Your challenge: ";
    private String description = "";


    public SpinWheelScreen() {
        game = TickTackBummGame.getTickTackBummGame();
        camera = TickTackBummGame.getGameCamera();
        batch = new SpriteBatch();
        stage = new Stage();
        randomNumb = new SecureRandom();

        gameMode = GameMode.NONE;

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        skin.getFont("default-font").getData().setScale(3);

        spinClick();
        spinOutput();
        challengeDisplayText();
        descriptionDisplayText();
        setupGameButton();
    }

    public void spinOutput() {
        wordPositionOutput = new BitmapFont();
        wordPositionOutput.setColor(Color.RED);
        wordPositionOutput.getData().setScale(8);
        wordPositionOutput.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public void challengeDisplayText() {
        challengeText = new BitmapFont();
        challengeText.setColor(Color.WHITE);
        challengeText.getData().setScale(4);
        challengeText.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public void descriptionDisplayText() {
        descriptionText = new BitmapFont();
        descriptionText.setColor(Color.WHITE);
        descriptionText.getData().setScale(3);
        descriptionText.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public void spinClick() {
        textField = new TextField("", skin);
        textField.setColor(Color.RED);
        textField.setAlignment(Align.center);

        textButton = new TextButton("SPIN", skin);
        textButton.setBounds((Gdx.graphics.getWidth() / 2.0f) - 150, Gdx.graphics.getHeight() / 5.0f, 300, 100);

        stage.addActor(textButton);
        Gdx.input.setInputProcessor(stage);

        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                setRandomNumber(randomNumb.nextInt(3));
                switch (getRandomNumber()) {
                    case 0:
                        textField.setText(WordPosition.TICK.toString());
                        setDescription("Diese Silbe muss am Anfang" + '\n' + "deines Wortes stehen.");
                        gameMode = GameMode.PREFIX;
                        break;
                    case 1:
                        textField.setText(WordPosition.TICK_TACK.toString());
                        setDescription("Diese Silbe muss in der Mitte" + '\n' + "deines Wortes zu finden sein.");
                        gameMode = GameMode.INFIX;
                        break;
                    case 2:
                        textField.setText(WordPosition.BOMBE.toString());
                        setDescription("Diese Silbe muss am Ende" + '\n' + "deines Wortes stehen.");
                        gameMode = GameMode.POSTFIX;
                        break;
                }

                // hide and disable button for user after spin
                textButton.setDisabled(true);
                textButton.setVisible(false);

                // show game button
                gameButton.setDisabled(false);
                gameButton.setVisible(true);

                // TODO: refactor to use LibGDX helpers and timer
            }
        });
    }

    public void setupGameButton() {
        buttonBackTextField = new TextField("", skin);
        buttonBackTextField.setAlignment(Align.center);

        gameButton = new TextButton("GAME", skin);
        gameButton.setBounds((Gdx.graphics.getWidth() / 2.0f) - 150, Gdx.graphics.getHeight() / 5.0f, 300, 100);

        stage.addActor(gameButton);
        Gdx.input.setInputProcessor(stage);

        gameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainGameScreen());
            }
        });

        gameButton.setDisabled(true);
        gameButton.setVisible(false);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(.18f, .21f, .32f, 1);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        stage.act();
        stage.draw();
        challengeText.draw(batch, CHALLENGE_STRING, ((Gdx.graphics.getWidth() / 2.0f) - 200), Gdx.graphics.getHeight() / 1.15f);
        wordPositionOutput.draw(batch, textField.getText(), (Gdx.graphics.getWidth() / 2.0f) - 225,
                Gdx.graphics.getHeight() / 1.80f);
        descriptionText.draw(batch, getDescription(), 150, Gdx.graphics.getHeight() / 2.20f);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        skin.dispose();
        stage.dispose();
        wordPositionOutput.dispose();
        challengeText.dispose();
    }


    public int getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(int randomNumber) {
        this.randomNumber = randomNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GameMode getGameMode() {
        return this.gameMode;
    }


}

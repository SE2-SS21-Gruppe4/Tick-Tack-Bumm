package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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

import java.util.Random;

import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.client.NetworkClient;
import se2.ticktackbumm.core.models.WordPosition;

public class SpinWheelScreen extends ScreenAdapter{
    private TickTackBummGame game;
    private final OrthographicCamera camera;
    private final AssetManager assetManager;
    private final NetworkClient networkClient;

    private SpriteBatch batch;
    private TextButton textButton;
    private TextField textField;
    private Skin skin;
    private Skin skin1;
    private Stage stage;
    private WordPosition wordPosition;
    private BitmapFont wordPositionOutput;
    private String output;


    public SpinWheelScreen(){
        game = TickTackBummGame.getTickTackBummGame();
        camera = TickTackBummGame.getGameCamera();
        batch = new SpriteBatch();
        assetManager = game.getManager();
        networkClient = game.getNetworkClient();
        stage = new Stage();
        wordPositionOutput = new BitmapFont();
        wordPositionOutput.setColor(Color.WHITE);
        wordPositionOutput.getData().setScale(4);
        wordPositionOutput.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        skin.getFont("default-font").getData().setScale(4);

        textField = new TextField("",skin);
        textField.setColor(Color.WHITE);
        textField.setAlignment(Align.center);

        textButton = new TextButton("Spin", skin);
        textButton.setBounds((Gdx.graphics.getWidth() / 2.0f) - 150,TickTackBummGame.HEIGHT / 2.0f,300,100);

        stage.addActor(textButton);
        Gdx.input.setInputProcessor(stage);

        textButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent e, float x, float y){
                int randomNumb = new Random().nextInt(3);
                switch (randomNumb){
                    case 0:
                       textField.setText(wordPosition.FRONT.toString());
                    break;
                    case 1:
                        textField.setText(wordPosition.MIDDLE.toString());
                    break;
                    case 2: textField.setText(wordPosition.BACK.toString());
                    break;
                }
            }
        });

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        stage.act();
        stage.draw();

        wordPositionOutput.draw(batch,textField.getText(),(Gdx.graphics.getWidth() / 2.0f)-100,
                Gdx.graphics.getHeight()/ 2.0f);
        batch.end();
    }
    @Override
    public void dispose() {
        batch.dispose();
        skin.dispose();
        stage.dispose();
    }
}

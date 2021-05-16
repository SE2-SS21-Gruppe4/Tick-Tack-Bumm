package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
import se2.ticktackbumm.core.models.Cards.Card;
import se2.ticktackbumm.core.models.Score;


public class MainGameScreen extends ScreenAdapter {
    private final TickTackBummGame game;
    private final OrthographicCamera camera;
    private final AssetManager assetManager;
    private final NetworkClient networkClient;
    private final BitmapFont font;
    private BitmapFont ttfBitmapFont;
    private final SpriteBatch batch;

    Score score;


    // scene2d UI
    private final Stage stage;
    private final Skin skin;
    private final Table textFieldTable;
    private final TextField textField;
    private final TextButton checkButton;
    private final Texture table;
    private final Image image;

    private Card card;

    private final BitmapFont textMaxScore;
    private static final int MAX_SCORE = 10;


    public MainGameScreen() {
        game = TickTackBummGame.getTickTackBummGame();
        camera = TickTackBummGame.getGameCamera();
        batch = game.getBatch();
        font = game.getFont();
        assetManager = game.getManager();
        networkClient = game.getNetworkClient();

        // maxScore
        textMaxScore = new BitmapFont();
        textMaxScore.setColor(Color.RED);
        textMaxScore.getData().setScale(5);
        textMaxScore.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        //card
        card = new Card(game.getBatch());

        // scene2d UI
        stage = new Stage(new FitViewport(TickTackBummGame.WIDTH, TickTackBummGame.HEIGHT));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        skin.getFont("default-font").getData().setScale(3);
//        skin.getFont("button").getData().setScale(2);
//        skin.getFont("font").getData().setScale(4);

        textField = new TextField("", skin);
        checkButton = new TextButton("CHECK", skin);

        table = new Texture(Gdx.files.internal("table.png"));
        image = new Image(table);

        score = new Score();
        score.getPlayer().get(0).setPosition(stage.getWidth()/2-350, stage.getHeight()/2+330);
        score.getPlayer().get(1).setPosition(stage.getWidth()/2+100, stage.getHeight()/2+310);
        score.getPlayer().get(2).setPosition(stage.getWidth()/2+140, stage.getHeight()/2-320);
        score.getPlayer().get(3).setPosition(stage.getWidth()/2-350, stage.getHeight()/2-330);

        image.setPosition(stage.getWidth()/2-313, stage.getHeight()/2-200);

        textFieldTable = setupTextfieldTable();

        stage.addActor(score.getPlayer().get(0));
        stage.addActor(score.getPlayer().get(1));
        stage.addActor(score.getPlayer().get(2));
        stage.addActor(score.getPlayer().get(3));

        stage.addActor(image);

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
        ScreenUtils.clear(.18f, .21f, .32f, 1);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        textMaxScore.draw(batch, "Max Score: " + MAX_SCORE, 100f, Gdx.graphics.getHeight() - 50f);
        score.getBitmaps().get(0).draw(batch, "7", stage.getWidth()/2-250, stage.getHeight()/2+600);
        score.getBitmaps().get(1).draw(batch, "4", stage.getWidth()/2+250, stage.getHeight()/2+600);
        score.getBitmaps().get(2).draw(batch, "8", stage.getWidth()/2+250, stage.getHeight()/2-330);
        score.getBitmaps().get(3).draw(batch, "1", stage.getWidth()/2-250, stage.getHeight()/2-330);
        stage.draw();
        batch.end();

        card.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        skin.dispose();
        font.dispose();
        textMaxScore.dispose();
    }
}

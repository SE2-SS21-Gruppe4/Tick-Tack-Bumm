package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.listeners.ReadyButtonListener;

public class WaitingScreen extends ScreenAdapter {

    private final String LOG_TAG = "WAITING_SCREEN";

    // Style constants
    private final float BUTTON_WIDTH = 450f;
    private final float BUTTON_HEIGHT = 120f;
    private final float AVATAR_BUTTON_SIZE = 200f;
    private final float AVATAR_BUTTON_PADDING = 20f;
    private final int AVATAR_TEXTURE_SIZE = 180;

    // TickTackBumm resources
    private final TickTackBummGame game;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    // Scene2D UI
    private final Stage stage;
    private final Skin skin;

    private final Label playerNameLabel;
    private final TextField playerNameTextField;
    private final Label playerColorLabel;
    private final Table nameAndColorTable;

    private final ImageButton playerAvatarButton0;
    private final ImageButton playerAvatarButton1;
    private final ImageButton playerAvatarButton2;
    private final ImageButton playerAvatarButton3;
    private final Table avatarButtonTable;

    private final TextButton readyButton;
    private final TextButton backButton;
    private final Table waitingButtonTable;

    public WaitingScreen() {
        game = TickTackBummGame.getTickTackBummGame();
        camera = TickTackBummGame.getGameCamera();
        batch = game.getBatch();

        stage = new Stage(new FitViewport(TickTackBummGame.WIDTH, TickTackBummGame.HEIGHT));
        skin = game.getManager().get("ui/uiskin.json", Skin.class);
        Gdx.input.setInputProcessor(stage);

        // increase font scale
        skin.getFont("default-font").getData().setScale(3f);

        // name and color input
        playerNameLabel = new Label("SPIELERNAME", skin);
        playerNameTextField = new TextField("Spieler " + game.getLocalPlayer().getPlayerId(), skin);
        playerColorLabel = new Label("AVATAR", skin);
        nameAndColorTable = new Table();

        setupNameInput();

        // avatar buttons
        avatarButtonTable = new Table();
        avatarButtonTable.setWidth(stage.getWidth());
        avatarButtonTable.setY(460f);

        playerAvatarButton0 = createAndAddAvatarButton("avatars/square_blue.png");
        playerAvatarButton1 = createAndAddAvatarButton("avatars/square_green.png");
        playerAvatarButton2 = createAndAddAvatarButton("avatars/square_yellow.png");
        playerAvatarButton3 = createAndAddAvatarButton("avatars/square_red.png");

        // bottom bar buttons
        readyButton = new TextButton("BEREIT", skin);
        backButton = new TextButton("ZURÜCK", skin);
        waitingButtonTable = new Table();

        setupBottomButtons();

        // add tables to stage
        stage.addActor(nameAndColorTable);
        stage.addActor(avatarButtonTable);
        stage.addActor(waitingButtonTable);
    }

    private void setupNameInput() {
        playerNameLabel.setFontScale(4);
        playerNameTextField.setAlignment(Align.center);
        playerColorLabel.setFontScale(4);

        nameAndColorTable.setWidth(stage.getWidth());
        nameAndColorTable.setY(800);

        nameAndColorTable.add(playerNameLabel).padBottom(20f);
        nameAndColorTable.row();
        nameAndColorTable.add(playerNameTextField).padBottom(100f).width(600f).height(125f);
        nameAndColorTable.row();
        nameAndColorTable.add(playerColorLabel).padBottom(20f);
    }

    private ImageButton createAndAddAvatarButton(String avatarTexturePath) {
        ImageButton.ImageButtonStyle avatarStyle =
                new ImageButton.ImageButtonStyle(skin.get("default", Button.ButtonStyle.class));

        avatarStyle.imageUp =
                new TextureRegionDrawable(new TextureRegion(
                        game.getManager().get(avatarTexturePath, Texture.class),
                        AVATAR_TEXTURE_SIZE,
                        AVATAR_TEXTURE_SIZE
                ));

        ImageButton avatarButton = new ImageButton(avatarStyle);

        avatarButtonTable.add(avatarButton)
                .padLeft(AVATAR_BUTTON_PADDING).padRight(AVATAR_BUTTON_PADDING)
                .width(AVATAR_BUTTON_SIZE).height(AVATAR_BUTTON_SIZE);

        return avatarButton;
    }

    private void setupBottomButtons() {
        readyButton.getLabel().setFontScale(4);
        backButton.getLabel().setFontScale(4);

        waitingButtonTable.setWidth(stage.getWidth());
        waitingButtonTable.align(Align.center | Align.bottom);

        readyButton.addListener(new ReadyButtonListener(this));

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getNetworkClient().disconnectClient();
                game.setLocalPlayer(null);

                Log.info(LOG_TAG, "Disconnected player from server and deleted local player from game; " +
                        "switching to MainMenuScreen");

                Gdx.app.postRunnable(() -> game.setScreen(new MenuScreen()));
            }
        });

        waitingButtonTable.add(backButton).padBottom(100f).padRight(25f).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
        waitingButtonTable.add(readyButton).padBottom(100f).padRight(25f).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
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
        stage.dispose();
    }

    public TextButton getReadyButton() {
        return readyButton;
    }
}

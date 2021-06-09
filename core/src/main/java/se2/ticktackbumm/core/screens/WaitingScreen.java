package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import se2.ticktackbumm.core.data.Avatars;
import se2.ticktackbumm.core.data.GameData;
import se2.ticktackbumm.core.listeners.AvatarButtonListener;
import se2.ticktackbumm.core.listeners.ReadyButtonListener;
import se2.ticktackbumm.core.player.Player;

import java.util.List;

public class WaitingScreen extends ScreenAdapter {

    private final String LOG_TAG = "WAITING_SCREEN";

    // Style constants
    private final float BUTTON_WIDTH = 450f;
    private final float BUTTON_HEIGHT = 120f;
    private final float AVATAR_BUTTON_SIZE = 200f;
    private final float AVATAR_BUTTON_PADDING = 20f;
    private final int AVATAR_TEXTURE_SIZE = 200;

    // TickTackBumm resources
    private final TickTackBummGame game;
    private final GameData gameData;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    // Scene2D UI
    private final Stage stage;
    private final Skin skin;

    private final Label playerInLobbyLabel;
    private final Label playerNameLabel0;
    private final Label playerNameLabel1;
    private final Label playerNameLabel2;
    private final Label playerNameLabel3;
    private final Label[] playerNameLabelsList;
    private final Table playerNamesTable;

    private final Sprite sprite;

    private final Label playerNameInputLabel;
    private final TextField playerNameTextField;
    private final Label playerAvatarLabel;
    private final Table nameAndColorTable;

    private final ImageButton playerAvatarButton0;
    private final ImageButton playerAvatarButton1;
    private final ImageButton playerAvatarButton2;
    private final ImageButton playerAvatarButton3;
    private final ButtonGroup<ImageButton> avatarButtonGroup;
    private final Table avatarButtonTable;

    private final AssetManager assetManager;

    private final TextButton readyButton;
    private final TextButton backButton;
    private final Table waitingButtonTable;

    private final Texture background;

    public WaitingScreen() {
        game = TickTackBummGame.getTickTackBummGame();
        camera = TickTackBummGame.getGameCamera();
        gameData = game.getGameData();
        batch = game.getBatch();
        assetManager = game.getManager();

        stage = new Stage(new FitViewport(TickTackBummGame.WIDTH+145f, TickTackBummGame.HEIGHT+145f));
        skin = game.getManager().get("ui/uiskin.json", Skin.class);
        Gdx.input.setInputProcessor(stage);

        // increase font scale
        skin.getFont("default-font").getData().setScale(3f);

        // player name labels
        playerInLobbyLabel = new Label("Spieler in Lobby", skin);
        playerNamesTable = new Table();
        // TODO: testing only; should be empty
        playerNameLabel0 = new Label("", skin);
        playerNameLabel1 = new Label("", skin);
        playerNameLabel2 = new Label("", skin);
        playerNameLabel3 = new Label("", skin);
        playerNameLabelsList = new Label[]{playerNameLabel0, playerNameLabel1, playerNameLabel2, playerNameLabel3};

        setupLobbyNamesLabels();

        // name and color input
        playerNameInputLabel = new Label("SPIELERNAME", skin);
        playerNameTextField = new TextField("Spieler" + game.getLocalPlayer().getPlayerId(), skin);
        playerAvatarLabel = new Label("AVATAR", skin);
        nameAndColorTable = new Table();

        background = assetManager.get("waitingScreen/background.png", Texture.class);

        setupNameInput();

        // avatar buttons
        avatarButtonTable = new Table();
        avatarButtonTable.setWidth(stage.getWidth());
        avatarButtonTable.setY(600f);

        playerAvatarButton0 = createAndSetupAvatarButton(Avatars.BLOND_GIRL, "avatars/blond_girl.png");
        playerAvatarButton1 = createAndSetupAvatarButton(Avatars.BLACKHAIRED_GUY, "avatars/blackhaired_guy.png");
        playerAvatarButton2 = createAndSetupAvatarButton(Avatars.BLOND_GUY, "avatars/blond_guy.png");
        playerAvatarButton3 = createAndSetupAvatarButton(Avatars.BRUNETTE_GIRL, "avatars/brunette_girl.png");

        avatarButtonGroup =
                new ButtonGroup<>(playerAvatarButton0, playerAvatarButton1, playerAvatarButton2, playerAvatarButton3);

        // bottom bar buttons
        readyButton = new TextButton("Bereit", skin);
        backButton = new TextButton("Zurueck", skin);
        waitingButtonTable = new Table();

        setupBottomButtons();

        sprite = new Sprite(background);
        sprite.setRegionWidth(TickTackBummGame.WIDTH);
        sprite.setRegionHeight(TickTackBummGame.HEIGHT);

        // add tables to stage
        stage.addActor(playerInLobbyLabel);
        stage.addActor(playerNamesTable);
        stage.addActor(nameAndColorTable);
        stage.addActor(avatarButtonTable);
        stage.addActor(waitingButtonTable);
    }

    private void setupLobbyNamesLabels() {
        playerInLobbyLabel.setFontScale(5f);
        playerInLobbyLabel.setWidth(stage.getWidth());
        playerInLobbyLabel.setAlignment(Align.center);
        playerInLobbyLabel.setY(1800f);
        playerInLobbyLabel.setVisible(false);

        playerNamesTable.setWidth(stage.getWidth());
        playerNamesTable.setY(1600f);

        playerNamesTable.add(playerNameLabel0).padRight(80f);
        playerNamesTable.add(playerNameLabel1).padLeft(80f);
        playerNamesTable.row();
        playerNamesTable.add(playerNameLabel2).padRight(80f);
        playerNamesTable.add(playerNameLabel3).padLeft(80f);
    }

    private void setupNameInput() {
        playerNameInputLabel.setFontScale(4);
        playerNameTextField.setAlignment(Align.center);
        playerAvatarLabel.setFontScale(4);

        nameAndColorTable.setWidth(stage.getWidth());
        nameAndColorTable.setY(1000f);

        nameAndColorTable.add(playerNameInputLabel).padBottom(20f);
        nameAndColorTable.row();
        nameAndColorTable.add(playerNameTextField).padBottom(200f).width(600f).height(125f);
        nameAndColorTable.row();
        nameAndColorTable.add(playerAvatarLabel);
    }

    private ImageButton createAndSetupAvatarButton(Avatars avatar, String avatarTexturePath) {
        ImageButton.ImageButtonStyle avatarStyle =
                new ImageButton.ImageButtonStyle(skin.get("default", Button.ButtonStyle.class));

        avatarStyle.imageUp =
                new TextureRegionDrawable(new TextureRegion(
                        game.getManager().get(avatarTexturePath, Texture.class),
                        AVATAR_TEXTURE_SIZE,
                        AVATAR_TEXTURE_SIZE
                ));

        TextureRegionDrawable textureRegionDrawable;

        switch (avatar) {
            case BRUNETTE_GIRL:
                textureRegionDrawable =
                        new TextureRegionDrawable(new TextureRegion(
                                game.getManager().get("avatars/brunette_girl_picked.png", Texture.class),
                                AVATAR_TEXTURE_SIZE,
                                AVATAR_TEXTURE_SIZE
                        ));
                break;
            case BLOND_GIRL:
                textureRegionDrawable =
                        new TextureRegionDrawable(new TextureRegion(
                                game.getManager().get("avatars/blond_girl_picked.png", Texture.class),
                                AVATAR_TEXTURE_SIZE,
                                AVATAR_TEXTURE_SIZE
                        ));
                break;
            case BLACKHAIRED_GUY:
                textureRegionDrawable =
                        new TextureRegionDrawable(new TextureRegion(
                                game.getManager().get("avatars/blackhaired_guy_picked.png", Texture.class),
                                AVATAR_TEXTURE_SIZE,
                                AVATAR_TEXTURE_SIZE
                        ));
                break;
            case BLOND_GUY:
                textureRegionDrawable =
                        new TextureRegionDrawable(new TextureRegion(
                                game.getManager().get("avatars/blond_guy_picked.png", Texture.class),
                                AVATAR_TEXTURE_SIZE,
                                AVATAR_TEXTURE_SIZE
                        ));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + avatar);
        }
        avatarStyle.imageChecked = textureRegionDrawable;

        ImageButton avatarButton = new ImageButton(avatarStyle);

        avatarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getLocalPlayer().setPlayerAvatar(avatar);
                Log.info("Selected avatar set to: " +
                        game.getLocalPlayer().getPlayerAvatar().toString());
            }
        });

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

        waitingButtonTable.add(backButton)
                .padBottom(100f).padRight(25f).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
        waitingButtonTable.add(readyButton)
                .padBottom(100f).padRight(25f).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
    }

    public void updatePlayerLabels() {
        List<Player> players = gameData.getPlayers();

        playerInLobbyLabel.setVisible(true);

        for (Label playerLabel : playerNameLabelsList) {
            playerLabel.setText("");
        }

        for (int i = 0; i < players.size(); i++) {
            playerNameLabelsList[i].setText(players.get(i).getPlayerName());
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(.18f, .21f, .32f, 1);

        batch.setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        sprite.draw(game.getBatch());
        game.getBatch().end();

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

    public TextField getPlayerNameTextField() {
        return playerNameTextField;
    }

    public ButtonGroup<ImageButton> getAvatarButtonGroup() {
        return avatarButtonGroup;
    }
}

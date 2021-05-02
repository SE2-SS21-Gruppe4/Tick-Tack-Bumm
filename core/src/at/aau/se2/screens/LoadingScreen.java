package at.aau.se2.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

import at.aau.se2.TickTackBummGame;
import at.aau.se2.network.NetworkConstants;
import at.aau.se2.network.messages.Messages;

public class LoadingScreen extends ScreenAdapter {
    private final TickTackBummGame game;
    private final AssetManager assetManager;
    private final Client client;
    private String serverResponse = "";
    BitmapFont font;
    private Batch batch;

    public LoadingScreen() {
        this.game = TickTackBummGame.getTickTackBummGame();
        this.assetManager = game.getManager();

        this.assetManager.load("explosion.atlas", TextureAtlas.class);

        batch = new SpriteBatch();
        font = new BitmapFont();

        // TODO: refactor
        this.client = this.game.getClient();

        Kryo kryo = this.client.getKryo();
        kryo.register(Messages.SomeRequest.class);
        kryo.register(Messages.SomeResponse.class);

        client.start();
        try {
            client.connect(NetworkConstants.TIMEOUT, "localhost", NetworkConstants.TCP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Messages.SomeRequest request = new Messages.SomeRequest();
        request.text = "Here is a request";
        client.sendTCP(request);

        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof Messages.SomeResponse) {
                    Messages.SomeResponse response = (Messages.SomeResponse) object;
                    System.out.println(response.text);
                    serverResponse = response.text;
                }
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1.0f, 0, 1.0f, 1);

        batch.begin();
        font.draw(batch, serverResponse, 800, 200);
        batch.end();

        if (assetManager.update()) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            TickTackBummGame.getTickTackBummGame().setScreen(new LaunchScreen());
        }
    }
}

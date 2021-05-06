package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Input;
import com.esotericsoftware.minlog.Log;

public class TextfieldInputListener implements Input.TextInputListener {

    @Override
    public void input(String text) {
        Log.info(text);
    }

    @Override
    public void canceled() {
        Log.info("CANCELED");
    }
}

package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.esotericsoftware.minlog.Log;

import java.util.logging.Logger;

public class TextfieldInputListener extends ClickListener {

    private final TextField textField;
    private String userInput;

    public TextfieldInputListener(TextField textField) {
        this.textField = textField;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        userInput = textField.getText();

//        textField.setText(""); // clear/consume user input
        textField.setDisabled(true); // disable field until next guess/next turn

        if (isValidInput(userInput)) {
            Log.info("Got valid user input: " + userInput);
        } else {
            Log.error("userInput", "No valid user input; expecting a single alphabetical word!");
        }
    }

    private boolean isValidInput(String userInput) {
        return ((userInput != null) && // input not null
                (!userInput.equals("")) && // input not empty
                (userInput.matches("^[a-zA-ZäöüÄÖÜ]*$"))); // input is a single alphabetical word
    }

}

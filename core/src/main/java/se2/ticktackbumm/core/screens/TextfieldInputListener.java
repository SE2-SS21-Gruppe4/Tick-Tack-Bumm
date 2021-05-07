package se2.ticktackbumm.core.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.esotericsoftware.minlog.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class TextfieldInputListener extends ClickListener {

    private final TextField textField;
    private String userInput;
    private final String LOG_TAG = "USER_INPUT";

    public TextfieldInputListener() {
        textField = null;
    }

    public TextfieldInputListener(TextField textField) {
        this.textField = textField;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        if (textField == null) {
            Log.error(LOG_TAG, "User input was null");
            return;
        }

        userInput = textField.getText().trim();
        Log.info(LOG_TAG, "Got user input: " + userInput);

//        textField.setText(""); // clear/consume user input
        textField.setDisabled(true); // disable field until next guess/next turn

        if (isValidWord(userInput)) {
            textField.setText("KORREKT");

//            textField.setVisible(false); // hide text field after correct guess?
        } else {
            textField.setText("FALSCH");
        }

        textField.setDisabled(false);
    }

    boolean isValidWord(String userInput) {
        if (!isValidInput(userInput)) {
            Log.error(LOG_TAG, "User input was invalid: " + userInput);
            return false;
        }

        FileHandle dictionaryFileHandle;
        if (Gdx.app != null && Gdx.app.getType() == ApplicationType.Android) { // on Android,
            dictionaryFileHandle = Gdx.files.internal("dictionaries/de_AT.txt");
        } else { // for testing
            dictionaryFileHandle = new FileHandle("resources/assets/dictionaries/de_AT.txt");
        }

        try (BufferedReader bufferedDictionaryReader = new BufferedReader(dictionaryFileHandle.reader("Cp1258"))
        ) {
            String line;
            while ((line = bufferedDictionaryReader.readLine()) != null) {
                if (line.equals(userInput)) {
                    Log.info(LOG_TAG, "User input matched line: " + line);
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            Log.error(LOG_TAG, "File could not be found - " + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            Log.error(LOG_TAG, "File encoding is not supported - " + e.getLocalizedMessage());
        } catch (IOException e) {
            Log.error(LOG_TAG, "Failed to read a line - " + e.getLocalizedMessage());
        }

        Log.info(LOG_TAG, "User input did not matched any line in the dictionary");
        return false;
    }

    boolean isValidInput(String userInput) {
        return ((userInput != null) && // input not null
                (!userInput.equals("")) && // input not empty
                (userInput.matches("^[a-zA-ZäöüÄÖÜ]*$"))); // input is a single alphabetical word
    }

}

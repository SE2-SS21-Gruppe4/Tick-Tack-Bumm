package se2.ticktackbumm.core.gamelogic;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.esotericsoftware.minlog.Log;

import java.io.*;


public class TextfieldInputListener extends ClickListener {
    private final String GERMAN_CHARACTER_REGEX = "[a-zA-ZäöüÄÖÜ]";

    private final String LOG_TAG = "USER_INPUT";
    private final String dictionaryInternalPath = "dictionaries/de_AT.txt";

    private final TextField textField;
    private String userInput;

    /**
     * Default class constructor used for testing. Sets textfield to null, because it is not needed in testing.
     */
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

//        textField.setText(""); // clear/consume user input?
        textField.setDisabled(true); // disable field until next guess/next turn

        if (isValidWord(userInput)) {
            textField.setText("KORREKT");
//            textField.setVisible(false); // hide text field after correct guess?
        } else {
            textField.setText("FALSCH");
            textField.setDisabled(false); // wrong guess; guess again
        }

        textField.setDisabled(false); // enable here again for testing only
    }

    boolean isValidWord(String userInput) {
        if (!isValidInput(userInput)) {
            Log.error(LOG_TAG, "User input was invalid: " + userInput);
            return false;
        }

        // TODO: switch over different game types
        if (!hasValidPostfix(userInput, "ung")) {
            // TODO: replace with postfix from game data
            Log.error(LOG_TAG, "User input does not match the required postfix '-ung': " + userInput);
            return false;
        }

        if (!isInDictionary(userInput)) {
            Log.error(LOG_TAG, "User input is not in Austrian dictionary: " + userInput);
            return false;
        }

        return true;
    }

    boolean isValidInput(String userInput) {
        return ((userInput != null) && // input not null
                (!userInput.equals("")) && // input not empty
                (userInput.matches("^" + GERMAN_CHARACTER_REGEX + "*$"))); // input is a single alphabetical word
    }

    boolean hasValidPostfix(String userInput, String postfix) {
        return userInput.matches("^" + GERMAN_CHARACTER_REGEX + "+" + postfix.toLowerCase() + "$");
    }

    boolean isInDictionary(String userInput) {
        Reader dictionaryFileReader = null;
        try {
            if (Gdx.app != null && Gdx.app.getType() == Application.ApplicationType.Android) { // on Android,
                dictionaryFileReader = Gdx.files.internal(dictionaryInternalPath).reader("Cp1252");
            } else { // for testing
                InputStream dictionaryStream;
                if ((dictionaryStream = TextfieldInputListener.class.getResourceAsStream("/" + dictionaryInternalPath)) != null) {
                    dictionaryFileReader = new InputStreamReader(dictionaryStream, "Cp1252");
                } else {
                    throw new FileNotFoundException("Dictionary file could not be found at: " + dictionaryInternalPath);
                }
            }
        } catch (UnsupportedEncodingException e) {
            Log.error(LOG_TAG, "Charset is not supported - " + e.getMessage());
            Gdx.app.exit();
        } catch (GdxRuntimeException | FileNotFoundException e) {
            Log.error(LOG_TAG, e.getMessage());
            Gdx.app.exit();
        }

        if (dictionaryFileReader != null) {
            try (BufferedReader bufferedDictionaryReader = new BufferedReader(dictionaryFileReader)
            ) {
                String line;
                while ((line = bufferedDictionaryReader.readLine()) != null) {
                    if (line.equals(userInput)) {
                        Log.info(LOG_TAG, "User input matched line: " + line);
                        return true;
                    }
                }
            } catch (IOException e) {
                Log.error(LOG_TAG, "Failed to read a line - " + e.getMessage());
            }
        }

        return false;
    }
}

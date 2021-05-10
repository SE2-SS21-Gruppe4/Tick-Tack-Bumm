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
                // TODO: switch over game data; select correct word checks
                if (wordInDictionary(userInput, bufferedDictionaryReader) && validPostfix(userInput, "ung"))
                    return true;
            } catch (IOException e) {
                Log.error(LOG_TAG, "Failed to read a line - " + e.getMessage());
            }
        }

        Log.info(LOG_TAG, "User input did not matched any line in the dictionary");
        return false;
    }

    // TODO: this is called by the validation functions
    private boolean wordInDictionary(String userInput, BufferedReader bufferedDictionaryReader) throws IOException {
        String line;
        while ((line = bufferedDictionaryReader.readLine()) != null) {
            if (line.equals(userInput)) {
                Log.info(LOG_TAG, "User input matched line: " + line);
                return true;
            }
        }
        return false;
    }

    // TODO: make this independently available
    boolean validPostfix(String userInput, String postfix) {
        return userInput.matches("^[a-zA-ZäöüÄÖÜ]*" + postfix.toLowerCase() + "$");
    }

    boolean isValidInput(String userInput) {
        return ((userInput != null) && // input not null
                (!userInput.equals("")) && // input not empty
                (userInput.matches("^[a-zA-ZäöüÄÖÜ]*$"))); // input is a single alphabetical word
    }

}

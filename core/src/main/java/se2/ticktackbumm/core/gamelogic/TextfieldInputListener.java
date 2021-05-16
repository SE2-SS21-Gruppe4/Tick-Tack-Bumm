package se2.ticktackbumm.core.gamelogic;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.esotericsoftware.minlog.Log;

import java.io.*;
import java.util.ArrayList;

import se2.ticktackbumm.core.models.Cards.Card;
import se2.ticktackbumm.core.models.WheelZustand;
import se2.ticktackbumm.core.screens.SpinWheelScreen;
import sun.security.provider.ConfigFile;


public class TextfieldInputListener extends ClickListener {
    private final String GERMAN_CHARACTER_REGEX = "[a-zA-ZäöüÄÖÜ]";

    private final String LOG_TAG = "USER_INPUT";
    private final String dictionaryInternalPath = "dictionaries/de_AT.txt";

    private final TextField textField;
    private String userInput;
    SpinWheelScreen spinWheelScreen;
    Card card;

    /**
     * Default class constructor used for testing. Sets textfield to null, because it is not needed in testing.
     */
    public TextfieldInputListener() {
        textField = null;

    }

    public TextfieldInputListener(SpinWheelScreen spinWheelScreen, Card card, TextField textField) {
        this.textField = textField;
        this.spinWheelScreen = spinWheelScreen;
        this.card = card;
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
//            textField.setDisabled(false); // wrong guess; guess again
        }

        textField.setDisabled(false); // enable here again for testing only
    }

    boolean isValidWord(String userInput) {
        char[] text = textField.toString().toCharArray();
        char[] syllable = card.getWord().toCharArray();
        if (!isValidInput(userInput)) {
            Log.error(LOG_TAG, "User input was invalid: " + userInput);
            return false;
        }

        // TODO: switch over different game types; disabled for testing only
//        if (!hasValidPostfix(userInput, "ung")) {
//            // TODO: replace with postfix from game data
//            Log.error(LOG_TAG, "User input does not match the required postfix '-ung': " + userInput);
//            return false;
//        }

        if (!isInDictionary(userInput)) {
            Log.error(LOG_TAG, "User input is not in Austrian dictionary: " + userInput);
            return false;
        }

        if (spinWheelScreen.getWheelZustand() == WheelZustand.NOT_BEGIN) {
            for (int i = 0; i < syllable.length; i++) {
                if(text[i]==syllable[i]){
                    return false;
                }
            }
        }
        if (spinWheelScreen.getWheelZustand() == WheelZustand.NOT_END) {
            int count =0;
            for (int i = syllable.length; i >= 0; i--) {
                if(text[text.length-1-count]==syllable[i-1]){
                    return false;
                }
                count++;
            }
        }
        if (spinWheelScreen.getWheelZustand() == WheelZustand.START) {
            for (int i = 0; i < syllable.length; i++) {
                if(!(text[i]==syllable[i])){
                    return false;
                }
            }
        }

        return true;
    }

    boolean isValidInput(String userInput) {
        return ((userInput != null) && // input not null
                (!userInput.equals("")) && // input not empty
                (userInput.matches("^" + GERMAN_CHARACTER_REGEX + "*$"))); // input is a single alphabetical word
    }

    boolean hasValidPrefix(String userInput, String prefix) {
        return userInput.toLowerCase().matches("^" + prefix.toLowerCase() + GERMAN_CHARACTER_REGEX + "+$");
    }

    boolean hasValidInfix(String userInput, String infix) {
        return userInput.toLowerCase().matches("^" + GERMAN_CHARACTER_REGEX + "+" + infix.toLowerCase() + GERMAN_CHARACTER_REGEX + "+$");
    }

    boolean hasValidPostfix(String userInput, String postfix) {
        return userInput.toLowerCase().matches("^" + GERMAN_CHARACTER_REGEX + "+" + postfix.toLowerCase() + "$");
    }

    boolean isInScrambledWord(String userInput, String scrambledWord) {
        if (userInput.length() < 4) { // word found has to be at least 4 characters long; game rule
            Log.error(LOG_TAG, "User input was too short (at least 4 character): " + userInput);
            return false;
        }

        char[] userInputChar = userInput.toLowerCase().toCharArray();
        ArrayList<Character> scrambledWordChars = new ArrayList<>();

        for (char letter : scrambledWord.toLowerCase().toCharArray()) {
            scrambledWordChars.add(letter);
        }

        for (char letter : userInputChar) {
            for (int i = 0; i < scrambledWordChars.size(); i++) {
                if (letter == scrambledWordChars.get(i)) {
                    scrambledWordChars.remove(i);
                    break;
                }
            }
        }

        return scrambledWordChars.size() == (scrambledWord.length() - userInput.length());
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

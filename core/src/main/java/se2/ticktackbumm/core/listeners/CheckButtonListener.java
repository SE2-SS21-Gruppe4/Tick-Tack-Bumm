package se2.ticktackbumm.core.listeners;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Timer;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.client.ClientMessageSender;
import se2.ticktackbumm.core.data.GameData;
import se2.ticktackbumm.core.screens.MainGameScreen;

import java.io.*;
import java.util.ArrayList;


public class CheckButtonListener extends ClickListener {
    private final String GERMAN_CHARACTER_REGEX = "[a-zA-ZäöüÄÖÜ]";
    private final String LOG_TAG = "USER_INPUT";
    private final String dictionaryInternalPath = "dictionaries/de_AT.txt";

    private TickTackBummGame game;
    private GameData gameData;
    private ClientMessageSender clientMessageSender;

    private final MainGameScreen gameScreen;
    private final TextField textField;
    private final TextButton checkButton;
    private String userInput;

    /**
     * Default class constructor used for testing. Sets textfield to null, because it is not needed in testing.
     */
    public CheckButtonListener() {
        this.gameScreen = null;
        this.textField = null;
        this.checkButton = null;
    }

    public CheckButtonListener(MainGameScreen mainGameScreen) {
        this.gameScreen = mainGameScreen;
        this.textField = mainGameScreen.getTextField();
        this.checkButton = mainGameScreen.getCheckButton();

        this.game = TickTackBummGame.getTickTackBummGame();
        this.gameData = game.getGameData();
        this.clientMessageSender = game.getNetworkClient().getClientMessageSender();
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        if (textField == null) {
            Log.error(LOG_TAG, "User input was null");
            return;
        }

        userInput = textField.getText().trim();
        Log.info(LOG_TAG, "Got user input: " + userInput);

        gameScreen.hideControls();

        if (isValidWord(userInput)) {
            textField.setText("KORREKT");
            clientMessageSender.sendPlayerTaskCompleted(userInput);
        } else {
            textField.setText("FALSCH");
            gameScreen.showControls();
        }

        Gdx.app.postRunnable(() -> Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                textField.setText("");
            }
        }, 1f));
    }

    boolean isValidWord(String userInput) {

        if (!isValidInput(userInput)) {
            Log.error(LOG_TAG, "User input was invalid: " + userInput);
            return false;
        }

        if (isLockedWord(userInput)) {
            Log.error(LOG_TAG, "User input was used previously: " + userInput);
            return false;
        }

        Log.info(LOG_TAG, "Syllable: " + gameData.getCurrentGameModeText());

        switch (gameData.getCurrentGameMode()) {
            case NONE:
                // TODO: add error handling; NONE should never appear here
                break;
            case PREFIX:
                if (!hasValidPrefix(userInput, gameData.getCurrentGameModeText())) {
                    Log.error(LOG_TAG, "User input does not match the required prefix '" + gameData.getCurrentGameModeText() + "': " + userInput);
                    return false;
                }
                break;
            case INFIX:
                if (!hasValidInfix(userInput, gameData.getCurrentGameModeText())) {
                    Log.error(LOG_TAG, "User input does not match the required infix '" + gameData.getCurrentGameModeText() + "': " + userInput);
                    return false;
                }
                break;
            case POSTFIX:
                if (!hasValidPostfix(userInput, gameData.getCurrentGameModeText())) {
                    Log.error(LOG_TAG, "User input does not match the required postfix '" + gameData.getCurrentGameModeText() + "': " + userInput);
                    return false;
                }
                break;
            case SCRAMBLED_WORD:
                if (!isInScrambledWord(userInput, gameData.getCurrentGameModeText())) {
                    Log.error(LOG_TAG, "User input is not in the scrambled word '" + gameData.getCurrentGameModeText() + "': " + userInput);
                    return false;
                }
                break;
        }

        if (!isInDictionary(userInput)) {
            Log.error(LOG_TAG, "User input is not in Austrian dictionary: " + userInput);
            return false;
        }

        return true;
    }

    boolean isLockedWord(String userInput) {
        return gameData.getLockedWords().contains(userInput);
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
                if ((dictionaryStream = CheckButtonListener.class.getResourceAsStream("/" + dictionaryInternalPath)) != null) {
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

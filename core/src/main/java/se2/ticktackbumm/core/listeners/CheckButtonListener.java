package se2.ticktackbumm.core.listeners;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.esotericsoftware.minlog.Log;
import se2.ticktackbumm.core.TickTackBummGame;
import se2.ticktackbumm.core.client.ClientMessageSender;
import se2.ticktackbumm.core.data.GameData;
import se2.ticktackbumm.core.screens.MainGameScreen;

import java.io.*;
import java.util.ArrayList;

public class CheckButtonListener extends ClickListener {

    /**
     * Regex pattern to match german alphabetical characters.
     */
    private final String GERMAN_CHARACTER_REGEX = "[a-zA-ZäöüÄÖÜ]";

    /**
     * The log tag is used to provide unique logging for the class.
     */
    private final String LOG_TAG = "USER_INPUT";

    /**
     * Instance of the main game screen of the current game.
     */
    private final MainGameScreen gameScreen;
    /**
     * The text field from the main game screen that contains the user's word guess.
     */
    private final TextField textField;

    /**
     * The game data which is included in the singleton instance of the game class. Provides functionality
     * read and alter the game's general data.
     */
    private GameData gameData;
    /**
     * The game's message sender, later contained in the singleton instance of the game class. Provides
     * functionality to send messages from client to server.
     */
    private ClientMessageSender clientMessageSender;

    /**
     * Default class constructor used for testing. Sets textfield to null, because it is not needed in testing.
     */
    public CheckButtonListener() {
        this.gameScreen = null;
        this.textField = null;
    }

    /**
     * Constructs a listener for the text field on the main game screen. The listener can validate and process
     * the user's input into the text field.
     *
     * @param mainGameScreen the instance of the main game screen of the current game
     */
    public CheckButtonListener(MainGameScreen mainGameScreen) {
        this.gameScreen = mainGameScreen;
        this.textField = mainGameScreen.getTextField();

        this.gameData = TickTackBummGame.getTickTackBummGame().getGameData();
        this.clientMessageSender = TickTackBummGame.getTickTackBummGame().getNetworkClient().getClientMessageSender();
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        if (textField == null) {
            Log.error(LOG_TAG, "User input was null");
            return;
        }

        String userInput = textField.getText().trim();
        Log.info(LOG_TAG, "Got user input: " + userInput);

        gameScreen.hideControls();

        if (isValidWord(userInput)) {
            gameScreen.setWordCheckInfoLabel("KORREKT!", Color.GREEN);
            clientMessageSender.sendPlayerTaskCompleted(userInput);
        } else {
            gameScreen.showControls();
        }

        textField.setText("");
    }

    /**
     * Validates the user input in regards to the German/Austrian language, the game mode and the current
     * player task.
     *
     * @param userInput the user input to validate
     * @return true if the user input is a valid word, false otherwise
     */
    boolean isValidWord(String userInput) {

        if (!isValidInput(userInput)) {
            Log.error(LOG_TAG, "User input was invalid: " + userInput);
            gameScreen.setWordCheckInfoLabel("FALSCH, das Wort ('" + userInput + "') ist keine " +
                    "gueltige Eingabe!", Color.RED);
            return false;
        }

        if (isLockedWord(userInput)) {
            Log.error(LOG_TAG, "User input was used previously: " + userInput);
            gameScreen.setWordCheckInfoLabel("FALSCH, das Wort ('" + userInput + "') wurde bereits " +
                    "verwendet!", Color.RED);
            return false;
        }

        Log.info(LOG_TAG, "Syllable: " + gameData.getCurrentGameModeText());

        switch (gameData.getCurrentGameMode()) {
            case NONE:
                // TODO: add error handling; NONE should never appear here
                break;
            case PREFIX:
                if (!hasValidPrefix(userInput, gameData.getCurrentGameModeText())) {
                    Log.error(LOG_TAG, "User input does not match the required prefix '"
                            + gameData.getCurrentGameModeText() + "': " + userInput);
                    gameScreen.setWordCheckInfoLabel("FALSCH, das Wort ('" + userInput + "') beginnt nicht mit " +
                            "'" + gameData.getCurrentGameModeText() + "'", Color.RED);
                    return false;
                }
                break;
            case INFIX:
                if (!hasValidInfix(userInput, gameData.getCurrentGameModeText())) {
                    Log.error(LOG_TAG, "User input does not match the required infix '"
                            + gameData.getCurrentGameModeText() + "': " + userInput);
                    gameScreen.setWordCheckInfoLabel("FALSCH, das Wort ('" + userInput + "') enthaelt nicht " +
                            "'" + gameData.getCurrentGameModeText() + "'", Color.RED);
                    return false;
                }
                break;
            case POSTFIX:
                if (!hasValidPostfix(userInput, gameData.getCurrentGameModeText())) {
                    Log.error(LOG_TAG, "User input does not match the required postfix '"
                            + gameData.getCurrentGameModeText() + "': " + userInput);
                    gameScreen.setWordCheckInfoLabel("FALSCH, das Wort ('" + userInput + "') endet nicht mit " +
                            "'" + gameData.getCurrentGameModeText() + "'", Color.RED);
                    return false;
                }
                break;
            case SCRAMBLED_WORD:
                if (!isInScrambledWord(userInput, gameData.getCurrentGameModeText())) {
                    Log.error(LOG_TAG, "User input is not in the scrambled word '"
                            + gameData.getCurrentGameModeText() + "': " + userInput);
                    return false;
                }
                break;
        }

        if (!isInDictionary(userInput)) {
            Log.error(LOG_TAG, "User input is not in Austrian dictionary: " + userInput);
            gameScreen.setWordCheckInfoLabel("FALSCH, das Wort ('" + userInput + "') ist nicht im " +
                    "Woerterbuch enthalten!", Color.RED);
            return false;
        }

        return true;
    }

    /**
     * Returns true if the input was already used this round by an other player and false otherwise.
     *
     * @param userInput the user input to validate
     * @return true if the word is locked, false otherwise
     */
    boolean isLockedWord(String userInput) {
        return gameData.getLockedWords().contains(userInput.toLowerCase());
    }

    /**
     * Returns true if the input has a valid German word syntax and false otherwise.
     *
     * @param userInput the user input to validate
     * @return true if the input has valid German syntax, false otherwise
     */
    boolean isValidInput(String userInput) {
        return ((userInput != null) && // input not null
                (!userInput.equals("")) && // input not empty
                (userInput.matches("^" + GERMAN_CHARACTER_REGEX + "*$"))); // input is a single alphabetical word
    }

    /**
     * Returns true if the input starts with the given prefix and false otherwise.
     *
     * @param userInput the user input to validate
     * @param prefix    the prefix to validate against the input
     * @return true if input has prefix, false otherwise
     */
    boolean hasValidPrefix(String userInput, String prefix) {
        return userInput.toLowerCase().matches("^" + prefix.toLowerCase() + GERMAN_CHARACTER_REGEX + "+$");
    }

    /**
     * Returns true if the input contains the given infix and false otherwise.
     *
     * @param userInput the user input to validate
     * @param infix     the infix to validate against the input
     * @return true if input has infix, false otherwise
     */
    boolean hasValidInfix(String userInput, String infix) {
        return userInput.toLowerCase().matches("^" + GERMAN_CHARACTER_REGEX + "+" + infix.toLowerCase() + GERMAN_CHARACTER_REGEX + "+$");
    }

    /**
     * Returns true if the input ends with the given postfix and false otherwise.
     *
     * @param userInput the user input to validate
     * @param postfix   the postfix to validate against the input
     * @return true if input has postfix, false otherwise
     */
    boolean hasValidPostfix(String userInput, String postfix) {
        return userInput.toLowerCase().matches("^" + GERMAN_CHARACTER_REGEX + "+" + postfix.toLowerCase() + "$");
    }

    /**
     * Returns true if the input is contained in the scrambled word and false otherwise.
     *
     * @param userInput     the user input to validate
     * @param scrambledWord the scrambled word to validate against the input
     * @return true if input is contained in scrambled word
     */
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

    /**
     * Returns true if the Austrian dictionary contains the given input word and false otherwise.
     *
     * @param userInput the user input to validate
     * @return true if the Austrian dictionary contains the input
     */
    boolean isInDictionary(String userInput) {
        Reader dictionaryFileReader = null;
        String dictionaryInternalPath = "dictionaries/de_AT.txt";

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

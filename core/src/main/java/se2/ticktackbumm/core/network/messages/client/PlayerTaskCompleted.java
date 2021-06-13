package se2.ticktackbumm.core.network.messages.client;

public class PlayerTaskCompleted {

    private String usedWord;

    public PlayerTaskCompleted() {
        // kryonet
    }

    public PlayerTaskCompleted(String usedWord) {
        this.usedWord = usedWord;
    }

    public String getUsedWord() {
        return usedWord;
    }
}

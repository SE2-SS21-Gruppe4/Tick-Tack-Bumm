package se2.ticktackbumm.core.network.messages;

public class SomeResponse {
    private String text;

    public SomeResponse() {
        // kryonet
    }

    public SomeResponse(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

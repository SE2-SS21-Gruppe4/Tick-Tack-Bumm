package se2.ticktackbumm.core.network.messages.client;

public class SomeRequest {

    private String text;

    public SomeRequest() {
        // kryonet
    }

    public SomeRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}


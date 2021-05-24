package se2.ticktackbumm.core.network.messages.client;

import se2.ticktackbumm.core.data.Avatars;

public class PlayerReady {

    private String playerName;
    private Avatars playerAvatar;

    public PlayerReady() {
        // kryonet
    }

    public PlayerReady(String playerName, Avatars playerAvatar) {
        this.playerName = playerName;
        this.playerAvatar = playerAvatar;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Avatars getPlayerAvatar() {
        return playerAvatar;
    }

    public void setPlayerAvatar(Avatars playerAvatar) {
        this.playerAvatar = playerAvatar;
    }
}

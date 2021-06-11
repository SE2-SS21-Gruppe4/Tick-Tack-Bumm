package se2.ticktackbumm.core.network.messages.client;

public class BombStart {

    private float bombTimer;

    public BombStart(){

    }

    public BombStart(float bombTimer){
        this.bombTimer = bombTimer;
    }

    public float getBombTimer(){
        return this.bombTimer;
    }

}

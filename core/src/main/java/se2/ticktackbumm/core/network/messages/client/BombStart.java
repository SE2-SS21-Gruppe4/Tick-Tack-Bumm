package se2.ticktackbumm.core.network.messages.client;

public class BombStart {

    private  int bombTimer;

    public BombStart(){

    }

    public BombStart(int bombTimer){
        this.bombTimer = bombTimer;
    }

    public int getBombTimer(){
        return this.bombTimer;
    }

}

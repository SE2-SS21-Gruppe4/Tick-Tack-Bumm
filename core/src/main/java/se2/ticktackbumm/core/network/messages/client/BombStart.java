package se2.ticktackbumm.core.network.messages.client;

public class BombStart {

    private float tickTimer;

    public BombStart(){}

    public BombStart(float tickTimer){
        this.tickTimer = tickTimer;
    }

    public void setTickTimer(float tickTimer){
        this.tickTimer = tickTimer;
    }
    public float getTickTimer(){
        return this.tickTimer;
    }
}

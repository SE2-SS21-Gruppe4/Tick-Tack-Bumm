package se2.ticktackbumm.core.network.messages.client;

public class StartBomb {

    private float tickTimer;

    public StartBomb(){}

    public StartBomb(float tickTimer){
        this.tickTimer = tickTimer;
    }

    public void setTickTimer(float tickTimer){
        this.tickTimer = tickTimer;
    }
    public float getTickTimer(){
        return this.tickTimer;
    }
}

package se2.ticktackbumm.core.network.messages.client;

public class CardOpened {

    private String word;

    public CardOpened(){}

    public CardOpened(String word) {
        this.word = word;
    }

    public String getWord(){
        return this.word;
    }

    public void setWord(String word){
        this.word = word;
    }
}

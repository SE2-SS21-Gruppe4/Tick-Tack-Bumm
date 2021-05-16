package se2.ticktackbumm.core.models.Cards;

public enum CardWords {

    CRO("CRO"),
    AB("AB"),
    WO("WO"),
    CHA("CHA"),
    ABL("ABL"),
    OR("OR"),
    SE("SE"),
    FRE("FRE"),
    UNC("UNC"),
    FL("FL"),
    NG("NG"),
    WER("WER"),
    BIR("BIR"),
    GER("GER"),
    ONS("ONS"),
    ACK("ACK"),
    EXP("EXP"),
    IGN("IGN"),
    IL("IL");

    private final String word;

    private CardWords( String word){
        this.word = word;
    }

    public String getWord(){
        return this.word;
    }
}

package se2.ticktackbumm.core.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import se2.ticktackbumm.core.models.attributes.Position;

public class Score extends Position{

    private int score;
    private final Stage stage;

    public Score(Position position) {
        addPlayerToBoard();
        this.score = 0;
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public void addPlayerToBoard(){
        //setActorSettings("table.png", 0,0,120,150);
        //stage.addActor(table.png);
        stage.act();
        stage.draw();
    }

    public void setActorSettings(Actor actor, float positionX, float positionY, float width, float height){
        actor.setBounds(positionX,positionY,width,height);
    }

}


package com.klimov_d.dungeon_hero;

import com.klimov_d.dungeon_hero.generators.StageGenerator;
import com.klimov_d.dungeon_hero.service.DrawManager;
import com.klimov_d.dungeon_hero.service.Drawable;

import java.util.ArrayList;
import java.util.List;

public class Dungeon implements Drawable {
    private List<Stage> stages;
    private int currentStageIndex;
    private Character person;

    public Dungeon() {
        this.stages = new ArrayList<>();
        this.currentStageIndex = 0;
    }
    public void addStage(Stage stage) {
        stages.add(stage);
    }
    public void moveToStage(Stage stage) {
        currentStageIndex = stages.indexOf(stage);
        person.setPosition(stage.getFreePosition());
    }



    @Override
    public void draw(DrawManager drawManager) {
        getCurrentStage().draw(drawManager);
        person.draw(drawManager);
        drawManager.putPerson(person);
    }

    public Character getPerson() {
        return person;
    }

    public void setPerson(Character person) {
        this.person = person;
    }

    public Stage getCurrentStage() {
        return stages.get(currentStageIndex);
    }

    public void update() {
        getCurrentStage().update(this);
    }
}

package com.klimov_d.dungeon_hero;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.klimov_d.dungeon_hero.commands.Command;
import com.klimov_d.dungeon_hero.commands.GameCommand;
import com.klimov_d.dungeon_hero.generators.DungeonGenerator;
import com.klimov_d.dungeon_hero.generators.StageGenerator;
import com.klimov_d.dungeon_hero.items.Equipment;
import com.klimov_d.dungeon_hero.items.Treasure;
import com.klimov_d.dungeon_hero.service.DrawManager;
import com.klimov_d.dungeon_hero.service.SpriteBatchDrawManager;

import java.util.List;
import java.util.Optional;

public class DungeonHeroGame extends ApplicationAdapter {
	SpriteBatch batch;
	BitmapFont whiteFont;

	Dungeon dungeon;
	DungeonGenerator dungeonGenerator;
	DrawManager drawManager;

	int screenHeight;
	int screenWidth;

	private static DungeonHeroGame game;
	public static DungeonHeroGame instance() { return game; }

	public DungeonHeroGame(int screenHeight, int screenWidth) {
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		game = this;
	}

	@Override
	public void create () {
		dungeonGenerator = new DungeonGenerator();
		batch = new SpriteBatch();
		whiteFont = loadFont("white");
		dungeon = dungeonGenerator.generateDungeon();
		createDrawManager();
	}

	private void createDrawManager() {
		drawManager = new SpriteBatchDrawManager(
				batch,
				whiteFont,
				dungeon.getCurrentStage().getHeight(),
				dungeon.getCurrentStage().getWidth()
		);
	}

	private static BitmapFont loadFont(String fontName) {
		return new BitmapFont(
				Gdx.files.internal("fonts/"+fontName+".fnt"),
				Gdx.files.internal("fonts/"+fontName+".png"),
				false);
	}

	@Override
	public void render () {
		update();
		ScreenUtils.clear(0, 0, 0, 1);
		drawManager.clear();
		dungeon.draw(drawManager);
		drawManager.drawAllSymbols(0, screenHeight - 1);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		whiteFont.dispose();
	}

	private void update() {
		if(processUserInput()) {
			updateWorld();
			if(gameEnd()) {
				Gdx.app.exit();
			}
		}
	}

	private boolean gameEnd() {
		return dungeon.getPerson().getHealth() <= 0;
	}

	private void updateWorld() {
		dungeon.update();
	}

	private boolean processUserInput() {
		Optional<Command> command = GameCommand.get();
		if(command.isPresent()) {
			drawManager.clearMessages();
			command.get().apply();
			return true;
		}
		return false;
	}

	public void personAction() {
		Stage stage = dungeon.getCurrentStage();
		Character person = dungeon.getPerson();
		if(stage.getTeleport(person.getPosition()).isPresent()) {
			dungeon.moveToStage(stage.getTeleport(person.getPosition()).get().getToStage());
			dungeon.getPerson().resetHp();
			createDrawManager();
		}
		else {
			takeAndUseEquipments(stage, person);
			takeTreasures(stage, person);
		}
	}

	private void takeAndUseEquipments(Stage stage, Character person) {
		for (var item :
				stage.getEquipments(person.getPosition())) {
			item.use(person);
			stage.removeEquipment(item);
			drawManager.addMessage(String.format("Персонаж подбирает и снаряжает %s", item.toString()));
		}
	}
	private void takeTreasures(Stage stage, Character person) {
		for (var item :
				stage.getTreasures(person.getPosition())) {
			item.take(person);
			stage.removeTreasure(item);
			drawManager.addMessage(String.format("Персонаж подбирает %s", item.toString()));
		}
	}

	public void movePersonRight() {
		movePerson(0, 1);
	}

	public void movePersonDown() {
		movePerson(1, 0);
	}

	public void movePersonLeft() {
		movePerson(0, -1);
	}

	public void movePersonUp() {
		movePerson(-1, 0);
	}

	private void movePerson(int lineOffset, int columnOffset) {
		Position curPos = dungeon.getPerson().getPosition();
		Stage stage = dungeon.getCurrentStage();
		Position newPos = new Position(curPos.getLine() + lineOffset, curPos.getColumn() + columnOffset);
		if(stage.cellIsExist(newPos) &&
			stage.isPassable(newPos) &&
			stage.noMonsters(newPos)) {
			dungeon.getPerson().setPosition(newPos);
			printCellContent(newPos);
		} else {
			attackMonsterIfExist(newPos);
		}
	}

	private void printCellContent(Position pos) {
		List<Treasure> treasures = dungeon.getCurrentStage().getTreasures(pos);
		List<Equipment> equipments = dungeon.getCurrentStage().getEquipments(pos);
		Optional<Teleport> teleport = dungeon.getCurrentStage().getTeleport(pos);
		teleport.ifPresent(value -> drawManager.addMessage(value.toString()));
		drawTreasures(treasures);
		drawEquipments(equipments);
	}

	private void drawTreasures(List<Treasure> treasures) {
		for (var treasure: treasures) {
			drawManager.addMessage(treasure.toString());
		}
	}

	private void drawEquipments(List<Equipment> equipments) {
		for (var equipment: equipments) {
			drawManager.addMessage(equipment.toString());
		}
	}

	private void attackMonsterIfExist(Position pos) {
		var monster = dungeon.getCurrentStage().getMonster(pos);
		if(monster.isPresent()) {
			drawManager.addMessage(String.format("Персонаж атакует %s", monster.get().toString()));
			dungeon.getPerson().attack(monster.get());
			if(monster.get().getHealth() <= 0) {
				drawManager.addMessage(String.format("%s погибает", monster.get().toString()));
				dungeon.getCurrentStage().removeMonster(monster.get());
			}
		}
	}

	public DrawManager getDrawManager() {
		return drawManager;
	}
}

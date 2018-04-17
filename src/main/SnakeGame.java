package main;

import lib.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeGame extends AbstractGame {

	private Random random = new Random();
	private Map map = new Map();
	private Snake snake = new Snake(10, 10);
	private Food food = new Food(15, 15);

	// private String state = "left";

	public SnakeGame() {
		// Just sample blocks. Feels free to remove them
		for (Block b : snake.getBody()) {
			map.addBlock(b);
		}
		map.addBlock(food);

	}

	public int getMapSize() {
		return map.getSize();
	}

	public List<Block> getBlocks() {
		return map.getBlocks();
	}

	// This method is called every loop
	@Override
	protected void gameLogic() {
		snake.move();
		for (Block b : snake.getBody()) {
			if (b.overlapped(food)) {
				Block newBlock = snake.expand();
				map.addBlock(newBlock);
				// System.out.println("Snake got food");
				food.setX(random.nextInt(map.getSize()));
				food.setY(random.nextInt(map.getSize()));
				break;
			}
		}

		if (snake.hitItself()) {
			end();
		}
	}

	// public void move() {
	// Block b = map.getBlocks().get(0);
	// int x = b.getX();
	// int y = b.getY();
	// switch (state) {
	// case "left":
	// x = b.getX() - 1;
	// y = b.getY();
	// break;
	// case "right":
	// x = b.getX() + 1;
	// y = b.getY();
	// break;
	// case "up":
	// x = b.getX();
	// y = b.getY() - 1;
	// break;
	// case "down":
	// x = b.getX();
	// y = b.getY() + 1;
	// break;
	// default:
	// break;
	// }
	// map.getBlocks().add(0, new Block(x, y));
	// map.getBlocks().remove(map.getBlocks().size() - 1);
	// }

	// This method is called when left key is pressed
	@Override
	protected void handleLeftKey() {
		// System.out.println("Left key is pressed");
		// if (!this.state.equals("right"))
		// state = "left";
		if (snake.getDx() != 1) {
			snake.setDx(-1);
			snake.setDy(0);
		}

	}

	// This method is called when right key is pressed
	@Override
	protected void handleRightKey() {
		// System.out.println("Right key is pressed");
		// if (!this.state.equals("left"))
		// state = "right";
		if (snake.getDx() != -1) {
			snake.setDx(1);
			snake.setDy(0);
		}
	}

	// This method is called when up key is pressed
	@Override
	protected void handleUpKey() {
		// System.out.println("Up key is pressed");
		// if (!this.state.equals("down"))
		// state = "up";
		if (snake.getDy() != 1) {
			snake.setDx(0);
			snake.setDy(-1);
		}

	}

	// This method is called when down key is pressed
	@Override
	protected void handleDownKey() {
		// System.out.println("Down key is pressed");
		// if (!this.state.equals("up"))
		// state = "down";
		if (snake.getDy() != -1) {
			snake.setDx(0);
			snake.setDy(1);
		}
	}

	public void load(Momento momento) {
		System.out.println("Load!");

		map.getBlocks().removeAll(map.getBlocks());

		snake.getBody().removeAll(snake.getBody());
		for(Block b : momento.snakeBlock) {
			snake.getBody().add(b);
		}
		
		snake.setDx(momento.saveDx);
		snake.setDy(momento.saveDy);
		food.setX(momento.saveFoodX);
		food.setY(momento.saveFoodY);

		for (Block b : snake.getBody()) {
			map.addBlock(b);
		}
		map.addBlock(food);
	}

	public Momento save() {
		System.out.println("Save!");
		return new Momento(map, snake, food);
	}

	static class Momento {
		
		private List<Block> snakeBlock = new ArrayList<Block>();
		private int saveDx;
		private int saveDy;
		private int saveFoodX;
		private int saveFoodY;

		public Momento(Map map, Snake snake, Food food) {

			for (Block b : snake.getBody()) {
				snakeBlock.add(new Block(b.getX(), b.getY()));
			}
			this.saveDx = snake.getDx();
			this.saveDy = snake.getDy();
			this.saveFoodX = food.getX();
			this.saveFoodY = food.getY();
		}

	}

}

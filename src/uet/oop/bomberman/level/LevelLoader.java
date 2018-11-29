package uet.oop.bomberman.level;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.exceptions.LoadLevelException;

/**
 * Load và lưu trữ thông tin bản đồ các màn chơi
 */
public abstract class LevelLoader {

	protected int _width, _height;
	protected int _level;
	protected Board _board;

	public LevelLoader(Board board, int level) throws LoadLevelException {
		_board = board;
		loadLevel(5);
	}

	public abstract void loadLevel(int level) throws LoadLevelException;

	public abstract void createEntities();

	public int getWidth() {
		return _width;
	}

	public int getHeight() {
		return _height;
	}

	public int getLevel() {
		return _level;
	}

}

package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.Message;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.bomb.FlameSegment;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.Item;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.Coordinates;
import uet.oop.bomberman.sound.Sound;
import uet.oop.bomberman.sound.Walk;
import uet.oop.bomberman.gui.menu.HighScore;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bomber extends Character {

    private List<Bomb> _bombs;
    protected Keyboard _input;
    private int step=5;
    /**
     * nếu giá trị này < 0 thì cho phép đặt đối tượng Bomb tiếp theo,
     * cứ mỗi lần đặt 1 Bomb mới, giá trị này sẽ được reset về 0 và giảm dần trong mỗi lần update()
     */
    protected int _timeBetweenPutBombs = 0;
    public static ArrayList<Item> _items = new ArrayList<Item>();

    public Bomber(int x, int y, Board board) {
        super(x, y, board);
        _bombs = _board.getBombs();
        _input = _board.getInput();
        _sprite = Sprite.player_right;
    }

    @Override
    public void update() {
        clearBombs();
        if (!_alive) {
            afterKill();
            return;
        }

        if (_timeBetweenPutBombs < -7500) _timeBetweenPutBombs = 0;
        else _timeBetweenPutBombs--;

        animate();

        calculateMove();

        detectPlaceBomb();
    }

    @Override
    public void render(Screen screen) {
        calculateXOffset();

        if (_alive)
            chooseSprite();
        else
            _sprite = Sprite.player_dead1;

        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(_board, this);
        Screen.setOffset(xScroll, 0);
    }

    /**
     * Kiểm tra xem có đặt được bom hay không? nếu có thì đặt bom tại vị trí hiện tại của Bomber
     */
    private void detectPlaceBomb() {
        // TODO: kiểm tra xem phím điều khiển đặt bom có được gõ và giá trị _timeBetweenPutBombs, Game.getBombRate() có thỏa mãn hay không
        // TODO:  Game.getBombRate() sẽ trả về số lượng bom có thể đặt liên tiếp tại thời điểm hiện tại
        // TODO: _timeBetweenPutBombs dùng để ngăn chặn Bomber đặt 2 Bomb cùng tại 1 vị trí trong 1 khoảng thời gian quá ngắn
        // TODO: nếu 3 điều kiện trên thỏa mãn thì thực hiện đặt bom bằng placeBomb()
        // TODO: sau khi đặt, nhớ giảm số lượng Bomb Rate và reset _timeBetweenPutBombs về 0
        if (_input.space && _timeBetweenPutBombs < 0 && Game.getBombRate() > 0) {

            placeBomb(getXTile(), getYTile());
            Game.addBombRate(-1);
            _timeBetweenPutBombs = 40;
        }
    }

    protected void placeBomb(int x, int y) {
        // TODO: thực hiện tạo đối tượng bom, đặt vào vị trí (x, y)
        Bomb bomb = new Bomb(x, y, _board);
        _board.addBomb(bomb);
        Sound.play("place");
    }

    private void clearBombs() {
        Iterator<Bomb> bs = _bombs.iterator();

        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Game.addBombRate(1);
            }
        }

    }

    @Override
    public void kill() {

        if (!_alive) return;
        _alive = false;
        _board.setLives();
        if (_board.getLives()==0){
            Sound.play("gameover");
        }

        else Sound.play("die");
        Message msg = new Message(" - ♥", getXMessage(), getYMessage(), 2, Color.white, 60);
        _board.addMessage(msg);
    }

    @Override
    protected void afterKill() {
        if(_timeAfter > 0) --_timeAfter;
        else {
            if(_bombs.size() == 0) {

                if(_board.getLives() == 0){
                    try{
                        HighScore.SetHighScoreToFile(_board.getGame());
                    }
                    catch (IOException exception) {
                        System.out.println("ERROR WHEN OPEN HIGH SCORE FILE!");
                    }
                    finally {
                        _board.endGame();
                    }
                }

                else
                    _board.restartLevel();
            }
        }
    }

    @Override
    protected void calculateMove() {
        // TODO: xử lý nhận tín hiệu điều khiển hướng đi từ _input và gọi move() để thực hiện di chuyển
        // TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển

        //countdown
        step--;

        _moving = true;

        if (_input.up) {
            move(0, -Game.getBomberSpeed());
        } else if (_input.down) {
            move(0, Game.getBomberSpeed());
        } else if (_input.left) {
            move(-Game.getBomberSpeed(), 0);
        } else if (_input.right) {
            move(Game.getBomberSpeed(), 0);
        } else {
            _moving = false;
        }

    }

    @Override
    public boolean canMove(double x, double y) {
        // TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không

        int tileX = Coordinates.pixelToTile(x);
        int tileY = Coordinates.pixelToTile(y);

        Entity newEntity = _board.getEntity(tileX, tileY, this);
        return collide(newEntity);
    }

    @Override
    public void move(double xa, double ya) {
        // TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không và thực hiện thay đổi tọa độ _x, _y
        // TODO: nhớ cập nhật giá trị _direction sau khi di chuyển

        double centerX = _x + _sprite.get_realWidth() / 2;
        double centerY = _y - _sprite.get_realHeight() / 2;

        if (xa > 0) _direction = 1;
        if (xa < 0) _direction = 3;
        if (ya > 0) _direction = 2;
        if (ya < 0) _direction = 0;
        if (canMove(centerX + xa, centerY + ya)) {
            _x += xa;
            _y += ya;
            if(step<=0){
                Sound.play("walk");
                step=30;
            }
        }

        moveCenter();
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý va chạm với Flame
        // TODO: xử lý va chạm với Enemy


        if (e instanceof FlameSegment) {
            this.kill();
            return false;
        } else if (e instanceof Enemy) {
            this.kill();
            return false;
        }else if(e.getSprite() == Sprite.bomb){
            return true;
        }else if(e instanceof LayeredEntity){
            if(((LayeredEntity) e).getTopEntity() instanceof Grass)
                return true;
            else if (((LayeredEntity) e).getTopEntity() instanceof SpeedItem){
                System.out.println("speed item");
                return ((LayeredEntity) e).getTopEntity().collide(this);
//                remove();
            }
            else if (((LayeredEntity) e).getTopEntity() instanceof BombItem){
                System.out.println("bomb item");
                return ((LayeredEntity) e).getTopEntity().collide(this);
            }
            else if (((LayeredEntity) e).getTopEntity() instanceof FlameItem){
                System.out.println("flame item");
                return ((LayeredEntity) e).getTopEntity().collide(this);
            }
            else if (((LayeredEntity) e).getTopEntity() instanceof Portal){
                System.out.println("PORTAL");
                return ((LayeredEntity) e).getTopEntity().collide(this);
            }
            else return false;
        }
        else if (e.getSprite() == Sprite.wall) {
            return false;
        }else if (e instanceof Balloon) {
            this.kill();
            return false;
        }

        return true;


    }

    /**
     * Item
     */
    public void addItem(Item item){
        if(item.isRemoved()) return ;
        item.setValues();
        _items.add(item);

    }
    public void clearUsedItems() {
        Item p;
        for (int i = 0; i < _items.size(); i++) {
            p = _items.get(i);
            if(p.isActive() == false)
                _items.remove(i);
        }
    }

    public void removeItems() {
        for (int i = 0; i < _items.size(); i++) {
            _items.remove(i);
        }
    }

    /**
     * To center
     */
    public void centerX() {
        int pixelOfEntity = Coordinates.tileToPixel(1);
        double centerX = _x + _sprite.get_realWidth() / 2;
        int tileCenterX = Coordinates.pixelToTile(centerX);
        _x = Coordinates.tileToPixel(tileCenterX) + pixelOfEntity / 2 - _sprite.get_realWidth() / 2;
    }

    public void centerY() {
        int pixelOfEntity = Coordinates.tileToPixel(1);
        double centerY = _y - _sprite.get_realHeight() / 2;
        int tileCenterY = Coordinates.pixelToTile(centerY);
        _y = Coordinates.tileToPixel(tileCenterY) + pixelOfEntity / 2 + _sprite.get_realHeight() / 2;
    }

    public void moveCenter() {
        int pixelOfEntity = Coordinates.tileToPixel(1);
        double centerX = _x + _sprite.get_realWidth() / 2;
        double centerY = _y - _sprite.get_realHeight() / 2;

        boolean contactTop = !canMove(centerX, centerY - pixelOfEntity / 2);
        boolean contactDown = !canMove(centerX, centerY + pixelOfEntity / 2);
        boolean contactLeft = !canMove(centerX - pixelOfEntity / 2, centerY);
        boolean contactRight = !canMove(centerX + pixelOfEntity / 2, centerY);

        if (_direction != 0 && contactDown) centerY();
        if (_direction != 1 && contactLeft) centerX();
        if (_direction != 2 && contactTop) centerY();
        if (_direction != 3 && contactRight) centerX();
    }

    private void chooseSprite() {
        switch (_direction) {
            case 0:
                _sprite = Sprite.player_up;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
                }
                break;
            case 1:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
            case 2:
                _sprite = Sprite.player_down;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
                }
                break;
            case 3:
                _sprite = Sprite.player_left;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
                }
                break;
            default:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
        }
    }
}

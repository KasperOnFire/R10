package r10;

import battleship.interfaces.Position;

/**
 *
 * @author Kasper
 */
public class Coordinates {

    private final int x;
    private final int y;
    private Position posUp;
    private Position posDown;
    private Position posRight;
    private Position posLeft;

    public Coordinates(Position shot) {
        x = shot.x;
        y = shot.y;
        posUp = new Position(shot.x, shot.y + 1);
        posDown = new Position(shot.x, shot.y - 1);
        posRight = new Position(shot.x + 1, shot.y);
        posLeft = new Position(shot.x - 1, shot.y);
    }

    public void removePosUp() {
        posUp = null;
    }

    public void removePosDown() {
        posDown = null;
    }

    public void removePosRight() {
        posRight = null;
    }

    public void removePosLeft() {
        posLeft = null;
    }

    public Position getPosUp() {
        return posUp;
    }

    public Position getPosDown() {
        return posDown;
    }

    public Position getPosRight() {
        return posRight;
    }

    public Position getPosLeft() {
        return posLeft;
    }

    public Position toPosition() {
        Position returnPos = new Position(x, y);
        return returnPos;
    }

}

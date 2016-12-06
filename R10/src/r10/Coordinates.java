package r10;

import battleship.interfaces.Position;

/**
 *
 * @author Kasper
 */
public class Coordinates {

    private final Position posUp;
    private final Position posDown;
    private final Position posRight;
    private final Position posLeft;

    public Coordinates(Position shot) {
        posUp = new Position(shot.x, shot.y + 1);
        posDown = new Position(shot.x, shot.y - 1);
        posRight = new Position(shot.x + 1, shot.y);
        posLeft = new Position(shot.x - 1, shot.y);
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

}

package r10;

import battleship.interfaces.Board;
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
    private int posStatus; //0 for not shot at, 1 for splash, 2 for hit

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
        if (this.x > 9) {
            return null;
        } else if (this.x < 0) {
            return null;
        } else if (this.y > 9) {
            return null;
        } else if (this.y < 0) {
            return null;
        }
        return posUp;
    }

    public Position getPosDown() {
        if (this.x > 9) {
            return null;
        } else if (this.x < 0) {
            return null;
        } else if (this.y > 9) {
            return null;
        } else if (this.y < 0) {
            return null;
        }
        return posDown;
    }

    public Position getPosRight() {
        if (this.x > 9) {
            return null;
        } else if (this.x < 0) {
            return null;
        } else if (this.y > 9) {
            return null;
        } else if (this.y < 0) {
            return null;
        }
        return posRight;
    }

    public Position getPosLeft() {
        if (this.x > 9) {
            return null;
        } else if (this.x < 0) {
            return null;
        } else if (this.y > 9) {
            return null;
        } else if (this.y < 0) {
            return null;
        }
        return posLeft;
    }

    public int getPosStatus() {
        return posStatus;
    }

    public Coordinates setCoordWithPos(Position pos) {
        Coordinates returncord = new Coordinates(pos);
        return returncord;
    }

    public Position toPosition() {
        Position returnPos = new Position(x, y);
        return returnPos;
    }

    public int[] getSpaces(Position pos, Board board) {
        int[] spacesUDLR = new int[4];
        spacesUDLR[0] = getSpaceUp(pos, board);
        spacesUDLR[1] = getSpaceDown(pos, board);
        spacesUDLR[2] = getSpaceLeft(pos, board);
        spacesUDLR[3] = getSpaceRight(pos, board);
        return spacesUDLR;
    }

    private int getSpaceUp(Position pos, Board board) {
        Coordinates coord = new Coordinates(pos);
        Coordinates testCoord = new Coordinates(coord.posUp);
        int remaining = board.sizeY() - coord.y;

        for (int i = 0; i < 4; i++) {
            if (testCoord.posStatus == 2) {
                remaining = testCoord.y - coord.y;
                break;
            } else if (testCoord.posStatus == 1) {
                remaining = testCoord.y - coord.y;
                break;
            } else {
                testCoord = testCoord.setCoordWithPos(testCoord.posUp);
                if (testCoord.getPosUp() == null) {

                }
            }
        }
        return remaining;
    }

    private int getSpaceDown(Position pos, Board board) {
        Coordinates coord = new Coordinates(pos);
        Coordinates testCoord = new Coordinates(coord.posDown);
        int remaining = board.sizeY() - coord.y;

        for (int i = 0; i < 4; i++) {
            if (testCoord.posStatus == 2) {
                remaining = testCoord.y - coord.y;
                break;
            } else if (testCoord.posStatus == 1) {
                remaining = testCoord.y - coord.y;
                break;
            } else {
                testCoord = testCoord.setCoordWithPos(testCoord.posDown);
            }
        }
        return remaining;
    }

    private int getSpaceLeft(Position pos, Board board) {
        Coordinates coord = new Coordinates(pos);
        Coordinates testCoord = new Coordinates(coord.posLeft);
        int remaining = board.sizeY() - coord.y;

        for (int i = 0; i < 4; i++) {
            if (testCoord.posStatus == 2) {
                remaining = testCoord.y - coord.y;
                break;
            } else if (testCoord.posStatus == 1) {
                remaining = testCoord.y - coord.y;
                break;
            } else {
                testCoord = testCoord.setCoordWithPos(testCoord.posLeft);
            }
        }
        return remaining;
    }

    private int getSpaceRight(Position pos, Board board) {
        Coordinates coord = new Coordinates(pos);
        Coordinates testCoord = new Coordinates(coord.posRight);
        int remaining = board.sizeY() - coord.y;

        for (int i = 0; i < 4; i++) {
            if (testCoord.posStatus == 2) {
                remaining = testCoord.y - coord.y;
                break;
            } else if (testCoord.posStatus == 1) {
                remaining = testCoord.y - coord.y;
                break;
            } else {
                testCoord = testCoord.setCoordWithPos(testCoord.posRight);
            }
        }
        return remaining;
    }

}

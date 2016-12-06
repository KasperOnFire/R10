/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.interfaces;

/**
 *
 * @author Tobias
 * Edit Dec 2015 Peter Lorensen: 
 * -added JavaDoc comments
 */
public interface Board {

    /**
     * The x size of the board.
     *
     * @return int x the size of the board
     */
    public int sizeX();

    /**
     * The y size of the board.
     *
     * @return int y the size of the board
     */
    public int sizeY();

    /**
     * Places a ship on the board.
     * Please observe the following when placing ships:
     * -If a ship or just part of it is placed outside the board it is wrecked.
     * -A wreck stays on the board.
     * -If a ship is placed, even partly, on a wrecked it is completely wrecked.
     * -If a ship is placed, even partly,  on top of an other ship 
     * both are completely wrecked.
     * 
     * @param pos Position the position of the ship on the board.
     * @param ship Ship the ship to be placed. This must be taken from the 
     * Fleet given out and may not be created your self.
     * @param vertical boolean if true the ship is placed vertical (increase in y)
     * If false the ship is placed horizontal (increase in x value).
     */
    public void placeShip(Position pos, Ship ship, boolean vertical);
}

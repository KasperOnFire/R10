package battleship.interfaces;

/**
 *
 * @author Tobias
 * Edit Dec 2015 Peter Lorensen: 
 * -added JavaDoc comments
 */
public interface Fleet extends Iterable<Ship>
{
    /**
     * Returns the number of ships in the fleet.
     * As ships are wrecked they are taken out of the fleet.
     * 
     * @return int the number of ships in the fleet.
     */
    public int getNumberOfShips();
    
    /**
     * Returns a ship at the given index.
     * 
     * @param index int the index number for which
     * a ship is requested.
     * @return Ship the ship at the given index
     * or null if the index is greate than 
     * the number of ships -1.
     */
    public Ship getShip(int index);
}

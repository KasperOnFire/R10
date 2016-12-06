/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.implementations;

import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Tobias
 */
class BoardImpl implements Board, Fleet
{ 
    //Color constants (sets the color in the output area text):
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m"; 
    private static final String ANSI_CYAN = "\u001B[36m";

    //Symbol constants:
    private static final String SHIP_2 = "\u2781";
    private static final String SHIP_3 = "\u2782";
    private static final String SHIP_4 = "\u2783";
    private static final String SHIP_5 = "\u2784";
    private static final String WRECK_2 = "\u2777";
    private static final String WRECK_3 = "\u2778";
    private static final String WRECK_4 = "\u2779";
    private static final String WRECK_5 = "\u277A";
    private static final String SHOT = "\u2734";
    private static final String EMPTY = "\u2738";
    
    private final int sizeX;
    private final int sizeY;
    private final ShipImpl[][] board;
    private final boolean[][] shots;
    private final List<ShipImpl> liveShips;
    private Position lastShot;

    public BoardImpl(int sizeX, int sizeY)
    {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        board = new ShipImpl[sizeX][sizeY];
        shots = new boolean[sizeX][sizeY];
        liveShips = new LinkedList<>();
        lastShot = new Position(-1,-1);
    }

    @Override
    public int sizeX()
    {
        return sizeX;
    }

    @Override
    public int sizeY()
    {
        return sizeY;
    }

    @Override
    public void placeShip(Position pos, Ship ship, boolean vertical)
    {
        if(pos == null) return;
        int x = pos.x;
        int y = pos.y;
        if(!(ship instanceof ShipImpl)) return;
        ShipImpl sh = (ShipImpl) ship;
        if(sh.isPlaced()) return;
        sh.place();
        for(int i = 0; i < sh.size(); ++i)
        {
            if(x < 0 || x >= sizeX || y < 0 || y >= sizeY)
            {
                sh.wreck();
            }
            else
            {
                ShipImpl other = board[x][y];
                if(other != null)
                {
                    if(!other.isWreck())
                    {
                        other.wreck();
                        liveShips.remove(other);
                    }
                    sh.wreck();
                }
                board[x][y] = sh;
            }
            if(vertical)
            {
                ++y;
            }
            else
            {
                ++x;
            }
        }
        if(!sh.isWreck())
        {
            liveShips.add(sh);
        }
    }
    
    public boolean fire(Position pos)
    {
        if(pos == null || pos.x < 0 || pos.x >= sizeX || pos.y < 0 || pos.y >= sizeY)
        {
            return false;
        }
        if(shots[pos.x][pos.y])
        {
            return false;
        }
        lastShot = pos;
        shots[pos.x][pos.y] = true;
        ShipImpl ship = board[pos.x][pos.y];
        if(ship == null) return false;
        if(ship.isWreck()) return false;
        //We have a hit
        ship.hit();
        if(ship.isWreck())
        {
            liveShips.remove(ship);
        }
        return true;
    }

    @Override
    public int getNumberOfShips()
    {
        return liveShips.size();
    }

    @Override
    public Ship getShip(int index)
    {
        return liveShips.get(index);
    }
    
    @Override
    public Iterator<Ship> iterator()
    {
        return new ShipIterator(liveShips.iterator());
    }
    
    
    /* Old toString()
    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        for(int y = sizeY-1; y >= 0; --y)
        {
            for(int x = 0; x < sizeX; ++x)
            {
                if(shots[x][y])
                {
                    res.append('*');
                    continue;
                }
                ShipImpl ship = board[x][y];
                if(ship == null)
                {
                    res.append('~');
                }
                else
                {
                    if(ship.isWreck())
                    {
                        res.append('W');
                    }
                    else
                    {
                        res.append('S');
                    }
                }
            }
            res.append('\n');
        }
        return res.toString();
    }*/
    
    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        for(int y = sizeY-1; y >= 0; --y)
        {
            for(int x = 0; x < sizeX; ++x)
            {
                String color = ANSI_RED;
                if(x == lastShot.x && y == lastShot.y)
                {
                    color = ANSI_CYAN;
                }
                ShipImpl ship = board[x][y]; //getting ship info
                boolean isShot = shots[x][y]; //getting shot info
                if ( ship!= null ) //There is a ship
                {
                    if(ship.isWreck()) //There is a wreck here
                    {
                        res.append(ANSI_RED+shipSymbol(ship, isShot) );
                    }
                    else if( shots[x][y] ){  //There is a ship that is hit here
                        res.append( ANSI_RED+shipSymbol(ship, isShot) );
                    }
                    else  //There is a ship that is NOT hit here
                    {
                        res.append(ANSI_RESET+shipSymbol(ship, isShot) );
                    }
                }
                else //There is no ship, but maybe a shot or just water: 
                {
                    if(shots[x][y])  //there is a shot
                    {
                        if(x == lastShot.x && y == lastShot.y)
                        {
                            res.append(ANSI_CYAN+SHOT); //Missed shot
                        }
                        else
                        {
                            res.append(ANSI_RESET+SHOT); //Missed shot
                        }
                    }
                    else{ //just blue water...
                        res.append(ANSI_CYAN+EMPTY);
                    }
                    
                }
            }
            res.append('\n');
        }
        return res.toString();
    }
    
        /**
     * Changes the symbol of the ship for print out 
     * depending on if the ship is OK, shot or wrecked.
     */
    private String shipSymbol( ShipImpl ship, boolean isShot ){
        String res = SHIP_2;
        switch (ship.size()){
            case 2: 
                res = ship.isWreck() || isShot  ? WRECK_2 : SHIP_2;  
                break;
            case 3: 
                res = ship.isWreck() || isShot  ? WRECK_3 : SHIP_3;  
                break;
            case 4: 
                res = ship.isWreck() || isShot  ? WRECK_4 : SHIP_4;  
                break;
            case 5:
                res = ship.isWreck() || isShot  ? WRECK_5 : SHIP_5;  
                break;
            default:
                res = ship.isWreck() || isShot  ? WRECK_2 : SHIP_2;  
        }
            
        return res;
    }
   
    private class ShipIterator implements Iterator<Ship>
    {
        private final Iterator<ShipImpl> it;

        public ShipIterator(Iterator<ShipImpl> it)
        {
            this.it = it;
        }
        
        @Override
        public boolean hasNext()
        {
            return it.hasNext();
        }

        @Override
        public Ship next()
        {
            return it.next();
        }
        
    }
}

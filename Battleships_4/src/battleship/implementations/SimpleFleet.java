/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.implementations;

import battleship.interfaces.Fleet;
import battleship.interfaces.Ship;
import java.util.Iterator;

/**
 *
 * @author Tobias Grundtvig
 */
public class SimpleFleet implements Fleet
{
    private final int[] ships;

    public SimpleFleet(int[] ships)
    {
        //Defensive copy...
        this.ships = new int[ships.length];
        for(int i = 0; i < ships.length; ++i)
        {
            this.ships[i] = ships[i];
        }
    }
    
    @Override
    public int getNumberOfShips()
    {
        return ships.length;
    }

    @Override
    public Ship getShip(int index)
    {
        return new SimpleShip(ships[index]);
    }

    @Override
    public Iterator<Ship> iterator()
    {
        return new ShipIterator();
    } 
    
    private static class SimpleShip implements Ship
    {
        private final int size;

        public SimpleShip(int size)
        {
            this.size = size;
        }
        
        @Override
        public int size()
        {
            return size;
        }
    }
    
    private class ShipIterator implements Iterator<Ship>
    {
        private int index;

        public ShipIterator()
        {
            index = 0;
        }
        
        @Override
        public boolean hasNext()
        {
            return index < ships.length;
        }

        @Override
        public Ship next()
        {
            return getShip(index++);
        }   
    }
}

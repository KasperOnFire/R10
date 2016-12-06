/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package e1;

import battleship.interfaces.BattleshipsPlayer;
import tournament.player.PlayerFactory;

/**
 *
 * @author Tobias Grundtvig
 */
public class E1 implements PlayerFactory<BattleshipsPlayer>
{

    public E1(){}
    
    
    @Override
    public BattleshipsPlayer getNewInstance()
    {
        return new RandomPlayer();
    }

    @Override
    public String getID()
    {
        return "E1";
    }

    @Override
    public String getName()
    {
        return "Random player";
    }

    @Override
    public String[] getAuthors()
    {
        String[] res = {"Tobias Grundtvig"};
        return res;
    }
    
}

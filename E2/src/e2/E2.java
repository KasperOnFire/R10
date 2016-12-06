/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package e2;

import battleship.interfaces.BattleshipsPlayer;
import tournament.player.PlayerFactory;

/**
 *
 * @author Tobias Grundtvig
 */
public class E2 implements PlayerFactory<BattleshipsPlayer>
{
    public E2(){}
    
    
    @Override
    public BattleshipsPlayer getNewInstance()
    {
        return new SystematicShotPlayer();
    }

    @Override
    public String getID()
    {
        return "E2";
    }

    @Override
    public String getName()
    {
        return "Systematic shooter";
    }

    @Override
    public String[] getAuthors()
    {
        String[] res = {"Tobias Grundtvig"};
        return res;
    }
    
}

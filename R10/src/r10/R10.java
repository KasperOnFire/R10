/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r10;

import battleship.interfaces.BattleshipsPlayer;
import tournament.player.PlayerFactory;

public class R10 implements PlayerFactory<BattleshipsPlayer> {

    public R10() {
    }

    @Override
    public BattleshipsPlayer getNewInstance() {
        return new AI();
    }

    @Override
    public String getID() {
        return "R10";
    }

    @Override
    public String getName() {
        return "noobkiller";
    }

    @Override
    public String[] getAuthors() {
        String[] res = {"David Carl, Tjalfe Møller & Kasper Breindal"};
        return res;
    }

}

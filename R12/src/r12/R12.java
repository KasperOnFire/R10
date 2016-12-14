package r12;

import battleship.interfaces.BattleshipsPlayer;
import tournament.player.PlayerFactory;

public class R12 implements PlayerFactory<BattleshipsPlayer> {

    public R12() {
    }

    @Override
    public BattleshipsPlayer getNewInstance() {
        return new AI();
    }

    @Override
    public String getID() {
        return "R12";
    }

    @Override
    public String getName() {
        return "noobkiller 2.0";
    }

    @Override
    public String[] getAuthors() {
        String[] res = {"David Carl, Tjalfe Møller & Kasper Breindal"};
        return res;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.implementations;

import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.BattleshipsPlayer;
import tournament.ui.TournamentUI;

/**
 *
 * @author Tobias
 */
class ProxyPlayer implements BattleshipsPlayer
{

    private final BattleshipsPlayer player;
    private final Timer timer;

    public ProxyPlayer(BattleshipsPlayer player, String id, TournamentUI ui, boolean useTimer)
    {
        this.player = player;
        if (useTimer)
        {
            timer = new Timer(ui, id);
        } else
        {
            timer = null;
        }
    }

    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY)
    {
        if (timer != null)
        {
            timer.start();
            timer.entry("startMatch");
        }
        try
        {
            player.startMatch(rounds, ships, sizeX, sizeY);
        } catch (Exception e)
        {
        }
        if(timer != null)
        {
            timer.exit();
        }
    }

    @Override
    public void placeShips(Fleet fleet, Board board)
    {
        if (timer != null)
        {
            timer.entry("placeShips");
        }
        try
        {
            player.placeShips(new FleetProxy(fleet), board);
        } catch (Exception e)
        {

        }
        if(timer != null)
        {
            timer.exit();
        }
    }

    @Override
    public void incoming(Position pos)
    {
        if (timer != null)
        {
            timer.entry("incoming");
        }
        try
        {
            player.incoming(pos);
        } catch (Exception e)
        {

        }
        if(timer != null)
        {
            timer.exit();
        }
    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips)
    {
        if (timer != null)
        {
            timer.entry("getFireCoordinates");
        }
        try
        {
            return player.getFireCoordinates(new FleetProxy(enemyShips));
        } catch (Exception e)
        {
            return new Position(0, 0);
        } finally
        {
            if (timer != null)
            {
                timer.exit();
            }
        }
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips)
    {
        if (timer != null)
        {
            timer.entry("hitFeedback");
        }
        try
        {
            player.hitFeedBack(hit, new FleetProxy(enemyShips));
        } catch (Exception e)
        {

        }
        if (timer != null)
        {
            timer.exit();
        }
    }

    @Override
    public void startRound(int round)
    {
        if (timer != null)
        {
            timer.entry("startRound");
        }
        try
        {
            player.startRound(round);
        } catch (Exception e)
        {

        }
        if (timer != null)
        {
            timer.exit();
        }
    }

    @Override
    public void endRound(int round, int shots, int enemyShots)
    {
        if (timer != null)
        {
            timer.entry("endRound");
        }
        try
        {
            player.endRound(round, shots, enemyShots);
        } catch (Exception e)
        {

        }
        if (timer != null)
        {
            timer.exit();
        }
    }

    @Override
    public void endMatch(int won, int lost, int draw)
    {
        if (timer != null)
        {
            timer.entry("endMatch");
        }
        try
        {
            player.endMatch(won, lost, draw);
        } catch (Exception e)
        {

        }
        if (timer != null)
        {
            timer.exit();
            timer.stop();
        }
    }

}

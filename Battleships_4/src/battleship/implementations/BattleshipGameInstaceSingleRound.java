/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.implementations;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Position;
import tournament.game.GameInstance;
import tournament.game.GameResult;
import tournament.ui.TournamentUI;

/**
 *
 * @author pelo
 */
public class BattleshipGameInstaceSingleRound implements GameInstance<BattleshipsPlayer>
{

    private final int sizeX;
    private final int sizeY;
    private final int[] ships;
    private int shotsFiredA;
    private int shotsFiredB;
    private int shipWrecks_AtStart_A;
    private int shipWrecks_AtStart_B;

    public BattleshipGameInstaceSingleRound(int sizeX, int sizeY, int[] ships)
    {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.ships = ships;
    }

    @Override
    public GameResult run(BattleshipsPlayer playerA, String aID, BattleshipsPlayer playerB, String bID, TournamentUI ui,
                          boolean useTimer)
    {
        /*      SETUP        */
        //Wrap players in proxys to avoid exploits and exceptions...
        BattleshipsPlayer pA = new ProxyPlayer(playerA, aID, ui, useTimer);
        BattleshipsPlayer pB = new ProxyPlayer(playerB, bID, ui, useTimer);

        //Getting the fleet of ships and boards from the Battleships class:
        StartFleet fleetA = new StartFleet(ships);
        StartFleet fleetB = new StartFleet(ships);
        BoardImpl boardA = new BoardImpl(sizeX, sizeY);
        BoardImpl boardB = new BoardImpl(sizeX, sizeY);
        //Resetting points:
        int winsA = 0;
        int winsB = 0;

        /*      METHOD CALLS TO GET READY        */
        //Calling startMatch() on each players AI:
        //Making defensive copies of the ship size array
        pA.startMatch(1, new SimpleFleet(ships), sizeX, sizeY);
        pB.startMatch(1, new SimpleFleet(ships), sizeX, sizeY);

        //Calling startRound() for each player:
        pA.startRound(1);
        pB.startRound(1);
        //Calling placeShips() for each player:
        pA.placeShips(fleetA, boardA);
        pB.placeShips(fleetB, boardB);
        //Setting info to be used elsewere:  ships wrecked at start of the game for each player:
        shipWrecks_AtStart_A = ships.length - boardA.getNumberOfShips();
        shipWrecks_AtStart_B = ships.length - boardB.getNumberOfShips();

        /*      FIRING       */
        //Printing inital board:
        System.out.println("\n\n\n**************** PLAYER B's BOARD: **************** ");
        System.out.println(boardB.toString());
        //Remembering the original player B's board before A's firing:

        //Attack playerA
        System.out.println("****** All Player A's shots (on B's board): ******");
        int pointsA = fireAndPrintSession(pA, pB, boardB);
        shotsFiredA = 100 - pointsA;  //Setting amount of shots fired. To be printed elsewere.

        System.out.println("\n\n\n**************** PLAYER A's BOARD: **************** ");
        System.out.println(boardA.toString());
        //Attack playerA            
        System.out.println("****** All Player B's shots (on As board): ******");
        int pointsB = fireAndPrintSession(pB, pA, boardA);
        shotsFiredB = 100 - pointsB; //Setting amount of shots fired. To be printed elsewere.

        /*      FINISHING AND CALCULATING POINTS:        */
        //Calling endRound() for each player:
        pA.endRound(1 + 1, pointsA, pointsB);
        pB.endRound(1 + 1, pointsB, pointsA);

        //Figuring out points & draws:
        if(pointsA > pointsB)
        {
            ++winsA;
        }
        else if(pointsB > pointsA)
        {
            ++winsB;
        }
        int draws = 1 - winsA - winsB;

        //Calling endMatch() for each player:
        pA.endMatch(winsA, winsB, draws);
        pB.endMatch(winsB, winsA, draws);

        //Figuring out major wins for this round:
        int majorA = 0;
        int majorB = 0;
        if(winsA > winsB)
        {
            majorA = 1;
            majorB = -1;
        }
        else if(winsB > winsA)
        {
            majorA = -1;
            majorB = 1;
        }
        return new GameResult(majorA, winsA, majorB, winsB);
    }

    private int fireAndPrintSession(BattleshipsPlayer attacker, BattleshipsPlayer defender, BoardImpl board)
    {
        int maxShots = sizeX * sizeY;
        int shots = 0;
        while(board.getNumberOfShips() > 0 && shots < maxShots)
        {
            Position pos = attacker.getFireCoordinates(board);
            boolean hit = board.fire(pos);
            attacker.hitFeedBack(hit, board);
            defender.incoming(pos);
            ++shots;

            //Printing:
            System.out.println("Shoot number " + shots + ":");
            System.out.println(board.toString());
        }
        return maxShots - shots;
    }

    public int getShipWrecks_AtStart_A()
    {
        return shipWrecks_AtStart_A;
    }

    /*  GET'er and SET'ers   */
    public int getShipWrecks_AtStart_B()
    {
        return shipWrecks_AtStart_B;
    }

    public int getShotsFiredA()
    {
        return shotsFiredA;
    }

    public int getShotsFiredB()
    {
        return shotsFiredB;
    }

}

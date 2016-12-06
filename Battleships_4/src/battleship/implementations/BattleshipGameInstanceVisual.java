/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.implementations;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Position;
import tournament.game.GameResult;
import tournament.impl.simpleui.TextTournamentUI;
import tournament.player.PlayerFactory;
import tournament.ui.TournamentUI;

/**
 *
 * @author pelo
 */
public class BattleshipGameInstanceVisual
{

    private final int sizeX;
    private final int sizeY;
    private final int[] ships;
    private int shotsFiredA;
    private int shotsFiredB;
    private int shipWrecks_AtStart_A;
    private int shipWrecks_AtStart_B;

    public BattleshipGameInstanceVisual(int sizeX, int sizeY, int[] ships)
    {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.ships = ships;
    }

    public void run(PlayerFactory<BattleshipsPlayer> playerA, PlayerFactory<BattleshipsPlayer> playerB, int rounds)
    {
        /*      SETUP        */
        //Wrap players in proxys to avoid exploits and exceptions...
        TournamentUI ui = new TextTournamentUI();
        BattleshipsPlayer pA = new ProxyPlayer(playerA.getNewInstance(), playerA.getID(), ui, false);
        BattleshipsPlayer pB = new ProxyPlayer(playerB.getNewInstance(), playerB.getID(), ui, false);

        
        //Resetting points:
        int winsA = 0;
        int winsB = 0;
        
        //Calling startMatch() on each players AI:
        //Making defensive copies of the ship size array
        
        pA.startMatch(rounds, new SimpleFleet(ships), sizeX, sizeY);
        pB.startMatch(rounds, new SimpleFleet(ships), sizeX, sizeY);

        for (int round = 1; round <= rounds; ++round)
        {
            /*      METHOD CALLS TO GET READY        */
            //Getting the fleet of ships and boards from the Battleships class:
            StartFleet fleetA = new StartFleet(ships);
            StartFleet fleetB = new StartFleet(ships);
            BoardImpl boardA = new BoardImpl(sizeX, sizeY);
            BoardImpl boardB = new BoardImpl(sizeX, sizeY);
            
            //Calling startRound() for each player:
            pA.startRound(round);
            pB.startRound(round);
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
            shotsFiredA = sizeX * sizeY - pointsA;  //Setting amount of shots fired. To be printed elsewere.

            System.out.println("\n\n\n**************** PLAYER A's BOARD: **************** ");
            System.out.println(boardA.toString());
            //Attack playerA            
            System.out.println("****** All Player B's shots (on As board): ******");
            int pointsB = fireAndPrintSession(pB, pA, boardA);
            shotsFiredB = sizeX * sizeY - pointsB; //Setting amount of shots fired. To be printed elsewere.

            /*      FINISHING AND CALCULATING POINTS:        */
            //Calling endRound() for each player:
            pA.endRound(round, pointsA, pointsB);
            pB.endRound(round, pointsB, pointsA);

            //Figuring out points & draws:
            if (pointsA > pointsB)
            {
                ++winsA;
            } else if (pointsB > pointsA)
            {
                ++winsB;
            }

            System.out.println("--- Details: ---");
            System.out.println("");
            System.out.println("Shots fired:");
            System.out.println(shotsFiredA + ": " + playerA.getName() + ", (PlayerA).");
            System.out.println(shotsFiredB + ": " + playerB.getName() + ", (PlayerB).");
            System.out.println("");
            System.out.println("Ships wrecked before game start:");
            System.out.println(shipWrecks_AtStart_A + ": " + playerA.getName() + ", (PlayerA).");
            System.out.println(shipWrecks_AtStart_B + ": " + playerB.getName() + ", (PlayerB).");

        }
        int draws = rounds - winsA - winsB;
        //Calling endMatch() for each player:
        pA.endMatch(winsA, winsB, draws);
        pB.endMatch(winsB, winsA, draws);

        //Figuring out major wins for this round:
        int majorA = 0;
        int majorB = 0;
        if (winsA > winsB)
        {
            majorA = 1;
            majorB = -1;
        } else if (winsB > winsA)
        {
            majorA = -1;
            majorB = 1;
        }
        GameResult res = new GameResult(majorA, winsA, majorB, winsB);
        //Printing out end result:
        System.out.println(playerA.getName() + ": " + winsA + " wins.");
        System.out.println(playerB.getName() + ": " + winsB + " wins.");
        printWinner(res, playerA.getName(), playerB.getName());
    }

    /**
     * Helper method to print the winner in nice red color.
     *
     * @param res the GameResult
     * @param nameOfPlayerA String with name of the player
     * @param nameOfPlayerB String with name of the player
     */
    private void printWinner(GameResult res, String nameOfPlayerA, String nameOfPlayerB)
    {
        //Color constants (sets the color in the output area text):
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";

        String toPrint = "THE WINNER IS: "; //Creating a string
        toPrint += ANSI_RED; //Changing color
        if (res.majorPointsA > res.majorPointsB)  //Getting the correct winner
        {
            toPrint += nameOfPlayerA;
        } else if (res.majorPointsA < res.majorPointsB)
        {
            toPrint += nameOfPlayerB;
        } else
        {
            toPrint += "No one";
        }
        toPrint += ANSI_RESET;
        System.out.println(toPrint);
    }

    private int fireAndPrintSession(BattleshipsPlayer attacker, BattleshipsPlayer defender, BoardImpl board)
    {
        int maxShots = sizeX * sizeY;
        int shots = 0;
        while (board.getNumberOfShips() > 0 && shots < maxShots)
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

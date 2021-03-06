/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package e2;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Board;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Tobias
 */
public class SystematicShotPlayer implements BattleshipsPlayer
{
    private int nextX;
    private int nextY;

    private final static Random rnd = new Random();
    private int sizeX;
    private int sizeY;
    
    private int scoreMax = 0;
    private int scoreMin = 100;
    private int totalShotsFired = 0;

    private Position shot;

    private Fleet enemyFleet;

    private int enemyShipCount = 5;
    private ArrayList<Position> shotsFired = new ArrayList();
    private ArrayList<Position> potentialShotsWave1 = new ArrayList();
    private ArrayList<Position> potentialShotsWave2 = new ArrayList();
    private ArrayList<Position> potentialShipShots = new ArrayList();
    private ArrayList<Position> shotsHit = new ArrayList();
    private ArrayList<Position> board = new ArrayList();


    /**
     * The method called when its time for the AI to place ships on the board
     * (at the beginning of each round).
     *
     * The Ship object to be placed MUST be taken from the Fleet given (do not
     * create your own Ship objects!).
     *
     * A ship is placed by calling the board.placeShip(..., Ship ship, ...) for
     * each ship in the fleet (see board interface for details on placeShip()).
     *
     * A player is not required to place all the ships. Ships placed outside the
     * board or on top of each other are wrecked.
     *
     * @param fleet Fleet all the ships that a player should place.
     * @param board Board the board were the ships must be placed.
     */
    @Override
    public void placeShips(Fleet fleet, Board board) {
        sizeX = board.sizeX();
        sizeY = board.sizeY();

        boolean[] vertical = new boolean[5];
        Position[] pos = new Position[5];
        Ship[] s = new Ship[5];

        for (int i = 0; i < 5; i++) {
            s[i] = fleet.getShip(i);
        }

        ArrayList<String> placementArray = new ArrayList<>(); //Array of cordinates that the current ship ocupie
        HashMap<String, String> map = new HashMap<>(); //hashmap of cordinates ocupied by allready placed ships

        while (true) {
            map.clear(); //clears the map of cordinates in case of a re-run of the loop
            for (int i = 0; i < 5; i++) {
                int x = 0;
                int y = 0;
                while (true) { //runs until ship can be placed without taking up the same space as another ship
                    placementArray.clear(); //clears the array if cordinates so it's ready for next ship
                    vertical[i] = rnd.nextBoolean();

                    if (vertical[i]) {
                        x = rnd.nextInt(sizeX);
                        y = rnd.nextInt(sizeY - s[i].size() - 1);
                        pos[i] = new Position(x, y);
                    } else {
                        x = rnd.nextInt(sizeX - (fleet.getShip(i).size() - 1));
                        y = rnd.nextInt(sizeY);
                        pos[i] = new Position(x, y);
                    }

                    for (int j = 0; j <= s[i].size(); j++) { //writes the cordinates to placementArray
                        int tempX = 0;
                        int tempY = 0;
                        if (vertical[i]) {
                            tempX = x;
                            tempY = y + j;
                        } else {
                            tempX = x + j;
                            tempY = y;
                        }
                        placementArray.add(tempX + "," + tempY);
                    }
                    boolean retry = false;
                    for (String str : placementArray) {
                        if (map.containsKey(str)) {
                            retry = true;
                        }
                    }
                    if (!retry) {
                        for (String str : placementArray) {
                            map.put(str, str);
                        }
                        break;
                    }
                }
            }
            //check if okay else try again
            break;
        }

        for (int i = 0; i < 5; i++) { //place all ships
            board.placeShip(pos[i], s[i], vertical[i]);
        }
    }
    
    
    /**
     * Called every time the enemy has fired a shot.
     * 
     * The purpose of this method is to allow the AI to react to the 
     * enemy's incoming fire and place his/her ships differently next round.
     * 
     * @param pos Position of the enemy's shot 
     */
    @Override
    public void incoming(Position pos)
    {
        
        //Do nothing
    }

    /**
     * Called by the Game application to get the Position of your shot.
     *  hitFeedBack(...) is called right after this method.
     * 
     * @param enemyShips Fleet the enemy's ships. Compare this to the Fleet 
     * supplied in the hitFeedBack(...) method to see if you have sunk any ships.
     * 
     * @return Position of you next shot.
     */
    @Override
    public Position getFireCoordinates(Fleet enemyShips)
    {
        Position shot = new Position(nextX, nextY);
        ++nextX;
        if(nextX >= sizeX)
        {
            nextX = 0; 
            ++nextY;
            if(nextY >= sizeY)
            {
                nextY = 0;
            }
        }
        return shot;
    }
    
    
    /**
     * Called right after getFireCoordinates(...) to let your AI know if you hit
     * something or not. 
     * 
     * Compare the number of ships in the enemyShips with that given in 
     * getFireCoordinates in order to see if you sunk a ship.
     * 
     * @param hit boolean is true if your last shot hit a ship. False otherwise.
     * @param enemyShips Fleet the enemy's ships.
     */
    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips)
    {
        //Do nothing
    }
    
    
    /**
     * Called in the beginning of each match to inform about the number of 
     * rounds being played.
     * @param rounds int the number of rounds i a match
     */
    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY)
    {
        //Do nothing...
    }
    
    
    /**
     * Called at the beginning of each round.
     * @param round int the current round number.
     */
    @Override
    public void startRound(int round)
    {
        //Do nothing
    }
    
    
    /**
     * Called at the end of each round to let you know if you won or lost.
     * Compare your points with the enemy's to see who won.
     * 
     * @param round int current round number.
     * @param points your points this round: 100 - number of shot used to sink 
     * all of the enemy's ships. 
     *
     * @param enemyPoints int enemy's points this round. 
     */
    @Override
    public void endRound(int round, int points, int enemyPoints)
    {
        //Do nothing
    }

    
    /**
     * Called at the end of a match (that usually last 1000 rounds) to let you 
     * know how many losses, victories and draws you scored.
     * 
     * @param won int the number of victories in this match.
     * @param lost int the number of losses in this match.
     * @param draw int the number of draws in this match.
     */
    @Override
    public void endMatch(int won, int lost, int draw)
    {
        //Do nothing
    }
}

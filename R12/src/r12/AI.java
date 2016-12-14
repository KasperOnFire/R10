package r12;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Board;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class AI implements BattleshipsPlayer {

    private final static Random rnd = new Random();
    private int sizeX;
    private int sizeY;

    private int scoreMax = 0;
    private int scoreMin = 100;
    private int totalShotsFired = 0;

    private Position shot;
    private heatMapCalc heatCalc = new heatMapCalc();
    
    private int shotnumber;
    
    private Fleet enemyFleet;
    
    private int[][] enemyBoard = new int[sizeX][sizeX];
    private int[][] boardHeatMap = new int[sizeX][sizeX];
    private int[] ships = {1,2,1,1};
    
    private ArrayList<Position> shotsFired = new ArrayList();
    private ArrayList<Position> potentialShotsWave1 = new ArrayList();
    private ArrayList<Position> potentialShipShots = new ArrayList();
    private ArrayList<Position> shotsHit = new ArrayList();
    private ArrayList<Position> board = new ArrayList();
    private ArrayList<String> placementArray = new ArrayList<>(); //Array of cordinates that the current ship ocupie
    private HashMap<String, String> map = new HashMap<>(); //hashmap of cordinates ocupied by allready placed ships
    
       
    public AI() {
    }

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
 
        while (true) {
            map.clear(); //clears the map of cordinates in case of a re-run of the loop
            for (int i = 4; i >= 0; i--) {
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
                        x = rnd.nextInt(sizeX - s[i].size() - 1);
                        y = rnd.nextInt(sizeY);
                        pos[i] = new Position(x, y);
                    }
 
                    for (int j = 0; j <= s[i].size() + 2; j++) { //writes the cordinates to placementArray
                        int tempX = 0;
                        int tempY = 0;
 
                        if (vertical[i]) {
                            tempX = x;
                            tempY = y + j - 1;
                            //Creds Maria
                            addAroundVert(s[i].size(), tempX, tempY);
 
                        } else {
                            tempX = x + j - 1;
                            tempY = y;
                            //Creds Maria
                            addAroundHoriz(s[i].size(), tempX, tempY);
 
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
 
    //Creds til Maria
    private void addAroundVert(int size, int x, int y) {
        int tempX = x;
        int tempY = y;
 
        //place the single instances : for example on Horizontal, only one slot
        //the left and right of ship, but several above/below.
        if (y != 0 || y != 9) {
            placementArray.add(tempX + "," + (tempY - 1));
            placementArray.add(tempX + "," + (tempY + size + 1));
        } else if (y == 0) {
            placementArray.add(tempX + "," + (tempY + size + 1));
        } else if (y == 9) {
            placementArray.add(tempX + "," + (tempY - 1));
        }
 
        //places the positions to the sides of.
        for (int i = 0; i < size; i++) {
            if (x != 0 || x != 9) {
                placementArray.add((tempX + 1) + "," + (tempY + i));
                placementArray.add((tempX + 1) + "," + (tempY - i));
            } else if (x == 0) {
                placementArray.add((tempX + 1) + "," + (tempY + i));
            } else if (x == 9) {
                placementArray.add((tempX + 1) + "," + (tempY - i));
            }
        }
 
    }
 
    private void addAroundHoriz(int size, int x, int y) {
        int tempX = x;
        int tempY = y;
 
        //place the single instances : for example on Horizontal, only one slot
        //the left and right of ship, but several above/below.
        if (x != 0 || x != 9) {
            placementArray.add((tempX - 1) + "," + tempY);
            placementArray.add((tempX + size + 1) + "," + tempY);
        } else if (tempX == 0) {
            placementArray.add((tempX + size + 1) + "," + tempY);
        } else if (tempX == 9) {
            placementArray.add((tempX - 1) + "," + tempY);
        }
 
        //places the positions above and below.
        for (int i = 0; i < size; i++) {
            if (x != 0 || x != 9) {
                placementArray.add((tempX + i) + "," + (tempY + 1));
                placementArray.add((tempX + i) + "," + (tempY - 1));
            } else if (x == 0) {
                placementArray.add((tempX + i) + "," + (tempY + 1));
            } else if (x == 9) {
                placementArray.add((tempX + i) + "," + (tempY - 1));
            }
        }
    }

    /**
     * Called every time the enemy has fired a shot.
     *
     * The purpose of this method is to allow the AI to react to the enemy's
     * incoming fire and place his/her ships differently next round.
     *
     * @param pos Position of the enemy's shot
     */
    @Override
    public void incoming(Position pos) {
        //Do nothing
    }

    /**
     * Called by the Game application to get the Position of your shot.
     * hitFeedBack(...) is called right after this method.
     *
     * @param enemyShips Fleet the enemy's ships. Compare this to the Fleet
     * supplied in the hitFeedBack(...) method to see if you have sunk any
     * ships.
     *
     * @return Position of you next shot.
     */
    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        updateShipsAlive(enemyShips);
        shotnumber++;
        shot = new Position(0, 0);
        System.out.println(shotnumber);
        if(shotnumber < 6){
            shot = randomStartShoot();
            System.out.println(shot.x+" " + shot.y);

        }else{
            shot = heatMapShoot();
        }

        enemyBoard[shot.x][shot.y] = 1;
        return shot;
    }
    
    private void updateShipsAlive(Fleet enemyShips){
        ships = new int[]{0,0,0,0};
        for (int i = 0; i < enemyShips.getNumberOfShips()-1; i++) {
            int size = enemyShips.getShip(i).size();
            if(size == 2) {
                ships[0]++;
            }else if(size == 3){
                ships[1]++;
            }else if(size == 4){
                ships[2]++;
            }else if(size == 5){
                ships[3]++;
            }
        }
    }
    
    private Position randomStartShoot(){
        while(true){
            System.out.println(potentialShotsWave1.size());
            shot = potentialShotsWave1.get(rnd.nextInt(potentialShotsWave1.size()));
            System.out.println(shot.x+" " + shot.y);
            if(shot.x > 2 && shot.x < 7 && shot.y > 2 && shot.y < 7){
                potentialShotsWave1.remove(potentialShotsWave1.indexOf(shot));
                break;
            }
        }
        return shot;
    }
    
    private Position heatMapShoot(){
        boardHeatMap = heatCalc.getHeatMap(enemyBoard, ships);
        int x = 0;
        int y = 0;
        int value = 0;
        for (int tempX = 0; tempX < 10; tempX++) {
            for (int tempY = 0; tempY < 10; tempY++) {
                if(boardHeatMap[tempX][tempY] > value){
                    x = tempX;
                    y = tempY;
                    value = boardHeatMap[tempX][tempY];
                }
            }
        }
        return new Position(x, y);
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
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
       
    }

    /**
     * Called in the beginning of each match to inform about the number of
     * rounds being played.
     *
     * @param rounds int the number of rounds i a match
     */
    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {
        //Do nothing...
    }

    /**
     * Called at the beginning of each round.
     *
     * @param round int the current round number.
     */
    @Override
    public void startRound(int round) {
        shotnumber = 0;
        board.clear();
        shotsFired.clear();
        shotsHit.clear();
        potentialShipShots.clear();
        potentialShotsWave1.clear();
        enemyBoard = new int[10][10];
        
        //int offset = rnd.nextInt(1);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (((i + j) % 2 == 0)) {
                    shot = new Position(i, j);
                    potentialShotsWave1.add(shot);
                }
            }
        }
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
    public void endRound(int round, int points, int enemyPoints) {
        if (points > scoreMax) {
            scoreMax = points;
        }
        if (points < scoreMin) {
            scoreMin = points;
        }
        /*if(points == 100){
            System.exit(0);
        }*/
        totalShotsFired += 100 - points;
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
    public void endMatch(int won, int lost, int draw) {
        System.out.println("Max score: " + scoreMax);
        System.out.println("Min score: " + scoreMin);
        System.out.println("Total Shots: " + totalShotsFired);
        System.out.println("Avg: " + totalShotsFired / (won + lost + draw));

    }
}

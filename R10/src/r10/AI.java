package r10;
 
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
    private int amountOfShots = 1;
   
    private boolean findRest = false;
   
    private Position shot;
    private Position lasthit = new Position(0, 0);
    private Position calculateHit;// = new Position(0, 0);
 
    private Fleet enemyFleet;
   
   
    private int hits = 0;
    private int enemyShipCount = 5;
    private ArrayList<Position> shotsFired = new ArrayList();
    private ArrayList<Position> potentialShotsWave1 = new ArrayList();
    private ArrayList<Position> potentialShotsWave2 = new ArrayList();
    private ArrayList<Position> potentialShipShots = new ArrayList();
    private ArrayList<Position> shotsHit = new ArrayList();
    private ArrayList<Position> board = new ArrayList();
 

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
        if (!potentialShipShots.isEmpty()) { //checks if there is any potential shots. (spaces around a hit)
            shot = potentialShipShots.get(rnd.nextInt(potentialShipShots.size()));
            potentialShipShots.remove(shot);
            findRest = true;
        } else if (!potentialShotsWave1.isEmpty()) { // shoots in the specified pattern
            shot = potentialShotsWave1.get(rnd.nextInt(potentialShotsWave1.size()));
            potentialShotsWave1.remove(potentialShotsWave1.indexOf(shot));
            findRest = false;
        } else if (!potentialShotsWave2.isEmpty()) { //next wave in the pattern
            shot = potentialShotsWave2.get(rnd.nextInt(potentialShotsWave2.size()));
            potentialShotsWave2.remove(potentialShotsWave2.indexOf(shot));
            findRest = false;
        } else {
            shot = board.get(rnd.nextInt(board.size()));
            findRest = false;
        }
 
        //FIX DEN SKYDER DEN SAMME POS
       
        shotsFired.add(shot);
        System.out.println("PotShipShots: " + potentialShipShots.size());
        for (Position potShip : potentialShipShots) {
            System.out.println("X: " + potShip.x + " Y: " + potShip.y);
        }
        System.out.println("Wave1: " + potentialShotsWave1.size());
        System.out.println("wave2: " + potentialShotsWave2.size());
        System.out.println("O'Shit: " + board.size());
        System.out.println("Shot at x: " + shot.x + " y: " + shot.y);        
        return shot;
    }
   
    //not used
    private Position shootPotential(Position shot) {
        Coordinates shotCoord = new Coordinates(shot);
        Position returnShot;
 
        if (shotCoord.getPosUp() != null) {
            returnShot = shotCoord.getPosUp();
            shotCoord.removePosUp();
            return returnShot;
        } else if (shotCoord.getPosDown() != null) {
            returnShot = shotCoord.getPosDown();
            shotCoord.removePosDown();
            return returnShot;
        } else if (shotCoord.getPosLeft() != null) {
            returnShot = shotCoord.getPosLeft();
            shotCoord.removePosLeft();
            return returnShot;
        } else if (shotCoord.getPosRight() != null) {
            returnShot = shotCoord.getPosRight();
            shotCoord.removePosRight();
            return returnShot;
        }
        return null;
    }
   
    private Position randomStartShoot(){
        while(true){
            shot = potentialShotsWave1.get(rnd.nextInt(potentialShotsWave1.size()));
            if(shot.x < 2 && shot.x > 7 && shot.y < 2 && shot.y > 7){
                potentialShotsWave1.remove(potentialShotsWave1.indexOf(shot));
                break;
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
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        enemyFleet = enemyShips;
        int fleetSize = enemyFleet.getNumberOfShips();

        Coordinates infoShot = new Coordinates(shot);

        if (hit) {
            shotsHit.add(shot);
            if (fleetSize == enemyShipCount) {
                potentialShipShots.add(infoShot.getPosUp());
                potentialShipShots.add(infoShot.getPosDown());
                potentialShipShots.add(infoShot.getPosLeft());
                potentialShipShots.add(infoShot.getPosRight());
                for (Position potPos : potentialShipShots) {
                    if (potPos.x > 9 || potPos.x < 0 || potPos.y > 9 || potPos.y < 0) {
                        potentialShipShots.remove(potentialShipShots.indexOf(potPos));
                    }
                    for (Position firedPos : shotsFired) {
                        if (firedPos == potPos) {
                            potentialShipShots.remove(potentialShipShots.indexOf(potPos));
                        }
                    }
                    for (int i = 0; i < potentialShotsWave1.size(); i++) {
                        if (potPos == potentialShotsWave1.get(i)) {
                            potentialShotsWave1.remove(i);
                        }
                    }
                    for (int i = 0; i < potentialShotsWave2.size(); i++) {
                        if (potPos == potentialShotsWave2.get(i)) {
                            potentialShotsWave2.remove(i);
                        }
                    }
                }
            }
        }
        enemyShipCount = fleetSize;
    }
   
        public void cleanShots(){
            for (Position potPos : potentialShipShots) {
                if (potPos.x > 9 || potPos.x < 0 || potPos.y > 9 || potPos.y < 0) {
                    potentialShipShots.remove(potentialShipShots.indexOf(potPos));
                        System.out.println("Removed OM pos: " + potPos);
                }
                for (Position firedPos : shotsFired) {
                    if(firedPos.x == potPos.x && firedPos.y == potPos.y){
                        potentialShipShots.remove(potentialShipShots.indexOf(potPos));
                        System.out.println("Removed AS pos: " + potPos);
                    }
                }
                for (Position wave1Pos : potentialShotsWave1) {
                    if (potPos.x == wave1Pos.x && potPos.y == wave1Pos.y) {
                        System.out.println("pik999");
                        potentialShotsWave1.remove(potentialShotsWave1.indexOf(potPos));
                        System.out.println("Removed W1 pos: " + potPos);
                    }
                }
 
                for (Position wave2Pos : potentialShotsWave2) {
                    if (potPos.x == wave2Pos.x && potPos.y == wave2Pos.y) {
                        System.out.println("pik888");
                        potentialShotsWave2.remove(potentialShotsWave2.indexOf(potPos));
                        System.out.println("Removed W2 pos: " + potPos);
                    }
                }
 
                for (Position boardP : board) {
                    if (potPos.equals(boardP)) {
                        board.remove(board.indexOf(potPos));
                        System.out.println("Removed boardP pos: " + potPos);
                    }
                }
            }
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
        //add potential shots to arraylist
        Position shot;
       
        hits = 0;
        board.clear();
        shotsFired.clear();
        shotsHit.clear();
        potentialShipShots.clear();
        potentialShotsWave1.clear();
        potentialShotsWave2.clear();
       
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                shot = new Position(i, j);
                board.add(shot);
 
            }
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (((i + j) % 4 == 0)) {
                    shot = new Position(i, j);
                    potentialShotsWave1.add(shot);
                }
            }
        }
 
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (((i + j) % 2 == 0)) {
                    shot = new Position(i, j);
                    potentialShotsWave2.add(shot);
                }
            }
        }
        //fjerner dobbeltgængere
        for (Position pos : potentialShotsWave1) {
            potentialShotsWave2.remove(potentialShotsWave2.indexOf(pos));
        }
       
        for (Position boardShotsRemove : board) {
            board.remove(potentialShotsWave2.indexOf(boardShotsRemove));
            board.remove(potentialShotsWave1.indexOf(boardShotsRemove));
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
        if(points > scoreMax){
            scoreMax = points;            
        }
        if(points < scoreMin){
            scoreMin = points;            
        }
       
        if(enemyPoints > points){
            System.out.println("Enemy win!");
        }else{
            System.out.println("Player win!");
        }
        /*if(points == 100){
            System.exit(0);
        }*/
        totalShotsFired += 100-points;
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
        System.out.println("Max score: "+ scoreMax);
        System.out.println("Min score: "+ scoreMin);
        System.out.println("Total Shots: "+ totalShotsFired);
        System.out.println("Avg: "+ totalShotsFired/(won+lost+draw));
       
    }
}
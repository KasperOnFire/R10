package battleshipstournament;

import battleship.implementations.BattleshipGameInstanceVisual;
import battleship.interfaces.BattleshipsPlayer;
import tournament.player.PlayerFactory;

/**
 *
 * @author Tobias Grundtvig & Peter Lorensen
 */
public class SingleRoundVisualizer
{
    public static void main(String[] args)
    {
        /*******  OVERWRITE AI HERE (with your own):  *******/
        //PlayerFactory<BattleshipsPlayer> playerAFactory = Loader.loadPlayer("D:\\BattleshipsTest\\R4.jar", "r4.R4");
        PlayerFactory<BattleshipsPlayer> playerAFactory = Loader.loadPlayer("../AIjars/R12.jar", "r12.R12");
        PlayerFactory<BattleshipsPlayer> playerBFactory = Loader.loadPlayer("../AIjars/R10.jar", "r10.R10");
          
        runGame(playerAFactory, playerBFactory, 1);
    }
    
    public static void runGame(PlayerFactory<BattleshipsPlayer> playerA, PlayerFactory<BattleshipsPlayer> playerB, int rounds)
    {
        //Initializing and getting the GameInstance that runs the game:
        int[] ships = {2,3,3,4,5};
        
        BattleshipGameInstanceVisual verbInstance = new BattleshipGameInstanceVisual(10, 10, ships);
        verbInstance.run( playerA, playerB, rounds);  //Running the game 
    }
    
    
}

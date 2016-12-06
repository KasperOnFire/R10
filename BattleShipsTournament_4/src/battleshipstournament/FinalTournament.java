package battleshipstournament;

import battleship.implementations.Battleships;
import battleship.interfaces.BattleshipsPlayer;
import java.util.ArrayList;
import java.util.Collection;
import tournament.Tournament;
import tournament.impl.simpleui.TextTournamentUI;
import tournament.player.PlayerFactory;



/**
 *
 * @author Tobias Grundtvig
 */
public class FinalTournament
{
    public static void main(String[] args) throws Exception
    {
        String path = "../FinalTournamentAIs";
        Loader loader = new Loader(path);
        
        Collection<PlayerFactory<BattleshipsPlayer>> allAIs = new ArrayList<>();
        
        //Loading examples
        Collection<PlayerFactory<BattleshipsPlayer>> examples = loader.loadCategory("E", 100);
        allAIs.addAll(examples);
        
        //Loading green league
        Collection<PlayerFactory<BattleshipsPlayer>> green = loader.loadCategory("G", 100);
        allAIs.addAll(green);
        
        //Loading yellow league
        Collection<PlayerFactory<BattleshipsPlayer>> yellow = loader.loadCategory("Y", 100);
        allAIs.addAll(yellow);
        
        //Loading red league
        Collection<PlayerFactory<BattleshipsPlayer>> red = loader.loadCategory("R", 100);
        allAIs.addAll(red);
        
        //Loading teachers AIs
        Collection<PlayerFactory<BattleshipsPlayer>> x = loader.loadCategory("X", 100);
        allAIs.addAll(x);
        
        //Loading tutors AIs
        Collection<PlayerFactory<BattleshipsPlayer>> t = loader.loadCategory("T", 100);
        allAIs.addAll(t);
        
        //Add examples to colored leagues...
        green.addAll(examples);
        yellow.addAll(examples);
        red.addAll(examples);
        
        System.out.println("\n\n\nFinal tournament: Green league...");
        TextTournamentUI.turnOffIO();
        Tournament.run(Battleships.getGameFactory(), green, true);
        TextTournamentUI.turnOnIO();
      
        System.out.println("\n\n\nFinal tournament: Yellow league...");
        TextTournamentUI.turnOffIO();
        Tournament.run(Battleships.getGameFactory(), yellow, true);
        TextTournamentUI.turnOnIO();
        
        System.out.println("\n\n\nFinal tournament: Red league...");
        TextTournamentUI.turnOffIO();
        Tournament.run(Battleships.getGameFactory(), red, true);
        TextTournamentUI.turnOnIO();
         
        System.out.println("\n\n\nFinal tournament: Total war...");
        TextTournamentUI.turnOffIO();
        Tournament.run(Battleships.getGameFactory(), allAIs, true);
        TextTournamentUI.turnOnIO();
        System.out.println("Goodbye...");
    }
}

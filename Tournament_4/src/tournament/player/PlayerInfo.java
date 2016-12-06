package tournament.player;

/**
 *
 * @author Tobias
 * Edit Dec 2015 Peter Lorensen: 
 * -added JavaDoc comments
 */
 
public interface PlayerInfo
{
    /**
     * The ID of your AI. 
     * NV: Must be unique in the tournament!
     * 
     * Example:
     * return "r12";
     * 
     * @return String the ID of your AI.
     */
    public String getID();
    
    
    /**
     * The name of your AI.
     * Can be any String.
     * 
     * Example:
     * return "Systematic Shooter";
     * 
     * @return String the name of your AI.
     */
    public String getName();
    
    /**
     * The name of the authors of the AI.
     * 
     * Example:
     * String[] res = {"Tobias Grundtvig", "Peter Lorensen"};
     * return res;
     * 
     * @return String[] the names of the authors.
     */
    public String[] getAuthors();
}

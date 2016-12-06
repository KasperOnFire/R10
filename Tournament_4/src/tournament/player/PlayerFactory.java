package tournament.player;

/**
 *
 * @author Tobias
 * 
 * @param <PlayerType>
 */
public interface PlayerFactory<PlayerType> extends PlayerInfo
{
    public PlayerType getNewInstance();
}

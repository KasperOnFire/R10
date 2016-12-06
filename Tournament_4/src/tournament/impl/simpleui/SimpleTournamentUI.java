/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tournament.impl.simpleui;

import java.util.List;
import tournament.impl.MatchInfo;
import tournament.impl.ParticipantInfo;
import tournament.ui.TournamentUI;

/**
 *
 * @author Tobias
 */
public class SimpleTournamentUI implements TournamentUI
{
    private MatchInfo[] matches;
    
    @Override
    public synchronized void tournamentReady(String gameName, ParticipantInfo[] participants, MatchInfo[] matches)
    {
        this.matches = matches;
        System.out.println(gameName + " Tournament!");
        System.out.println("****************");
        System.out.println("* Participants *");
        System.out.println("****************");
        for(ParticipantInfo pi : participants)
        {
            System.out.println(pi.getID() + " - " + pi.getName());
        }
        System.out.println("****************\n");
    }

    @Override
    public synchronized void matchStarted(int matchIndex)
    {
        MatchInfo mi = matches[matchIndex];
        System.out.println("Match started: ");
        System.out.println( mi.getParticipantA().getID() + " - " + mi.getParticipantA().getName() + " vs " +
                            mi.getParticipantB().getID() + " - " + mi.getParticipantB().getName());
    }

    @Override
    public synchronized void matchFinished(int matchIndex)
    {
        MatchInfo mi = matches[matchIndex];
        System.out.println("Match finished: ");
        System.out.println( mi.getParticipantA().getID() + " - " + mi.getParticipantA().getName() + " vs " +
                            mi.getParticipantB().getID() + " - " + mi.getParticipantB().getName());
        System.out.println("Result: " + mi.getResult());
        
    }

    @Override
    public synchronized void tournamentFinished(List<ParticipantInfo> sortedParticipants)
    {
        System.out.println("Tournament finished! Results: ");
        for(ParticipantInfo p : sortedParticipants)
        {
            System.out.println(p.getID() + " - " + p.getName() + " : " + p.getMajorPoints() + " / " + p.getMinorPoints());
        }
    }

    @Override
    public void timeoutOccurred(String id, String method)
    {
        System.out.println("******************************************************");
        System.out.println("TIMEOUT: " + id + " in method " + method);
        System.out.println("******************************************************");
    }

    @Override
    public void error(String s)
    {
        System.out.println("ERROR: " + s);
    }

    @Override
    public void timeConsumedReport(String id, long milliseconds)
    {
        System.out.println("*******************************************************************");
        System.out.println("TIME CONSUMED: " + id + " : " + (milliseconds / 60000) + " minutes.");
        System.out.println("*******************************************************************");
    }
    
}

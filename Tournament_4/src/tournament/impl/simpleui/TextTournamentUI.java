/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tournament.impl.simpleui;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import tournament.impl.MatchInfo;
import tournament.impl.ParticipantInfo;
import tournament.ui.TournamentUI;

/**
 *
 * @author Tobias
 */
public class TextTournamentUI implements TournamentUI
{
    private static Random rnd = new Random();
    private static InputStreamReader input;
    private static final InputStream in;
    private static final PrintStream out;
    private static final PrintStream err;
    private static final PrintStream dummyOut;
    private static final InputStream dummyIn;
    private MatchInfo[] matches;
    private int matchesStarted;
    private int matchesEnded;
    
    static 
    {
        in = System.in;
        out = System.out;
        err = System.err;
        input = new InputStreamReader(in);
        dummyOut = new PrintStream(new OutputStream()
        {

            @Override
            public void write(int b) throws IOException
            {
                //Do nothing...
            }
        });
        dummyIn = new InputStream()
        {

            @Override
            public int read() throws IOException
            {
                return -1;
            }
        };
        
    }
    
    public static void turnOffIO()
    {
        System.setIn(dummyIn);
        System.setOut(dummyOut);
        System.setErr(dummyOut);
    }
    
    public static void turnOnIO()
    {
        System.setIn(in);
        System.setOut(out);
        System.setErr(err);
    }
    
    public static void print(String s)
    {
        out.print(s);
    }
    
    public static void println(String s)
    {
        out.println(s);
    }

    public TextTournamentUI()
    {
    }
    
    
    @Override
    public synchronized void tournamentReady(String gameName, ParticipantInfo[] participants, MatchInfo[] matches)
    {
        this.matchesStarted = 0;
        this.matchesEnded = 0;
        this.matches = matches;
        /*for(int i = 0; i < 100; ++i)
        {
            out.println("");
        }*/
        out.println("\n" + gameName + " Tournament!");
        out.println("Number of participants: " + participants.length);
        out.println("Number of matches: " + matches.length);
        out.println("****************");
        out.println("* Participants *");
        out.println("****************");
        for(ParticipantInfo pi : participants)
        {
            out.println(pi.getID() + " - " + pi.getName());
        }
        out.println("****************\n");
        out.print("Press ENTER to start tournament...");
        waitForKeyPress();
    }

    @Override
    public synchronized void matchStarted(int matchIndex)
    {
        ++matchesStarted;
        MatchInfo mi = matches[matchIndex];
        out.println("Match started: " + matchesStarted + "/" + matches.length);
        out.println( mi.getParticipantA().getID() + " - " + mi.getParticipantA().getName() + " vs " +
                            mi.getParticipantB().getID() + " - " + mi.getParticipantB().getName() + "\n");
    }

    @Override
    public synchronized void matchFinished(int matchIndex)
    {
        ++matchesEnded;
        MatchInfo mi = matches[matchIndex];
        out.println("Match finished: " + matchesEnded + "/" + matches.length);
        out.println( mi.getParticipantA().getID() + " - " + mi.getParticipantA().getName() + " vs " +
                            mi.getParticipantB().getID() + " - " + mi.getParticipantB().getName() + "\n");
        //out.println("Result: " + mi.getResult());
        
    }

    @Override
    public synchronized void tournamentFinished(List<ParticipantInfo> sortedParticipants)
    {
        for(int i = 0; i < 100; ++i)
        {
            out.println("");
        }
        out.println("Tournament finished! Press ENTER to see rankings...");
        int place = sortedParticipants.size();
        int index = 0;
        while(place > 2)
        {
            ParticipantInfo p = sortedParticipants.get(index);
            waitForKeyPress();
            out.print("Place " + place + ": " + p.getID() + " - " + p.getName() + " : " + p.getMajorPoints() + " / " + p.getMinorPoints());
            --place;
            ++index;
        }
        ParticipantInfo p2 = sortedParticipants.get(index);
        ++index;
        ParticipantInfo p1 = sortedParticipants.get(index);
        ParticipantInfo s1 = p1;
        ParticipantInfo s2 = p2;
        if(rnd.nextBoolean())
        {
            s1 = p2;
            s2 = p1;
        }
        waitForKeyPress();
        out.println("\n\n*** Now we have only two left: (in random order) ***\n");
        out.println(s1.getID() + " - " + s1.getName());
        out.println("\n" + s2.getID() + " - " + s2.getName());
        out.print("\n\n*** Press ENTER to see who wins!!! ***");
        waitForKeyPress();
        out.println("\nFirst place: " + p1.getID() + " - " + p1.getName() + " : " + p1.getMajorPoints() + " / " + p1.getMinorPoints());
        out.println("\nSecond place: " + p2.getID() + " - " + p2.getName() + " : " + p2.getMajorPoints() + " / " + p2.getMinorPoints());
        out.println("\n\n\n\nThank you for using the tournament system.");
        out.println("\nPress ENTER to continue...");
        waitForKeyPress();
        for(int i = 0; i < 100; ++i)
        {
            out.println("");
        }
    }
    
    private void waitForKeyPress()
    {
        try
        {
            while(input.read() != '\n'){}
        } catch (IOException ex)
        {
            Logger.getLogger(TextTournamentUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void timeoutOccurred(String id, String method)
    {
        out.println("******************************************************");
        out.println("TIMEOUT: " + id + " in method " + method);
        out.println("******************************************************");
    }
    
    @Override
    public void timeConsumedReport(String id, long milliseconds)
    {
        out.println("*******************************************************************");
        out.println("TIME CONSUMED: " + id + " : " + (milliseconds / 60000) + " minutes.");
        out.println("*******************************************************************");
    }
    
    @Override
    public void error(String s)
    {
        out.println("ERROR: " + s);
    }
}

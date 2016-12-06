/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import battleship.interfaces.Position;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import r10.Coordinates;

/**
 *
 * @author Kasper
 */
public class CoordinatesTest {

    public CoordinatesTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testPosUp() {
        //Positive test
        System.out.println("test pos up");
        Position test = new Position(4, 4);
        Coordinates testCoord = new Coordinates(test);

        String result = testCoord.getPosUp().toString();
        String expResult = "(4, 5)";

        assertEquals(expResult, result);
        System.out.println("testPosUp Passed");
    }
}

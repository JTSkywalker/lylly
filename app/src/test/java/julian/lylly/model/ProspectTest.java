package julian.lylly.model;

import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.Before;
import java.util.ArrayList;
import java.util.List;

import static julian.lylly.model.Prospect.*;

import static org.junit.Assert.*;

/**
 * Created by VAIO on 21.11.2015.
 */
public class ProspectTest {

    @Before
    public void Before() {
        List<Integer> weights = new ArrayList<>();
        weights.add(4);
        weights.add(3);
        weights.add(3);
        weights.add(6);
        start = new LocalDate(2115,11,10);
        end = new LocalDate(2115,11,14);
        Duration min = new Duration(1000*9);
        Duration max = new Duration(1000*13);
        prospect1 = new Prospect("test", new Tag("tag"),
            start, end, min, max, weights);
        date1 = new LocalDate(2115,11,11);
        duration1 = new Duration(1000);
        durationMinPlus = min.plus(1000);
        durationMaxPlus = max.plus(1000 * 4);
    }

    Prospect prospect1;
    LocalDate start, end, date1;
    Duration duration1, durationMinPlus, durationMaxPlus;

    @Test
    public void testGetBudgets() {
        List<Pair<Duration,Duration>> result =
                prospect1.getBudgets(date1, duration1);
        List<Pair<Duration,Duration>> expRes = new ArrayList<>();
        expRes.add(new Pair<>(new Duration(1000*2), new Duration(1000*3)));
        expRes.add(new Pair<>(new Duration(1000*2), new Duration(1000*3)));
        expRes.add(new Pair<>(new Duration(1000*4), new Duration(1000*6)));
        assertEquals(expRes, result);
    }

    @Test
    public void testGetBudgetsPointerEqStart() {
        prospect1.getBudgets(start, duration1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBudgetsPointerEqDecrStart() {
        prospect1.getBudgets(start.minusDays(1), duration1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBudgetsPointerEqEnd() {
        prospect1.getBudgets(end, duration1);
    }
    @Test
    public void testGetBudgetsPointerEqDecrEnd() {
        prospect1.getBudgets(end.minusDays(1), duration1);
    }

    @Test
    public void testGetBudgetsMinPlus() {
        ArrayList<Integer> weights = new ArrayList<>();
        weights.add(2);
        weights.add(2);
        weights.add(2);
        weights.add(2);
        prospect1.setWeights(weights);
        List<Pair<Duration,Duration>> result =
                prospect1.getBudgets(date1, durationMinPlus);
        List<Pair<Duration,Duration>> expRes = new ArrayList<>();
        expRes.add(new Pair<>(new Duration(0), new Duration(1000)));
        expRes.add(new Pair<>(new Duration(0), new Duration(1000)));
        expRes.add(new Pair<>(new Duration(0), new Duration(1000)));
        assertEquals(expRes, result);
    }

    @Test
    public void testGetBudgetsMaxPlus() {
        List<Pair<Duration,Duration>> result =
                prospect1.getBudgets(date1, durationMaxPlus);
        List<Pair<Duration,Duration>> expRes = new ArrayList<>();
        expRes.add(new Pair<>(new Duration(0), new Duration(0)));
        expRes.add(new Pair<>(new Duration(0), new Duration(0)));
        expRes.add(new Pair<>(new Duration(0), new Duration(0)));
        assertEquals(expRes, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckNameConstraint() {
        checkNameConstraint(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckTagConstraint() {
        checkTagConstraint(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckStartEndConstraintEq() {
        LocalDate start = new LocalDate(2002,4,7);
        LocalDate end   = new LocalDate(2002,4,7);
        checkStartEndConstraint(start, end);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckStartEndConstraintGrY() {
        LocalDate start = new LocalDate(2003,4,7);
        LocalDate end   = new LocalDate(2002,4,7);
        checkStartEndConstraint(start, end);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckStartEndConstraintGrM() {
        LocalDate start = new LocalDate(2002,10,7);
        LocalDate end   = new LocalDate(2002,4,7);
        checkStartEndConstraint(start, end);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckStartEndConstraintGrD() {
        LocalDate start = new LocalDate(2002,4,9);
        LocalDate end   = new LocalDate(2002,4,7);
        checkStartEndConstraint(start, end);
    }

    @Test
    public void testCheckStartEndConstraintLeY() {
        LocalDate start = new LocalDate(2001,4,7);
        LocalDate end   = new LocalDate(2002,4,7);
        checkStartEndConstraint(start, end);
    }

    @Test
    public void testCheckStartEndConstraintLeM() {
        LocalDate start = new LocalDate(2002,4,7);
        LocalDate end   = new LocalDate(2002,9,7);
        checkStartEndConstraint(start, end);
    }

    @Test
    public void testCheckStartEndConstraintLeD() {
        LocalDate start = new LocalDate(2002,4,7);
        LocalDate end   = new LocalDate(2002,4,27);
        checkStartEndConstraint(start, end);
    }

    @Test
    public void testCheckMinMaxConstraintEq() {
        Duration min = new Duration(42);
        Duration max = new Duration(42);
        checkMinMaxConstraint(min, max);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckMinMaxConstraintGr() {
        Duration min = new Duration(77);
        Duration max = new Duration(42);
        checkMinMaxConstraint(min, max);
    }

    @Test
    public void testCheckMinMaxConstraintLe() {
        Duration min = new Duration(42);
        Duration max = new Duration(100);
        checkMinMaxConstraint(min, max);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckWeightsConstraintNeg() {
        LocalDate start = new LocalDate(2000,11,22);
        LocalDate end   = new LocalDate(2000,10,22);
        ArrayList<Integer> weights = new ArrayList<>();
        weights.add(1);
        weights.add(1);
        checkWeightsConstraint(start, end, weights);
    }

    @Test
    public void testCheckWeightsConstraintEmptyOK() {
        LocalDate start = new LocalDate(2000,11,22);
        LocalDate end   = new LocalDate(2000,11,22);
        ArrayList<Integer> weights = new ArrayList<>();
        checkWeightsConstraint(start, end, weights);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckWeightsConstraintEmptyFail() {
        LocalDate start = new LocalDate(2000,11,22);
        LocalDate end   = new LocalDate(2000,11,23);
        ArrayList<Integer> weights = new ArrayList<>();
        checkWeightsConstraint(start, end, weights);
    }

    @Test
    public void testCheckWeightsConstraintEmpty1OK() {
        LocalDate start = new LocalDate(2000,11,22);
        LocalDate end   = new LocalDate(2000,11,23);
        ArrayList<Integer> weights = new ArrayList<>();
        weights.add(42);
        checkWeightsConstraint(start, end, weights);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckWeightsConstraintTooLow() {
        LocalDate start = new LocalDate(2000,11,22);
        LocalDate end   = new LocalDate(2000,11,27);
        ArrayList<Integer> weights = new ArrayList<>();
        weights.add(42);
        weights.add(7);
        checkWeightsConstraint(start, end, weights);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckWeightsConstraintTooHigh() {
        LocalDate start = new LocalDate(2000,11,22);
        LocalDate end   = new LocalDate(2000,11,23);
        ArrayList<Integer> weights = new ArrayList<>();
        weights.add(42);
        weights.add(42);
        checkWeightsConstraint(start, end, weights);
    }

    @Test
    public void testCheckWeightsConstraintOk() {
        LocalDate start = new LocalDate(2000,11,22);
        LocalDate end   = new LocalDate(2000,11,25);
        ArrayList<Integer> weights = new ArrayList<>();
        weights.add(42);
        weights.add(42);
        weights.add(42);
        checkWeightsConstraint(start, end, weights);
    }

    @Test
    public void testCheckWeightsConstraintOkYear() {
        LocalDate start = new LocalDate(2000,11,22);
        LocalDate end   = new LocalDate(2001,11,22);
        ArrayList<Integer> weights = new ArrayList<>();
        for(int i=0; i<365; i++) {
            weights.add(i);
        }
        checkWeightsConstraint(start, end, weights);
    }

    @Test
    public void testCheckWeightsConstraintOkMonth() {
        LocalDate start = new LocalDate(2000,11,22);
        LocalDate end   = new LocalDate(2000,12,22);
        ArrayList<Integer> weights = new ArrayList<>();
        for(int i=0; i<30; i++) {
            weights.add(i);
        }
        checkWeightsConstraint(start, end, weights);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckWeightsConstraintFailMonth() {
        LocalDate start = new LocalDate(2000,11,22);
        LocalDate end   = new LocalDate(2001,12,23);
        ArrayList<Integer> weights = new ArrayList<>();
        weights.add(7);
        checkWeightsConstraint(start, end, weights);
    }
}
package julian.lylly.model;

import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by VAIO on 21.11.2015.
 */
public class ProspectTest {

    @Test
    public void testGetBudgets() {
        List<Integer> weights = new ArrayList<>();
        weights.add(3);
        weights.add(3);
        weights.add(6);
        Prospect instance = new Prospect("test", new Tag("tag"),
                new LocalDate(2115,11,10), new LocalDate(2115,11,13),
                new Duration(1000*9), new Duration(1000*13),
                weights);
        List<Pair<Duration,Duration>> result = instance.getBudgets(new LocalDate(2115,11,11),
                new Duration(1000*1));
        List<Pair<Duration,Duration>> expRes = new ArrayList<>();
        expRes.add(new Pair<>(new Duration(1000*2), new Duration(1000*3)));
        expRes.add(new Pair<>(new Duration(1000*2), new Duration(1000*3)));
        expRes.add(new Pair<>(new Duration(1000*4), new Duration(1000*6)));
        assertEquals(expRes, result);
    }

}
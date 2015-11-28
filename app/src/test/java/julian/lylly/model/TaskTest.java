package julian.lylly.model;

import org.joda.time.Duration;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by VAIO on 28.11.2015.
 */
public class TaskTest {

    final long s = 1000;
    final long m = 60*s;
    final long h = 60*m;
    final long d = 24*h;

    @Before
    public void Before() {
        tag = new Tag("tag name");

        taskE = new Task();

        taskW = new Task();
        taskW.setDescr("descr");
        taskW.setTag(tag);
        taskW.setImportance(1);
        taskW.setUrgency(2);

        isD = new Instant( 7*d);
        ieD = new Instant(49*d);
        isH = new Instant( 7*h);
        ieH = new Instant(49*h);
        isM = new Instant( 7*m);
        ieM = new Instant(49*m);
        isS = new Instant( 7*s);
        ieS = new Instant(49*s);
        is  = new Instant( 7  );
        ie  = new Instant(49  );

        ivD = new Interval(isD, ieD);
        ivH = new Interval(isH, ieH);
        ivM = new Interval(isM, ieM);
        ivS = new Interval(isS, ieS);
        iv  = new Interval( is,  ie);
    }

    Tag tag;
    Task taskE, taskW;
    Interval ivD, ivH, ivM, ivS, iv;
    Instant isD, ieD, isH, ieH, isM, ieM, isS, ieS, is, ie;

    @Test
    public void testStartOk() throws Exception {
        taskE.start();
        assertTrue(taskE.isActive());
    }

    @Test(expected = IllegalStateException.class)
    public void testStartFail() throws Exception {
        taskE.start();
        taskE.start();
    }

    @Test
    public void testPauseOk() throws Exception {
        taskE.start();
        taskE.pause();
        assertFalse(taskE.isActive());
    }

    @Test(expected = IllegalStateException.class)
    public void testPauseFail() throws Exception {
        taskE.pause();
    }

    @Test(expected = IllegalStateException.class)
    public void testPauseFail2() throws Exception {
        taskE.start();
        taskE.pause();
        taskE.pause();
    }

    @Test
    public void testPlayPauseOk1() throws Exception {
        taskE.playPause();
        assertTrue(taskE.isActive());
    }

    @Test
    public void testPlayPauseOk2() throws Exception {
        taskE.playPause();
        taskE.playPause();
        assertFalse(taskE.isActive());
    }

    @Test
    public void testPlayPauseOk3() throws Exception {
        taskE.start();
        taskE.playPause();
        assertFalse(taskE.isActive());
    }

    @Test
    public void testPlayPauseOk4() throws Exception {
        taskE.playPause();
        taskE.pause();
        assertFalse(taskE.isActive());
    }

    @Test
    public void testPlayPauseOk5() throws Exception {
        taskE.start();
        taskE.pause();
        taskE.playPause();
        assertTrue(taskE.isActive());
    }

    @Test
    public void testFinishOk1() throws Exception {
        taskE.finish();
        assertTrue(!taskE.isActive() && taskE.isDone());
    }

    @Test
    public void testFinishOk2() throws Exception {
        taskE.start();
        taskE.finish();
        assertTrue(!taskE.isActive() && taskE.isDone());
    }

    @Test
    public void testIsFragment() throws Exception {
        fail("not yet implemented");
    }

    @Test
    public void testCheckCompleteness() throws Exception {
        taskE.checkCompleteness();
        assertTrue(taskE.isFragment());
    }

    @Test
    public void testCheckCompletenessOk() throws Exception {
        taskW.checkCompleteness();
        assertFalse(taskW.isFragment());
    }

    @Test
    public void testEvalDurationSumD() throws Exception {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(ivD);
        taskW.setIntervals(intervals);
        Duration res = taskW.evalDurationSum();
        Duration exp = ivD.toDuration();
        assertEquals(exp, res);
    }

    @Test
    public void testEvalDurationSumH() throws Exception {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(ivH);
        taskW.setIntervals(intervals);
        Duration res = taskW.evalDurationSum();
        Duration exp = ivH.toDuration();
        assertEquals(exp, res);
    }

    @Test
    public void testEvalDurationSumM() throws Exception {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(ivM);
        taskW.setIntervals(intervals);
        Duration res = taskW.evalDurationSum();
        Duration exp = ivM.toDuration();
        assertEquals(exp, res);
    }

    @Test
    public void testEvalDurationSumS() throws Exception {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(ivS);
        taskW.setIntervals(intervals);
        Duration res = taskW.evalDurationSum();
        Duration exp = ivS.toDuration();
        assertEquals(exp, res);
    }

    @Test
    public void testEvalDurationSum() throws Exception {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(iv);
        taskW.setIntervals(intervals);
        Duration res = taskW.evalDurationSum();
        Duration exp = iv.toDuration();
        assertEquals(exp, res);
    }

    @Test
    public void testGetTimeSpentInInterval() throws Exception {
        fail("not yet implemented");
    }
}
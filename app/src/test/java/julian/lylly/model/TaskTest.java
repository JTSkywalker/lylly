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
        isL = new Instant( 7  );
        ieL = new Instant(49  );

        ivD = new Interval(isD, ieD);
        ivH = new Interval(isH, ieH);
        ivM = new Interval(isM, ieM);
        ivS = new Interval(isS, ieS);
        ivL = new Interval(isL, ieL);
    }

    Tag tag;
    Task taskE, taskW;
    Interval ivD, ivH, ivM, ivS, ivL;
    Instant isD, ieD, isH, ieH, isM, ieM, isS, ieS, isL, ieL;

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

    //@Test TODO:implement
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
        Duration exp = new Duration(42*d);
        assertEquals(exp, res);
    }

    @Test
    public void testEvalDurationSumH() throws Exception {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(ivH);
        taskW.setIntervals(intervals);
        Duration res = taskW.evalDurationSum();
        Duration exp = new Duration(42*h);
        assertEquals(exp, res);
    }

    @Test
    public void testEvalDurationSumM() throws Exception {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(ivM);
        taskW.setIntervals(intervals);
        Duration res = taskW.evalDurationSum();
        Duration exp = new Duration(42*m);
        assertEquals(exp, res);
    }

    @Test
    public void testEvalDurationSumS() throws Exception {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(ivS);
        taskW.setIntervals(intervals);
        Duration res = taskW.evalDurationSum();
        Duration exp = new Duration(42*s);
        assertEquals(exp, res);
    }

    @Test
    public void testEvalDurationSumL() throws Exception {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(ivL);
        taskW.setIntervals(intervals);
        Duration res = taskW.evalDurationSum();
        Duration exp = new Duration(42);
        assertEquals(exp, res);
    }

    @Test
    public void testEvalDurationSumDH() throws Exception {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(ivH);
        intervals.add(ivD);
        taskW.setIntervals(intervals);
        Duration res = taskW.evalDurationSum();
        Duration exp = new Duration(42*d + 42*h);
        assertEquals(exp, res);
    }

    @Test
    public void testEvalDurationSumMD() throws Exception {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(ivM);
        intervals.add(ivD);
        taskW.setIntervals(intervals);
        Duration res = taskW.evalDurationSum();
        Duration exp = new Duration(42*d + 42*m);
        assertEquals(exp, res);
    }

    @Test
    public void testEvalDurationSumSH() throws Exception {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(ivS);
        intervals.add(ivH);
        taskW.setIntervals(intervals);
        Duration res = taskW.evalDurationSum();
        Duration exp = new Duration(42*s + 42*h);
        assertEquals(exp, res);
    }

    @Test
    public void testEvalDurationSumLS() throws Exception {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(ivL);
        intervals.add(ivS);
        taskW.setIntervals(intervals);
        Duration res = taskW.evalDurationSum();
        Duration exp = new Duration(42*s + 42);
        assertEquals(exp, res);
    }

    @Test
    public void testEvalDurationSumLD() throws Exception {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(ivL);
        intervals.add(ivD);
        taskW.setIntervals(intervals);
        Duration res = taskW.evalDurationSum();
        Duration exp = new Duration(42*d + 42);
        assertEquals(exp, res);
    }

    @Test
    public void testEvalDurationSumMHD() throws Exception {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(ivM);
        intervals.add(ivH);
        intervals.add(ivD);
        taskW.setIntervals(intervals);
        Duration res = taskW.evalDurationSum();
        Duration exp = new Duration(42*d + 42*h + 42*m);
        assertEquals(exp, res);
    }

    @Test
    public void testEvalDurationSumLMH() throws Exception {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(ivL);
        intervals.add(ivM);
        intervals.add(ivH);
        taskW.setIntervals(intervals);
        Duration res = taskW.evalDurationSum();
        Duration exp = new Duration(42*h + 42*m + 42);
        assertEquals(exp, res);
    }

    @Test
    public void testEvalDurationSumLSMHD() throws Exception {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(ivL);
        intervals.add(ivS);
        intervals.add(ivM);
        intervals.add(ivH);
        intervals.add(ivD);
        taskW.setIntervals(intervals);
        Duration res = taskW.evalDurationSum();
        Duration exp = new Duration(42*d + 42*h + 42*m + 42*s + 42);
        assertEquals(exp, res);
    }


    @Test
    public void testGetTimeSpentInInterval() throws Exception {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(ivL);
        taskW.setIntervals(intervals);
        taskW.setStartTime(isS);
        Interval focus = new Interval(new Instant(0), ieS);
        Duration res = taskW.getTimeSpentInInterval(focus);
        Duration exp = new Duration(42*s + 42);
        assertEquals(exp, res);
    }
}
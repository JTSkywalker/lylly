/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package julian.lylly.model;

import org.joda.time.Duration;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Task implements Serializable {

	//TODO: test! especially getTimeSpentInInterval & evalDurationSum

	/*
	context condition: fragment <==>      descr == null ||   tag == null
								  || importance == -1 || urgency == -1
	*/

	//status:
	private boolean fragment = true;
	private boolean done     = false;


	// essential:
	private String descr;
	private Tag tag;
	private int importance = -1,
			    urgency    = -1;

	//optional:
	private LocalDate startDate;
	private LocalDate expiryDate;


	//variable
	private List<Interval> intervals = new ArrayList<>();
	private Instant starttime;


	/*
	status operators:
	*/

	public void start() {
		if (isActive()) {
			throw new IllegalStateException("already active");
		}
		starttime = Instant.now();
	}

	public void pause() {
		if (!isActive()) {
			throw new IllegalStateException("already non-active");
		}
		intervals.add(new Interval(starttime, Instant.now()));
		starttime = null;
	}

	public void playPause() {
		if (isActive()) {
			pause();
		} else {
			start();
		}
	}

	public void finish() {
		if (isActive()) {
			pause();
		}
		done = true;
	}



	/*
	status getter:
	*/

	public boolean isFragment() {
		return fragment;

	}

	public boolean isActive() {
		return starttime != null;
	}

	public boolean isDone() {
		return done;
	}



	/*
	get & set properties:
	*/

	void checkCompleteness() {
		fragment
			= descr == null || tag == null || importance == -1 || urgency == -1;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
		checkCompleteness();
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
		checkCompleteness();
	}

	public int getImportance() {
		return importance;
	}

	public void setImportance(int importance) {
		this.importance = importance;
		checkCompleteness();
	}

	public void setStartTime(Instant starttime) {
		this.starttime = starttime;
	}

	public int getUrgency() {
		return urgency;
	}

	public void setUrgency(int urgency) {
		this.urgency = urgency;
		checkCompleteness();
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}



	/*
	getter for timetracking:
	*/

	public Duration evalDurationSum() {
		return getTimeSpentInInterval(null, null);
	}

	public void setIntervals(List<Interval> intervals) {
		checkDistinctness(intervals);
		this.intervals = intervals;
	}

	static void checkDistinctness(List<Interval> intervals) {
		//TODO: implement
	}

	public List<Interval> getIntervals() {
		return intervals;
	}


	/**
	 * behaviour undefined if interval list is unsorted
	 * any {@code null} input is interpreted as a non-bound in that direction
	 * @param focusStart
	 * @param focusEnd
	 * @return
	 */
	public Duration getTimeSpentInInterval(LocalDate focusStart, LocalDate focusEnd) {
		Instant startInst, endInst;
		if (intervals.size() == 0 && !isActive()) { // #
			return Duration.ZERO;
		}
		if (focusStart == null) {
			startInst = intervals.size() == 0
					? starttime // != null because #
					: intervals.get(0).getStart().toInstant();
		} else {
			startInst = focusStart.toDateTimeAtStartOfDay().toInstant();
		}
		if (focusEnd == null) {
			endInst = isActive()
					? Instant.now()
					//v intervals.size() != 0 because #
					: intervals.get(intervals.size() - 1).getEnd().toInstant();//TODO
		} else {
			endInst   = focusEnd.toDateTimeAtStartOfDay().toInstant();
		}
		Interval focus = new Interval(startInst, endInst);

		return getTimeSpentInInterval(focus);
	}

	public Duration getTimeSpentInInterval(Interval focus) {
		if (focus == null) {
			throw new IllegalArgumentException("focus == nul");
		}

		Duration timespent = Duration.ZERO;

		for (Interval i : intervals) {
			Duration ov = computeOverlap(i, focus);
			timespent = timespent.plus(ov);
		}
		if (isActive()) {
			Interval activeInterval = new Interval(starttime, Instant.now());
			Duration overlapDuration = computeOverlap(activeInterval, focus);
			timespent = timespent.plus(overlapDuration);
		}

		return timespent;
	}

	private Duration computeOverlap(Interval x, Interval y) {
		if (x.overlaps(y)) {
			return x.overlap(y).toDuration();
		} else {
			return Duration.ZERO;
		}
	}

}
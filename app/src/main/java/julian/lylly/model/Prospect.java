/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package julian.lylly.model;

import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.Period;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Prospect implements Serializable {

	/*
	It has to be possible to turn off and/or down/up a prospect.
	User should be motivated to get as near as possible even if the original
	bounds are not reachable any more.
	*/

	/*
	context conditions:
		name != null
		tag != null
		start < end
		min <= max
		weights.size() == end-start
	*/

	private String name;
	private Tag tag;
	private LocalDate start, end;
	private Duration min, max;
	private List<Integer> weights;

	public Prospect(String name, Tag tag, LocalDate start, LocalDate end,
					Duration min, Duration max, List<Integer> weights) {

		checkNameConstraint(name);
		checkTagConstraint(tag);
		checkStartEndConstraint(start, end);
		checkMinMaxConstraint(min, max);
		checkWeightsConstraint(start, end, weights);

		this.name = name;
		this.tag = tag;
		this.start = start;
		this.end = end;
		this.min = min;
		this.max = max;
		this.weights = weights;
	}

	public boolean isActive() {
		LocalDate now = LocalDate.now();
		return !now.isBefore(start) && !end.isBefore(now);
	}

	public boolean isBeforeStart() {
		return LocalDate.now().isBefore(start);
	}

	public boolean isOver() {
		return LocalDate.now().isAfter(end);
	}

	public boolean isSucceeded(Duration timespent) {
		return isOver() && !timespent.isShorterThan(min) && !timespent.isLongerThan(max);
	}

	public boolean tooLow(Duration timespent) {
		return isOver() && timespent.isShorterThan(min);
	}

	public boolean tooHigh(Duration timespent) {
		return isOver() && timespent.isLongerThan(max);
	}

	public String getName() {
		return name;
	}

	public Tag getTag() {
		return tag;
	}

	public LocalDate getStart() {
		return start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public Duration getMin() {
		return min;
	}

	public Duration getMax() {
		return max;
	}

	public List<Integer> getWeights() {
		return weights;
	}

	public List<Pair<Duration, Duration>> getBudgets(LocalDate pointer, Duration timespent) {
		int relDay = Period.fieldDifference(start, pointer).getDays();
		if (pointer.isBefore(start) || !end.isAfter(pointer)) {
			throw new IllegalArgumentException("day is out of range");
		}
		List<Integer> subl = weights.subList(relDay - 1, weights.size());
		int sum = 0;
		for (int k : subl) {
			sum += k;
		}

		Duration minleft = Util.maxDuration(new Duration(0), min.minus(timespent));
		Duration maxleft = Util.maxDuration(new Duration(0), max.minus(timespent));
		List<Pair<Duration, Duration>> res = new ArrayList<>();
		for (int k : subl) {
			res.add(new Pair(minleft.multipliedBy(k).dividedBy(sum),
					maxleft.multipliedBy(k).dividedBy(sum)));
		}
		return res;
	}

	public Pair<Duration, Duration> getNextBudget(LocalDate date, Duration timespent) {
		return getBudgets(date, timespent).get(0);
	}

	public void setName(String name) {
		checkBeforeStart();
		checkNameConstraint(name);
		this.name = name;
	}

	public void setTag(Tag tag) {
		checkBeforeStart();
		checkTagConstraint(tag);
		this.tag = tag;
	}

	public void setStartEnd(LocalDate start, LocalDate end, List<Integer> weights) {
		checkBeforeStart();
		checkStartEndConstraint(start, end);
		checkWeightsConstraint(start, end, weights);
		this.start = start;
		this.end = end;
		this.weights = weights;
	}

	public void setMinMax(Duration min, Duration max) {
		checkBeforeStart();
		checkMinMaxConstraint(min, max);
		this.min = min;
		this.max = max;
	}

	public void setWeights(List<Integer> weights) {
		checkWeightsConstraint(start, end, weights);
		this.weights = weights;
	}

	private void checkNameConstraint(String name) {
		if(name == null) {
			throw new IllegalArgumentException("name must not be null");
		}
	}

	private void checkTagConstraint(Tag tag) {
		if(tag == null) {
			throw new IllegalArgumentException("tag must not be null");
		}
	}

	private void checkStartEndConstraint(LocalDate start, LocalDate end) {
		if (!start.isBefore(end)) {
			throw new IllegalArgumentException("start must be less than end");
		}
	}

	private void checkMinMaxConstraint(Duration min, Duration max) {
		if (min.isLongerThan(max)) {
			throw new IllegalArgumentException("min must be less or equal max");
		}
	}

	private void checkWeightsConstraint(LocalDate start, LocalDate end, List<Integer> weights) {
		int len = Days.daysBetween(start, end).getDays();
		if (weights.size() != len) {
			throw new IllegalArgumentException(
					"weights.size() = " + weights.size() + " != " + len + " = end-start");
		}
	}

	private void checkBeforeStart() {
		if(!isBeforeStart()) {
			throw new UnsupportedOperationException("active prospects are final");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Prospect prospect = (Prospect) o;

		if (!name.equals(prospect.name)) return false;
		if (!tag.equals(prospect.tag)) return false;
		if (!start.equals(prospect.start)) return false;
		if (!end.equals(prospect.end)) return false;
		if (!min.equals(prospect.min)) return false;
		if (!max.equals(prospect.max)) return false;
		return weights.equals(prospect.weights);

	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + tag.hashCode();
		result = 31 * result + start.hashCode();
		result = 31 * result + end.hashCode();
		result = 31 * result + min.hashCode();
		result = 31 * result + max.hashCode();
		result = 31 * result + weights.hashCode();
		return result;
	}
}

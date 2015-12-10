/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package julian.lylly.model;

import org.joda.time.Duration;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TaskOrganizerImpl implements TaskOrganizer {

	//TODO: test!

	private final Map<Tag,List<Task>> toDo = new HashMap<>();

	public Duration getInvestedTime(Prospect prospect) {
		return getInvestedTime(prospect.getStart(), prospect.getEnd(), prospect.getTag());
	}

	@Override
	public List<Task> getFilteredTasks(List<Tag> tags) {
		if (tags.isEmpty()) {
			tags = new ArrayList<>(toDo.keySet());
		}
		List<Task> res = new ArrayList<>();
		for (Tag tag : tags) {
			if (toDo.containsKey(tag)) {
				for (Task task : toDo.get(tag)) {
					if (task.isRecent()) {
						res.add(task);
					}
				}
			}
		}
		return res;
	}

	@Override
	public Duration getInvestedTime(LocalDate start, LocalDate end, Tag tag) {
		if (!toDo.containsKey(tag)) {
			return new Duration(0);
		}
		List<Task> li = toDo.get(tag);
		Duration sum = Duration.ZERO;
		for (Task t : li) {
			sum = sum.plus(t.getTimeSpentInInterval(start, end));
		}
		return sum;
	}

	@Override
	public Duration getTodaysInvTime(Tag tag) {
		LocalDate today = LocalDate.now();
		return getInvestedTime(today, today.plusDays(1), tag);
	}

	@Override
	public void addTask(Task task) {
		Tag tag = task.getTag();
		if (toDo.containsKey(tag)) {
			toDo.get(tag).add(task);
		} else {
			List<Task> arrli = new ArrayList<>();
			arrli.add(task);
			toDo.put(tag, arrli);
		}
	}

	@Override
	public void removeTask(Task task) {
		Tag tag = task.getTag();
		if (!toDo.containsKey(tag)) {
			throw new IllegalArgumentException("task does not exist");
		}
		toDo.get(tag).remove(task);
	}

}

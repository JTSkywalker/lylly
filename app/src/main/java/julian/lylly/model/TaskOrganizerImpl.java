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

	private final List<Task> toDo = new ArrayList<>();

	public Duration getInvestedTime(Prospect prospect) {
		return getInvestedTime(prospect.getStart(), prospect.getEnd(), prospect.getTag());
	}

	@Override
	public List<Task> getFilteredTasks(List<Tag> tags) {
		List<Task> res = new ArrayList<>();
		if (tags.isEmpty()) {
			for (Task task : toDo) {
				if (task.isRecent()) {
					res.add(task);
				}
			}
			return res;
		}
		for (Tag tag : tags) {
			for (Task task :
					toDo) {
				if (task.getTag().equals(tag) && task.isRecent()) {
					res.add(task);
				}
			}
		}
		return res;
	}

	@Override
	public Duration getInvestedTime(LocalDate start, LocalDate end, Tag tag) {
		Duration sum = Duration.ZERO;
		for (Task task : toDo) {
			// Check for null, because some tasks may not have a tag
			if(task.getTag()!= null) {
				if (task.getTag().equals(tag)) {
					sum = sum.plus(task.getTimeSpentInInterval(start, end));
				}
			}
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
		toDo.add(task);
	}

	@Override
	public void removeTask(Task task) {
		toDo.remove(task);
	}

}

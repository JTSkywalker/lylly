/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package julian.lylly.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TaskOrganizerImpl implements TaskOrganizer {

	private final Map<Tag,List<Task>> toDo = new HashMap<>();

	public long getInvestedTime(Prospect prospect) {
		return getInvestedTime(prospect.getStartInMillis(),
							   prospect.getEndInMillis(),
							   prospect.getTag());
	}

	@Override
	public List<Task> getFilteredTasks(List<Tag> tags) {
		List<Task> res = new ArrayList<>();
		for (Tag tag : tags) {
			if (toDo.containsKey(tag)) {
				res.addAll(toDo.get(tag));
			}
		}
		return res;
	}

	@Override
	public long getInvestedTime(long start, long end, Tag tag) {
		if (!toDo.containsKey(tag)) {
			return 0;
		}
		List<Task> li = toDo.get(tag);
		long sum = 0;
		for (Task t : li) {
			sum += t.getTimeSpentInInterval(start, end);
		}
		return sum;
	}

	@Override
	public long getTodaysInvTime(Tag tag) {
		long today = Util.cutMillis(System.currentTimeMillis());
		return getInvestedTime(today, today + Util.MILLIS_PER_DAY, tag);
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

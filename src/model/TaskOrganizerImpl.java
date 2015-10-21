/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import java.util.List;


public class TaskOrganizerImpl implements TaskOrganizer {

	//TODO: implement... maybe somehow sorted tasklist?

	private final List<Task> toDo = new ArrayList<>();

	public long getInvestedTime(Prospect prospect) {
		long sum = 0;
		for (int i=prospect.getStart(); i < prospect.getEnd(); i++) {
			sum += getInvestedTime(i, prospect.getTag());
		}
		return sum;
	}

	@Override
	public List<Task> getFilteredTasks(List<Tag> tag) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public long getInvestedTime(int day, Tag tag) {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getTodaysInvTime(Tag tag) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void addTask(Task task) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void removeTask(Task task) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}

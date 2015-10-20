/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.List;
import java.util.Map;

public interface TaskOrganizer {

	public List<Task> getFilteredTasks(List<Tag> tag);

	public Map<Tag,Pair<Long,Long> > getTodaysBudgets();

	public long getInvestedTime(int day, Tag tag);

	public long getTodaysInvTime(Tag tag);


	public void addTask(Task task);

	public void removeTask(Task task);

}

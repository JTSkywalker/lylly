/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package julian.lylly.model;

import java.util.List;

public interface TaskOrganizer {

	public List<Task> getFilteredTasks(List<Tag> tag);

	public long getInvestedTime(long start, long end, Tag tag);

	public long getTodaysInvTime(Tag tag);

	public long getInvestedTime(Prospect prospect);


	public void addTask(Task task);

	public void removeTask(Task task);

}

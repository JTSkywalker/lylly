/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package julian.lylly.model;

import org.joda.time.Duration;
import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.List;

public interface TaskOrganizer extends Serializable {

	List<Task> getFilteredTasks(List<Tag> tag);

	Duration getInvestedTime(LocalDate start, LocalDate end, Tag tag);

	Duration getTodaysInvTime(Tag tag);

	Duration getInvestedTime(Prospect prospect);


	void addTask(Task task);

	void removeTask(Task task);

}

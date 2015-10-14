/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;


public class Organizer {

	private List<Task> toDo;
	private SortedMap< Calendar,Map<Tag,Integer> > budgets; // maybe other data structure
	private List<Prospect> prospects;

	private List<Generator> gens;

	private List<Tag> tags;

	public List<Task> determineToDo() {
		return partialSort(getTodaysBudgets().keySet());
	}

	private List<Task> partialSort(Set<Tag> tags) {
		int n = tags.size();
		List<List<Task>> taskss = new ArrayList<>(n + 1);

		List<Tag> tagli = new ArrayList<>();
		tagli.addAll(tags);

		for (int k=0; k < n+1; k++) {
			taskss.add(new ArrayList<Task>());
		}
		for (Task t : toDo) {
			boolean unset = true;
			for (int k=0; k < n; k++) {
				if (t.hasTag(tagli.get(k)) && unset) {
					taskss.get(k).add(t);
					unset = false;
					break;
				}
			}
			if (unset) {
				taskss.get(n).add(t);
			}
		}

		List<Task> tasks = new ArrayList<>();
		for (List<Task> ts : taskss) {
			tasks.addAll(ts);
		}

		return tasks;
	}

	public List<Prospect> getProspects() {
		return prospects;
	}

	public List<Generator> getGens() {
		return gens;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public Map<Tag,Integer> getTodaysBudgets() {
		return getBudgets(System.currentTimeMillis());
	}

	// Warning: this method might behave inappropriate!
	public Map<Tag,Integer> getBudgets(long timeMillis) {
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(timeMillis);
		return budgets.get(cal);
	}

	public Map<Tag,Integer> getBudgets(Calendar date) {
		return budgets.get(date);
	}

}

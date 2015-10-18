/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;


public class Organizer {

	public static final long START_OF_DAY = 0;

	/*
	context conditions:
		tag names must be unique
		for each budget-prospect-violation there should be a task created
	*/


	private final List<Task> toDo          = new ArrayList<>();
	private final List<Tag> tags           = new ArrayList<>();
	private final List<Generator> gens     = new ArrayList<>();
	private final List<Prospect> prospects = new ArrayList<>();

	/**
	* the integer is the number of the day where epoch (= 1970/01/01) is the day zero,
	* the Long value in the Map is the number of milliseconds
	*/
	private final SortedMap<Integer,Map<Tag,Long> > budgets
										   = new TreeMap<>();


	/*
	toDo delegates:
	*/

	public boolean addTask(Task e) {
		return toDo.add(e);
	}

	public boolean removeTask(Task o) {
		return toDo.remove(o);
	}

	public Task removeTask(int index) {
		return toDo.remove(index);
	}


	/*
	tags delegates:
	*/

	public boolean addTag(Tag e) {
		return tags.add(e);
	}

	public boolean remove(Tag o) {
		return tags.remove(o);
	}

	public Tag removeTag(int index) {
		return tags.remove(index);
	}


	/*
	prospects delegates:
	*/

	public boolean addProspect(Prospect e) {
		return prospects.add(e);
	}

	public boolean removeProspect(Prospect o) {
		return prospects.remove(o);
	}

	public Prospect removeProspect(int index) {
		return prospects.remove(index);
	}


	/*
	change budgets:
	*/
	//TODO: implement



	/*
	fetch functions:
	*/

	//TODO: implement fetchViolations, violations between budgets and prospects

	public List<Task> fetchTodaysToDo() {
		return partialSort(fetchTodaysBudgets().keySet());
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
				if (t.getTag().contains(tagli.get(k)) && unset) {
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
		for (List<Task> ts : taskss) {//TODO: change this, maybe interleaving
			tasks.addAll(ts);
		}

		return tasks;
	}

	public Map<Tag,Long> fetchTodaysBudgets() {
		return fetchBudgets(System.currentTimeMillis());
	}

	public Map<Tag,Long> fetchBudgets(long timeMillis) {
		return budgets.get(timeMillis);
	}



	/*
	getter:
	*/

	public List<Prospect> getProspects() {
		return prospects;
	}

	public List<Generator> getGens() {
		return gens;
	}

	public List<Tag> getTags() {
		return tags;
	}
}

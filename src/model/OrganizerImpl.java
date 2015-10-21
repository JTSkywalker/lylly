/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrganizerImpl implements Organizer {

	public static final long START_OF_DAY = 0;

	/*
	context conditions:
		tag names must be unique
	*/
	//TODO: check context conditions


	private final List<Tag> tags = new ArrayList<>();

	private final TaskOrganizer     taskOrg     = new TaskOrganizerImpl();
	private final ProspectOrganizer prospectOrg = new ProspectOrganizerImpl();


	//TODO: maybe generalize this
	//TODO: think about future budgets
	@Override
	public Map<Tag, Pair<Long, Long>> getTodaysBudgets() {
		Map<Tag, Pair<Long,Long>> res = new HashMap<>();
		for (Tag tag : tags) {
			res.put(tag, getBudget(prospectOrg.getActiveProspect(tag)));
		}
		return res;
	}

	private Pair<Long,Long> getBudget(Prospect prospect) {
		return prospect.getNextBudget(Util.millisToDay(System.currentTimeMillis()),
									  taskOrg.getInvestedTime(prospect));
	}

	/*
	tag functions:
	*/

	public List<Tag> getTags() {
		return tags;
	}

	public void addTag(Tag e) {
		tags.add(e);
	}

	public void removeTag(Tag o) {
		tags.remove(o);
	}


	/*
	tOrg delegates:
	*/

	@Override
	public List<Task> getFilteredTasks(List<Tag> tag) {
		return taskOrg.getFilteredTasks(tag);
	}

	@Override
	public long getInvestedTime(int day, Tag tag) {
		return taskOrg.getInvestedTime(day, tag);
	}

	@Override
	public long getInvestedTime(Prospect prospect) {
		return taskOrg.getInvestedTime(prospect);
	}

	@Override
	public long getTodaysInvTime(Tag tag) {
		return taskOrg.getTodaysInvTime(tag);
	}

	@Override
	public void addTask(Task task) {
		taskOrg.addTask(task);
	}

	@Override
	public void removeTask(Task task) {
		taskOrg.removeTask(task);
	}




	/*
	pOrg delegates:
	*/

	@Override
	public Prospect getActiveProspect(Tag tag) {
		return prospectOrg.getActiveProspect(tag);
	}

	@Override
	public List<Prospect> getFutureProspects() {
		return prospectOrg.getFutureProspects();
	}

	@Override
	public List<Prospect> getActiveProspects() {
		return prospectOrg.getActiveProspects();
	}

	@Override
	public List<Prospect> getFinishedProspect() {
		return prospectOrg.getFinishedProspect();
	}

	@Override
	public List<Prospect> getDiscardedProspects() {
		return prospectOrg.getDiscardedProspects();
	}

	@Override
	public void addProspect(Prospect prospect) {
		prospectOrg.addProspect(prospect);
	}

	@Override
	public void discardProspect(Prospect prospect) {
		prospectOrg.discardProspect(prospect);
	}

}

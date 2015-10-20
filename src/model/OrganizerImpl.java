/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OrganizerImpl implements Organizer {

	public static final long START_OF_DAY = 0;

	/*
	context conditions:
		tag names must be unique
		for each budget-prospect-violation there should be a task created
	*/


	private final List<Tag> tags = new ArrayList<>();

	private final TaskOrganizer     tOrg = new TaskOrganizerImpl();
	private final ProspectOrganizer pOrg = new ProspectOrganizerImpl();

	public long getInvestedTime(Prospect prospect) {
		long sum = 0;
		for (int i=prospect.getStart(); i < prospect.getEnd(); i++) {
			sum += tOrg.getInvestedTime(i, prospect.getTag());
		}
		return sum;
	}

	/*
	tags delegates:
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
		return tOrg.getFilteredTasks(tag);
	}

	@Override
	public Map<Tag, Pair<Long, Long>> getTodaysBudgets() {
		return tOrg.getTodaysBudgets();
	}

	@Override
	public long getInvestedTime(int day, Tag tag) {
		return tOrg.getInvestedTime(day, tag);
	}


	@Override
	public long getTodaysInvTime(Tag tag) {
		return tOrg.getTodaysInvTime(tag);
	}

	@Override
	public void addTask(Task task) {
		tOrg.addTask(task);
	}

	@Override
	public void removeTask(Task task) {
		tOrg.removeTask(task);
	}




	/*
	pOrg delegates:
	*/

	@Override
	public List<Prospect> getFutureProspects() {
		return pOrg.getFutureProspects();
	}

	@Override
	public List<Prospect> getActiveProspects() {
		return pOrg.getActiveProspects();
	}

	@Override
	public List<Prospect> getFinishedProspect() {
		return pOrg.getFinishedProspect();
	}

	@Override
	public List<Prospect> getFailLowProspects() {
		return pOrg.getFailLowProspects();
	}

	@Override
	public List<Prospect> getFailHighProspects() {
		return pOrg.getFailHighProspects();
	}

	@Override
	public List<Prospect> getDiscardedProspects() {
		return pOrg.getDiscardedProspects();
	}

	@Override
	public void addProspect(Prospect prospect) {
		pOrg.addProspect(prospect);
	}

	@Override
	public void removeProspect(Prospect prospect) {
		pOrg.removeProspect(prospect);
	}
}

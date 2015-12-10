/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package julian.lylly.model;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;


public class ProspectOrganizerImpl implements ProspectOrganizer {

	/*
	When there are different prospects which overlap in time and have the same
	tag, this might lead to disaster.

	context condition:
		their should be no two overlapping active prospects with the same tag
	*/


	private final List<Prospect> enabled   = new ArrayList<>();
	private final List<Prospect> discarded = new ArrayList<>();

	//TODO: testing!

	@Override
	public List<Prospect> getProspects() {
		List<Prospect> res = new ArrayList<>();
		res.addAll(enabled);
		res.addAll(discarded);
		return res;
	}

	@Override
	public List<Prospect> getProspects(Tag tag) {
		List<Prospect> res = new ArrayList<>();
		for (Prospect p : enabled) {
			if (p.getTag().equals(tag)) {
				res.add(p);
			}
		}
		return res;
	}

	/**
	 * returns the first (and hopefully only) active prospect with the given
	 * tag, null if there is none.
	 * @param tag
	 * @return
	 */
	@Override
	public Prospect getActiveProspect(Tag tag) {
		List<Prospect> active = getActiveProspects();
		for(Prospect p : active) {
			if (p.getTag().equals(tag)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * returns prospects that have not yet started
	 * @return
	 */
	@Override
	public List<Prospect> getFutureProspects() {
		List<Prospect> res = new ArrayList<>();
		for (Prospect p : enabled) {
			if (LocalDate.now().isAfter(p.getEnd())) {
				res.add(p);
			}
		}
		return res;
	}

	/**
	 * returns all active prospects, e.g. start<=now<=end
	 * @return
	 */
	@Override
	public List<Prospect> getActiveProspects() {
		List<Prospect> res = new ArrayList<>();
		for (Prospect p : enabled) {
			if (p.isActive()) {
				res.add(p);
			}
		}
		return res;
	}

	/**
	 * returns all prospects which are already over, also if they were discarded
	 * e.g. now > end
	 * @return
	 */
	@Override
	public List<Prospect> getFinishedProspect() {
		List<Prospect> res = new ArrayList<>();
		for (Prospect p : enabled) {
			if (p.isOver()) {
				res.add(p);
			}
		}
		for (Prospect p : discarded) {
			if (p.isOver()) {
				res.add(p);
			}
		}
		return res;
	}

	/**
	 * returns only "active" discarded prospects, e.g. those with start<=now<=end
	 * @return
	 */
	@Override
	public List<Prospect> getDiscardedProspects() {
		List<Prospect> res = new ArrayList<>();
		for (Prospect p : discarded) {
			if (p.isActive()) {
				res.add(p);
			}
		}
		return res;
	}

	/**
	 * adds a new prospect.
	 * @param prospect
	 */
	@Override
	public void addProspect(Prospect prospect) {
		checkOverlaps(prospect, getProspects(prospect.getTag()));
		enabled.add(prospect);
	}

	/**
	 * if prospect is active, it is discarded, if it is beforeStart it is deleted,
	 * and if it isOver nothing happens.
	 * @param prospect
	 */
	@Override
	public void discardProspect(Prospect prospect) {
		if (enabled.contains(prospect)) {
			if (prospect.isActive()) {
				discarded.add(prospect);
			}
			if (!prospect.isOver()) {
				enabled.remove(prospect);
			}
		}
	}

	private void checkOverlaps(Prospect newie, List<Prospect> olds) {
		for (Prospect p : olds) {
			if (!p.getStart().isAfter(newie.getStart()) && !newie.getStart().isAfter(p.getEnd())
			 || !p.getStart().isAfter(newie.getEnd())   && !newie.getEnd().isAfter(p.getEnd())) {
				throw new IllegalArgumentException("newie overlaps");
			}
		}
	}

}

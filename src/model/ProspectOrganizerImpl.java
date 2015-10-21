/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import java.util.List;


public class ProspectOrganizerImpl implements ProspectOrganizer {

	/*
	When there are different prospects which overlap in time and have the same
	tag, this might lead to disaster.

	context condition:
		their should be no two overlapping active prospects with the same tag
	*/

	//TODO: check context conditions

	//TODO: switch to two list model: enabled & discarded
	private final List<Prospect> pro = new ArrayList<>();
	private final List<Prospect> dis = new ArrayList<>();
	private final List<Prospect> fin = new ArrayList<>();

	@Override
	public List<Prospect> getFutureProspects() {
		List<Prospect> res = new ArrayList<>();
		long now = System.currentTimeMillis();
		for (Prospect p : pro) {
			if (now < p.getStartInMillis()) {
				res.add(p);
			}
		}
		return res;
	}

	@Override
	public List<Prospect> getActiveProspects() {
		List<Prospect> res = new ArrayList<>();
		checkForFinishedProspects();
		long now = System.currentTimeMillis();
		for (Prospect p : pro) {
			if (p.getStartInMillis() <= now) {
				res.add(p);
			}
		}
		return res;
	}

	@Override
	public List<Prospect> getFinishedProspect() {
		checkForFinishedProspects();
		return fin;
	}

	//TODO: this also
	@Override
	public List<Prospect> getDiscardedProspects() {
		return dis;
	}

	@Override
	public void addProspect(Prospect prospect) {
		if (prospect.isActive() || prospect.isOver()) {
			throw new IllegalArgumentException("only future prospects can be added");
		}
		pro.add(prospect);
	}

	//TODO: this needs a javadoc
	@Override
	public void discardProspect(Prospect prospect) {
		if (prospect.isActive()) {
			dis.add(prospect);
		}
		if (!prospect.isOver()) {
			pro.remove(prospect);
		}
	}

	//TODO: remove/replace this
	private void checkForFinishedProspects() {
		for(Prospect p : pro) {
			if (p.isOver()) {
				fin.add(p);
				pro.remove(p);
			}
		}
		for(Prospect p : dis) {
			if (p.isOver()) {
				fin.add(p);
				dis.remove(p);
			}
		}
	}

}

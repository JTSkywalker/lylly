/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.List;
import java.util.Map;

public interface ProspectOrganizer {

	public List<Prospect> getFutureProspects();

	public List<Prospect> getActiveProspects();

	public List<Prospect> getFinishedProspect();

	public List<Prospect> getFailLowProspects();

	public List<Prospect> getFailHighProspects();

	public List<Prospect> getDiscardedProspects();


	public void addProspect(Prospect prospect);

	public void removeProspect(Prospect prospect);

	public Map<Tag, Pair<Long, Long>> getTodaysBudgets();

}

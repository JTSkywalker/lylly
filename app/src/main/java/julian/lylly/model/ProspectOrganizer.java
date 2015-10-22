/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package julian.lylly.model;

import java.util.List;

public interface ProspectOrganizer {

	public List<Prospect> getProspects(Tag tag);

	public Prospect getActiveProspect(Tag tag);

	public List<Prospect> getFutureProspects();

	public List<Prospect> getActiveProspects();

	public List<Prospect> getFinishedProspect();

	/**
	 * returns only "active" discarded prospects, e.g. those with start<=now<=end
	 * @return
	 */
	public List<Prospect> getDiscardedProspects();


	public void addProspect(Prospect prospect);

	public void discardProspect(Prospect prospect);

}

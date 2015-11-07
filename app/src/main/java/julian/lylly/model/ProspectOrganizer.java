/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package julian.lylly.model;

import java.io.Serializable;
import java.util.List;

public interface ProspectOrganizer extends Serializable {

	List<Prospect> getProspects(Tag tag);

	List<Prospect> getProspects();

	Prospect getActiveProspect(Tag tag);

	List<Prospect> getFutureProspects();

	List<Prospect> getActiveProspects();

	List<Prospect> getFinishedProspect();

	/**
	 * returns only "active" discarded prospects, e.g. those with start<=now<=end
	 * @return
	 */
	List<Prospect> getDiscardedProspects();


	void addProspect(Prospect prospect);

	void discardProspect(Prospect prospect);

}

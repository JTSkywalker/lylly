/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.List;

public interface ProspectOrganizer {

	public List<Prospect> getFutureProspects();

	public List<Prospect> getActiveProspects();

	public List<Prospect> getFinishedProspect();

	//TODO: define discarded! Are finished discarded prospects discarded?
	public List<Prospect> getDiscardedProspects();


	public void addProspect(Prospect prospect);

	public void discardProspect(Prospect prospect);

}

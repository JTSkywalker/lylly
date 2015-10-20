/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.List;

public interface Organizer extends TaskOrganizer, ProspectOrganizer {

	public long getInvestedTime(Prospect prospect);

	public List<Tag> getTags();

	public void addTag(Tag tag);

	public void removeTag(Tag tag);

}

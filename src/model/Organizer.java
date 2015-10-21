/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.List;
import java.util.Map;

public interface Organizer extends TaskOrganizer, ProspectOrganizer {

	public Map<Tag, Pair<Long, Long>> getTodaysBudgets();

	public List<Tag> getTags();

	public void addTag(Tag tag);

	public void removeTag(Tag tag);

}

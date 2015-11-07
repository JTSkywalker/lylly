/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package julian.lylly.model;

import org.joda.time.Duration;

import java.util.List;
import java.util.Map;

public interface Organizer extends TaskOrganizer, ProspectOrganizer {

	Map<Tag, Pair<Duration, Duration>> getTodaysBudgets();

	List<Tag> getTags();

	void addTag(Tag tag);

	void removeTag(Tag tag);

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package julian.lylly.model;

import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.List;


public class ProspectGenerator implements Generator, Serializable {

	// implement later

	@Override
	public List genTasksFromTill(LocalDate start, LocalDate end) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}

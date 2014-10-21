/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 9/24/14 4:06 PM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.referencelayer.importer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: conesa
 * Date: 24/09/2014
 * Time: 16:06
 */
public class ProcessReport {

	private String processName = "";
	private List<ProcessReport> subProcesses = new ArrayList<ProcessReport>();
	private Period period = new Period();
	private Percentage percentage = new Percentage();
	private Logger LOGGER = LoggerFactory.getLogger(ProcessReport.class);

	public ProcessReport(String processName, int steps){
		this.processName = processName;
		this.percentage.setTotal(steps);
	}

	public ProcessReport() {

	}

	public String getProcessName() {
		return processName;
	}


	public List<ProcessReport> getSubProcesses() {
		return subProcesses;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void started(){
		period.setStart(new Date());
		LOGGER.info(processName + " started");
	}
	public void finished(){
		period.setEnd(new Date());
		LOGGER.info(processName + " finished after " + getDuration() + " seconds");
	}
	public int getDuration(){
		return period.getDuration();
	}

	public ProcessReport addSubProcess(String name, int steps){
		ProcessReport subprocess = new ProcessReport(name, steps);
		subProcesses.add(subprocess);
		return subprocess;
	}
	public void oneMore(){
		percentage.oneMore();
	}

	public Period getPeriod() {
		return period;
	}

	public Percentage getPercentage() {
		return percentage;
	}


}

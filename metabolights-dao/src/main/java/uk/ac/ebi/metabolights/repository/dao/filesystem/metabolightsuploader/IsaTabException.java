/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 4/23/13 10:33 AM
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

package uk.ac.ebi.metabolights.repository.dao.filesystem.metabolightsuploader;

import org.isatools.errorreporter.model.ISAFileErrorReport;

import java.util.List;

public class IsaTabException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ISAFileErrorReport> errors;
    private String msg;

	public IsaTabException (String message, List<ISAFileErrorReport> errors){
		super(message);
		this.errors = errors;
        this.msg = message;
		
	}

    public IsaTabException (String message, Exception e){
        super(message, e);
        this.msg = message;

    }

    public IsaTabException(String message) {
        super(message);
        this.msg = message;
    }

    public List<ISAFileErrorReport> getErrors(){
		return errors;
	}
	@Override
    public String getMessage(){
        return this.msg;
    }

    public String geTechnicalInfo(){
        StringBuffer logsStrBuffer = new StringBuffer();

        for(ISAFileErrorReport error : errors){
            logsStrBuffer.append(error.getProblemSummary().toString());
            logsStrBuffer.append("\n");
        }

        return logsStrBuffer.toString();
    }
}
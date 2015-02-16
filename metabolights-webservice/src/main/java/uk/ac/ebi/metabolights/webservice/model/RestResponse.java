/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Feb-10
 * Modified by:   conesa
 *
 *
 * Copyright 2015 EMBL-European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.webservice.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: conesa
 * Date: 10/02/15
 * Time: 11:29
 */
public class RestResponse<Content> {

	private static final Logger logger = LoggerFactory.getLogger(RestResponse.class);
	private Content content;
	private String message;
	private Exception err;
	public RestResponse(){};
	public RestResponse (Content content){
		this.content = content;
	}
	public RestResponse (Exception err){
		this.message = err.getMessage();
		this.err = err;
	}

	public RestResponse (String message,Exception err){
		this.message = message;
		this.err = err;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Exception getErr() {
		return err;
	}

	public void setErr(Exception err) {
		this.err = err;
	}



}
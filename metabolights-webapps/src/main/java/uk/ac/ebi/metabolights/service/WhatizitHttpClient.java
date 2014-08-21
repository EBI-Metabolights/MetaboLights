/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 9/24/12 12:40 PM
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

package uk.ac.ebi.metabolights.service;

import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import org.apache.commons.httpclient.ChunkedInputStream;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * This is a trivial HTTP client that can be used to contact the WHATIZIT
 * pipelines over streamed HTTP. At the other end of the line there is a servlet
 * listening for the following format:
 * <p/>
 * For the sake of clarity the syntax rule follows:
 * <p/>
 * nameOfAProcessingPipeline <document
 * xmlns:xlink='http://www.w3.org/1999/xlink' xmlns:z='http://www.ebi.ac.uk/z'
 * source='Whatizit'> (<text> your text </text>)+ </document>
 * <p/>
 * The first line must contain the name of a pipeline followed by a linefeed.
 * The subsequent blocks of text must be wrapped with the text tags. All of the
 * text blocks must be wrapped with the document tags.
 * <p/>
 * Note: To invoque the client set the "http.keepAlive" parameter to false:
 * -Dhttp.keepAlive=false
 */
public class WhatizitHttpClient {

	private static Logger logger = Logger.getLogger(WhatizitHttpClient.class);

	// URL of the processing servlet
	public static final String SERVER_URL = "http://www.ebi.ac.uk/webservices/whatizit/pipe";
	public static final String XML_START = "<document xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:z=\"http://www.ebi.ac.uk/z\" source=\"Whatizit\">";
	public static final String XML_END = "</document>";

	protected boolean uploadDone;
	protected boolean downloadDone;
	protected HttpClient client;
	protected PostMethod httppost;

	public WhatizitHttpClient() throws MalformedURLException, IOException {
		this(SERVER_URL);
	}

	public WhatizitHttpClient(String urlStr) throws MalformedURLException,
			IOException {
		client = new HttpClient();
		// client.getParams().setParameter("http.socket.timeout", new
		// Integer(20));
		httppost = new PostMethod(urlStr);
		uploadDone = false;
		downloadDone = false;
	}

	public void upload(String in) throws IOException {
		if (uploadDone)
			throw new IOException("Upload done already.");

		httppost.setRequestEntity(new StringRequestEntity(in, "UTF-8", null));
		try {
			client.executeMethod(httppost);
			uploadDone = true;
		} catch (Exception e) {
			logger.error("Error uploading content to Whatizit servlet! " + e);
		}
	}

	public String download() throws IOException {
		String result = "";
		InputStream chunkedResponseData = null;
		if (downloadDone)
			throw new IOException("Download done already.");
		try {
			if (httppost.getStatusCode() == HttpStatus.SC_OK) {
				chunkedResponseData = new ChunkedInputStream(
						httppost.getResponseBodyAsStream());
				result = convertStreamToString(chunkedResponseData);
			} else {
				logger.error("Whatizit servlet failure with error status: "
						+ httppost.getStatusLine().toString());
			}
			downloadDone = true;
		} catch (Exception e) {
			logger.error("Error downloading content from Whatizit servlet! "
					+ e);
		} finally {
			if (chunkedResponseData != null) {
				chunkedResponseData.close();
			}
		}
		return result;
	}

	public void close() {
		if (httppost != null) {
			httppost.abort();
			httppost.releaseConnection();
		}
	}

	protected static String convertStreamToString(InputStream is)
			throws IOException {
		String result = "";
		if (is != null) {
			String line;
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				while ((line = reader.readLine()) != null) {
					result = result + line + "\n";
				}
			} finally {
				if (reader != null)
					reader.close();
			}

		}
		return result;
	}
}
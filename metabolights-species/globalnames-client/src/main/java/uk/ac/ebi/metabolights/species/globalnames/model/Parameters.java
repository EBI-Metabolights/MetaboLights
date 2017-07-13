/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2017-Jul-13
 * Modified by:   kenneth
 *
 * Copyright 2017 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.species.globalnames.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenneth on 13/07/2017.
 */
public class Parameters {
    String with_context;
    String header_only;
    String with_canonical_ranks;
    String with_vernaculars;
    String best_match_only;
    List<Integer> data_sources = new ArrayList<Integer>();
    List<Integer> preferred_data_sources = new ArrayList<Integer>();
    String resolve_once;

    public String getWith_context() {
        return with_context;
    }

    public void setWith_context(String with_context) {
        this.with_context = with_context;
    }

    public String getHeader_only() {
        return header_only;
    }

    public void setHeader_only(String header_only) {
        this.header_only = header_only;
    }

    public String getWith_canonical_ranks() {
        return with_canonical_ranks;
    }

    public void setWith_canonical_ranks(String with_canonical_ranks) {
        this.with_canonical_ranks = with_canonical_ranks;
    }

    public String getWith_vernaculars() {
        return with_vernaculars;
    }

    public void setWith_vernaculars(String with_vernaculars) {
        this.with_vernaculars = with_vernaculars;
    }

    public String getBest_match_only() {
        return best_match_only;
    }

    public void setBest_match_only(String best_match_only) {
        this.best_match_only = best_match_only;
    }

    public List<Integer> getData_sources() {
        return data_sources;
    }

    public void setData_sources(List<Integer> data_sources) {
        this.data_sources = data_sources;
    }

    public List<Integer> getPreferred_data_sources() {
        return preferred_data_sources;
    }

    public void setPreferred_data_sources(List<Integer> preferred_data_sources) {
        this.preferred_data_sources = preferred_data_sources;
    }

    public String getResolve_once() {
        return resolve_once;
    }

    public void setResolve_once(String resolve_once) {
        this.resolve_once = resolve_once;
    }
}

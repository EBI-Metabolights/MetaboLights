--
-- PostgreSQL database dump
--

-- Dumped from database version 10.9
-- Dumped by pg_dump version 10.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: isatab; Type: SCHEMA; Schema: -; Owner: isatab
--

CREATE SCHEMA isatab;


ALTER SCHEMA isatab OWNER TO isatab;

--
-- Name: _final_median(numeric[]); Type: FUNCTION; Schema: isatab; Owner: isatab
--

CREATE FUNCTION isatab._final_median(numeric[]) RETURNS numeric
    LANGUAGE sql IMMUTABLE
    AS $_$
   SELECT AVG(val)
   FROM (
     SELECT val
     FROM unnest($1) val
     ORDER BY 1
     LIMIT  2 - MOD(array_upper($1, 1), 2)
     OFFSET CEIL(array_upper($1, 1) / 2.0) - 1
   ) sub;
$_$;


ALTER FUNCTION isatab._final_median(numeric[]) OWNER TO isatab;

--
-- Name: numberofpipedentries(); Type: FUNCTION; Schema: isatab; Owner: isatab
--

CREATE FUNCTION isatab.numberofpipedentries() RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
part text;
parts text;
numberofparts integer;
BEGIN
  foreach part in array string_to_array(database_identifier,'|') from maf_info where database_identifier like '%|%'
  loop
    foreach parts in array part
    loop
      numberofparts := numberofparts + 1;
    end loop;  
  end loop;
  return numberofparts;
END;
$$;


ALTER FUNCTION isatab.numberofpipedentries() OWNER TO isatab;

--
-- Name: trigger_fct_db_seq_trigger(); Type: FUNCTION; Schema: isatab; Owner: isatab
--

CREATE FUNCTION isatab.trigger_fct_db_seq_trigger() RETURNS trigger
    LANGUAGE plpgsql SECURITY DEFINER
    AS $$
BEGIN
  if TG_OP = 'INSERT' then
    if NEW.id is null then
      select nextval('db_seq') into NEW.id ;
    end if;
  end if;
RETURN NEW;
end
$$;


ALTER FUNCTION isatab.trigger_fct_db_seq_trigger() OWNER TO isatab;

--
-- Name: trigger_fct_ml_stats_trg(); Type: FUNCTION; Schema: isatab; Owner: isatab
--

CREATE FUNCTION isatab.trigger_fct_ml_stats_trg() RETURNS trigger
    LANGUAGE plpgsql SECURITY DEFINER
    AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
      IF NEW.ID IS NULL THEN
        SELECT nextval('ml_stats_seq') INTO NEW.ID ;
      END IF;
  END IF;
RETURN NEW;
END
$$;


ALTER FUNCTION isatab.trigger_fct_ml_stats_trg() OWNER TO isatab;

--
-- Name: trigger_fct_ref_attribute_def_trg(); Type: FUNCTION; Schema: isatab; Owner: isatab
--

CREATE FUNCTION isatab.trigger_fct_ref_attribute_def_trg() RETURNS trigger
    LANGUAGE plpgsql SECURITY DEFINER
    AS $$
BEGIN  
   if TG_OP = 'INSERT' then 
      if NEW.ID is null then 
         select nextval('ref_attribute_def_seq') into NEW.ID ; 
      end if; 
   end if; 
RETURN NEW;
end
$$;


ALTER FUNCTION isatab.trigger_fct_ref_attribute_def_trg() OWNER TO isatab;

--
-- Name: trigger_fct_ref_attribute_trg(); Type: FUNCTION; Schema: isatab; Owner: isatab
--

CREATE FUNCTION isatab.trigger_fct_ref_attribute_trg() RETURNS trigger
    LANGUAGE plpgsql SECURITY DEFINER
    AS $$
BEGIN  
   if TG_OP = 'INSERT' then 
      if NEW.ID is null then 
         select nextval('ref_attribute_seq') into NEW.ID ; 
      end if; 
   end if; 
RETURN NEW;
end
$$;


ALTER FUNCTION isatab.trigger_fct_ref_attribute_trg() OWNER TO isatab;

--
-- Name: trigger_fct_ref_met_pathways_trg(); Type: FUNCTION; Schema: isatab; Owner: isatab
--

CREATE FUNCTION isatab.trigger_fct_ref_met_pathways_trg() RETURNS trigger
    LANGUAGE plpgsql SECURITY DEFINER
    AS $$
BEGIN  
   if TG_OP = 'INSERT' then 
      if NEW.ID is null then 
         select nextval('ref_met_pathways_seq') into NEW.ID ; 
      end if; 
   end if; 
RETURN NEW;
end
$$;


ALTER FUNCTION isatab.trigger_fct_ref_met_pathways_trg() OWNER TO isatab;

--
-- Name: trigger_fct_ref_met_spectra_seq_tr(); Type: FUNCTION; Schema: isatab; Owner: isatab
--

CREATE FUNCTION isatab.trigger_fct_ref_met_spectra_seq_tr() RETURNS trigger
    LANGUAGE plpgsql SECURITY DEFINER
    AS $$
BEGIN     if TG_OP = 'INSERT' then       if NEW.id is null then          select nextval('ref_met_spectra_seq') into NEW.id ;       end if;    end if; RETURN NEW;
end
$$;


ALTER FUNCTION isatab.trigger_fct_ref_met_spectra_seq_tr() OWNER TO isatab;

--
-- Name: trigger_fct_ref_met_to_species_hack_pk(); Type: FUNCTION; Schema: isatab; Owner: isatab
--

CREATE FUNCTION isatab.trigger_fct_ref_met_to_species_hack_pk() RETURNS trigger
    LANGUAGE plpgsql SECURITY DEFINER
    AS $$
BEGIN  
   if TG_OP = 'INSERT' then    
         select nextval('ref_met_to_species_seq') into NEW.id ; 
   end if; 
RETURN NEW;
end
$$;


ALTER FUNCTION isatab.trigger_fct_ref_met_to_species_hack_pk() OWNER TO isatab;

--
-- Name: trigger_fct_ref_met_to_species_pk(); Type: FUNCTION; Schema: isatab; Owner: isatab
--

CREATE FUNCTION isatab.trigger_fct_ref_met_to_species_pk() RETURNS trigger
    LANGUAGE plpgsql SECURITY DEFINER
    AS $$
BEGIN  
   if TG_OP = 'INSERT' then 
      if NEW.id is null then 
         select nextval('ref_met_to_species_seq') into NEW.id ; 
      end if; 
   end if; 
RETURN NEW;
end
$$;


ALTER FUNCTION isatab.trigger_fct_ref_met_to_species_pk() OWNER TO isatab;

--
-- Name: trigger_fct_ref_metabolite_trigger(); Type: FUNCTION; Schema: isatab; Owner: isatab
--

CREATE FUNCTION isatab.trigger_fct_ref_metabolite_trigger() RETURNS trigger
    LANGUAGE plpgsql SECURITY DEFINER
    AS $$
DECLARE
  new_id bigint;
BEGIN
  if TG_OP = 'INSERT' then
    if NEW.ID is null then
      select nextval('ref_metabolite_seq') into new_id ;
      NEW.id := new_id;
    end if;
    if NEW.ACC is null then
      NEW.acc := 'MTBLC'||new_id;
    end if;

  end if;
RETURN NEW;
end
$$;


ALTER FUNCTION isatab.trigger_fct_ref_metabolite_trigger() OWNER TO isatab;

--
-- Name: trigger_fct_ref_species_group_trg(); Type: FUNCTION; Schema: isatab; Owner: isatab
--

CREATE FUNCTION isatab.trigger_fct_ref_species_group_trg() RETURNS trigger
    LANGUAGE plpgsql SECURITY DEFINER
    AS $$
BEGIN  
   if TG_OP = 'INSERT' then 
      if NEW.id is null then 
         select nextval('ref_species_group_seq') into NEW.id ; 
      end if; 
   end if; 
RETURN NEW;
end
$$;


ALTER FUNCTION isatab.trigger_fct_ref_species_group_trg() OWNER TO isatab;

--
-- Name: trigger_fct_ref_species_members_trg(); Type: FUNCTION; Schema: isatab; Owner: isatab
--

CREATE FUNCTION isatab.trigger_fct_ref_species_members_trg() RETURNS trigger
    LANGUAGE plpgsql SECURITY DEFINER
    AS $$
BEGIN  
   if TG_OP = 'INSERT' then 
      if NEW.id is null then 
         select nextval('ref_species_members_seq') into NEW.id ; 
      end if; 
   end if; 
RETURN NEW;
end
$$;


ALTER FUNCTION isatab.trigger_fct_ref_species_members_trg() OWNER TO isatab;

--
-- Name: trigger_fct_ref_species_trg(); Type: FUNCTION; Schema: isatab; Owner: isatab
--

CREATE FUNCTION isatab.trigger_fct_ref_species_trg() RETURNS trigger
    LANGUAGE plpgsql SECURITY DEFINER
    AS $$
BEGIN  
   if TG_OP = 'INSERT' then 
      if NEW.id is null then 
         select nextval('ref_species_seq') into NEW.id ; 
      end if; 
   end if; 
RETURN NEW;
end
$$;


ALTER FUNCTION isatab.trigger_fct_ref_species_trg() OWNER TO isatab;

--
-- Name: trigger_fct_ref_xref_trg(); Type: FUNCTION; Schema: isatab; Owner: isatab
--

CREATE FUNCTION isatab.trigger_fct_ref_xref_trg() RETURNS trigger
    LANGUAGE plpgsql SECURITY DEFINER
    AS $$
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF NEW.ID IS NULL THEN
      SELECT nextval('ref_xref_seq1') INTO NEW.ID ;
    END IF;
  END COLUMN_SEQUENCES;
RETURN NEW;
END
$$;


ALTER FUNCTION isatab.trigger_fct_ref_xref_trg() OWNER TO isatab;

--
-- Name: trigger_fct_users_trg(); Type: FUNCTION; Schema: isatab; Owner: isatab
--

CREATE FUNCTION isatab.trigger_fct_users_trg() RETURNS trigger
    LANGUAGE plpgsql SECURITY DEFINER
    AS $$
BEGIN  
   if TG_OP = 'INSERT' then 
      --if NEW.id is null then 
         select nextval('user_detail_seq') into NEW.id ; 
      --end if; 
   end if; 
RETURN NEW;
end
$$;


ALTER FUNCTION isatab.trigger_fct_users_trg() OWNER TO isatab;

--
-- Name: median(numeric); Type: AGGREGATE; Schema: isatab; Owner: isatab
--

CREATE AGGREGATE isatab.median(numeric) (
    SFUNC = array_append,
    STYPE = numeric[],
    INITCOND = '{}',
    FINALFUNC = isatab._final_median
);


ALTER AGGREGATE isatab.median(numeric) OWNER TO isatab;

SET default_tablespace = '';

SET default_with_oids = false;


--
-- Name: db_seq; Type: SEQUENCE; Schema: isatab; Owner: isatab
--

CREATE SEQUENCE isatab.db_seq
    START WITH 261
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 20;


ALTER TABLE isatab.db_seq OWNER TO isatab;


--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: isatab; Owner: isatab
--

CREATE SEQUENCE isatab.hibernate_sequence
    START WITH 845
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 20;


ALTER TABLE isatab.hibernate_sequence OWNER TO isatab;

--
-- Name: metabolights_parameters; Type: TABLE; Schema: isatab; Owner: isatab
--

CREATE TABLE isatab.metabolights_parameters (
    name character varying(512) NOT NULL,
    value character varying(4000) NOT NULL
);


ALTER TABLE isatab.metabolights_parameters OWNER TO isatab;

--
-- Name: ml_stats; Type: TABLE; Schema: isatab; Owner: isatab
--

CREATE TABLE isatab.ml_stats (
    id bigint NOT NULL,
    page_section character varying(20) NOT NULL,
    str_name character varying(200) NOT NULL,
    str_value character varying(200) NOT NULL,
    sort_order bigint
);


ALTER TABLE isatab.ml_stats OWNER TO isatab;

--
-- Name: ml_stats_seq; Type: SEQUENCE; Schema: isatab; Owner: isatab
--

CREATE SEQUENCE isatab.ml_stats_seq
    START WITH 97993
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE isatab.ml_stats_seq OWNER TO isatab;

--
-- Name: ref_attribute; Type: TABLE; Schema: isatab; Owner: isatab
--

CREATE TABLE isatab.ref_attribute (
    id bigint NOT NULL,
    attribute_def_id bigint NOT NULL,
    spectra_id bigint,
    value character varying(4000),
    pathway_id numeric(38,0)
);


ALTER TABLE isatab.ref_attribute OWNER TO isatab;

--
-- Name: ref_attribute_def; Type: TABLE; Schema: isatab; Owner: isatab
--

CREATE TABLE isatab.ref_attribute_def (
    id bigint NOT NULL,
    name character varying(500),
    description character varying(500)
);


ALTER TABLE isatab.ref_attribute_def OWNER TO isatab;

--
-- Name: ref_attribute_def_seq; Type: SEQUENCE; Schema: isatab; Owner: isatab
--

CREATE SEQUENCE isatab.ref_attribute_def_seq
    START WITH 100
    INCREMENT BY 1
    MINVALUE 100
    NO MAXVALUE
    CACHE 20;


ALTER TABLE isatab.ref_attribute_def_seq OWNER TO isatab;

--
-- Name: ref_attribute_seq; Type: SEQUENCE; Schema: isatab; Owner: isatab
--

CREATE SEQUENCE isatab.ref_attribute_seq
    START WITH 103020
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 20;


ALTER TABLE isatab.ref_attribute_seq OWNER TO isatab;

--
-- Name: ref_db; Type: TABLE; Schema: isatab; Owner: isatab
--

CREATE TABLE isatab.ref_db (
    id numeric(38,0) NOT NULL,
    db_name character varying(50) NOT NULL
);


ALTER TABLE isatab.ref_db OWNER TO isatab;

--
-- Name: ref_met_pathways; Type: TABLE; Schema: isatab; Owner: isatab
--

CREATE TABLE isatab.ref_met_pathways (
    id bigint NOT NULL,
    pathway_db_id bigint NOT NULL,
    met_id bigint NOT NULL,
    name character varying(512) NOT NULL,
    path_to_pathway_file character varying(4000),
    species_id bigint NOT NULL
);


ALTER TABLE isatab.ref_met_pathways OWNER TO isatab;

--
-- Name: ref_met_pathways_seq; Type: SEQUENCE; Schema: isatab; Owner: isatab
--

CREATE SEQUENCE isatab.ref_met_pathways_seq
    START WITH 8000
    INCREMENT BY 1
    MINVALUE 8000
    NO MAXVALUE
    CACHE 20;


ALTER TABLE isatab.ref_met_pathways_seq OWNER TO isatab;

--
-- Name: ref_met_spectra; Type: TABLE; Schema: isatab; Owner: isatab
--

CREATE TABLE isatab.ref_met_spectra (
    id bigint NOT NULL,
    name character varying(2000) NOT NULL,
    path_to_json character varying(150) NOT NULL,
    spectra_type character varying(10) NOT NULL,
    met_id numeric(38,0) NOT NULL
);


ALTER TABLE isatab.ref_met_spectra OWNER TO isatab;

--
-- Name: ref_met_spectra_seq; Type: SEQUENCE; Schema: isatab; Owner: isatab
--

CREATE SEQUENCE isatab.ref_met_spectra_seq
    START WITH 500020
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 20;


ALTER TABLE isatab.ref_met_spectra_seq OWNER TO isatab;

--
-- Name: ref_met_to_species; Type: TABLE; Schema: isatab; Owner: isatab
--

CREATE TABLE isatab.ref_met_to_species (
    id bigint NOT NULL,
    met_id bigint NOT NULL,
    species_id bigint NOT NULL,
    ref_xref_id bigint,
    db_id numeric(32,0)
);


ALTER TABLE isatab.ref_met_to_species OWNER TO isatab;

--
-- Name: ref_met_to_species_seq; Type: SEQUENCE; Schema: isatab; Owner: isatab
--

CREATE SEQUENCE isatab.ref_met_to_species_seq
    START WITH 100000
    INCREMENT BY 1
    MINVALUE 100000
    NO MAXVALUE
    CACHE 20;


ALTER TABLE isatab.ref_met_to_species_seq OWNER TO isatab;

--
-- Name: ref_metabolite; Type: TABLE; Schema: isatab; Owner: isatab
--

CREATE TABLE isatab.ref_metabolite (
    id numeric(38,0) NOT NULL,
    acc character varying(2000) NOT NULL,
    name character varying(2000) NOT NULL,
    description character varying(2000),
    inchi character varying(2000),
    temp_id character varying(20),
    created_date timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
    updated_date timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
    iupac_names character varying(2000),
    formula character varying(100),
    has_species bigint DEFAULT 0,
    has_pathways bigint DEFAULT 0,
    has_reactions bigint DEFAULT 0,
    has_nmr bigint DEFAULT 0,
    has_ms bigint DEFAULT 0,
    has_literature bigint DEFAULT 0,
    inchikey character varying(2000)
);


ALTER TABLE isatab.ref_metabolite OWNER TO isatab;

--
-- Name: COLUMN ref_metabolite.iupac_names; Type: COMMENT; Schema: isatab; Owner: isatab
--

COMMENT ON COLUMN isatab.ref_metabolite.iupac_names IS 'Iupac names separated by pipe: |';


--
-- Name: ref_metabolite_seq; Type: SEQUENCE; Schema: isatab; Owner: isatab
--

CREATE SEQUENCE isatab.ref_metabolite_seq
    START WITH 2000000
    INCREMENT BY 1
    MINVALUE 2000000
    NO MAXVALUE
    CACHE 20;


ALTER TABLE isatab.ref_metabolite_seq OWNER TO isatab;

--
-- Name: ref_species; Type: TABLE; Schema: isatab; Owner: isatab
--

CREATE TABLE isatab.ref_species (
    id bigint NOT NULL,
    species character varying(512) NOT NULL,
    description character varying(4000),
    taxon character varying(512),
    final_id bigint,
    species_member bigint
);


ALTER TABLE isatab.ref_species OWNER TO isatab;

--
-- Name: ref_species_group; Type: TABLE; Schema: isatab; Owner: isatab
--

CREATE TABLE isatab.ref_species_group (
    name character varying(512) NOT NULL,
    id bigint NOT NULL,
    parent_id bigint
);


ALTER TABLE isatab.ref_species_group OWNER TO isatab;

--
-- Name: ref_species_group_seq; Type: SEQUENCE; Schema: isatab; Owner: isatab
--

CREATE SEQUENCE isatab.ref_species_group_seq
    START WITH 150
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE isatab.ref_species_group_seq OWNER TO isatab;

--
-- Name: ref_species_members; Type: TABLE; Schema: isatab; Owner: isatab
--

CREATE TABLE isatab.ref_species_members (
    id bigint NOT NULL,
    group_id bigint NOT NULL,
    parent_member_id bigint,
    taxon character varying(512) NOT NULL,
    taxon_desc character varying(512)
);


ALTER TABLE isatab.ref_species_members OWNER TO isatab;

--
-- Name: ref_species_members_seq; Type: SEQUENCE; Schema: isatab; Owner: isatab
--

CREATE SEQUENCE isatab.ref_species_members_seq
    START WITH 150
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE isatab.ref_species_members_seq OWNER TO isatab;

--
-- Name: ref_species_seq; Type: SEQUENCE; Schema: isatab; Owner: isatab
--

CREATE SEQUENCE isatab.ref_species_seq
    START WITH 10000
    INCREMENT BY 1
    MINVALUE 10000
    NO MAXVALUE
    CACHE 20;


ALTER TABLE isatab.ref_species_seq OWNER TO isatab;

--
-- Name: ref_xref; Type: TABLE; Schema: isatab; Owner: isatab
--

CREATE TABLE isatab.ref_xref (
    id bigint,
    acc character varying(20) NOT NULL,
    db_id bigint NOT NULL
);


ALTER TABLE isatab.ref_xref OWNER TO isatab;

--
-- Name: ref_xref_seq1; Type: SEQUENCE; Schema: isatab; Owner: isatab
--

CREATE SEQUENCE isatab.ref_xref_seq1
    START WITH 25000
    INCREMENT BY 1
    MINVALUE 25000
    NO MAXVALUE
    CACHE 20;


ALTER TABLE isatab.ref_xref_seq1 OWNER TO isatab;

--
-- Name: stableid; Type: TABLE; Schema: isatab; Owner: isatab
--

CREATE TABLE isatab.stableid (
    id bigint NOT NULL,
    prefix character varying(255),
    seq bigint
);


ALTER TABLE isatab.stableid OWNER TO isatab;

--
-- Name: studies; Type: TABLE; Schema: isatab; Owner: isatab
--

CREATE TABLE isatab.studies (
    id bigint NOT NULL,
    acc character varying(255),
    obfuscationcode character varying(255),
    releasedate timestamp without time zone NOT NULL,
    status bigint NOT NULL,
    studysize numeric(38,0),
    updatedate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone NOT NULL,
    submissiondate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone NOT NULL,
    validations text,
    studytype character varying(1000),
    curator character varying,
    override character varying,
    species character varying,
    sample_rows bigint,
    assay_rows bigint,
    maf_rows bigint,
    biostudies_acc character varying,
    placeholder character varying,
    validation_status character varying
);


ALTER TABLE isatab.studies OWNER TO isatab;


--
-- Name: study_user; Type: TABLE; Schema: isatab; Owner: isatab
--

CREATE TABLE isatab.study_user (
    userid bigint NOT NULL,
    studyid bigint NOT NULL
);


ALTER TABLE isatab.study_user OWNER TO isatab;

--
-- Name: user_detail_seq; Type: SEQUENCE; Schema: isatab; Owner: isatab
--

CREATE SEQUENCE isatab.user_detail_seq
    START WITH 3000
    INCREMENT BY 1
    MINVALUE 3000
    NO MAXVALUE
    CACHE 20;


ALTER TABLE isatab.user_detail_seq OWNER TO isatab;

--
-- Name: users; Type: TABLE; Schema: isatab; Owner: isatab
--

CREATE TABLE isatab.users (
    id bigint NOT NULL,
    address character varying(255),
    affiliation character varying(255),
    affiliationurl character varying(255),
    apitoken character varying(255),
    email character varying(255),
    firstname character varying(255),
    joindate timestamp without time zone,
    lastname character varying(255),
    password character varying(255),
    role bigint NOT NULL,
    status bigint NOT NULL,
    username character varying(255),
    orcid character varying(255),
    metaspace_api_key character varying(255)
);


ALTER TABLE isatab.users OWNER TO isatab;

--
-- Name: metabolights_parameters metabolights_parameters_pkey; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.metabolights_parameters
    ADD CONSTRAINT metabolights_parameters_pkey PRIMARY KEY (name);


--
-- Name: ml_stats ml_stats_pkey; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ml_stats
    ADD CONSTRAINT ml_stats_pkey PRIMARY KEY (id);


--
-- Name: ref_attribute_def ref_attribute_def_pkey; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_attribute_def
    ADD CONSTRAINT ref_attribute_def_pkey PRIMARY KEY (id);


--
-- Name: ref_attribute ref_attribute_pkey; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_attribute
    ADD CONSTRAINT ref_attribute_pkey PRIMARY KEY (id);


--
-- Name: ref_db ref_db_db_name_key; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_db
    ADD CONSTRAINT ref_db_db_name_key UNIQUE (db_name);


--
-- Name: ref_db ref_db_pkey; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_db
    ADD CONSTRAINT ref_db_pkey PRIMARY KEY (id);


--
-- Name: ref_met_pathways ref_met_pathways_pkey; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_met_pathways
    ADD CONSTRAINT ref_met_pathways_pkey PRIMARY KEY (id);


--
-- Name: ref_met_spectra ref_met_spectra_path_to_json_key; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_met_spectra
    ADD CONSTRAINT ref_met_spectra_path_to_json_key UNIQUE (path_to_json);


--
-- Name: ref_met_spectra ref_met_spectra_pkey; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_met_spectra
    ADD CONSTRAINT ref_met_spectra_pkey PRIMARY KEY (id);


--
-- Name: ref_met_to_species ref_met_to_species_met_id_species_id_ref_xref_id_key; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_met_to_species
    ADD CONSTRAINT ref_met_to_species_met_id_species_id_ref_xref_id_key UNIQUE (met_id, species_id, ref_xref_id);


--
-- Name: ref_met_to_species ref_met_to_species_pkey; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_met_to_species
    ADD CONSTRAINT ref_met_to_species_pkey PRIMARY KEY (id);


--
-- Name: ref_metabolite ref_metabolite_acc_key; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_metabolite
    ADD CONSTRAINT ref_metabolite_acc_key UNIQUE (acc);


--
-- Name: ref_metabolite ref_metabolite_pkey; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_metabolite
    ADD CONSTRAINT ref_metabolite_pkey PRIMARY KEY (id);


--
-- Name: ref_species_group ref_species_group_name_key; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_species_group
    ADD CONSTRAINT ref_species_group_name_key UNIQUE (name);


--
-- Name: ref_species_group ref_species_group_pkey; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_species_group
    ADD CONSTRAINT ref_species_group_pkey PRIMARY KEY (id);


--
-- Name: ref_species_members ref_species_members_pkey; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_species_members
    ADD CONSTRAINT ref_species_members_pkey PRIMARY KEY (id);


--
-- Name: ref_species_members ref_species_members_taxon_key; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_species_members
    ADD CONSTRAINT ref_species_members_taxon_key UNIQUE (taxon);


--
-- Name: ref_species ref_species_pkey; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_species
    ADD CONSTRAINT ref_species_pkey PRIMARY KEY (id);


--
-- Name: ref_species ref_species_taxon_key; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_species
    ADD CONSTRAINT ref_species_taxon_key UNIQUE (taxon);


--
-- Name: ref_xref ref_xref_acc_db_id_key; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_xref
    ADD CONSTRAINT ref_xref_acc_db_id_key UNIQUE (acc, db_id);


--
-- Name: ref_xref ref_xref_id_key; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_xref
    ADD CONSTRAINT ref_xref_id_key UNIQUE (id);


--
-- Name: stableid stableid_pkey; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.stableid
    ADD CONSTRAINT stableid_pkey PRIMARY KEY (id);


--
-- Name: studies studies_acc_key; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.studies
    ADD CONSTRAINT studies_acc_key UNIQUE (acc);


--
-- Name: studies studies_obfuscationcode_key; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.studies
    ADD CONSTRAINT studies_obfuscationcode_key UNIQUE (obfuscationcode);


--
-- Name: studies studies_pkey; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.studies
    ADD CONSTRAINT studies_pkey PRIMARY KEY (id);


--
-- Name: study_user study_user_pkey; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.study_user
    ADD CONSTRAINT study_user_pkey PRIMARY KEY (studyid, userid);


--
-- Name: users users_apitoken_key; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.users
    ADD CONSTRAINT users_apitoken_key UNIQUE (apitoken);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: users users_username_key; Type: CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- Name: ref_met_to_spe_uk; Type: INDEX; Schema: isatab; Owner: isatab
--

CREATE UNIQUE INDEX ref_met_to_spe_uk ON isatab.ref_met_to_species USING btree (met_id, species_id, ref_xref_id);


--
-- Name: ref_species_final_idx; Type: INDEX; Schema: isatab; Owner: isatab
--

CREATE INDEX ref_species_final_idx ON isatab.ref_species USING btree (final_id);


--
-- Name: ref_xref_uk1; Type: INDEX; Schema: isatab; Owner: isatab
--

CREATE UNIQUE INDEX ref_xref_uk1 ON isatab.ref_xref USING btree (acc, db_id);


--
-- Name: ref_db db_seq_trigger; Type: TRIGGER; Schema: isatab; Owner: isatab
--

CREATE TRIGGER db_seq_trigger BEFORE INSERT OR UPDATE ON isatab.ref_db FOR EACH ROW EXECUTE PROCEDURE isatab.trigger_fct_db_seq_trigger();


--
-- Name: ml_stats ml_stats_trg; Type: TRIGGER; Schema: isatab; Owner: isatab
--

CREATE TRIGGER ml_stats_trg BEFORE INSERT ON isatab.ml_stats FOR EACH ROW EXECUTE PROCEDURE isatab.trigger_fct_ml_stats_trg();


--
-- Name: ref_attribute_def ref_attribute_def_trg; Type: TRIGGER; Schema: isatab; Owner: isatab
--

CREATE TRIGGER ref_attribute_def_trg BEFORE INSERT ON isatab.ref_attribute_def FOR EACH ROW EXECUTE PROCEDURE isatab.trigger_fct_ref_attribute_def_trg();


--
-- Name: ref_attribute ref_attribute_trg; Type: TRIGGER; Schema: isatab; Owner: isatab
--

CREATE TRIGGER ref_attribute_trg BEFORE INSERT ON isatab.ref_attribute FOR EACH ROW EXECUTE PROCEDURE isatab.trigger_fct_ref_attribute_trg();


--
-- Name: ref_met_pathways ref_met_pathways_trg; Type: TRIGGER; Schema: isatab; Owner: isatab
--

CREATE TRIGGER ref_met_pathways_trg BEFORE INSERT ON isatab.ref_met_pathways FOR EACH ROW EXECUTE PROCEDURE isatab.trigger_fct_ref_met_pathways_trg();


--
-- Name: ref_met_spectra ref_met_spectra_seq_tr; Type: TRIGGER; Schema: isatab; Owner: isatab
--

CREATE TRIGGER ref_met_spectra_seq_tr BEFORE INSERT ON isatab.ref_met_spectra FOR EACH ROW EXECUTE PROCEDURE isatab.trigger_fct_ref_met_spectra_seq_tr();


--
-- Name: ref_met_to_species ref_met_to_species_hack_pk; Type: TRIGGER; Schema: isatab; Owner: isatab
--

CREATE TRIGGER ref_met_to_species_hack_pk BEFORE INSERT ON isatab.ref_met_to_species FOR EACH ROW EXECUTE PROCEDURE isatab.trigger_fct_ref_met_to_species_hack_pk();


--
-- Name: ref_met_to_species ref_met_to_species_pk; Type: TRIGGER; Schema: isatab; Owner: isatab
--

CREATE TRIGGER ref_met_to_species_pk BEFORE INSERT ON isatab.ref_met_to_species FOR EACH ROW EXECUTE PROCEDURE isatab.trigger_fct_ref_met_to_species_pk();


--
-- Name: ref_metabolite ref_metabolite_trigger; Type: TRIGGER; Schema: isatab; Owner: isatab
--

CREATE TRIGGER ref_metabolite_trigger BEFORE INSERT OR UPDATE ON isatab.ref_metabolite FOR EACH ROW EXECUTE PROCEDURE isatab.trigger_fct_ref_metabolite_trigger();


--
-- Name: ref_species_group ref_species_group_trg; Type: TRIGGER; Schema: isatab; Owner: isatab
--

CREATE TRIGGER ref_species_group_trg BEFORE INSERT ON isatab.ref_species_group FOR EACH ROW EXECUTE PROCEDURE isatab.trigger_fct_ref_species_group_trg();


--
-- Name: ref_species_members ref_species_members_trg; Type: TRIGGER; Schema: isatab; Owner: isatab
--

CREATE TRIGGER ref_species_members_trg BEFORE INSERT ON isatab.ref_species_members FOR EACH ROW EXECUTE PROCEDURE isatab.trigger_fct_ref_species_members_trg();


--
-- Name: ref_species ref_species_trg; Type: TRIGGER; Schema: isatab; Owner: isatab
--

CREATE TRIGGER ref_species_trg BEFORE INSERT ON isatab.ref_species FOR EACH ROW EXECUTE PROCEDURE isatab.trigger_fct_ref_species_trg();


--
-- Name: ref_xref ref_xref_trg; Type: TRIGGER; Schema: isatab; Owner: isatab
--

CREATE TRIGGER ref_xref_trg BEFORE INSERT ON isatab.ref_xref FOR EACH ROW EXECUTE PROCEDURE isatab.trigger_fct_ref_xref_trg();


--
-- Name: users users_trg; Type: TRIGGER; Schema: isatab; Owner: isatab
--

CREATE TRIGGER users_trg BEFORE INSERT ON isatab.users FOR EACH ROW EXECUTE PROCEDURE isatab.trigger_fct_users_trg();


--
-- Name: study_user fk_7rl2cwr6ys8u0re1ondnmy2cm; Type: FK CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.study_user
    ADD CONSTRAINT fk_7rl2cwr6ys8u0re1ondnmy2cm FOREIGN KEY (studyid) REFERENCES isatab.studies(id) ON DELETE CASCADE;


--
-- Name: study_user fk_r07d3wk0i2e8nvhbcagoyiqfk; Type: FK CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.study_user
    ADD CONSTRAINT fk_r07d3wk0i2e8nvhbcagoyiqfk FOREIGN KEY (userid) REFERENCES isatab.users(id) ON DELETE CASCADE;


--
-- Name: ref_met_pathways ref_met_pathways_db_fk; Type: FK CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_met_pathways
    ADD CONSTRAINT ref_met_pathways_db_fk FOREIGN KEY (pathway_db_id) REFERENCES isatab.ref_db(id);


--
-- Name: ref_met_pathways ref_met_pathways_met_fk; Type: FK CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_met_pathways
    ADD CONSTRAINT ref_met_pathways_met_fk FOREIGN KEY (met_id) REFERENCES isatab.ref_metabolite(id) ON DELETE CASCADE;


--
-- Name: ref_met_pathways ref_met_pathways_species_fk; Type: FK CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_met_pathways
    ADD CONSTRAINT ref_met_pathways_species_fk FOREIGN KEY (species_id) REFERENCES isatab.ref_species(id);


--
-- Name: ref_met_spectra ref_met_spectrum_ref_meta_fk1; Type: FK CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_met_spectra
    ADD CONSTRAINT ref_met_spectrum_ref_meta_fk1 FOREIGN KEY (met_id) REFERENCES isatab.ref_metabolite(id) ON DELETE CASCADE;


--
-- Name: ref_met_to_species ref_met_to_met_fk; Type: FK CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_met_to_species
    ADD CONSTRAINT ref_met_to_met_fk FOREIGN KEY (met_id) REFERENCES isatab.ref_metabolite(id) ON DELETE CASCADE;


--
-- Name: ref_met_to_species ref_met_to_species; Type: FK CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_met_to_species
    ADD CONSTRAINT ref_met_to_species FOREIGN KEY (species_id) REFERENCES isatab.ref_species(id);


--
-- Name: ref_met_to_species ref_met_to_xref; Type: FK CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_met_to_species
    ADD CONSTRAINT ref_met_to_xref FOREIGN KEY (ref_xref_id) REFERENCES isatab.ref_xref(id);


--
-- Name: ref_species ref_species_final_id; Type: FK CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_species
    ADD CONSTRAINT ref_species_final_id FOREIGN KEY (final_id) REFERENCES isatab.ref_species(id);


--
-- Name: ref_species_group ref_species_group_ref_spe_fk1; Type: FK CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_species_group
    ADD CONSTRAINT ref_species_group_ref_spe_fk1 FOREIGN KEY (parent_id) REFERENCES isatab.ref_species_group(id);


--
-- Name: ref_xref ref_xref_ref_db_fk1; Type: FK CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_xref
    ADD CONSTRAINT ref_xref_ref_db_fk1 FOREIGN KEY (db_id) REFERENCES isatab.ref_db(id);


--
-- Name: ref_species_members species_members_to_grp; Type: FK CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_species_members
    ADD CONSTRAINT species_members_to_grp FOREIGN KEY (group_id) REFERENCES isatab.ref_species_group(id);


--
-- Name: ref_species species_to_members; Type: FK CONSTRAINT; Schema: isatab; Owner: isatab
--

ALTER TABLE ONLY isatab.ref_species
    ADD CONSTRAINT species_to_members FOREIGN KEY (species_member) REFERENCES isatab.ref_species_members(id);


--
-- PostgreSQL database dump complete
--


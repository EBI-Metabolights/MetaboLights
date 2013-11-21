<?xml version="1.0" encoding="ISO-8859-1"?>
<definitions xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/" xmlns:tns="http://aphia/v1.0" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/" xmlns="http://schemas.xmlsoap.org/wsdl/" targetNamespace="http://aphia/v1.0">
<types>
<xsd:schema targetNamespace="http://aphia/v1.0"
>
 <xsd:import namespace="http://schemas.xmlsoap.org/soap/encoding/" />
 <xsd:import namespace="http://schemas.xmlsoap.org/wsdl/" />
 <xsd:complexType name="AphiaRecord">
  <xsd:all>
   <xsd:element name="AphiaID" type="xsd:int"/>
   <xsd:element name="url" type="xsd:string"/>
   <xsd:element name="scientificname" type="xsd:string"/>
   <xsd:element name="authority" type="xsd:string"/>
   <xsd:element name="rank" type="xsd:string"/>
   <xsd:element name="status" type="xsd:string"/>
   <xsd:element name="unacceptreason" type="xsd:string"/>
   <xsd:element name="valid_AphiaID" type="xsd:int"/>
   <xsd:element name="valid_name" type="xsd:string"/>
   <xsd:element name="valid_authority" type="xsd:string"/>
   <xsd:element name="kingdom" type="xsd:string"/>
   <xsd:element name="phylum" type="xsd:string"/>
   <xsd:element name="class" type="xsd:string"/>
   <xsd:element name="order" type="xsd:string"/>
   <xsd:element name="family" type="xsd:string"/>
   <xsd:element name="genus" type="xsd:string"/>
   <xsd:element name="citation" type="xsd:string"/>
   <xsd:element name="lsid" type="xsd:string"/>
   <xsd:element name="isMarine" type="xsd:int"/>
   <xsd:element name="isBrackish" type="xsd:int"/>
   <xsd:element name="isFreshwater" type="xsd:int"/>
   <xsd:element name="isTerrestrial" type="xsd:int"/>
   <xsd:element name="isExtinct" type="xsd:int"/>
   <xsd:element name="match_type" type="xsd:string"/>
   <xsd:element name="modified" type="xsd:string"/>
  </xsd:all>
 </xsd:complexType>
 <xsd:complexType name="AphiaRecords">
  <xsd:complexContent>
   <xsd:restriction base="SOAP-ENC:Array">
    <xsd:attribute ref="SOAP-ENC:arrayType" wsdl:arrayType="tns:AphiaRecord[]"/>
   </xsd:restriction>
  </xsd:complexContent>
 </xsd:complexType>
 <xsd:complexType name="AphiaMatches">
  <xsd:complexContent>
   <xsd:restriction base="SOAP-ENC:Array">
    <xsd:attribute ref="SOAP-ENC:arrayType" wsdl:arrayType="tns:AphiaRecords[]"/>
   </xsd:restriction>
  </xsd:complexContent>
 </xsd:complexType>
 <xsd:complexType name="scientificnames">
  <xsd:complexContent>
   <xsd:restriction base="SOAP-ENC:Array">
    <xsd:attribute ref="SOAP-ENC:arrayType" wsdl:arrayType="xsd:string[]"/>
   </xsd:restriction>
  </xsd:complexContent>
 </xsd:complexType>
 <xsd:complexType name="externalidentifiers">
  <xsd:complexContent>
   <xsd:restriction base="SOAP-ENC:Array">
    <xsd:attribute ref="SOAP-ENC:arrayType" wsdl:arrayType="xsd:string[]"/>
   </xsd:restriction>
  </xsd:complexContent>
 </xsd:complexType>
 <xsd:complexType name="Source">
  <xsd:all>
   <xsd:element name="source_id" type="xsd:int"/>
   <xsd:element name="use" type="xsd:string"/>
   <xsd:element name="reference" type="xsd:string"/>
   <xsd:element name="page" type="xsd:string"/>
   <xsd:element name="url" type="xsd:string"/>
   <xsd:element name="link" type="xsd:string"/>
   <xsd:element name="fulltext" type="xsd:string"/>
  </xsd:all>
 </xsd:complexType>
 <xsd:complexType name="Sources">
  <xsd:complexContent>
   <xsd:restriction base="SOAP-ENC:Array">
    <xsd:attribute ref="SOAP-ENC:arrayType" wsdl:arrayType="tns:Source[]"/>
   </xsd:restriction>
  </xsd:complexContent>
 </xsd:complexType>
 <xsd:complexType name="Classification">
  <xsd:all>
   <xsd:element name="AphiaID" type="xsd:int"/>
   <xsd:element name="rank" type="xsd:string"/>
   <xsd:element name="scientificname" type="xsd:string"/>
   <xsd:element name="child" type="tns:Classification"/>
  </xsd:all>
 </xsd:complexType>
 <xsd:complexType name="Vernacular">
  <xsd:all>
   <xsd:element name="vernacular" type="xsd:string"/>
   <xsd:element name="language_code" type="xsd:string"/>
   <xsd:element name="language" type="xsd:string"/>
  </xsd:all>
 </xsd:complexType>
 <xsd:complexType name="Vernaculars">
  <xsd:complexContent>
   <xsd:restriction base="SOAP-ENC:Array">
    <xsd:attribute ref="SOAP-ENC:arrayType" wsdl:arrayType="tns:Vernacular[]"/>
   </xsd:restriction>
  </xsd:complexContent>
 </xsd:complexType>
</xsd:schema>
</types>
<message name="getAphiaIDRequest">
  <part name="scientificname" type="xsd:string" />
  <part name="marine_only" type="xsd:boolean" /></message>
<message name="getAphiaIDResponse">
  <part name="return" type="xsd:int" /></message>
<message name="getAphiaRecordsRequest">
  <part name="scientificname" type="xsd:string" />
  <part name="like" type="xsd:boolean" />
  <part name="fuzzy" type="xsd:boolean" />
  <part name="marine_only" type="xsd:boolean" />
  <part name="offset" type="xsd:int" /></message>
<message name="getAphiaRecordsResponse">
  <part name="return" type="tns:AphiaRecords" /></message>
<message name="getAphiaNameByIDRequest">
  <part name="AphiaID" type="xsd:int" /></message>
<message name="getAphiaNameByIDResponse">
  <part name="return" type="xsd:string" /></message>
<message name="getAphiaRecordByIDRequest">
  <part name="AphiaID" type="xsd:int" /></message>
<message name="getAphiaRecordByIDResponse">
  <part name="return" type="tns:AphiaRecord" /></message>
<message name="getAphiaRecordByExtIDRequest">
  <part name="id" type="xsd:string" />
  <part name="type" type="xsd:string" /></message>
<message name="getAphiaRecordByExtIDResponse">
  <part name="return" type="tns:AphiaRecord" /></message>
<message name="getExtIDbyAphiaIDRequest">
  <part name="AphiaID" type="xsd:int" />
  <part name="type" type="xsd:string" /></message>
<message name="getExtIDbyAphiaIDResponse">
  <part name="return" type="tns:externalidentifiers" /></message>
<message name="getAphiaRecordsByNamesRequest">
  <part name="scientificnames" type="tns:scientificnames" />
  <part name="like" type="xsd:boolean" />
  <part name="fuzzy" type="xsd:boolean" />
  <part name="marine_only" type="xsd:boolean" /></message>
<message name="getAphiaRecordsByNamesResponse">
  <part name="return" type="tns:AphiaMatches" /></message>
<message name="getAphiaRecordsByVernacularRequest">
  <part name="vernacular" type="xsd:string" />
  <part name="like" type="xsd:boolean" />
  <part name="offset" type="xsd:int" /></message>
<message name="getAphiaRecordsByVernacularResponse">
  <part name="return" type="tns:AphiaRecords" /></message>
<message name="getAphiaRecordsByDateRequest">
  <part name="startdate" type="xsd:string" />
  <part name="enddate" type="xsd:string" />
  <part name="marine_only" type="xsd:boolean" />
  <part name="offset" type="xsd:int" /></message>
<message name="getAphiaRecordsByDateResponse">
  <part name="return" type="tns:AphiaRecords" /></message>
<message name="getAphiaClassificationByIDRequest">
  <part name="AphiaID" type="xsd:int" /></message>
<message name="getAphiaClassificationByIDResponse">
  <part name="return" type="tns:Classification" /></message>
<message name="getSourcesByAphiaIDRequest">
  <part name="AphiaID" type="xsd:int" /></message>
<message name="getSourcesByAphiaIDResponse">
  <part name="return" type="tns:Sources" /></message>
<message name="getAphiaSynonymsByIDRequest">
  <part name="AphiaID" type="xsd:int" /></message>
<message name="getAphiaSynonymsByIDResponse">
  <part name="return" type="tns:AphiaRecords" /></message>
<message name="getAphiaVernacularsByIDRequest">
  <part name="AphiaID" type="xsd:int" /></message>
<message name="getAphiaVernacularsByIDResponse">
  <part name="return" type="tns:Vernaculars" /></message>
<message name="getAphiaChildrenByIDRequest">
  <part name="AphiaID" type="xsd:int" />
  <part name="offset" type="xsd:int" />
  <part name="marine_only" type="xsd:boolean" /></message>
<message name="getAphiaChildrenByIDResponse">
  <part name="return" type="tns:AphiaRecords" /></message>
<message name="matchAphiaRecordsByNamesRequest">
  <part name="scientificnames" type="tns:scientificnames" />
  <part name="marine_only" type="xsd:boolean" /></message>
<message name="matchAphiaRecordsByNamesResponse">
  <part name="return" type="tns:AphiaMatches" /></message>
<portType name="AphiaNameServicePortType">
  <operation name="getAphiaID">
    <documentation>&lt;strong&gt;Get the (first) exact matching AphiaID for a given name.&lt;br/&gt;Parameters:
   &lt;ul&gt;
    &lt;li&gt;&lt;u&gt;marine_only&lt;/u&gt;: limit to marine taxa. Default=true.&lt;/li&gt;
   &lt;/ul&gt;
  &lt;/strong&gt;</documentation>
    <input message="tns:getAphiaIDRequest"/>
    <output message="tns:getAphiaIDResponse"/>
  </operation>
  <operation name="getAphiaRecords">
    <documentation>&lt;strong&gt;Get one or more matching (max. 50) AphiaRecords for a given name.&lt;br/&gt;Parameters:
   &lt;ul&gt;
    &lt;li&gt;&lt;u&gt;like&lt;/u&gt;: add a '%'-sign added after the ScientificName (SQL LIKE function). Default=true.&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;fuzzy&lt;/u&gt;: this parameter is deprecated (and ignored since 2013-07-17). Please use the function matchAphiaRecordsByNames() for fuzzy/near matching.&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;marine_only&lt;/u&gt;: limit to marine taxa. Default=true.&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;offset&lt;/u&gt;: starting recordnumber, when retrieving next chunck of (50) records. Default=1.&lt;/li&gt;
   &lt;/ul&gt;
  &lt;/strong&gt;&lt;br /&gt; For the structure of the returned AphiaRecords, please refer to the function getAphiaRecordByID()</documentation>
    <input message="tns:getAphiaRecordsRequest"/>
    <output message="tns:getAphiaRecordsResponse"/>
  </operation>
  <operation name="getAphiaNameByID">
    <documentation>&lt;strong&gt;Get the correct name for a given AphiaID&lt;/strong&gt;.</documentation>
    <input message="tns:getAphiaNameByIDRequest"/>
    <output message="tns:getAphiaNameByIDResponse"/>
  </operation>
  <operation name="getAphiaRecordByID">
    <documentation>&lt;strong&gt;Get the complete AphiaRecord for a given AphiaID.&lt;/strong&gt;&lt;br /&gt;
 The returned AphiaRecord has this format:&lt;br /&gt;&lt;ul&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;AphiaID&lt;/b&gt;&lt;/u&gt;: unique and persistent identifier within WoRMS. Primary key in the database.&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;url&lt;/b&gt;&lt;/u&gt;: HTTP URL to the AphiaRecord&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;scientificname&lt;/b&gt;&lt;/u&gt;: the full scientific name without authorship&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;authority&lt;/b&gt;&lt;/u&gt;: the authorship information for the scientificname formatted according to the conventions of the applicable nomenclaturalCode&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;rank&lt;/b&gt;&lt;/u&gt;: the taxonomic rank of the most specific name in the scientificname&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;status&lt;/b&gt;&lt;/u&gt;: the status of the use of the scientificname as a label for a taxon. Requires taxonomic opinion to define the scope of a taxon&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;unacceptreason&lt;/b&gt;&lt;/u&gt;: the reason why a scientificname is unaccepted&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;valid_AphiaID&lt;/b&gt;&lt;/u&gt;: the AphiaID (for the scientificname) of the currently accepted taxon&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;valid_name&lt;/b&gt;&lt;/u&gt;: the scientificname of the currently accepted taxon&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;valid_authority&lt;/b&gt;&lt;/u&gt;: the authorship information for the scientificname of the currently accepted taxon&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;kingdom&lt;/b&gt;&lt;/u&gt;: the full scientific name of the kingdom in which the taxon is classified&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;phylum&lt;/b&gt;&lt;/u&gt;: the full scientific name of the phylum or division in which the taxon is classified&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;class&lt;/b&gt;&lt;/u&gt;: the full scientific name of the class in which the taxon is classified&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;order&lt;/b&gt;&lt;/u&gt;: the full scientific name of the order in which the taxon is classified&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;family&lt;/b&gt;&lt;/u&gt;: the full scientific name of the family in which the taxon is classified&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;genus&lt;/b&gt;&lt;/u&gt;: the full scientific name of the genus in which the taxon is classified&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;citation&lt;/b&gt;&lt;/u&gt;: a bibliographic reference for the resource as a statement indicating how this record should be cited (attributed) when used&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;lsid&lt;/b&gt;&lt;/u&gt;: LifeScience Identifier. Persistent GUID for an AphiaID&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;isMarine&lt;/b&gt;&lt;/u&gt;: a boolean flag indicating whether the taxon is a marine organism, i.e. can be found in/above sea water. Possible values: 0/1/NULL&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;isBrackish&lt;/b&gt;&lt;/u&gt;: a boolean flag indicating whether the taxon occurrs in brackish habitats. Possible values: 0/1/NULL&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;isFreshwater&lt;/b&gt;&lt;/u&gt;: a boolean flag indicating whether the taxon occurrs in freshwater habitats, i.e. can be found in/above rivers or lakes. Possible values: 0/1/NULL&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;isTerrestrial&lt;/b&gt;&lt;/u&gt;: a boolean flag indicating the taxon is a terrestial organism, i.e. occurrs on land as opposed to the sea. Possible values: 0/1/NULL&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;isExtinct&lt;/b&gt;&lt;/u&gt;: a flag indicating an extinct organism. Possible values: 0/1/NUL&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;match_type&lt;/b&gt;&lt;/u&gt;: Type of match. Possible values: exact/like/phonetic/near_1/near_2&lt;/li&gt;&lt;li&gt;&lt;u&gt;&lt;b&gt;modified&lt;/b&gt;&lt;/u&gt;: The most recent date-time in GMT on which the resource was changed&lt;/li&gt;&lt;/ul&gt;</documentation>
    <input message="tns:getAphiaRecordByIDRequest"/>
    <output message="tns:getAphiaRecordByIDResponse"/>
  </operation>
  <operation name="getAphiaRecordByExtID">
    <documentation>&lt;strong&gt;Get the Aphia Record for a given external identifier.
&lt;br/&gt;Parameters:
&lt;ul&gt;
 &lt;li&gt;
  &lt;u&gt;type&lt;/u&gt;: Should have one of the following values:
   &lt;ul&gt;
    &lt;li&gt;&lt;u&gt;bold&lt;/u&gt;: Barcode of Life Database (BOLD) TaxID&lt;/li&gt;
    &lt;li&gt;&lt;u&gt;dyntaxa&lt;/u&gt;: Dyntaxa ID&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;eol&lt;/u&gt;: Encyclopedia of Life (EoL) page identifier&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;fishbase&lt;/u&gt;: FishBase species ID&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;iucn&lt;/u&gt;: IUCN Red List Identifier&lt;/li&gt;
    &lt;li&gt;&lt;u&gt;lsid&lt;/u&gt;: Life Science Identifier&lt;/li&gt;
    &lt;li&gt;&lt;u&gt;ncbi&lt;/u&gt;: NCBI Taxonomy ID (Genbank)&lt;/li&gt;
    &lt;li&gt;&lt;u&gt;tsn&lt;/u&gt;: ITIS Taxonomic Serial Number&lt;/li&gt;
  &lt;/ul&gt;
 &lt;/li&gt;
&lt;/ul&gt;&lt;/strong&gt;</documentation>
    <input message="tns:getAphiaRecordByExtIDRequest"/>
    <output message="tns:getAphiaRecordByExtIDResponse"/>
  </operation>
  <operation name="getExtIDbyAphiaID">
    <documentation>&lt;strong&gt;Get any external identifier(s) for a given AphiaID.
&lt;br/&gt;Parameters:
&lt;ul&gt;
 &lt;li&gt;
  &lt;u&gt;type&lt;/u&gt;: Should have one of the following values:
   &lt;ul&gt;
    &lt;li&gt;&lt;u&gt;bold&lt;/u&gt;: Barcode of Life Database (BOLD) TaxID&lt;/li&gt;
    &lt;li&gt;&lt;u&gt;dyntaxa&lt;/u&gt;: Dyntaxa ID&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;eol&lt;/u&gt;: Encyclopedia of Life (EoL) page identifier&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;fishbase&lt;/u&gt;: FishBase species ID&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;iucn&lt;/u&gt;: IUCN Red List Identifier&lt;/li&gt;
    &lt;li&gt;&lt;u&gt;lsid&lt;/u&gt;: Life Science Identifier&lt;/li&gt;
    &lt;li&gt;&lt;u&gt;ncbi&lt;/u&gt;: NCBI Taxonomy ID (Genbank)&lt;/li&gt;
    &lt;li&gt;&lt;u&gt;tsn&lt;/u&gt;: ITIS Taxonomic Serial Number&lt;/li&gt;
  &lt;/ul&gt;
 &lt;/li&gt;
&lt;/ul&gt;&lt;/strong&gt;</documentation>
    <input message="tns:getExtIDbyAphiaIDRequest"/>
    <output message="tns:getExtIDbyAphiaIDResponse"/>
  </operation>
  <operation name="getAphiaRecordsByNames">
    <documentation>&lt;strong&gt;For each given scientific name, try to find one or more AphiaRecords.&lt;br/&gt;
  This allows you to match multiple names in one call. Limited to 500 names at once for performance reasons.
  &lt;br/&gt;Parameters:
   &lt;ul&gt;
    &lt;li&gt;&lt;u&gt;like&lt;/u&gt;: add a '%'-sign after the ScientificName (SQL LIKE function). Default=false.&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;fuzzy&lt;/u&gt;: this parameter is deprecated (and ignored since 2013-07-17). Please use the function matchAphiaRecordsByNames() for fuzzy/near matching.&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;marine_only&lt;/u&gt;: limit to marine taxa. Default=true.&lt;/li&gt;
   &lt;/ul&gt;&lt;/strong&gt;
   &lt;br /&gt; For the structure of the returned AphiaRecords, please refer to the function getAphiaRecordByID()</documentation>
    <input message="tns:getAphiaRecordsByNamesRequest"/>
    <output message="tns:getAphiaRecordsByNamesResponse"/>
  </operation>
  <operation name="getAphiaRecordsByVernacular">
    <documentation>&lt;strong&gt;Get one or more Aphia Records (max. 50) for a given vernacular.&lt;/strong&gt;&lt;br/&gt;Parameters:
   &lt;ul&gt;
    &lt;li&gt;&lt;u&gt;like&lt;/u&gt;: add a '%'-sign before and after the input (SQL LIKE '%vernacular%' function). Default=false.&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;offset&lt;/u&gt;: starting record number, when retrieving next chunck of (50) records. Default=1.&lt;/li&gt;
   &lt;/ul&gt;
  &lt;/strong&gt;
  &lt;br /&gt; For the structure of the returned AphiaRecords, please refer to the function getAphiaRecordByID()</documentation>
    <input message="tns:getAphiaRecordsByVernacularRequest"/>
    <output message="tns:getAphiaRecordsByVernacularResponse"/>
  </operation>
  <operation name="getAphiaRecordsByDate">
    <documentation>&lt;strong&gt;Lists all AphiaRecords (taxa) modified or added between a specific time interval.&lt;br/&gt;
 &lt;br/&gt;Parameters:
  &lt;ul&gt;
   &lt;li&gt;&lt;u&gt;startdate&lt;/u&gt;: ISO 8601 formatted start date(time). Default=today(). i.e. 2013-11-21T09:33:49+00:00&lt;/li&gt;
   &lt;li&gt;&lt;u&gt;enddate&lt;/u&gt;: ISO 8601 formatted end date(time). Default=today().i.e. 2013-11-21T09:33:49+00:00&lt;/li&gt;
   &lt;li&gt;&lt;u&gt;marine_only&lt;/u&gt;: limit to marine taxa. Default=true.&lt;/li&gt;
   &lt;li&gt;&lt;u&gt;offset&lt;/u&gt;: starting record number, when retrieving next chunck of (50) records. Default=1.&lt;/li&gt;
  &lt;/ul&gt;&lt;/strong&gt;
  &lt;br /&gt; For the structure of the returned AphiaRecords, please refer to the function getAphiaRecordByID()</documentation>
    <input message="tns:getAphiaRecordsByDateRequest"/>
    <output message="tns:getAphiaRecordsByDateResponse"/>
  </operation>
  <operation name="getAphiaClassificationByID">
    <documentation>&lt;strong&gt;Get the complete classification for one taxon. This also includes any sub or super ranks.&lt;/strong&gt;</documentation>
    <input message="tns:getAphiaClassificationByIDRequest"/>
    <output message="tns:getAphiaClassificationByIDResponse"/>
  </operation>
  <operation name="getSourcesByAphiaID">
    <documentation>&lt;strong&gt;Get one or more sources/references including links, for one AphiaID&lt;/strong&gt;</documentation>
    <input message="tns:getSourcesByAphiaIDRequest"/>
    <output message="tns:getSourcesByAphiaIDResponse"/>
  </operation>
  <operation name="getAphiaSynonymsByID">
    <documentation>&lt;strong&gt;Get all synonyms for a given AphiaID.&lt;/strong&gt;</documentation>
    <input message="tns:getAphiaSynonymsByIDRequest"/>
    <output message="tns:getAphiaSynonymsByIDResponse"/>
  </operation>
  <operation name="getAphiaVernacularsByID">
    <documentation>&lt;strong&gt;Get all vernaculars for a given AphiaID.&lt;/strong&gt;</documentation>
    <input message="tns:getAphiaVernacularsByIDRequest"/>
    <output message="tns:getAphiaVernacularsByIDResponse"/>
  </operation>
  <operation name="getAphiaChildrenByID">
    <documentation>&lt;strong&gt;Get the direct children (max. 50) for a given AphiaID.&lt;/strong&gt;&lt;br /&gt;Parameters:
   &lt;ul&gt;
	&lt;li&gt;&lt;u&gt;offset&lt;/u&gt;: starting record number, when retrieving next chunck of (50) records. Default=1.&lt;/li&gt;
	&lt;li&gt;&lt;u&gt;marine_only&lt;/u&gt;: limit to marine taxa. Default=true.&lt;/li&gt;
   &lt;/ul&gt;</documentation>
    <input message="tns:getAphiaChildrenByIDRequest"/>
    <output message="tns:getAphiaChildrenByIDResponse"/>
  </operation>
  <operation name="matchAphiaRecordsByNames">
    <documentation>&lt;strong&gt;For each given scientific name (may include authority), try to find one or more AphiaRecords, using the TAXAMATCH fuzzy matching algorithm by Tony Rees.&lt;br/&gt;
 This allows you to (fuzzy) match multiple names in one call. Limited to 50 names at once for performance reasons.
 &lt;br/&gt;Parameters:
  &lt;ul&gt;
	&lt;li&gt;&lt;u&gt;marine_only&lt;/u&gt;: limit to marine taxa. Default=true.&lt;/li&gt;
  &lt;/ul&gt;&lt;/strong&gt;
  &lt;br /&gt; For the structure of the returned AphiaRecords, please refer to the function getAphiaRecordByID()</documentation>
    <input message="tns:matchAphiaRecordsByNamesRequest"/>
    <output message="tns:matchAphiaRecordsByNamesResponse"/>
  </operation>
</portType>
<binding name="AphiaNameServiceBinding" type="tns:AphiaNameServicePortType">
  <soap:binding style="rpc" transport="http://schemas.xmlsoap.org/soap/http"/>
  <operation name="getAphiaID">
    <soap:operation soapAction="getAphiaID" style="rpc"/>
    <input><soap:body use="encoded" namespace="http://aphia/v1.0/AphiaID" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></input>
    <output><soap:body use="encoded" namespace="http://aphia/v1.0/AphiaID" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></output>
  </operation>
  <operation name="getAphiaRecords">
    <soap:operation soapAction="getAphiaRecords" style="rpc"/>
    <input><soap:body use="encoded" namespace="http://aphia/v1.0/AphiaRecords" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></input>
    <output><soap:body use="encoded" namespace="http://aphia/v1.0/AphiaRecords" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></output>
  </operation>
  <operation name="getAphiaNameByID">
    <soap:operation soapAction="getAphiaNameByID" style="rpc"/>
    <input><soap:body use="encoded" namespace="http://aphia/v1.0/AphiaName" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></input>
    <output><soap:body use="encoded" namespace="http://aphia/v1.0/AphiaName" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></output>
  </operation>
  <operation name="getAphiaRecordByID">
    <soap:operation soapAction="getAphiaRecordByID" style="rpc"/>
    <input><soap:body use="encoded" namespace="http://aphia/v1.0/AphiaRecord" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></input>
    <output><soap:body use="encoded" namespace="http://aphia/v1.0/AphiaRecord" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></output>
  </operation>
  <operation name="getAphiaRecordByExtID">
    <soap:operation soapAction="getAphiaRecordByExtID" style="rpc"/>
    <input><soap:body use="encoded" namespace="http://aphia/v1.0/AphiaRecord" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></input>
    <output><soap:body use="encoded" namespace="http://aphia/v1.0/AphiaRecord" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></output>
  </operation>
  <operation name="getExtIDbyAphiaID">
    <soap:operation soapAction="getExtIDbyAphiaID" style="rpc"/>
    <input><soap:body use="encoded" namespace="http://aphia/v1.0/externalidentifiers" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></input>
    <output><soap:body use="encoded" namespace="http://aphia/v1.0/externalidentifiers" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></output>
  </operation>
  <operation name="getAphiaRecordsByNames">
    <soap:operation soapAction="getAphiaRecordsByNames" style="rpc"/>
    <input><soap:body use="encoded" namespace="http://aphia/v1.0/AphiaMatches" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></input>
    <output><soap:body use="encoded" namespace="http://aphia/v1.0/AphiaMatches" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></output>
  </operation>
  <operation name="getAphiaRecordsByVernacular">
    <soap:operation soapAction="getAphiaRecordsByVernacular" style="rpc"/>
    <input><soap:body use="encoded" namespace="http://aphia/v1.0/AphiaRecords" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></input>
    <output><soap:body use="encoded" namespace="http://aphia/v1.0/AphiaRecords" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></output>
  </operation>
  <operation name="getAphiaRecordsByDate">
    <soap:operation soapAction="getAphiaRecordsByDate" style="rpc"/>
    <input><soap:body use="encoded" namespace="http://aphia/v1.0/AphiaRecords" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></input>
    <output><soap:body use="encoded" namespace="http://aphia/v1.0/AphiaRecords" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></output>
  </operation>
  <operation name="getAphiaClassificationByID">
    <soap:operation soapAction="getAphiaClassificationByID" style="rpc"/>
    <input><soap:body use="encoded" namespace="http://aphia/v1.0/Classification" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></input>
    <output><soap:body use="encoded" namespace="http://aphia/v1.0/Classification" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></output>
  </operation>
  <operation name="getSourcesByAphiaID">
    <soap:operation soapAction="getSourcesByAphiaID" style="rpc"/>
    <input><soap:body use="encoded" namespace="http://aphia/v1.0/Sources" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></input>
    <output><soap:body use="encoded" namespace="http://aphia/v1.0/Sources" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></output>
  </operation>
  <operation name="getAphiaSynonymsByID">
    <soap:operation soapAction="getAphiaSynonymsByID" style="rpc"/>
    <input><soap:body use="encoded" namespace="http://aphia/v1.0/AphiaRecords" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></input>
    <output><soap:body use="encoded" namespace="http://aphia/v1.0/AphiaRecords" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></output>
  </operation>
  <operation name="getAphiaVernacularsByID">
    <soap:operation soapAction="getAphiaVernacularsByID" style="rpc"/>
    <input><soap:body use="encoded" namespace="http://aphia/v1.0/AphiaVernaculars" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></input>
    <output><soap:body use="encoded" namespace="http://aphia/v1.0/AphiaVernaculars" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></output>
  </operation>
  <operation name="getAphiaChildrenByID">
    <soap:operation soapAction="getAphiaChildrenByID" style="rpc"/>
    <input><soap:body use="encoded" namespace="http://aphia/v1.0/AphiaRecords" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></input>
    <output><soap:body use="encoded" namespace="http://aphia/v1.0/AphiaRecords" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></output>
  </operation>
  <operation name="matchAphiaRecordsByNames">
    <soap:operation soapAction="matchAphiaRecordsByNames" style="rpc"/>
    <input><soap:body use="encoded" namespace="http://aphia/v1.0/AphiaMatches" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></input>
    <output><soap:body use="encoded" namespace="http://aphia/v1.0/AphiaMatches" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/></output>
  </operation>
</binding>
<service name="AphiaNameService">
<documentation>The data is licensed under a Creative Commons 'BY' 3.0 License, see http://creativecommons.org/licenses/by/3.0/deed.en. For more information, please visit http://www.marinespecies.org/aphia.php?p=webservice. </documentation>
  <port name="AphiaNameServicePort" binding="tns:AphiaNameServiceBinding">
    <soap:address location="http://www.marinespecies.org/aphia.php?p=soap"/>
  </port>
</service>
</definitions>
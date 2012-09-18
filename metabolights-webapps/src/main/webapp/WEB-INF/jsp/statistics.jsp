<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!--
-- Studies
select 'Studies', count(*) from study group by 'Studies'
-- Assays
select Count(*)  from ASSAY
-- Protocols
Select  Count(*) from PROTOCOL where DESCRIPTION IS NOT NULL
-- Metabolites identified
select Count(*) from METABOLITE

-- Metabolites summary
SELECT DB, COunt(*) as Total FROM (select CASE
WHEN instr(identifier,'CHEBI:')=1 THEN 'CHEBI'
WHEN instr(identifier,'CID')=1 THEN 'PUBCHEM'
WHEN instr(identifier,'HMDB')=1 THEN 'HMDB'
WHEN instr(identifier,'LM')=1 THEN 'LIPID MAPS'
WHEN instr(identifier,'C')=1 THEN 'KEGG'
WHEN identifier IS NULL THEN 'NO ID'
ELSE identifier
END AS DB  from METABOLITE)
group by DB

-- USers
select COunt(*) from user_detail
 -->


<div class="text_header plain">
    <spring:message code="msg.statistics" />
</div>

<br/><br/>
<H1>Data in MetboLights</H1>
Number of studies: <b>15</b> (10 Public, 5 Private)<br/>
Number of protocols: <b>93</b><br/>
Number of assays: <b>714</b><br/>
Different organisms: <b>8</b><br/>

<br/><br/>
<H1>Metabolites identified</H1>
<table>
  <tr>
    <th>Database</th>
    <th>Total</th>
  </tr>
  <tr>
    <td>ChEBI</td>
    <td>827</td>
  </tr>
  <tr>
    <td>No id</td>
    <td>383</td>
  </tr>
  <tr>
    <td>HMDB</td>
    <td>136</td>
  </tr>
  <tr>
    <td>PubChem</td>
    <td>110</td>
  </tr>
  <tr>
    <td>KEGG</td>
    <td>4</td>
  </tr>
  <tr>
    <td>LIPID MAPS</td>
    <td>3</td>
  </tr>
  <tr>
    <th>6 databases</th>
    <th>1463</th>
  </tr>
</table>

<br/><br/>
<H1>Submitters</H1>
Number of registered users: 38

<br/><br/>
<H1>Online access</H1>
Comming soon!
<br/>
<br/>
<br/>
Generated on the 13th September 2012
<br/>



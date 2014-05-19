<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:z="http://www.ebi.ac.uk/z"
		xmlns:xlink='http://www.w3.org/1999/xlink'
		version="1.0">
 <xsl:template name="replace-string">
    <xsl:param name="text"/>
    <xsl:param name="replace"/>
    <xsl:param name="with"/>
    <xsl:choose>
      <xsl:when test="contains($text,$replace)">
        <xsl:value-of select="substring-before($text,$replace)"/>
        <xsl:value-of select="$with"/>
        <xsl:call-template name="replace-string">
          <xsl:with-param name="text"
select="substring-after($text,$replace)"/>
          <xsl:with-param name="replace" select="$replace"/>
          <xsl:with-param name="with" select="$with"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$text"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
 
  <xsl:output method="html" encoding="utf-8"/>

  <xsl:template match="@*|node()">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()" />
    </xsl:copy>
  </xsl:template>      

  <xsl:template match="document">
    <p align="justify">
      <xsl:apply-templates/>
    </p>
  </xsl:template>

  <xsl:template match="documentBody">
    <xsl:apply-templates/>
  </xsl:template>

  <xsl:template match="textmine">
    <xsl:apply-templates/>
  </xsl:template>

  <xsl:template match="text">
    <xsl:apply-templates/>
  </xsl:template>

  <xsl:template match="plain">
    <xsl:apply-templates/>
  </xsl:template>

  <xsl:template match="SENT">
    <xsl:apply-templates/><xsl:value-of select="@pm"/>
  </xsl:template>

  <xsl:template match="z:uniprot">
  <xsl:choose>
  <xsl:when test="(number(@fb) &lt; 160) and not(number(@abbrscore) &lt; 0)">
    <span title="UniProt">

    <xsl:variable name="orSeparated">
      <xsl:call-template name="replace-string">
        <xsl:with-param name="text" select="@ids"/>
        <xsl:with-param name="replace" select="','"/>
        <xsl:with-param name="with" select="'+OR+key:'"/>
      </xsl:call-template>
    </xsl:variable>

    <a style="text-decoration:none; color:#717aec; font-weight:normal"
       href="http://beta.uniprot.org/uniprot/?query=key:{$orSeparated}">

<!--    <a style="text-decoration:none; color:#717aec; font-weight:normal" 
       href="http://www.ebi.uniprot.org/uniprot-srv/elSearch.do?querytext={@ids}">-->
       <xsl:value-of select="." disable-output-escaping="yes"/>
    </a>
    </span>
    <xsl:if test="@meaning != ''">
      <span style="font-style:italic;" title="This acronym means: {@meaning}">
        <img src="img/d.png" height="14" width="14" border="0" />
      </span>
    </xsl:if>
  </xsl:when>
  <xsl:otherwise>
    <span title="UniProt">
    <xsl:value-of select="." disable-output-escaping="yes"/>
    </span>
  </xsl:otherwise>
  </xsl:choose>
  </xsl:template>

  <xsl:template match="z:go">
    <span title="Gene Ontology">
    <a style="text-decoration:none; color:#ff7d5f; font-weight:normal" 
       href="http://www.ebi.ac.uk/ego/GTerm?id={@ids}">
      <xsl:value-of select="." disable-output-escaping="yes"/>
    </a>
    </span>
  </xsl:template>

  <xsl:template match="z:drug">
    <span title="Drug" style="text-decoration:none; color:green; font-weight:normal">

    <xsl:choose>
      <xsl:when test="contains(@ids,',')">
        <xsl:value-of select="." disable-output-escaping="yes"/>
        <xsl:text>(&#xA0;</xsl:text>
        <xsl:call-template name="drugBankList">
          <xsl:with-param name="str" select="@ids"/>
        </xsl:call-template>
        <xsl:text>)</xsl:text>
      </xsl:when>
      <xsl:otherwise>
        <a style="text-decoration:none; color:green; font-weight:normal"
           href="http://redpoll.pharmacy.ualberta.ca/drugbank/cgi-bin/getCard.cgi?CARD={@ids}">
          <xsl:value-of select="." disable-output-escaping="yes"/>
        </a>
      </xsl:otherwise>
    </xsl:choose>
    </span>
  </xsl:template>

  <xsl:template name="drugBankList">
    <xsl:param name="str"/>
    <xsl:choose>
      <xsl:when test="contains($str,',')">
        <a style="text-decoration:none; color:green; font-weight:normal" href="http://redpoll.pharmacy.ualberta.ca/drugbank/cgi-bin/getCard.cgi?CARD={substring-before($str,',')}"><xsl:value-of select="substring-before($str,',')"/></a>
        <xsl:text>&#xA0;</xsl:text>
        <xsl:call-template name="drugBankList">
          <xsl:with-param name="str" select="substring-after($str,',')"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <a style="text-decoration:none; color:green; font-weight:normal" href="http://redpoll.pharmacy.ualberta.ca/drugbank/cgi-bin/getCard.cgi?CARD={$str}"><xsl:value-of select="$str"/></a>
        <xsl:text>&#xA0;</xsl:text>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match="z:species">
    <span title="Species">
    <a style="text-decoration:none; color:#d2495d; font-weight:normal" 
       href="http://www.ncbi.nih.gov/Taxonomy/Browser/wwwtax.cgi?id={@ids}&amp;lvl=0">
       <xsl:value-of select="." disable-output-escaping="yes"/>
    </a>
    </span>
  </xsl:template>

  <xsl:template match="z:disease">
    <span title="Disease">
    <a style="text-decoration:none; color:purple; font-weight:normal" 
       href="http://www.healthcentral.com/ency/408/{@ids}.html">
      <xsl:value-of select="." disable-output-escaping="yes"/>
    </a>
    </span>
  </xsl:template>

  <xsl:template match="z:symptom">
    <span title="Symptom">
    <a style="text-decoration:none; color:magenta; font-weight:normal" 
       href="http://www.healthcentral.com/ency/408/{@ids}.html">
      <xsl:value-of select="." disable-output-escaping="yes"/>
    </a>
    </span>
  </xsl:template>
</xsl:stylesheet>

<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:z="http://www.ebi.ac.uk/z"
		xmlns:xlink='http://www.w3.org/1999/xlink'
		version="1.0">

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
    <a style="text-decoration:none; color:#717aec; font-weight:normal" 
       href="http://www.ebi.uniprot.org/uniprot-srv/elSearch.do?querytext={@ids}">
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
        <a style="text-decoration:none; color:green; font-weight:normal" href="http://redpoll.pharmacy.ualberta.ca/drugbank/
cgi-bin/getCard.cgi?CARD={substring-before($str,',')}"><xsl:value-of select="substring-before($str,',')"/></a>
        <xsl:text>&#xA0;</xsl:text>
        <xsl:call-template name="drugBankList">
          <xsl:with-param name="str" select="substring-after($str,',')"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <a style="text-decoration:none; color:green; font-weight:normal" href="http://redpoll.pharmacy.ualberta.ca/drugbank/
cgi-bin/getCard.cgi?CARD={$str}"><xsl:value-of select="$str"/></a>
        <xsl:text>&#xA0;</xsl:text>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match="z:chebi">
    <span title="Chemical Entity of Biological Interest">
    <a style="text-decoration:none;	color: #006666;background: #eef5f5;text-decoration:underline;" 
       href="http://www.ebi.ac.uk/chebi/searchId.do?chebiId=CHEBI%3A{@ids}">
      <xsl:value-of select="." disable-output-escaping="yes"/>
    </a>
    </span>
  </xsl:template>

  <xsl:template match="z:e">
    <xsl:choose>
    <xsl:when test="@sem = 'chemical'">
      <span title="Chemical" style="text-decoration:none; color:#b13434; font-weight:normal">
        <xsl:value-of select="." disable-output-escaping="yes"/>
      </span>
    </xsl:when>

    <xsl:when test="@sem = 'enzyme'">
      <span title="Enzyme">
      <a style="text-decoration:none; color:#ae56e5; font-weight:normal" href="http://www.ebi.ac.uk/intenz/query?cmd=SearchEC&amp;ec={@ids}">
        <xsl:value-of select="." disable-output-escaping="yes"/>
      </a>
      </span>
    </xsl:when>
    </xsl:choose>
  </xsl:template>

</xsl:stylesheet>

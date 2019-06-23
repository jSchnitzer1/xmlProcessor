<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : CompanyInfoTransformation.xsl
    Created on : January 30, 2017, 7:53 PM
    Author     : khaled
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:c="http://xml.hm1.org/schema/CompanyInfoSchema">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <xsl:for-each select="/c:companyInfo/c:company">
            
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>

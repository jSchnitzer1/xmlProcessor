<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="/">
        <xsl:element name="GPA">
            <xsl:value-of select="sum(courseList/course/totalGrades) div sum(courseList/course/credit)"/>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>
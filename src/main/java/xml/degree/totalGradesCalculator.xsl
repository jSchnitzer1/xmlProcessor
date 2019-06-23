<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:dt="http://xml.hm1.org/schema/DegreeAndTranscript">
    <xsl:output method="xml" indent="yes"/>
    <!--
    mark   level   GPA
    90-100  A       4
    80-89   B       3
    70-79   C       2
    60-69   D       1
    <60     F       0
-->
    <xsl:template match="/">
        <xsl:element name="courseList">
            <xsl:for-each select="dt:degreeAndTranscript/dt:academicCourses/dt:course">
                <xsl:element name="course">
                    <xsl:element name="courseName">
                        <xsl:value-of select="dt:name"/>
                    </xsl:element>
                    <xsl:element name="mark">
                        <xsl:value-of select="dt:mark"/>
                    </xsl:element>
                    <xsl:element name="credit">
                        <xsl:value-of select="dt:credit"/>
                    </xsl:element>
                    <xsl:element name="totalGrades">
                        <xsl:choose>
                            <xsl:when test="dt:mark &gt; 89 and dt:mark &lt; 101">
                                <xsl:value-of select="4 * dt:credit"/>
                            </xsl:when>
                            <xsl:when test="dt:mark &gt; 79 and dt:mark &lt; 90">
                                <xsl:value-of select="3 * dt:credit"/>
                            </xsl:when>
                            <xsl:when test="dt:mark &gt; 69 and dt:mark &lt; 80">
                                <xsl:value-of select="2 * dt:credit"/>
                            </xsl:when>
                            <xsl:when test="dt:mark &gt; 59 and dt:mark &lt; 70">
                                <xsl:value-of select="1 * dt:credit"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="0"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:element>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>
<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : ApplicationProfileMaker.xsl
    Created on : January 30, 2017, 9:12 PM
    Author     : khaled
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:c="http://xml.hm1.org/schema/CompanyInfoSchema"
                xmlns:new_dt="http://xml.hm1.org/schema/DegreeAndTranscript"
                xmlns:new_emp="http://xml.hm1.org/schema/EmploymentRecordSchema"
                xmlns:m="http://xml.hm1.org/schema/ShortCV"
                xmlns:p="http://xml.hm1.org/schema/ApplicantProfileSchema">
    

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    

    <xsl:template match="/">
        <xsl:element name="p:profile">
            <xsl:variable name="name" select="document('EmploymentRecord.xml')/new_emp:employmentRecord/new_emp:fullEmployeeName"/>
            <xsl:variable name="age" select="document('EmploymentRecord.xml')/new_emp:employmentRecord/new_emp:age"/>
            <xsl:variable name="phone" select="document('EmploymentRecord.xml')/new_emp:employmentRecord/new_emp:phone"/>
            <xsl:variable name="email" select="document('EmploymentRecord.xml')/new_emp:employmentRecord/new_emp:emailAddress"/>
            <xsl:variable name="address" select="document('EmploymentRecord.xml')/new_emp:employmentRecord/new_emp:address"/>
            <xsl:variable name="employer" select="document('EmploymentRecord.xml')/new_emp:employmentRecord/new_emp:employer"/>
            
            
            <xsl:element name="p:cv">
                <xsl:element name="p:name">
                    <xsl:value-of select="$name" />
                </xsl:element>
                <xsl:element name="p:age">
                    <xsl:value-of select="$age" />
                </xsl:element>
                <xsl:element name="p:tel">
                    <xsl:value-of select="$phone" />
                </xsl:element>
                <xsl:element name="p:email">
                    <xsl:value-of select="$email" />
                </xsl:element>
                <xsl:element name="p:address">
                    <xsl:value-of select="$address" />
                </xsl:element>
                <xsl:element name="p:qualifications">
                    <xsl:for-each select="$employer">
                        <xsl:for-each select="new_emp:skills">
                            <xsl:element name="p:skill">
                                <xsl:value-of select="new_emp:skillName" />
                            </xsl:element>
                        </xsl:for-each>
                    </xsl:for-each>
                </xsl:element>
            </xsl:element>
            <xsl:element name="p:education">
                <xsl:element name="p:eduItem">
                    <xsl:element name="p:degree">
                        <xsl:value-of select="document('DegreeAndTranscript.xml')/new_dt:degreeAndTranscript/new_dt:faculty" />
                    </xsl:element>
                    <xsl:element name="p:school">
                        <xsl:value-of select="document('DegreeAndTranscript.xml')/new_dt:degreeAndTranscript/new_dt:school" />
                    </xsl:element>
                    <xsl:element name="p:enrollDate">
                        <xsl:value-of select="'2014-01-01'" />
                    </xsl:element>
                    <xsl:element name="p:graduationDate">
                        <xsl:value-of select="document('DegreeAndTranscript.xml')/new_dt:degreeAndTranscript/new_dt:graduationDate" />
                    </xsl:element>
                    <xsl:element name="p:GPA">
                        <xsl:value-of select="document('gpa.xml')" />
                    </xsl:element>
                </xsl:element>
            </xsl:element>
            <xsl:element name="p:career">
                <xsl:for-each select="$employer">
                    <xsl:variable name="company_name" select="new_emp:employerName"/>
                    <xsl:element name="p:careerItem">
                        <xsl:element name="p:company">
                            <xsl:element name="p:companyName">
                                <xsl:value-of select="$company_name" />
                            </xsl:element>
                            <xsl:for-each select="document('CompanyInfo.xml')/c:companyInfo/c:company">
                                <xsl:if test="$company_name = c:name">
                                    <xsl:element name="p:companyLocation">
                                        <xsl:value-of select="c:address" />
                                    </xsl:element>
                                    <xsl:element name="p:companyTel">
                                        <xsl:value-of select="c:phone" />
                                    </xsl:element>
                                </xsl:if>
                            </xsl:for-each>
                            <xsl:element name="p:position">
                                <xsl:value-of select="new_emp:position" />
                            </xsl:element>
                            <xsl:element name="p:startDate">
                                <xsl:value-of select="new_emp:startDate" />
                            </xsl:element>
                            <xsl:element name="p:endDate">
                                <xsl:value-of select="new_emp:endDate" />
                            </xsl:element>
                            <xsl:element name="p:description">
                                <xsl:value-of select="'-'" />
                            </xsl:element>
                        </xsl:element>
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>
            <xsl:element name="p:letter">
                <xsl:value-of select="document('ShortCV.xml')/m:material/m:letter" />
            </xsl:element>
            <xsl:element name="p:preference">
                <xsl:element name="p:desirePlace">
                    <xsl:value-of select="document('ShortCV.xml')/m:material/m:preference/m:desirePlace" />
                </xsl:element>
                <xsl:element name="p:jobType">
                    <xsl:value-of select="document('ShortCV.xml')/m:material/m:preference/m:jobType" />
                </xsl:element>
            </xsl:element>
        </xsl:element>
    </xsl:template>
    
</xsl:stylesheet>

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlprocessor;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.*;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import jaxb.company.CompanyInfo;
import jaxb.shortcv.Material;
import org.xml.sax.helpers.DefaultHandler;
import org.w3c.dom.*;
import org.xml.sax.*;
import xml.degree.*;
import xml.employment.Employer;
import xml.employment.EmploymentRecord;
import xml.employment.Skill;

/**
 *
 * @author khaled
 * @resources
 * https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
 * http://www.rgagnon.com/javadetails/java-0669.html
 * http://www.drdobbs.com/jvm/easy-dom-parsing-in-java/231002580
 * http://www.jdom.org/
 * http://www.microhowto.info/howto/process_an_xml_document_using_an_xslt_stylesheet_in_java.html
 */
public class XMLProcessor {

    /**
     * @param args the command line arguments
     */
    private static CompanyInfo companyInfo;
    private static final EmploymentRecord er = new EmploymentRecord();
    private static DegreeAndTranscript dt = new DegreeAndTranscript();
    private static Material material;

    public static void main(String[] args) {
        System.out.println("****** Validating XMLs aginst XSDs  ******\n");

        /*validateXMLUsingSAX(1, "src/main/java/xml/degree/DegreeAndTranscript.xml", "src/main/java/xml/degree/DegreeAndTranscriptSchema.xsd");
        System.out.println();
        validateXMLUsingSAX(2, "src/main/java/xml/employment/EmploymentRecord.xml", "src/main/java/xml/employment/EmploymentRecordSchema.xsd");
        System.out.println();
        validateXMLUsingSAX(3, "src/main/java/xml/company/CompanyInfo.xml", "src/main/java/xml/company/CompanyInfoSchema.xsd");
        System.out.println();
        validateXMLUsingSAX(4, "src/main/java/xml/profile/ApplicantProfile.xml", "src/main/java/xml/profile/ApplicationProfileSchema.xsd");
        System.out.println();
        validateXMLUsingSAX(5, "src/main/java/xml/shortcv/ShortCV.xml", "src/main/java/xml/shortcv/ShortCVSchema.xsd");

        System.out.println("\n\n****** Parse DegreeAndTranscript.xml using DOM  ******\n");
        parseDegreeAndTranscriptDOM();
        System.out.println("\n\n****** Parse EmploymentRecord.xml using SAX  ******\n");
        parseEmploymentRecordSAX();
        */
        
        System.out.println("\n\n****** Parse CompanyInfo.xml using jaxb  ******\n");
        parseCompanyInfoJaxb();
        System.out.println("\n\n****** Parse ShortCV.xml using jaxb  ******\n");
        parseShortCvJaxb();
        System.out.println("\n\n****** Calculate GPA and produce result in gpx.xml using xslt ******\n");
        calculateGPAUsingXslt();
        System.out.println("\n\n****** Parse and make ApplicationProfile.xml using xslt  ******\n");
        parseAndMakeApplicationProfileXslt();
    }

    private static void validateXMLUsingSAX(Integer index, String xml, String xsd) {

        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();

            factory.setNamespaceAware(true);
            factory.setValidating(false);
            SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            SAXParser parser = null;

            try {
                factory.setSchema(schemaFactory.newSchema(new Source[]{new StreamSource(xsd)}));
                parser = factory.newSAXParser();
            } catch (SAXException ex) {
                System.out.println(index + ". Validate " + xml + " against " + xsd + ": \nXSD Error: " + ex.getMessage());
            }

            XMLReader reader = parser.getXMLReader();
            reader.setErrorHandler(
                    new ErrorHandler() {
                public void warning(SAXParseException e) throws SAXException {
                }

                public void error(SAXParseException e) throws SAXException {
                    throw e;
                }

                public void fatalError(SAXParseException e) throws SAXException {
                    throw e;
                }
            });

            reader.parse(new InputSource(xml));
            System.out.println(index + ". Validate " + xml + " against " + xsd + ": \nCorrect");
        } catch (Exception ex) {
            System.out.println(index + ". Validate " + xml + " against " + xsd + ": \n" + ex.getMessage());
        }

    }

    private static void parseDegreeAndTranscriptDOM() {
        String ns = "http://xml.hm1.org/schema/DegreeAndTranscript";
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);

            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse("src/main/java/xml/degree/DegreeAndTranscript.xml");
            Element root = document.getDocumentElement();
            root.normalize();

            NodeList children = root.getChildNodes();
            String fullStudentName = getNodeValue("dt:fullStudentName", children);
            System.out.println("fullStudentName: " + fullStudentName);
            dt.setFullStudentName(fullStudentName);

            String personalId = getNodeValue("dt:personalId", children);
            System.out.println("personalId: " + personalId);
            dt.setPersonalId(personalId);

            String gender = getNodeValue("dt:gender", children);
            System.out.println("gender: " + gender);
            dt.setGender(gender);

            String school = getNodeValue("dt:school", children);
            System.out.println("school: " + school);
            dt.setSchool(school);

            String faculty = getNodeValue("dt:faculty", children);
            System.out.println("faculty: " + faculty);
            dt.setFaculty(faculty);

            String address = getNodeValue("dt:address", children).trim();
            System.out.println("address: " + address);
            dt.setAddress(address);

            String phone = getNodeValue("dt:phone", children);
            System.out.println("phone: " + phone);
            dt.setPhone(phone);

            String emailAddress = getNodeValue("dt:emailAddress", children);
            System.out.println("emailAddress: " + emailAddress);
            dt.setEmailAddress(emailAddress);

            String graduationDate = getNodeValue("dt:graduationDate", children);
            System.out.println("graduationDate: " + graduationDate);
            dt.setGraduationDate(graduationDate);

            NodeList academicCoursesElements = root.getElementsByTagNameNS(ns, "academicCourses"); // another way around the NS is: root.getElementsByTagName("dt:academicCourses");
            List<AcademicCourse> academicCoursesList = new ArrayList<>();

            for (int i = 0; i < academicCoursesElements.getLength(); i++) {
                String courseType = getNodeValue("dt:courseType", academicCoursesElements.item(i).getChildNodes());
                NodeList courses = ((Element) academicCoursesElements.item(i)).getElementsByTagNameNS(ns, "course");

                List<Course> coursesList = new ArrayList<>();

                System.out.println("Academic courses of type: " + courseType + " are:");
                for (int j = 0; j < courses.getLength(); j++) {
                    NodeList courseNodes = courses.item(j).getChildNodes();

                    String name = getNodeValue("dt:name", courseNodes);
                    System.out.println("\tFor course name: " + name);

                    String code = getNodeValue("dt:code", courseNodes);
                    System.out.println("\t\tcode: " + code);

                    String period = getNodeValue("dt:period", courseNodes);
                    System.out.println("\t\tCourse period: " + period);

                    String mark = getNodeValue("dt:mark", courseNodes);
                    System.out.println("\t\tCourse mark: " + mark);

                    String date = getNodeValue("dt:date", courseNodes);
                    System.out.println("\t\tCourse date: " + date);

                    String credit = getNodeValue("dt:credit", courseNodes);
                    System.out.println("\t\tCourse credit: " + credit);

                    coursesList.add(new Course(name, code, period, mark, date, credit));
                }
                academicCoursesList.add(new AcademicCourse(courseType, coursesList));
            }

            dt.setAcademicCourses(academicCoursesList);
            createNewDegreeXML_DOM(builder, fullStudentName, personalId, gender, school, faculty, address, phone, emailAddress, graduationDate, academicCoursesList);

        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static String getNodeValue(String tagName, NodeList nodes) {
        for (int x = 0; x < nodes.getLength(); x++) {
            Node node = nodes.item(x);
            if (node.getNodeName().equalsIgnoreCase(tagName)) {
                NodeList childNodes = node.getChildNodes();
                for (int y = 0; y < childNodes.getLength(); y++) {
                    Node data = childNodes.item(y);
                    if (data.getNodeType() == Node.TEXT_NODE) {
                        return data.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    private static void createNewDegreeXML_DOM(DocumentBuilder builder, String fullStudentName, String personalId, String gender, String school, String faculty, String address, String phone, String emailAddress, String graduationDate, List<AcademicCourse> academicCoursesList) {
        try {
            //Another way is to use jdom: http://www.jdom.org/
            Document document = builder.newDocument();
            Element root = document.createElementNS("http://xml.hm1.org/schema/DegreeAndTranscript", "new_dt:degreeAndTranscript");

            Element firstNameElement = document.createElement("new_dt:fullStudentName");
            firstNameElement.appendChild(document.createTextNode(fullStudentName));
            root.appendChild(firstNameElement);

            Element personalIdElement = document.createElement("new_dt:personalId");
            personalIdElement.appendChild(document.createTextNode(personalId));
            root.appendChild(personalIdElement);

            Element genderElement = document.createElement("new_dt:gender");
            genderElement.appendChild(document.createTextNode(gender));
            root.appendChild(genderElement);

            Element schoolElement = document.createElement("new_dt:school");
            schoolElement.appendChild(document.createTextNode(school));
            root.appendChild(schoolElement);

            Element facultyElement = document.createElement("new_dt:faculty");
            facultyElement.appendChild(document.createTextNode(faculty));
            root.appendChild(facultyElement);

            Element addressElement = document.createElement("new_dt:address");
            addressElement.appendChild(document.createTextNode(address));
            root.appendChild(addressElement);

            Element phoneElement = document.createElement("new_dt:phone");
            phoneElement.appendChild(document.createTextNode(phone));
            root.appendChild(phoneElement);

            Element emailAddressElement = document.createElement("new_dt:emailAddress");
            emailAddressElement.appendChild(document.createTextNode(emailAddress));
            root.appendChild(emailAddressElement);

            Element graduationDateElement = document.createElement("new_dt:graduationDate");
            graduationDateElement.appendChild(document.createTextNode(graduationDate));
            root.appendChild(graduationDateElement);

            Collections.reverse(academicCoursesList); // make some changes on original xml !
            for (AcademicCourse ac : academicCoursesList) {
                Element academicCourses = document.createElement("new_dt:academicCourses");

                Element courseTypeElement = document.createElement("new_dt:courseType");
                courseTypeElement.appendChild(document.createTextNode(ac.getCourseType()));
                academicCourses.appendChild(courseTypeElement);

                for (Course c : ac.getCourses()) {
                    Element course = document.createElement("new_dt:course");

                    Element nameElement = document.createElement("new_dt:name");
                    nameElement.appendChild(document.createTextNode(c.getName()));
                    course.appendChild(nameElement);

                    Element codeElement = document.createElement("new_dt:code");
                    codeElement.appendChild(document.createTextNode(c.getCode()));
                    course.appendChild(codeElement);

                    Element periodElement = document.createElement("new_dt:period");
                    periodElement.appendChild(document.createTextNode(c.getPeriod()));
                    course.appendChild(periodElement);

                    Element markElement = document.createElement("new_dt:mark");
                    markElement.appendChild(document.createTextNode(c.getMark()));
                    course.appendChild(markElement);

                    Element dateElement = document.createElement("new_dt:date");
                    dateElement.appendChild(document.createTextNode(c.getDate()));
                    course.appendChild(dateElement);

                    Element creditElement = document.createElement("new_dt:credit");
                    creditElement.appendChild(document.createTextNode(c.getCredit()));
                    course.appendChild(creditElement);

                    academicCourses.appendChild(course);
                }

                root.appendChild(academicCourses);
            }

            document.appendChild(root);

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("src/main/java/xml/generated/DegreeAndTranscript.xml"));
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(source, result);

            System.out.println("\nA new parsed DegreeAndTranscript XML has been created in: src/main/java/xml/generated/DegreeAndTranscript.xml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void parseEmploymentRecordSAX() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler;
            handler = new DefaultHandler() {
                boolean b_fullEmployeeName, b_personalId, b_gender, b_age, b_address, b_phone, b_emailAddress, b_employer, b_employerName, b_employerAddress, b_position, b_startDate, b_endDate, b_skills, b_skillName, b_proficiency;
                List<Employer> employers = new ArrayList<>();
                List<Skill> skills;
                Employer tmpEmployer = null;
                Skill tmpSkill = null;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("emp:fullEmployeeName")) {
                        b_fullEmployeeName = true;
                    }

                    if (qName.equalsIgnoreCase("emp:personalId")) {
                        b_personalId = true;
                    }

                    if (qName.equalsIgnoreCase("emp:gender")) {
                        b_gender = true;
                    }

                    if (qName.equalsIgnoreCase("emp:age")) {
                        b_age = true;
                    }

                    if (qName.equalsIgnoreCase("emp:address")) {
                        b_address = true;
                    }

                    if (qName.equalsIgnoreCase("emp:phone")) {
                        b_phone = true;
                    }

                    if (qName.equalsIgnoreCase("emp:emailAddress")) {
                        b_emailAddress = true;
                    }

                    if (qName.equalsIgnoreCase("emp:employer")) {
                        b_employer = true;
                    }

                    if (qName.equalsIgnoreCase("emp:employerName")) {
                        b_employerName = true;
                    }

                    if (qName.equalsIgnoreCase("emp:employerAddress")) {
                        b_employerAddress = true;
                    }

                    if (qName.equalsIgnoreCase("emp:position")) {
                        b_position = true;
                    }

                    if (qName.equalsIgnoreCase("emp:startDate")) {
                        b_startDate = true;
                    }

                    if (qName.equalsIgnoreCase("emp:endDate")) {
                        b_endDate = true;
                    }

                    if (qName.equalsIgnoreCase("emp:skills")) {
                        b_skills = true;
                    }

                    if (qName.equalsIgnoreCase("emp:skillName")) {
                        b_skillName = true;
                    }

                    if (qName.equalsIgnoreCase("emp:proficiency")) {
                        b_proficiency = true;
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (qName.equals("emp:skills")) {
                        skills.add(new Skill(tmpSkill.getSkillName(), tmpSkill.getProficiency()));
                        tmpSkill = null;
                    }
                    if (qName.equals("emp:employer")) {
                        employers.add(new Employer(tmpEmployer.getEmployerName(), tmpEmployer.getEmployerAddress(), tmpEmployer.getPosition(), tmpEmployer.getStartDate(), tmpEmployer.getEndDate(), skills));
                        tmpEmployer = null;
                        skills = null;
                    }
                }

                @Override
                public void characters(char ch[], int start, int length) throws SAXException {

                    if (b_fullEmployeeName) {
                        er.setFullEmployeeName(new String(ch, start, length));
                        System.out.println("fullEmployeeName: " + er.getFullEmployeeName());
                        b_fullEmployeeName = false;
                    }

                    if (b_personalId) {
                        er.setPersonalId(new String(ch, start, length));
                        System.out.println("personalId: " + er.getPersonalId());
                        b_personalId = false;
                    }

                    if (b_gender) {
                        er.setGender(new String(ch, start, length));
                        System.out.println("gender: " + er.getGender());
                        b_gender = false;
                    }

                    if (b_age) {
                        er.setAge(new String(ch, start, length));
                        System.out.println("age: " + er.getAge());
                        b_age = false;
                    }

                    if (b_address) {
                        er.setAddress(new String(ch, start, length));
                        System.out.println("address: " + er.getAddress());
                        b_address = false;
                    }

                    if (b_phone) {
                        er.setPhone(new String(ch, start, length));
                        System.out.println("phone: " + er.getPhone());
                        b_phone = false;
                    }

                    if (b_emailAddress) {
                        er.setEmailAddress(new String(ch, start, length));
                        System.out.println("emailAddress: " + er.getEmailAddress());
                        b_emailAddress = false;
                    }

                    if (b_employer) {
                        System.out.println("employer: ");
                        tmpEmployer = new Employer();
                        skills = new ArrayList<>();
                        b_employer = false;
                    }

                    if (b_employerName) {
                        String employerName = new String(ch, start, length);
                        System.out.println("employerName : " + employerName);
                        if (tmpEmployer != null) {
                            tmpEmployer.setEmployerName(employerName);
                        }
                        b_employerName = false;
                    }

                    if (b_employerAddress) {
                        String employerAddress = new String(ch, start, length);
                        System.out.println("employerAddress : " + employerAddress);
                        if (tmpEmployer != null) {
                            tmpEmployer.setEmployerAddress(employerAddress);
                        }
                        b_employerAddress = false;
                    }

                    if (b_position) {
                        String position = new String(ch, start, length);
                        System.out.println("position : " + position);
                        if (tmpEmployer != null) {
                            tmpEmployer.setPosition(position);
                        }
                        b_position = false;
                    }

                    if (b_startDate) {
                        String startDate = new String(ch, start, length);
                        System.out.println("startDate : " + startDate);
                        if (tmpEmployer != null) {
                            tmpEmployer.setStartDate(startDate);
                        }
                        b_startDate = false;
                    }

                    if (b_endDate) {
                        String endDate = new String(ch, start, length);
                        System.out.println("endDate : " + endDate);
                        if (tmpEmployer != null) {
                            tmpEmployer.setEndDate(endDate);
                        }
                        b_endDate = false;
                    }

                    if (b_skills) {
                        tmpSkill = new Skill();
                        System.out.println("skills : " + new String(ch, start, length));
                        b_skills = false;
                    }

                    if (b_skillName) {
                        String skillName = new String(ch, start, length);
                        System.out.println("skillName : " + skillName);
                        if (tmpSkill != null) {
                            tmpSkill.setSkillName(skillName);
                        }
                        b_skillName = false;
                    }

                    if (b_proficiency) {
                        String proficiency = new String(ch, start, length);
                        System.out.println("proficiency : " + proficiency);
                        if (tmpSkill != null) {
                            tmpSkill.setProficiency(proficiency);
                        }
                        b_proficiency = false;
                    }
                }

                @Override
                public void endDocument() throws SAXException {
                    try {
                        er.setEmployer(employers);

                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = factory.newDocumentBuilder();
                        Document document = builder.newDocument();
                        Element root = document.createElementNS("http://xml.hm1.org/schema/EmploymentRecordSchema", "new_emp:employmentRecord");

                        Element firstNameElement = document.createElement("new_emp:fullEmployeeName");
                        firstNameElement.appendChild(document.createTextNode(er.getFullEmployeeName()));
                        root.appendChild(firstNameElement);

                        Element personalIdElement = document.createElement("new_emp:personalId");
                        personalIdElement.appendChild(document.createTextNode(er.getPersonalId()));
                        root.appendChild(personalIdElement);

                        Element genderElement = document.createElement("new_emp:gender");
                        genderElement.appendChild(document.createTextNode(er.getGender()));
                        root.appendChild(genderElement);

                        Element ageElement = document.createElement("new_emp:age");
                        ageElement.appendChild(document.createTextNode(er.getAge()));
                        root.appendChild(ageElement);

                        Element addressElement = document.createElement("new_emp:address");
                        addressElement.appendChild(document.createTextNode(er.getAddress()));
                        root.appendChild(addressElement);

                        Element phoneElement = document.createElement("new_emp:phone");
                        phoneElement.appendChild(document.createTextNode(er.getPhone()));
                        root.appendChild(phoneElement);

                        Element emailAddressElement = document.createElement("new_emp:emailAddress");
                        emailAddressElement.appendChild(document.createTextNode(er.getEmailAddress()));
                        root.appendChild(emailAddressElement);

                        Collections.reverse(er.getEmployer());
                        for (Employer employer : er.getEmployer()) {
                            Element employerElement = document.createElement("new_emp:employer");

                            Element employerNameElement = document.createElement("new_emp:employerName");
                            employerNameElement.appendChild(document.createTextNode(employer.getEmployerName()));
                            employerElement.appendChild(employerNameElement);

                            Element employerAddressElement = document.createElement("new_emp:employerAddress");
                            employerAddressElement.appendChild(document.createTextNode(employer.getEmployerAddress()));
                            employerElement.appendChild(employerAddressElement);

                            Element positionElement = document.createElement("new_emp:position");
                            positionElement.appendChild(document.createTextNode(employer.getPosition()));
                            employerElement.appendChild(positionElement);

                            if (employer.getSkills() != null && employer.getSkills().size() > 0) {
                                for (Skill skill : employer.getSkills()) {
                                    Element skillsElement = document.createElement("new_emp:skills");

                                    Element skillNameElement = document.createElement("new_emp:skillName");
                                    skillNameElement.appendChild(document.createTextNode(skill.getSkillName()));
                                    skillsElement.appendChild(skillNameElement);

                                    Element proficiencyElement = document.createElement("new_emp:proficiency");
                                    proficiencyElement.appendChild(document.createTextNode(skill.getProficiency()));
                                    skillsElement.appendChild(proficiencyElement);

                                    employerElement.appendChild(skillsElement);
                                }
                            }

                            Element startDateElement = document.createElement("new_emp:startDate");
                            startDateElement.appendChild(document.createTextNode(employer.getStartDate()));
                            employerElement.appendChild(startDateElement);

                            Element endDateElement = document.createElement("new_emp:endDate");
                            endDateElement.appendChild(document.createTextNode(employer.getEndDate()));
                            employerElement.appendChild(endDateElement);

                            root.appendChild(employerElement);
                        }

                        document.appendChild(root);

                        DOMSource source = new DOMSource(document);
                        StreamResult result = new StreamResult(new File("src/main/java/xml/generated/EmploymentRecord.xml"));
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        transformer.transform(source, result);

                        System.out.println("\nA new parsed EmploymentRecord XML has been created in: src/main/java/xml/generated/EmploymentRecord.xml");

                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                    } catch (TransformerConfigurationException ex) {
                        ex.printStackTrace();
                    } catch (TransformerException ex) {
                        ex.printStackTrace();
                    }
                }

            };

            saxParser.parse("src/main/java/xml/employment/EmploymentRecord.xml", handler);

        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void parseCompanyInfoJaxb() {
        try {
            JAXBContext jaxbContect = JAXBContext.newInstance(jaxb.company.ObjectFactory.class);
            Unmarshaller unmarshaller = jaxbContect.createUnmarshaller();
            companyInfo = (CompanyInfo) unmarshaller.unmarshal(new File("src/main/java/xml/company/CompanyInfo.xml"));

            List<CompanyInfo.Company> companyList = companyInfo.getCompany();
            System.out.println("Total no. of companies are: " + companyList.size());
            System.out.println("Companies:");
            companyList.stream().forEach((company) -> {
                System.out.println("\tCompany: " + company.getName());
                System.out.println("\t\tRole: " + company.getRole());
                System.out.println("\t\tAddress: " + company.getAddress().trim().replaceAll("[\\t\\n\\r]", " "));
                System.out.println("\t\tPhone: " + company.getPhone());
            });

            CompanyInfo.Company company = new CompanyInfo.Company();
            company.setName("Saab");
            company.setAddress("Kista, Stockholm");
            company.setPhone("46701111111");
            company.setRole("Jet manufatcuring");

            companyList.add(company);

            System.out.print("\nA new company named: \"" + company.getName() + "\" has been created successfully");

            Marshaller marshaller = jaxbContect.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(companyInfo, new File("src/main/java/xml/generated/CompanyInfo.xml"));

            System.out.println("\nA new parsed EmploymentRecord XML has been created in: src/main/java/xml/generated/CompanyInfo.xml");

        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
    }

    private static void parseShortCvJaxb() {
        try {
            JAXBContext jaxbContect = JAXBContext.newInstance(jaxb.shortcv.ObjectFactory.class);
            Unmarshaller unmarshaller = jaxbContect.createUnmarshaller();
            material = (Material) unmarshaller.unmarshal(new File("src/main/java/xml/shortcv/ShortCV.xml"));

            System.out.println("Job seeker name: " + material.getCv().getName());
            System.out.println("\tGender: " + material.getCv().getGender());
            System.out.println("\tAddress: " + material.getCv().getAddress());
            System.out.println("\tEmail: " + material.getCv().getEmail());
            System.out.println("\tPhone: " + material.getCv().getTel());
            System.out.println("\tMotivation Letter: " + material.getLetter());
            System.out.println("\tPreferences: ");
            System.out.println("\t\tJob type: " + material.getPreference().getJobType());
            System.out.println("\t\tDesire place: " + material.getPreference().getDesirePlace());

            Marshaller marshaller = jaxbContect.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(material, new File("src/main/java/xml/generated/ShortCV.xml"));

            System.out.println("\nA new parsed ShortCV XML has been created in: src/main/java/xml/generated/ShortCV.xml");

        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
    }

    private static void parseAndMakeApplicationProfileXslt() {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();

            String stylesheetPathname = "src/main/java/xml/generated/ApplicationProfileMaker.xsl";
            Source stylesheetSource = new StreamSource(new File(stylesheetPathname).getAbsoluteFile());

            Transformer transformer = factory.newTransformer(stylesheetSource);

            String outputPathname = "src/main/java/xml/generated/ApplicantProfile.xml";
            Result outputResult = new StreamResult(new File(outputPathname).getAbsoluteFile());

            transformer.transform(new StreamSource(), outputResult);
            System.out.println("A new parsed ApplicationProfile XML has been created in: src/main/java/xml/generated/ApplicationProfile.xml\n\n");

        } catch (TransformerConfigurationException ex) {
            ex.printStackTrace();
        } catch (TransformerException ex) {
            ex.printStackTrace();
        }

    }

    private static void calculateGPAUsingXslt() {
        try {
            //calculate total grades for each course
            xslTransformer("src/main/java/xml/degree/totalGradesCalculator.xsl", "src/main/java/xml/degree/DegreeAndTranscript.xml", "src/main/java/xml/generated/totalGrades.xml");

            //calculate GPA
            xslTransformer("src/main/java/xml/degree/gpaCalculator.xsl", "src/main/java/xml/generated/totalGrades.xml", "src/main/java/xml/generated/gpa.xml");
            System.out.println("GPA is calucated and produced in gpa.xml in: src/main/java/xml/generated/gpa.xml\n");
        } catch (TransformerConfigurationException ex) {
            ex.printStackTrace();
        } catch (TransformerException ex) {
            ex.printStackTrace();
        }
    }

    private static void xslTransformer(String inputXSL, String inputXML, String outputXML) throws TransformerException {
        TransformerFactory tFactory = TransformerFactory.newInstance();
        StreamSource xsl = new StreamSource(new File(inputXSL));
        StreamSource input = new StreamSource(new File(inputXML));
        StreamResult output = new StreamResult(new File(outputXML));
        Transformer transformer = tFactory.newTransformer(xsl);
        transformer.transform(input, output);
    }

}

# xmlProcessor

### How to generate classes out of XSD?
To add jaxb classes, we need to use the XJC tool that comes with java8:

$ xjc -d src/main/java -p jaxb.company -encoding UTF-8 src/main/java/xml/company/CompanyInfoSchema.xsd

$ xjc -d src/main/java -p jaxb.shortcv -encoding UTF-8 src/main/java/xml/shortcv/ShortCVSchema.xsd 

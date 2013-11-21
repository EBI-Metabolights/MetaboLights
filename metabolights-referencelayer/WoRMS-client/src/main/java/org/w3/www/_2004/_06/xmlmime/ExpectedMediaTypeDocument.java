/*
 * An XML document type.
 * Localname: expectedMediaType
 * Namespace: http://www.w3.org/2004/06/xmlmime
 * Java type: org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.www._2004._06.xmlmime;


/**
 * A document containing one expectedMediaType(@http://www.w3.org/2004/06/xmlmime) element.
 *
 * This is a complex type.
 */
public interface ExpectedMediaTypeDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ExpectedMediaTypeDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC975E981773C6D5C52067BB5301352BB").resolveHandle("expectedmediatype0e08doctype");
    
    /**
     * Gets the "expectedMediaType" element
     */
    java.util.List getExpectedMediaType();
    
    /**
     * Gets (as xml) the "expectedMediaType" element
     */
    org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument.ExpectedMediaType xgetExpectedMediaType();
    
    /**
     * Sets the "expectedMediaType" element
     */
    void setExpectedMediaType(java.util.List expectedMediaType);
    
    /**
     * Sets (as xml) the "expectedMediaType" element
     */
    void xsetExpectedMediaType(org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument.ExpectedMediaType expectedMediaType);
    
    /**
     * An XML expectedMediaType(@http://www.w3.org/2004/06/xmlmime).
     *
     * This is a list type whose items are org.w3.www._2004._06.xmlmime.ExpectedMediaTypeItem.
     */
    public interface ExpectedMediaType extends org.apache.xmlbeans.XmlAnySimpleType
    {
        java.util.List getListValue();
        java.util.List xgetListValue();
        void setListValue(java.util.List list);
        /** @deprecated */
        java.util.List listValue();
        /** @deprecated */
        java.util.List xlistValue();
        /** @deprecated */
        void set(java.util.List list);
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ExpectedMediaType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC975E981773C6D5C52067BB5301352BB").resolveHandle("expectedmediatypec05aelemtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument.ExpectedMediaType newValue(java.lang.Object obj) {
              return (org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument.ExpectedMediaType) type.newValue( obj ); }
            
            public static org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument.ExpectedMediaType newInstance() {
              return (org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument.ExpectedMediaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument.ExpectedMediaType newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument.ExpectedMediaType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument newInstance() {
          return (org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}

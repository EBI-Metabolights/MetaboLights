/*
 * An XML document type.
 * Localname: getAphiaVernacularsByIDResponse
 * Namespace: http://aphia/v1.0/AphiaVernaculars
 * Java type: aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiavernaculars;


/**
 * A document containing one getAphiaVernacularsByIDResponse(@http://aphia/v1.0/AphiaVernaculars) element.
 *
 * This is a complex type.
 */
public interface GetAphiaVernacularsByIDResponseDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetAphiaVernacularsByIDResponseDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC975E981773C6D5C52067BB5301352BB").resolveHandle("getaphiavernacularsbyidresponse5812doctype");
    
    /**
     * Gets the "getAphiaVernacularsByIDResponse" element
     */
    aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument.GetAphiaVernacularsByIDResponse getGetAphiaVernacularsByIDResponse();
    
    /**
     * Sets the "getAphiaVernacularsByIDResponse" element
     */
    void setGetAphiaVernacularsByIDResponse(aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument.GetAphiaVernacularsByIDResponse getAphiaVernacularsByIDResponse);
    
    /**
     * Appends and returns a new empty "getAphiaVernacularsByIDResponse" element
     */
    aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument.GetAphiaVernacularsByIDResponse addNewGetAphiaVernacularsByIDResponse();
    
    /**
     * An XML getAphiaVernacularsByIDResponse(@http://aphia/v1.0/AphiaVernaculars).
     *
     * This is a complex type.
     */
    public interface GetAphiaVernacularsByIDResponse extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetAphiaVernacularsByIDResponse.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC975E981773C6D5C52067BB5301352BB").resolveHandle("getaphiavernacularsbyidresponsef770elemtype");
        
        /**
         * Gets the "return" element
         */
        aphia.v1_0.Vernaculars getReturn();
        
        /**
         * Tests for nil "return" element
         */
        boolean isNilReturn();
        
        /**
         * Sets the "return" element
         */
        void setReturn(aphia.v1_0.Vernaculars xreturn);
        
        /**
         * Appends and returns a new empty "return" element
         */
        aphia.v1_0.Vernaculars addNewReturn();
        
        /**
         * Nils the "return" element
         */
        void setNilReturn();
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument.GetAphiaVernacularsByIDResponse newInstance() {
              return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument.GetAphiaVernacularsByIDResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument.GetAphiaVernacularsByIDResponse newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument.GetAphiaVernacularsByIDResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument newInstance() {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}

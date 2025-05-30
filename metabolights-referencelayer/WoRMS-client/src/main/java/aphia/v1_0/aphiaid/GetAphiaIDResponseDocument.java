/*
 * An XML document type.
 * Localname: getAphiaIDResponse
 * Namespace: http://aphia/v1.0/AphiaID
 * Java type: aphia.v1_0.aphiaid.GetAphiaIDResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiaid;


/**
 * A document containing one getAphiaIDResponse(@http://aphia/v1.0/AphiaID) element.
 *
 * This is a complex type.
 */
public interface GetAphiaIDResponseDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetAphiaIDResponseDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC975E981773C6D5C52067BB5301352BB").resolveHandle("getaphiaidresponse60fadoctype");
    
    /**
     * Gets the "getAphiaIDResponse" element
     */
    aphia.v1_0.aphiaid.GetAphiaIDResponseDocument.GetAphiaIDResponse getGetAphiaIDResponse();
    
    /**
     * Sets the "getAphiaIDResponse" element
     */
    void setGetAphiaIDResponse(aphia.v1_0.aphiaid.GetAphiaIDResponseDocument.GetAphiaIDResponse getAphiaIDResponse);
    
    /**
     * Appends and returns a new empty "getAphiaIDResponse" element
     */
    aphia.v1_0.aphiaid.GetAphiaIDResponseDocument.GetAphiaIDResponse addNewGetAphiaIDResponse();
    
    /**
     * An XML getAphiaIDResponse(@http://aphia/v1.0/AphiaID).
     *
     * This is a complex type.
     */
    public interface GetAphiaIDResponse extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetAphiaIDResponse.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC975E981773C6D5C52067BB5301352BB").resolveHandle("getaphiaidresponsece07elemtype");
        
        /**
         * Gets the "return" element
         */
        int getReturn();
        
        /**
         * Gets (as xml) the "return" element
         */
        org.apache.xmlbeans.XmlInt xgetReturn();
        
        /**
         * Tests for nil "return" element
         */
        boolean isNilReturn();
        
        /**
         * Sets the "return" element
         */
        void setReturn(int xreturn);
        
        /**
         * Sets (as xml) the "return" element
         */
        void xsetReturn(org.apache.xmlbeans.XmlInt xreturn);
        
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
            public static aphia.v1_0.aphiaid.GetAphiaIDResponseDocument.GetAphiaIDResponse newInstance() {
              return (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument.GetAphiaIDResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static aphia.v1_0.aphiaid.GetAphiaIDResponseDocument.GetAphiaIDResponse newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument.GetAphiaIDResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static aphia.v1_0.aphiaid.GetAphiaIDResponseDocument newInstance() {
          return (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static aphia.v1_0.aphiaid.GetAphiaIDResponseDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static aphia.v1_0.aphiaid.GetAphiaIDResponseDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static aphia.v1_0.aphiaid.GetAphiaIDResponseDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static aphia.v1_0.aphiaid.GetAphiaIDResponseDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static aphia.v1_0.aphiaid.GetAphiaIDResponseDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static aphia.v1_0.aphiaid.GetAphiaIDResponseDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static aphia.v1_0.aphiaid.GetAphiaIDResponseDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static aphia.v1_0.aphiaid.GetAphiaIDResponseDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static aphia.v1_0.aphiaid.GetAphiaIDResponseDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static aphia.v1_0.aphiaid.GetAphiaIDResponseDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static aphia.v1_0.aphiaid.GetAphiaIDResponseDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static aphia.v1_0.aphiaid.GetAphiaIDResponseDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static aphia.v1_0.aphiaid.GetAphiaIDResponseDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static aphia.v1_0.aphiaid.GetAphiaIDResponseDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static aphia.v1_0.aphiaid.GetAphiaIDResponseDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aphia.v1_0.aphiaid.GetAphiaIDResponseDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aphia.v1_0.aphiaid.GetAphiaIDResponseDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aphia.v1_0.aphiaid.GetAphiaIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}

/*
 * An XML document type.
 * Localname: getAphiaRecordsResponse
 * Namespace: http://aphia/v1.0/AphiaRecords
 * Java type: aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiarecords;


/**
 * A document containing one getAphiaRecordsResponse(@http://aphia/v1.0/AphiaRecords) element.
 *
 * This is a complex type.
 */
public interface GetAphiaRecordsResponseDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetAphiaRecordsResponseDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC975E981773C6D5C52067BB5301352BB").resolveHandle("getaphiarecordsresponse9aa4doctype");
    
    /**
     * Gets the "getAphiaRecordsResponse" element
     */
    aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument.GetAphiaRecordsResponse getGetAphiaRecordsResponse();
    
    /**
     * Sets the "getAphiaRecordsResponse" element
     */
    void setGetAphiaRecordsResponse(aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument.GetAphiaRecordsResponse getAphiaRecordsResponse);
    
    /**
     * Appends and returns a new empty "getAphiaRecordsResponse" element
     */
    aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument.GetAphiaRecordsResponse addNewGetAphiaRecordsResponse();
    
    /**
     * An XML getAphiaRecordsResponse(@http://aphia/v1.0/AphiaRecords).
     *
     * This is a complex type.
     */
    public interface GetAphiaRecordsResponse extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetAphiaRecordsResponse.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC975E981773C6D5C52067BB5301352BB").resolveHandle("getaphiarecordsresponsec0d0elemtype");
        
        /**
         * Gets the "return" element
         */
        aphia.v1_0.AphiaRecords getReturn();
        
        /**
         * Tests for nil "return" element
         */
        boolean isNilReturn();
        
        /**
         * Sets the "return" element
         */
        void setReturn(aphia.v1_0.AphiaRecords xreturn);
        
        /**
         * Appends and returns a new empty "return" element
         */
        aphia.v1_0.AphiaRecords addNewReturn();
        
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
            public static aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument.GetAphiaRecordsResponse newInstance() {
              return (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument.GetAphiaRecordsResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument.GetAphiaRecordsResponse newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument.GetAphiaRecordsResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument newInstance() {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}

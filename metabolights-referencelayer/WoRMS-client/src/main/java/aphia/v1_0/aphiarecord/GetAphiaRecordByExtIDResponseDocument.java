/*
 * An XML document type.
 * Localname: getAphiaRecordByExtIDResponse
 * Namespace: http://aphia/v1.0/AphiaRecord
 * Java type: aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiarecord;


/**
 * A document containing one getAphiaRecordByExtIDResponse(@http://aphia/v1.0/AphiaRecord) element.
 *
 * This is a complex type.
 */
public interface GetAphiaRecordByExtIDResponseDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetAphiaRecordByExtIDResponseDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC975E981773C6D5C52067BB5301352BB").resolveHandle("getaphiarecordbyextidresponsefe41doctype");
    
    /**
     * Gets the "getAphiaRecordByExtIDResponse" element
     */
    aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument.GetAphiaRecordByExtIDResponse getGetAphiaRecordByExtIDResponse();
    
    /**
     * Sets the "getAphiaRecordByExtIDResponse" element
     */
    void setGetAphiaRecordByExtIDResponse(aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument.GetAphiaRecordByExtIDResponse getAphiaRecordByExtIDResponse);
    
    /**
     * Appends and returns a new empty "getAphiaRecordByExtIDResponse" element
     */
    aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument.GetAphiaRecordByExtIDResponse addNewGetAphiaRecordByExtIDResponse();
    
    /**
     * An XML getAphiaRecordByExtIDResponse(@http://aphia/v1.0/AphiaRecord).
     *
     * This is a complex type.
     */
    public interface GetAphiaRecordByExtIDResponse extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetAphiaRecordByExtIDResponse.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC975E981773C6D5C52067BB5301352BB").resolveHandle("getaphiarecordbyextidresponse7967elemtype");
        
        /**
         * Gets the "return" element
         */
        aphia.v1_0.AphiaRecord getReturn();
        
        /**
         * Tests for nil "return" element
         */
        boolean isNilReturn();
        
        /**
         * Sets the "return" element
         */
        void setReturn(aphia.v1_0.AphiaRecord xreturn);
        
        /**
         * Appends and returns a new empty "return" element
         */
        aphia.v1_0.AphiaRecord addNewReturn();
        
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
            public static aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument.GetAphiaRecordByExtIDResponse newInstance() {
              return (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument.GetAphiaRecordByExtIDResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument.GetAphiaRecordByExtIDResponse newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument.GetAphiaRecordByExtIDResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument newInstance() {
          return (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}

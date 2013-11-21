/*
 * An XML document type.
 * Localname: getAphiaRecordsByDate
 * Namespace: http://aphia/v1.0/AphiaRecords
 * Java type: aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiarecords;


/**
 * A document containing one getAphiaRecordsByDate(@http://aphia/v1.0/AphiaRecords) element.
 *
 * This is a complex type.
 */
public interface GetAphiaRecordsByDateDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetAphiaRecordsByDateDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC975E981773C6D5C52067BB5301352BB").resolveHandle("getaphiarecordsbydate5a40doctype");
    
    /**
     * Gets the "getAphiaRecordsByDate" element
     */
    aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument.GetAphiaRecordsByDate getGetAphiaRecordsByDate();
    
    /**
     * Sets the "getAphiaRecordsByDate" element
     */
    void setGetAphiaRecordsByDate(aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument.GetAphiaRecordsByDate getAphiaRecordsByDate);
    
    /**
     * Appends and returns a new empty "getAphiaRecordsByDate" element
     */
    aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument.GetAphiaRecordsByDate addNewGetAphiaRecordsByDate();
    
    /**
     * An XML getAphiaRecordsByDate(@http://aphia/v1.0/AphiaRecords).
     *
     * This is a complex type.
     */
    public interface GetAphiaRecordsByDate extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetAphiaRecordsByDate.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC975E981773C6D5C52067BB5301352BB").resolveHandle("getaphiarecordsbydateb2c8elemtype");
        
        /**
         * Gets the "startdate" element
         */
        java.lang.String getStartdate();
        
        /**
         * Gets (as xml) the "startdate" element
         */
        org.apache.xmlbeans.XmlString xgetStartdate();
        
        /**
         * Tests for nil "startdate" element
         */
        boolean isNilStartdate();
        
        /**
         * Sets the "startdate" element
         */
        void setStartdate(java.lang.String startdate);
        
        /**
         * Sets (as xml) the "startdate" element
         */
        void xsetStartdate(org.apache.xmlbeans.XmlString startdate);
        
        /**
         * Nils the "startdate" element
         */
        void setNilStartdate();
        
        /**
         * Gets the "enddate" element
         */
        java.lang.String getEnddate();
        
        /**
         * Gets (as xml) the "enddate" element
         */
        org.apache.xmlbeans.XmlString xgetEnddate();
        
        /**
         * Tests for nil "enddate" element
         */
        boolean isNilEnddate();
        
        /**
         * Sets the "enddate" element
         */
        void setEnddate(java.lang.String enddate);
        
        /**
         * Sets (as xml) the "enddate" element
         */
        void xsetEnddate(org.apache.xmlbeans.XmlString enddate);
        
        /**
         * Nils the "enddate" element
         */
        void setNilEnddate();
        
        /**
         * Gets the "marine_only" element
         */
        boolean getMarineOnly();
        
        /**
         * Gets (as xml) the "marine_only" element
         */
        org.apache.xmlbeans.XmlBoolean xgetMarineOnly();
        
        /**
         * Tests for nil "marine_only" element
         */
        boolean isNilMarineOnly();
        
        /**
         * Sets the "marine_only" element
         */
        void setMarineOnly(boolean marineOnly);
        
        /**
         * Sets (as xml) the "marine_only" element
         */
        void xsetMarineOnly(org.apache.xmlbeans.XmlBoolean marineOnly);
        
        /**
         * Nils the "marine_only" element
         */
        void setNilMarineOnly();
        
        /**
         * Gets the "offset" element
         */
        int getOffset();
        
        /**
         * Gets (as xml) the "offset" element
         */
        org.apache.xmlbeans.XmlInt xgetOffset();
        
        /**
         * Tests for nil "offset" element
         */
        boolean isNilOffset();
        
        /**
         * Sets the "offset" element
         */
        void setOffset(int offset);
        
        /**
         * Sets (as xml) the "offset" element
         */
        void xsetOffset(org.apache.xmlbeans.XmlInt offset);
        
        /**
         * Nils the "offset" element
         */
        void setNilOffset();
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument.GetAphiaRecordsByDate newInstance() {
              return (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument.GetAphiaRecordsByDate) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument.GetAphiaRecordsByDate newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument.GetAphiaRecordsByDate) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument newInstance() {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}

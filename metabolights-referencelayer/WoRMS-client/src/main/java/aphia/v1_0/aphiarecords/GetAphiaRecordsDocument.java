/*
 * An XML document type.
 * Localname: getAphiaRecords
 * Namespace: http://aphia/v1.0/AphiaRecords
 * Java type: aphia.v1_0.aphiarecords.GetAphiaRecordsDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiarecords;


/**
 * A document containing one getAphiaRecords(@http://aphia/v1.0/AphiaRecords) element.
 *
 * This is a complex type.
 */
public interface GetAphiaRecordsDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetAphiaRecordsDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC975E981773C6D5C52067BB5301352BB").resolveHandle("getaphiarecords5125doctype");
    
    /**
     * Gets the "getAphiaRecords" element
     */
    aphia.v1_0.aphiarecords.GetAphiaRecordsDocument.GetAphiaRecords getGetAphiaRecords();
    
    /**
     * Sets the "getAphiaRecords" element
     */
    void setGetAphiaRecords(aphia.v1_0.aphiarecords.GetAphiaRecordsDocument.GetAphiaRecords getAphiaRecords);
    
    /**
     * Appends and returns a new empty "getAphiaRecords" element
     */
    aphia.v1_0.aphiarecords.GetAphiaRecordsDocument.GetAphiaRecords addNewGetAphiaRecords();
    
    /**
     * An XML getAphiaRecords(@http://aphia/v1.0/AphiaRecords).
     *
     * This is a complex type.
     */
    public interface GetAphiaRecords extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetAphiaRecords.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC975E981773C6D5C52067BB5301352BB").resolveHandle("getaphiarecords6a92elemtype");
        
        /**
         * Gets the "scientificname" element
         */
        java.lang.String getScientificname();
        
        /**
         * Gets (as xml) the "scientificname" element
         */
        org.apache.xmlbeans.XmlString xgetScientificname();
        
        /**
         * Tests for nil "scientificname" element
         */
        boolean isNilScientificname();
        
        /**
         * Sets the "scientificname" element
         */
        void setScientificname(java.lang.String scientificname);
        
        /**
         * Sets (as xml) the "scientificname" element
         */
        void xsetScientificname(org.apache.xmlbeans.XmlString scientificname);
        
        /**
         * Nils the "scientificname" element
         */
        void setNilScientificname();
        
        /**
         * Gets the "like" element
         */
        boolean getLike();
        
        /**
         * Gets (as xml) the "like" element
         */
        org.apache.xmlbeans.XmlBoolean xgetLike();
        
        /**
         * Tests for nil "like" element
         */
        boolean isNilLike();
        
        /**
         * Sets the "like" element
         */
        void setLike(boolean like);
        
        /**
         * Sets (as xml) the "like" element
         */
        void xsetLike(org.apache.xmlbeans.XmlBoolean like);
        
        /**
         * Nils the "like" element
         */
        void setNilLike();
        
        /**
         * Gets the "fuzzy" element
         */
        boolean getFuzzy();
        
        /**
         * Gets (as xml) the "fuzzy" element
         */
        org.apache.xmlbeans.XmlBoolean xgetFuzzy();
        
        /**
         * Tests for nil "fuzzy" element
         */
        boolean isNilFuzzy();
        
        /**
         * Sets the "fuzzy" element
         */
        void setFuzzy(boolean fuzzy);
        
        /**
         * Sets (as xml) the "fuzzy" element
         */
        void xsetFuzzy(org.apache.xmlbeans.XmlBoolean fuzzy);
        
        /**
         * Nils the "fuzzy" element
         */
        void setNilFuzzy();
        
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
            public static aphia.v1_0.aphiarecords.GetAphiaRecordsDocument.GetAphiaRecords newInstance() {
              return (aphia.v1_0.aphiarecords.GetAphiaRecordsDocument.GetAphiaRecords) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static aphia.v1_0.aphiarecords.GetAphiaRecordsDocument.GetAphiaRecords newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (aphia.v1_0.aphiarecords.GetAphiaRecordsDocument.GetAphiaRecords) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsDocument newInstance() {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aphia.v1_0.aphiarecords.GetAphiaRecordsDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aphia.v1_0.aphiarecords.GetAphiaRecordsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}

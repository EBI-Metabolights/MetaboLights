/*
 * An XML document type.
 * Localname: getAphiaRecordsByNames
 * Namespace: http://aphia/v1.0/AphiaMatches
 * Java type: aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiamatches;


/**
 * A document containing one getAphiaRecordsByNames(@http://aphia/v1.0/AphiaMatches) element.
 *
 * This is a complex type.
 */
public interface GetAphiaRecordsByNamesDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetAphiaRecordsByNamesDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC975E981773C6D5C52067BB5301352BB").resolveHandle("getaphiarecordsbynamesb601doctype");
    
    /**
     * Gets the "getAphiaRecordsByNames" element
     */
    aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument.GetAphiaRecordsByNames getGetAphiaRecordsByNames();
    
    /**
     * Sets the "getAphiaRecordsByNames" element
     */
    void setGetAphiaRecordsByNames(aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument.GetAphiaRecordsByNames getAphiaRecordsByNames);
    
    /**
     * Appends and returns a new empty "getAphiaRecordsByNames" element
     */
    aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument.GetAphiaRecordsByNames addNewGetAphiaRecordsByNames();
    
    /**
     * An XML getAphiaRecordsByNames(@http://aphia/v1.0/AphiaMatches).
     *
     * This is a complex type.
     */
    public interface GetAphiaRecordsByNames extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetAphiaRecordsByNames.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC975E981773C6D5C52067BB5301352BB").resolveHandle("getaphiarecordsbynames8b47elemtype");
        
        /**
         * Gets the "scientificnames" element
         */
        aphia.v1_0.Scientificnames getScientificnames();
        
        /**
         * Tests for nil "scientificnames" element
         */
        boolean isNilScientificnames();
        
        /**
         * Sets the "scientificnames" element
         */
        void setScientificnames(aphia.v1_0.Scientificnames scientificnames);
        
        /**
         * Appends and returns a new empty "scientificnames" element
         */
        aphia.v1_0.Scientificnames addNewScientificnames();
        
        /**
         * Nils the "scientificnames" element
         */
        void setNilScientificnames();
        
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
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument.GetAphiaRecordsByNames newInstance() {
              return (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument.GetAphiaRecordsByNames) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument.GetAphiaRecordsByNames newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument.GetAphiaRecordsByNames) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument newInstance() {
          return (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aphia.v1_0.aphiamatches.GetAphiaRecordsByNamesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}

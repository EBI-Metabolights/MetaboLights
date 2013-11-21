/*
 * An XML document type.
 * Localname: getAphiaVernacularsByID
 * Namespace: http://aphia/v1.0/AphiaVernaculars
 * Java type: aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiavernaculars;


/**
 * A document containing one getAphiaVernacularsByID(@http://aphia/v1.0/AphiaVernaculars) element.
 *
 * This is a complex type.
 */
public interface GetAphiaVernacularsByIDDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetAphiaVernacularsByIDDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC975E981773C6D5C52067BB5301352BB").resolveHandle("getaphiavernacularsbyidb813doctype");
    
    /**
     * Gets the "getAphiaVernacularsByID" element
     */
    aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument.GetAphiaVernacularsByID getGetAphiaVernacularsByID();
    
    /**
     * Sets the "getAphiaVernacularsByID" element
     */
    void setGetAphiaVernacularsByID(aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument.GetAphiaVernacularsByID getAphiaVernacularsByID);
    
    /**
     * Appends and returns a new empty "getAphiaVernacularsByID" element
     */
    aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument.GetAphiaVernacularsByID addNewGetAphiaVernacularsByID();
    
    /**
     * An XML getAphiaVernacularsByID(@http://aphia/v1.0/AphiaVernaculars).
     *
     * This is a complex type.
     */
    public interface GetAphiaVernacularsByID extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetAphiaVernacularsByID.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC975E981773C6D5C52067BB5301352BB").resolveHandle("getaphiavernacularsbyid1532elemtype");
        
        /**
         * Gets the "AphiaID" element
         */
        int getAphiaID();
        
        /**
         * Gets (as xml) the "AphiaID" element
         */
        org.apache.xmlbeans.XmlInt xgetAphiaID();
        
        /**
         * Tests for nil "AphiaID" element
         */
        boolean isNilAphiaID();
        
        /**
         * Sets the "AphiaID" element
         */
        void setAphiaID(int aphiaID);
        
        /**
         * Sets (as xml) the "AphiaID" element
         */
        void xsetAphiaID(org.apache.xmlbeans.XmlInt aphiaID);
        
        /**
         * Nils the "AphiaID" element
         */
        void setNilAphiaID();
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument.GetAphiaVernacularsByID newInstance() {
              return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument.GetAphiaVernacularsByID) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument.GetAphiaVernacularsByID newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument.GetAphiaVernacularsByID) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument newInstance() {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}

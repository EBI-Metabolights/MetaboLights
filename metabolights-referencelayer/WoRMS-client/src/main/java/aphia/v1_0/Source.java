/*
 * XML Type:  Source
 * Namespace: http://aphia/v1.0
 * Java type: aphia.v1_0.Source
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0;


/**
 * An XML Source(@http://aphia/v1.0).
 *
 * This is a complex type.
 */
public interface Source extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Source.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC975E981773C6D5C52067BB5301352BB").resolveHandle("source7a11type");
    
    /**
     * Gets the "source_id" element
     */
    int getSourceId();
    
    /**
     * Gets (as xml) the "source_id" element
     */
    org.apache.xmlbeans.XmlInt xgetSourceId();
    
    /**
     * Sets the "source_id" element
     */
    void setSourceId(int sourceId);
    
    /**
     * Sets (as xml) the "source_id" element
     */
    void xsetSourceId(org.apache.xmlbeans.XmlInt sourceId);
    
    /**
     * Gets the "use" element
     */
    java.lang.String getUse();
    
    /**
     * Gets (as xml) the "use" element
     */
    org.apache.xmlbeans.XmlString xgetUse();
    
    /**
     * Sets the "use" element
     */
    void setUse(java.lang.String use);
    
    /**
     * Sets (as xml) the "use" element
     */
    void xsetUse(org.apache.xmlbeans.XmlString use);
    
    /**
     * Gets the "reference" element
     */
    java.lang.String getReference();
    
    /**
     * Gets (as xml) the "reference" element
     */
    org.apache.xmlbeans.XmlString xgetReference();
    
    /**
     * Sets the "reference" element
     */
    void setReference(java.lang.String reference);
    
    /**
     * Sets (as xml) the "reference" element
     */
    void xsetReference(org.apache.xmlbeans.XmlString reference);
    
    /**
     * Gets the "page" element
     */
    java.lang.String getPage();
    
    /**
     * Gets (as xml) the "page" element
     */
    org.apache.xmlbeans.XmlString xgetPage();
    
    /**
     * Sets the "page" element
     */
    void setPage(java.lang.String page);
    
    /**
     * Sets (as xml) the "page" element
     */
    void xsetPage(org.apache.xmlbeans.XmlString page);
    
    /**
     * Gets the "url" element
     */
    java.lang.String getUrl();
    
    /**
     * Gets (as xml) the "url" element
     */
    org.apache.xmlbeans.XmlString xgetUrl();
    
    /**
     * Sets the "url" element
     */
    void setUrl(java.lang.String url);
    
    /**
     * Sets (as xml) the "url" element
     */
    void xsetUrl(org.apache.xmlbeans.XmlString url);
    
    /**
     * Gets the "link" element
     */
    java.lang.String getLink();
    
    /**
     * Gets (as xml) the "link" element
     */
    org.apache.xmlbeans.XmlString xgetLink();
    
    /**
     * Sets the "link" element
     */
    void setLink(java.lang.String link);
    
    /**
     * Sets (as xml) the "link" element
     */
    void xsetLink(org.apache.xmlbeans.XmlString link);
    
    /**
     * Gets the "fulltext" element
     */
    java.lang.String getFulltext();
    
    /**
     * Gets (as xml) the "fulltext" element
     */
    org.apache.xmlbeans.XmlString xgetFulltext();
    
    /**
     * Sets the "fulltext" element
     */
    void setFulltext(java.lang.String fulltext);
    
    /**
     * Sets (as xml) the "fulltext" element
     */
    void xsetFulltext(org.apache.xmlbeans.XmlString fulltext);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static aphia.v1_0.Source newInstance() {
          return (aphia.v1_0.Source) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static aphia.v1_0.Source newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (aphia.v1_0.Source) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static aphia.v1_0.Source parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.Source) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static aphia.v1_0.Source parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.Source) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static aphia.v1_0.Source parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.Source) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static aphia.v1_0.Source parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.Source) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static aphia.v1_0.Source parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.Source) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static aphia.v1_0.Source parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.Source) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static aphia.v1_0.Source parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.Source) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static aphia.v1_0.Source parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.Source) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static aphia.v1_0.Source parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.Source) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static aphia.v1_0.Source parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aphia.v1_0.Source) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static aphia.v1_0.Source parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.Source) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static aphia.v1_0.Source parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.Source) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static aphia.v1_0.Source parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.Source) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static aphia.v1_0.Source parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aphia.v1_0.Source) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aphia.v1_0.Source parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aphia.v1_0.Source) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aphia.v1_0.Source parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aphia.v1_0.Source) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}

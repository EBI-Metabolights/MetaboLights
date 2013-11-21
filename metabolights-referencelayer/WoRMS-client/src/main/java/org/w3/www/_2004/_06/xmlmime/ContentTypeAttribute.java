/*
 * An XML attribute type.
 * Localname: contentType
 * Namespace: http://www.w3.org/2004/06/xmlmime
 * Java type: org.w3.www._2004._06.xmlmime.ContentTypeAttribute
 *
 * Automatically generated - do not modify.
 */
package org.w3.www._2004._06.xmlmime;


/**
 * A document containing one contentType(@http://www.w3.org/2004/06/xmlmime) attribute.
 *
 * This is a complex type.
 */
public interface ContentTypeAttribute extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ContentTypeAttribute.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC975E981773C6D5C52067BB5301352BB").resolveHandle("contenttypeed83attrtypetype");
    
    /**
     * Gets the "contentType" attribute
     */
    java.lang.String getContentType();
    
    /**
     * Gets (as xml) the "contentType" attribute
     */
    org.w3.www._2004._06.xmlmime.ContentTypeAttribute.ContentType xgetContentType();
    
    /**
     * True if has "contentType" attribute
     */
    boolean isSetContentType();
    
    /**
     * Sets the "contentType" attribute
     */
    void setContentType(java.lang.String contentType);
    
    /**
     * Sets (as xml) the "contentType" attribute
     */
    void xsetContentType(org.w3.www._2004._06.xmlmime.ContentTypeAttribute.ContentType contentType);
    
    /**
     * Unsets the "contentType" attribute
     */
    void unsetContentType();
    
    /**
     * An XML contentType(@http://www.w3.org/2004/06/xmlmime).
     *
     * This is an atomic type that is a restriction of org.w3.www._2004._06.xmlmime.ContentTypeAttribute$ContentType.
     */
    public interface ContentType extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ContentType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC975E981773C6D5C52067BB5301352BB").resolveHandle("contenttyped54eattrtype");
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static org.w3.www._2004._06.xmlmime.ContentTypeAttribute.ContentType newValue(java.lang.Object obj) {
              return (org.w3.www._2004._06.xmlmime.ContentTypeAttribute.ContentType) type.newValue( obj ); }
            
            public static org.w3.www._2004._06.xmlmime.ContentTypeAttribute.ContentType newInstance() {
              return (org.w3.www._2004._06.xmlmime.ContentTypeAttribute.ContentType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static org.w3.www._2004._06.xmlmime.ContentTypeAttribute.ContentType newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (org.w3.www._2004._06.xmlmime.ContentTypeAttribute.ContentType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static org.w3.www._2004._06.xmlmime.ContentTypeAttribute newInstance() {
          return (org.w3.www._2004._06.xmlmime.ContentTypeAttribute) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static org.w3.www._2004._06.xmlmime.ContentTypeAttribute newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (org.w3.www._2004._06.xmlmime.ContentTypeAttribute) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static org.w3.www._2004._06.xmlmime.ContentTypeAttribute parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (org.w3.www._2004._06.xmlmime.ContentTypeAttribute) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static org.w3.www._2004._06.xmlmime.ContentTypeAttribute parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.www._2004._06.xmlmime.ContentTypeAttribute) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static org.w3.www._2004._06.xmlmime.ContentTypeAttribute parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.www._2004._06.xmlmime.ContentTypeAttribute) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static org.w3.www._2004._06.xmlmime.ContentTypeAttribute parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.www._2004._06.xmlmime.ContentTypeAttribute) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static org.w3.www._2004._06.xmlmime.ContentTypeAttribute parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.www._2004._06.xmlmime.ContentTypeAttribute) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static org.w3.www._2004._06.xmlmime.ContentTypeAttribute parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.www._2004._06.xmlmime.ContentTypeAttribute) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static org.w3.www._2004._06.xmlmime.ContentTypeAttribute parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.www._2004._06.xmlmime.ContentTypeAttribute) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static org.w3.www._2004._06.xmlmime.ContentTypeAttribute parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.www._2004._06.xmlmime.ContentTypeAttribute) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static org.w3.www._2004._06.xmlmime.ContentTypeAttribute parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.www._2004._06.xmlmime.ContentTypeAttribute) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static org.w3.www._2004._06.xmlmime.ContentTypeAttribute parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.w3.www._2004._06.xmlmime.ContentTypeAttribute) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static org.w3.www._2004._06.xmlmime.ContentTypeAttribute parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (org.w3.www._2004._06.xmlmime.ContentTypeAttribute) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static org.w3.www._2004._06.xmlmime.ContentTypeAttribute parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.www._2004._06.xmlmime.ContentTypeAttribute) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static org.w3.www._2004._06.xmlmime.ContentTypeAttribute parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (org.w3.www._2004._06.xmlmime.ContentTypeAttribute) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static org.w3.www._2004._06.xmlmime.ContentTypeAttribute parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.w3.www._2004._06.xmlmime.ContentTypeAttribute) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.w3.www._2004._06.xmlmime.ContentTypeAttribute parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.w3.www._2004._06.xmlmime.ContentTypeAttribute) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.w3.www._2004._06.xmlmime.ContentTypeAttribute parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.w3.www._2004._06.xmlmime.ContentTypeAttribute) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}

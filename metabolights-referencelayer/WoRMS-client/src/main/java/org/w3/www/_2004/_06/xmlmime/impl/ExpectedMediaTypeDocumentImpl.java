/*
 * An XML document type.
 * Localname: expectedMediaType
 * Namespace: http://www.w3.org/2004/06/xmlmime
 * Java type: org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument
 *
 * Automatically generated - do not modify.
 */
package org.w3.www._2004._06.xmlmime.impl;
/**
 * A document containing one expectedMediaType(@http://www.w3.org/2004/06/xmlmime) element.
 *
 * This is a complex type.
 */
public class ExpectedMediaTypeDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument
{
    
    public ExpectedMediaTypeDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName EXPECTEDMEDIATYPE$0 = 
        new javax.xml.namespace.QName("http://www.w3.org/2004/06/xmlmime", "expectedMediaType");
    
    
    /**
     * Gets the "expectedMediaType" element
     */
    public java.util.List getExpectedMediaType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXPECTEDMEDIATYPE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getListValue();
        }
    }
    
    /**
     * Gets (as xml) the "expectedMediaType" element
     */
    public org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument.ExpectedMediaType xgetExpectedMediaType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument.ExpectedMediaType target = null;
            target = (org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument.ExpectedMediaType)get_store().find_element_user(EXPECTEDMEDIATYPE$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "expectedMediaType" element
     */
    public void setExpectedMediaType(java.util.List expectedMediaType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXPECTEDMEDIATYPE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EXPECTEDMEDIATYPE$0);
            }
            target.setListValue(expectedMediaType);
        }
    }
    
    /**
     * Sets (as xml) the "expectedMediaType" element
     */
    public void xsetExpectedMediaType(org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument.ExpectedMediaType expectedMediaType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument.ExpectedMediaType target = null;
            target = (org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument.ExpectedMediaType)get_store().find_element_user(EXPECTEDMEDIATYPE$0, 0);
            if (target == null)
            {
                target = (org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument.ExpectedMediaType)get_store().add_element_user(EXPECTEDMEDIATYPE$0);
            }
            target.set(expectedMediaType);
        }
    }
    /**
     * An XML expectedMediaType(@http://www.w3.org/2004/06/xmlmime).
     *
     * This is a list type whose items are org.w3.www._2004._06.xmlmime.ExpectedMediaTypeItem.
     */
    public static class ExpectedMediaTypeImpl extends org.apache.xmlbeans.impl.values.XmlListImpl implements org.w3.www._2004._06.xmlmime.ExpectedMediaTypeDocument.ExpectedMediaType
    {
        
        public ExpectedMediaTypeImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected ExpectedMediaTypeImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}

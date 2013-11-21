/*
 * An XML document type.
 * Localname: gYear
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.GYearDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one gYear(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class GYearDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.GYearDocument
{
    
    public GYearDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GYEAR$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "gYear");
    
    
    /**
     * Gets the "gYear" element
     */
    public org.xmlsoap.schemas.soap.encoding.GYear getGYear()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.GYear target = null;
            target = (org.xmlsoap.schemas.soap.encoding.GYear)get_store().find_element_user(GYEAR$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "gYear" element
     */
    public void setGYear(org.xmlsoap.schemas.soap.encoding.GYear gYear)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.GYear target = null;
            target = (org.xmlsoap.schemas.soap.encoding.GYear)get_store().find_element_user(GYEAR$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.GYear)get_store().add_element_user(GYEAR$0);
            }
            target.set(gYear);
        }
    }
    
    /**
     * Appends and returns a new empty "gYear" element
     */
    public org.xmlsoap.schemas.soap.encoding.GYear addNewGYear()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.GYear target = null;
            target = (org.xmlsoap.schemas.soap.encoding.GYear)get_store().add_element_user(GYEAR$0);
            return target;
        }
    }
}

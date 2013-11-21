/*
 * An XML document type.
 * Localname: gDay
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.GDayDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one gDay(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class GDayDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.GDayDocument
{
    
    public GDayDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GDAY$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "gDay");
    
    
    /**
     * Gets the "gDay" element
     */
    public org.xmlsoap.schemas.soap.encoding.GDay getGDay()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.GDay target = null;
            target = (org.xmlsoap.schemas.soap.encoding.GDay)get_store().find_element_user(GDAY$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "gDay" element
     */
    public void setGDay(org.xmlsoap.schemas.soap.encoding.GDay gDay)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.GDay target = null;
            target = (org.xmlsoap.schemas.soap.encoding.GDay)get_store().find_element_user(GDAY$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.GDay)get_store().add_element_user(GDAY$0);
            }
            target.set(gDay);
        }
    }
    
    /**
     * Appends and returns a new empty "gDay" element
     */
    public org.xmlsoap.schemas.soap.encoding.GDay addNewGDay()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.GDay target = null;
            target = (org.xmlsoap.schemas.soap.encoding.GDay)get_store().add_element_user(GDAY$0);
            return target;
        }
    }
}

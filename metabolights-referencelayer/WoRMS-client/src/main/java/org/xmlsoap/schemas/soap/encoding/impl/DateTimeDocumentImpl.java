/*
 * An XML document type.
 * Localname: dateTime
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.DateTimeDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one dateTime(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class DateTimeDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.DateTimeDocument
{
    
    public DateTimeDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATETIME$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "dateTime");
    
    
    /**
     * Gets the "dateTime" element
     */
    public org.xmlsoap.schemas.soap.encoding.DateTime getDateTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.DateTime target = null;
            target = (org.xmlsoap.schemas.soap.encoding.DateTime)get_store().find_element_user(DATETIME$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "dateTime" element
     */
    public void setDateTime(org.xmlsoap.schemas.soap.encoding.DateTime dateTime)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.DateTime target = null;
            target = (org.xmlsoap.schemas.soap.encoding.DateTime)get_store().find_element_user(DATETIME$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.DateTime)get_store().add_element_user(DATETIME$0);
            }
            target.set(dateTime);
        }
    }
    
    /**
     * Appends and returns a new empty "dateTime" element
     */
    public org.xmlsoap.schemas.soap.encoding.DateTime addNewDateTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.DateTime target = null;
            target = (org.xmlsoap.schemas.soap.encoding.DateTime)get_store().add_element_user(DATETIME$0);
            return target;
        }
    }
}

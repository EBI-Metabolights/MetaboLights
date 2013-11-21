/*
 * An XML document type.
 * Localname: gYearMonth
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.GYearMonthDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one gYearMonth(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class GYearMonthDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.GYearMonthDocument
{
    
    public GYearMonthDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GYEARMONTH$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "gYearMonth");
    
    
    /**
     * Gets the "gYearMonth" element
     */
    public org.xmlsoap.schemas.soap.encoding.GYearMonth getGYearMonth()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.GYearMonth target = null;
            target = (org.xmlsoap.schemas.soap.encoding.GYearMonth)get_store().find_element_user(GYEARMONTH$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "gYearMonth" element
     */
    public void setGYearMonth(org.xmlsoap.schemas.soap.encoding.GYearMonth gYearMonth)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.GYearMonth target = null;
            target = (org.xmlsoap.schemas.soap.encoding.GYearMonth)get_store().find_element_user(GYEARMONTH$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.GYearMonth)get_store().add_element_user(GYEARMONTH$0);
            }
            target.set(gYearMonth);
        }
    }
    
    /**
     * Appends and returns a new empty "gYearMonth" element
     */
    public org.xmlsoap.schemas.soap.encoding.GYearMonth addNewGYearMonth()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.GYearMonth target = null;
            target = (org.xmlsoap.schemas.soap.encoding.GYearMonth)get_store().add_element_user(GYEARMONTH$0);
            return target;
        }
    }
}

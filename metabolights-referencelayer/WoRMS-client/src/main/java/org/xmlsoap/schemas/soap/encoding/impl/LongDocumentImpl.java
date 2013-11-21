/*
 * An XML document type.
 * Localname: long
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.LongDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one long(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class LongDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.LongDocument
{
    
    public LongDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName LONG$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "long");
    
    
    /**
     * Gets the "long" element
     */
    public org.xmlsoap.schemas.soap.encoding.Long getLong()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Long target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Long)get_store().find_element_user(LONG$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "long" element
     */
    public void setLong(org.xmlsoap.schemas.soap.encoding.Long xlong)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Long target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Long)get_store().find_element_user(LONG$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.Long)get_store().add_element_user(LONG$0);
            }
            target.set(xlong);
        }
    }
    
    /**
     * Appends and returns a new empty "long" element
     */
    public org.xmlsoap.schemas.soap.encoding.Long addNewLong()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Long target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Long)get_store().add_element_user(LONG$0);
            return target;
        }
    }
}

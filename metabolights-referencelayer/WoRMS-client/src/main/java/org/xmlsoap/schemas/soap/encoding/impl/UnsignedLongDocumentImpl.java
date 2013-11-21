/*
 * An XML document type.
 * Localname: unsignedLong
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.UnsignedLongDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one unsignedLong(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class UnsignedLongDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.UnsignedLongDocument
{
    
    public UnsignedLongDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName UNSIGNEDLONG$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "unsignedLong");
    
    
    /**
     * Gets the "unsignedLong" element
     */
    public org.xmlsoap.schemas.soap.encoding.UnsignedLong getUnsignedLong()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.UnsignedLong target = null;
            target = (org.xmlsoap.schemas.soap.encoding.UnsignedLong)get_store().find_element_user(UNSIGNEDLONG$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "unsignedLong" element
     */
    public void setUnsignedLong(org.xmlsoap.schemas.soap.encoding.UnsignedLong unsignedLong)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.UnsignedLong target = null;
            target = (org.xmlsoap.schemas.soap.encoding.UnsignedLong)get_store().find_element_user(UNSIGNEDLONG$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.UnsignedLong)get_store().add_element_user(UNSIGNEDLONG$0);
            }
            target.set(unsignedLong);
        }
    }
    
    /**
     * Appends and returns a new empty "unsignedLong" element
     */
    public org.xmlsoap.schemas.soap.encoding.UnsignedLong addNewUnsignedLong()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.UnsignedLong target = null;
            target = (org.xmlsoap.schemas.soap.encoding.UnsignedLong)get_store().add_element_user(UNSIGNEDLONG$0);
            return target;
        }
    }
}

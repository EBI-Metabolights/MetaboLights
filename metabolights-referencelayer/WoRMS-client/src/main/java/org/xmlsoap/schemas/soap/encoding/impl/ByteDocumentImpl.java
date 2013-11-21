/*
 * An XML document type.
 * Localname: byte
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.ByteDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one byte(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class ByteDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.ByteDocument
{
    
    public ByteDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName BYTE$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "byte");
    
    
    /**
     * Gets the "byte" element
     */
    public org.xmlsoap.schemas.soap.encoding.Byte getByte()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Byte target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Byte)get_store().find_element_user(BYTE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "byte" element
     */
    public void setByte(org.xmlsoap.schemas.soap.encoding.Byte xbyte)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Byte target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Byte)get_store().find_element_user(BYTE$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.Byte)get_store().add_element_user(BYTE$0);
            }
            target.set(xbyte);
        }
    }
    
    /**
     * Appends and returns a new empty "byte" element
     */
    public org.xmlsoap.schemas.soap.encoding.Byte addNewByte()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Byte target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Byte)get_store().add_element_user(BYTE$0);
            return target;
        }
    }
}

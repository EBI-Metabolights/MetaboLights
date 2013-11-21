/*
 * An XML document type.
 * Localname: unsignedByte
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.UnsignedByteDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one unsignedByte(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class UnsignedByteDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.UnsignedByteDocument
{
    
    public UnsignedByteDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName UNSIGNEDBYTE$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "unsignedByte");
    
    
    /**
     * Gets the "unsignedByte" element
     */
    public org.xmlsoap.schemas.soap.encoding.UnsignedByte getUnsignedByte()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.UnsignedByte target = null;
            target = (org.xmlsoap.schemas.soap.encoding.UnsignedByte)get_store().find_element_user(UNSIGNEDBYTE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "unsignedByte" element
     */
    public void setUnsignedByte(org.xmlsoap.schemas.soap.encoding.UnsignedByte unsignedByte)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.UnsignedByte target = null;
            target = (org.xmlsoap.schemas.soap.encoding.UnsignedByte)get_store().find_element_user(UNSIGNEDBYTE$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.UnsignedByte)get_store().add_element_user(UNSIGNEDBYTE$0);
            }
            target.set(unsignedByte);
        }
    }
    
    /**
     * Appends and returns a new empty "unsignedByte" element
     */
    public org.xmlsoap.schemas.soap.encoding.UnsignedByte addNewUnsignedByte()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.UnsignedByte target = null;
            target = (org.xmlsoap.schemas.soap.encoding.UnsignedByte)get_store().add_element_user(UNSIGNEDBYTE$0);
            return target;
        }
    }
}

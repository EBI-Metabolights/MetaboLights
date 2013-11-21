/*
 * An XML document type.
 * Localname: base64Binary
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.Base64BinaryDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one base64Binary(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class Base64BinaryDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.Base64BinaryDocument
{
    
    public Base64BinaryDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName BASE64BINARY$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "base64Binary");
    
    
    /**
     * Gets the "base64Binary" element
     */
    public org.xmlsoap.schemas.soap.encoding.Base64Binary getBase64Binary()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Base64Binary target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Base64Binary)get_store().find_element_user(BASE64BINARY$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "base64Binary" element
     */
    public void setBase64Binary(org.xmlsoap.schemas.soap.encoding.Base64Binary base64Binary)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Base64Binary target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Base64Binary)get_store().find_element_user(BASE64BINARY$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.Base64Binary)get_store().add_element_user(BASE64BINARY$0);
            }
            target.set(base64Binary);
        }
    }
    
    /**
     * Appends and returns a new empty "base64Binary" element
     */
    public org.xmlsoap.schemas.soap.encoding.Base64Binary addNewBase64Binary()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Base64Binary target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Base64Binary)get_store().add_element_user(BASE64BINARY$0);
            return target;
        }
    }
}

/*
 * An XML document type.
 * Localname: int
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.IntDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one int(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class IntDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.IntDocument
{
    
    public IntDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName INT$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int");
    
    
    /**
     * Gets the "int" element
     */
    public org.xmlsoap.schemas.soap.encoding.Int getInt()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Int target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Int)get_store().find_element_user(INT$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "int" element
     */
    public void setInt(org.xmlsoap.schemas.soap.encoding.Int xint)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Int target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Int)get_store().find_element_user(INT$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.Int)get_store().add_element_user(INT$0);
            }
            target.set(xint);
        }
    }
    
    /**
     * Appends and returns a new empty "int" element
     */
    public org.xmlsoap.schemas.soap.encoding.Int addNewInt()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Int target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Int)get_store().add_element_user(INT$0);
            return target;
        }
    }
}

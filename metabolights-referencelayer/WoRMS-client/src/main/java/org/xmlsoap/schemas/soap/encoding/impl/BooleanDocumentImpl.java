/*
 * An XML document type.
 * Localname: boolean
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.BooleanDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one boolean(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class BooleanDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.BooleanDocument
{
    
    public BooleanDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName BOOLEAN$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "boolean");
    
    
    /**
     * Gets the "boolean" element
     */
    public org.xmlsoap.schemas.soap.encoding.Boolean getBoolean()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Boolean target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Boolean)get_store().find_element_user(BOOLEAN$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "boolean" element
     */
    public void setBoolean(org.xmlsoap.schemas.soap.encoding.Boolean xboolean)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Boolean target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Boolean)get_store().find_element_user(BOOLEAN$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.Boolean)get_store().add_element_user(BOOLEAN$0);
            }
            target.set(xboolean);
        }
    }
    
    /**
     * Appends and returns a new empty "boolean" element
     */
    public org.xmlsoap.schemas.soap.encoding.Boolean addNewBoolean()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Boolean target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Boolean)get_store().add_element_user(BOOLEAN$0);
            return target;
        }
    }
}

/*
 * An XML document type.
 * Localname: short
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.ShortDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one short(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class ShortDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.ShortDocument
{
    
    public ShortDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SHORT$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "short");
    
    
    /**
     * Gets the "short" element
     */
    public org.xmlsoap.schemas.soap.encoding.Short getShort()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Short target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Short)get_store().find_element_user(SHORT$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "short" element
     */
    public void setShort(org.xmlsoap.schemas.soap.encoding.Short xshort)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Short target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Short)get_store().find_element_user(SHORT$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.Short)get_store().add_element_user(SHORT$0);
            }
            target.set(xshort);
        }
    }
    
    /**
     * Appends and returns a new empty "short" element
     */
    public org.xmlsoap.schemas.soap.encoding.Short addNewShort()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Short target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Short)get_store().add_element_user(SHORT$0);
            return target;
        }
    }
}

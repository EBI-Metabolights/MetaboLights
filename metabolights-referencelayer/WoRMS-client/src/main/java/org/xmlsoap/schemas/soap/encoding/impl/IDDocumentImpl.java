/*
 * An XML document type.
 * Localname: ID
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.IDDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one ID(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class IDDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.IDDocument
{
    
    public IDDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ID$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "ID");
    
    
    /**
     * Gets the "ID" element
     */
    public org.xmlsoap.schemas.soap.encoding.ID getID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.ID target = null;
            target = (org.xmlsoap.schemas.soap.encoding.ID)get_store().find_element_user(ID$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "ID" element
     */
    public void setID(org.xmlsoap.schemas.soap.encoding.ID id)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.ID target = null;
            target = (org.xmlsoap.schemas.soap.encoding.ID)get_store().find_element_user(ID$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.ID)get_store().add_element_user(ID$0);
            }
            target.set(id);
        }
    }
    
    /**
     * Appends and returns a new empty "ID" element
     */
    public org.xmlsoap.schemas.soap.encoding.ID addNewID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.ID target = null;
            target = (org.xmlsoap.schemas.soap.encoding.ID)get_store().add_element_user(ID$0);
            return target;
        }
    }
}

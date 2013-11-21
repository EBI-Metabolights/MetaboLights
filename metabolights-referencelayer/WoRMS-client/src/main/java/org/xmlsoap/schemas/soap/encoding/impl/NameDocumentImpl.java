/*
 * An XML document type.
 * Localname: Name
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.NameDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one Name(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class NameDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.NameDocument
{
    
    public NameDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName NAME$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "Name");
    
    
    /**
     * Gets the "Name" element
     */
    public org.xmlsoap.schemas.soap.encoding.Name getName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Name target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Name)get_store().find_element_user(NAME$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "Name" element
     */
    public void setName(org.xmlsoap.schemas.soap.encoding.Name name)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Name target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Name)get_store().find_element_user(NAME$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.Name)get_store().add_element_user(NAME$0);
            }
            target.set(name);
        }
    }
    
    /**
     * Appends and returns a new empty "Name" element
     */
    public org.xmlsoap.schemas.soap.encoding.Name addNewName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Name target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Name)get_store().add_element_user(NAME$0);
            return target;
        }
    }
}

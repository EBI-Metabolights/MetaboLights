/*
 * An XML document type.
 * Localname: NMTOKEN
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.NMTOKENDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one NMTOKEN(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class NMTOKENDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.NMTOKENDocument
{
    
    public NMTOKENDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName NMTOKEN$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "NMTOKEN");
    
    
    /**
     * Gets the "NMTOKEN" element
     */
    public org.xmlsoap.schemas.soap.encoding.NMTOKEN getNMTOKEN()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.NMTOKEN target = null;
            target = (org.xmlsoap.schemas.soap.encoding.NMTOKEN)get_store().find_element_user(NMTOKEN$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "NMTOKEN" element
     */
    public void setNMTOKEN(org.xmlsoap.schemas.soap.encoding.NMTOKEN nmtoken)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.NMTOKEN target = null;
            target = (org.xmlsoap.schemas.soap.encoding.NMTOKEN)get_store().find_element_user(NMTOKEN$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.NMTOKEN)get_store().add_element_user(NMTOKEN$0);
            }
            target.set(nmtoken);
        }
    }
    
    /**
     * Appends and returns a new empty "NMTOKEN" element
     */
    public org.xmlsoap.schemas.soap.encoding.NMTOKEN addNewNMTOKEN()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.NMTOKEN target = null;
            target = (org.xmlsoap.schemas.soap.encoding.NMTOKEN)get_store().add_element_user(NMTOKEN$0);
            return target;
        }
    }
}

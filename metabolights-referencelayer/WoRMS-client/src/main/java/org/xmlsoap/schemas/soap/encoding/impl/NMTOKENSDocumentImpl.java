/*
 * An XML document type.
 * Localname: NMTOKENS
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.NMTOKENSDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one NMTOKENS(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class NMTOKENSDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.NMTOKENSDocument
{
    
    public NMTOKENSDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName NMTOKENS$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "NMTOKENS");
    
    
    /**
     * Gets the "NMTOKENS" element
     */
    public org.xmlsoap.schemas.soap.encoding.NMTOKENS getNMTOKENS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.NMTOKENS target = null;
            target = (org.xmlsoap.schemas.soap.encoding.NMTOKENS)get_store().find_element_user(NMTOKENS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "NMTOKENS" element
     */
    public void setNMTOKENS(org.xmlsoap.schemas.soap.encoding.NMTOKENS nmtokens)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.NMTOKENS target = null;
            target = (org.xmlsoap.schemas.soap.encoding.NMTOKENS)get_store().find_element_user(NMTOKENS$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.NMTOKENS)get_store().add_element_user(NMTOKENS$0);
            }
            target.set(nmtokens);
        }
    }
    
    /**
     * Appends and returns a new empty "NMTOKENS" element
     */
    public org.xmlsoap.schemas.soap.encoding.NMTOKENS addNewNMTOKENS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.NMTOKENS target = null;
            target = (org.xmlsoap.schemas.soap.encoding.NMTOKENS)get_store().add_element_user(NMTOKENS$0);
            return target;
        }
    }
}

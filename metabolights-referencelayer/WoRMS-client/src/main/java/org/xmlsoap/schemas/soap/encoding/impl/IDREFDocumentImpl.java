/*
 * An XML document type.
 * Localname: IDREF
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.IDREFDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one IDREF(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class IDREFDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.IDREFDocument
{
    
    public IDREFDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDREF$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "IDREF");
    
    
    /**
     * Gets the "IDREF" element
     */
    public org.xmlsoap.schemas.soap.encoding.IDREF getIDREF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.IDREF target = null;
            target = (org.xmlsoap.schemas.soap.encoding.IDREF)get_store().find_element_user(IDREF$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "IDREF" element
     */
    public void setIDREF(org.xmlsoap.schemas.soap.encoding.IDREF idref)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.IDREF target = null;
            target = (org.xmlsoap.schemas.soap.encoding.IDREF)get_store().find_element_user(IDREF$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.IDREF)get_store().add_element_user(IDREF$0);
            }
            target.set(idref);
        }
    }
    
    /**
     * Appends and returns a new empty "IDREF" element
     */
    public org.xmlsoap.schemas.soap.encoding.IDREF addNewIDREF()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.IDREF target = null;
            target = (org.xmlsoap.schemas.soap.encoding.IDREF)get_store().add_element_user(IDREF$0);
            return target;
        }
    }
}

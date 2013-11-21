/*
 * An XML document type.
 * Localname: IDREFS
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.IDREFSDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one IDREFS(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class IDREFSDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.IDREFSDocument
{
    
    public IDREFSDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDREFS$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "IDREFS");
    
    
    /**
     * Gets the "IDREFS" element
     */
    public org.xmlsoap.schemas.soap.encoding.IDREFS getIDREFS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.IDREFS target = null;
            target = (org.xmlsoap.schemas.soap.encoding.IDREFS)get_store().find_element_user(IDREFS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "IDREFS" element
     */
    public void setIDREFS(org.xmlsoap.schemas.soap.encoding.IDREFS idrefs)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.IDREFS target = null;
            target = (org.xmlsoap.schemas.soap.encoding.IDREFS)get_store().find_element_user(IDREFS$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.IDREFS)get_store().add_element_user(IDREFS$0);
            }
            target.set(idrefs);
        }
    }
    
    /**
     * Appends and returns a new empty "IDREFS" element
     */
    public org.xmlsoap.schemas.soap.encoding.IDREFS addNewIDREFS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.IDREFS target = null;
            target = (org.xmlsoap.schemas.soap.encoding.IDREFS)get_store().add_element_user(IDREFS$0);
            return target;
        }
    }
}

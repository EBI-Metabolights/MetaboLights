/*
 * An XML document type.
 * Localname: ENTITY
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.ENTITYDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one ENTITY(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class ENTITYDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.ENTITYDocument
{
    
    public ENTITYDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ENTITY$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "ENTITY");
    
    
    /**
     * Gets the "ENTITY" element
     */
    public org.xmlsoap.schemas.soap.encoding.ENTITY getENTITY()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.ENTITY target = null;
            target = (org.xmlsoap.schemas.soap.encoding.ENTITY)get_store().find_element_user(ENTITY$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "ENTITY" element
     */
    public void setENTITY(org.xmlsoap.schemas.soap.encoding.ENTITY entity)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.ENTITY target = null;
            target = (org.xmlsoap.schemas.soap.encoding.ENTITY)get_store().find_element_user(ENTITY$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.ENTITY)get_store().add_element_user(ENTITY$0);
            }
            target.set(entity);
        }
    }
    
    /**
     * Appends and returns a new empty "ENTITY" element
     */
    public org.xmlsoap.schemas.soap.encoding.ENTITY addNewENTITY()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.ENTITY target = null;
            target = (org.xmlsoap.schemas.soap.encoding.ENTITY)get_store().add_element_user(ENTITY$0);
            return target;
        }
    }
}

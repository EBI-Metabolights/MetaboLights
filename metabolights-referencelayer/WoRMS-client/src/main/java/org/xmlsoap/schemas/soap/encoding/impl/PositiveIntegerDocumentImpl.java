/*
 * An XML document type.
 * Localname: positiveInteger
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.PositiveIntegerDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one positiveInteger(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class PositiveIntegerDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.PositiveIntegerDocument
{
    
    public PositiveIntegerDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName POSITIVEINTEGER$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "positiveInteger");
    
    
    /**
     * Gets the "positiveInteger" element
     */
    public org.xmlsoap.schemas.soap.encoding.PositiveInteger getPositiveInteger()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.PositiveInteger target = null;
            target = (org.xmlsoap.schemas.soap.encoding.PositiveInteger)get_store().find_element_user(POSITIVEINTEGER$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "positiveInteger" element
     */
    public void setPositiveInteger(org.xmlsoap.schemas.soap.encoding.PositiveInteger positiveInteger)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.PositiveInteger target = null;
            target = (org.xmlsoap.schemas.soap.encoding.PositiveInteger)get_store().find_element_user(POSITIVEINTEGER$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.PositiveInteger)get_store().add_element_user(POSITIVEINTEGER$0);
            }
            target.set(positiveInteger);
        }
    }
    
    /**
     * Appends and returns a new empty "positiveInteger" element
     */
    public org.xmlsoap.schemas.soap.encoding.PositiveInteger addNewPositiveInteger()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.PositiveInteger target = null;
            target = (org.xmlsoap.schemas.soap.encoding.PositiveInteger)get_store().add_element_user(POSITIVEINTEGER$0);
            return target;
        }
    }
}

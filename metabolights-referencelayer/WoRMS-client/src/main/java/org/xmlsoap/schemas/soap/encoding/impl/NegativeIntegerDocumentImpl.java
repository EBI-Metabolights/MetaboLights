/*
 * An XML document type.
 * Localname: negativeInteger
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.NegativeIntegerDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one negativeInteger(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class NegativeIntegerDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.NegativeIntegerDocument
{
    
    public NegativeIntegerDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName NEGATIVEINTEGER$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "negativeInteger");
    
    
    /**
     * Gets the "negativeInteger" element
     */
    public org.xmlsoap.schemas.soap.encoding.NegativeInteger getNegativeInteger()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.NegativeInteger target = null;
            target = (org.xmlsoap.schemas.soap.encoding.NegativeInteger)get_store().find_element_user(NEGATIVEINTEGER$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "negativeInteger" element
     */
    public void setNegativeInteger(org.xmlsoap.schemas.soap.encoding.NegativeInteger negativeInteger)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.NegativeInteger target = null;
            target = (org.xmlsoap.schemas.soap.encoding.NegativeInteger)get_store().find_element_user(NEGATIVEINTEGER$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.NegativeInteger)get_store().add_element_user(NEGATIVEINTEGER$0);
            }
            target.set(negativeInteger);
        }
    }
    
    /**
     * Appends and returns a new empty "negativeInteger" element
     */
    public org.xmlsoap.schemas.soap.encoding.NegativeInteger addNewNegativeInteger()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.NegativeInteger target = null;
            target = (org.xmlsoap.schemas.soap.encoding.NegativeInteger)get_store().add_element_user(NEGATIVEINTEGER$0);
            return target;
        }
    }
}

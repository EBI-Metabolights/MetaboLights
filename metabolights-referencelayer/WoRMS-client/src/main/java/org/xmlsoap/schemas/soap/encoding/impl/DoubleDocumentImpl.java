/*
 * An XML document type.
 * Localname: double
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.DoubleDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one double(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class DoubleDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.DoubleDocument
{
    
    public DoubleDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DOUBLE$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "double");
    
    
    /**
     * Gets the "double" element
     */
    public org.xmlsoap.schemas.soap.encoding.Double getDouble()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Double target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Double)get_store().find_element_user(DOUBLE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "double" element
     */
    public void setDouble(org.xmlsoap.schemas.soap.encoding.Double xdouble)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Double target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Double)get_store().find_element_user(DOUBLE$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.Double)get_store().add_element_user(DOUBLE$0);
            }
            target.set(xdouble);
        }
    }
    
    /**
     * Appends and returns a new empty "double" element
     */
    public org.xmlsoap.schemas.soap.encoding.Double addNewDouble()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Double target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Double)get_store().add_element_user(DOUBLE$0);
            return target;
        }
    }
}

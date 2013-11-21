/*
 * An XML document type.
 * Localname: float
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.FloatDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one float(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class FloatDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.FloatDocument
{
    
    public FloatDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName FLOAT$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "float");
    
    
    /**
     * Gets the "float" element
     */
    public org.xmlsoap.schemas.soap.encoding.Float getFloat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Float target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Float)get_store().find_element_user(FLOAT$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "float" element
     */
    public void setFloat(org.xmlsoap.schemas.soap.encoding.Float xfloat)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Float target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Float)get_store().find_element_user(FLOAT$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.Float)get_store().add_element_user(FLOAT$0);
            }
            target.set(xfloat);
        }
    }
    
    /**
     * Appends and returns a new empty "float" element
     */
    public org.xmlsoap.schemas.soap.encoding.Float addNewFloat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Float target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Float)get_store().add_element_user(FLOAT$0);
            return target;
        }
    }
}

/*
 * An XML document type.
 * Localname: duration
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.DurationDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one duration(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class DurationDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.DurationDocument
{
    
    public DurationDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DURATION$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "duration");
    
    
    /**
     * Gets the "duration" element
     */
    public org.xmlsoap.schemas.soap.encoding.Duration getDuration()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Duration target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Duration)get_store().find_element_user(DURATION$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "duration" element
     */
    public void setDuration(org.xmlsoap.schemas.soap.encoding.Duration duration)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Duration target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Duration)get_store().find_element_user(DURATION$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.Duration)get_store().add_element_user(DURATION$0);
            }
            target.set(duration);
        }
    }
    
    /**
     * Appends and returns a new empty "duration" element
     */
    public org.xmlsoap.schemas.soap.encoding.Duration addNewDuration()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Duration target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Duration)get_store().add_element_user(DURATION$0);
            return target;
        }
    }
}

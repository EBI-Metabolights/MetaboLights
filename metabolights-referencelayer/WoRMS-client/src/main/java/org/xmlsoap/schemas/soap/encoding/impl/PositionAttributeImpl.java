/*
 * An XML attribute type.
 * Localname: position
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.PositionAttribute
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one position(@http://schemas.xmlsoap.org/soap/encoding/) attribute.
 *
 * This is a complex type.
 */
public class PositionAttributeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.PositionAttribute
{
    
    public PositionAttributeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName POSITION$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "position");
    
    
    /**
     * Gets the "position" attribute
     */
    public java.lang.String getPosition()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(POSITION$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "position" attribute
     */
    public org.xmlsoap.schemas.soap.encoding.ArrayCoordinate xgetPosition()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.ArrayCoordinate target = null;
            target = (org.xmlsoap.schemas.soap.encoding.ArrayCoordinate)get_store().find_attribute_user(POSITION$0);
            return target;
        }
    }
    
    /**
     * True if has "position" attribute
     */
    public boolean isSetPosition()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(POSITION$0) != null;
        }
    }
    
    /**
     * Sets the "position" attribute
     */
    public void setPosition(java.lang.String position)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(POSITION$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(POSITION$0);
            }
            target.setStringValue(position);
        }
    }
    
    /**
     * Sets (as xml) the "position" attribute
     */
    public void xsetPosition(org.xmlsoap.schemas.soap.encoding.ArrayCoordinate position)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.ArrayCoordinate target = null;
            target = (org.xmlsoap.schemas.soap.encoding.ArrayCoordinate)get_store().find_attribute_user(POSITION$0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.ArrayCoordinate)get_store().add_attribute_user(POSITION$0);
            }
            target.set(position);
        }
    }
    
    /**
     * Unsets the "position" attribute
     */
    public void unsetPosition()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(POSITION$0);
        }
    }
}

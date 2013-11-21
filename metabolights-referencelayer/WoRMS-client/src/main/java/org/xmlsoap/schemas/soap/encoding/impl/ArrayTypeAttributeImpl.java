/*
 * An XML attribute type.
 * Localname: arrayType
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.ArrayTypeAttribute
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one arrayType(@http://schemas.xmlsoap.org/soap/encoding/) attribute.
 *
 * This is a complex type.
 */
public class ArrayTypeAttributeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.ArrayTypeAttribute
{
    
    public ArrayTypeAttributeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ARRAYTYPE$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "arrayType");
    
    
    /**
     * Gets the "arrayType" attribute
     */
    public java.lang.String getArrayType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ARRAYTYPE$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "arrayType" attribute
     */
    public org.apache.xmlbeans.XmlString xgetArrayType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ARRAYTYPE$0);
            return target;
        }
    }
    
    /**
     * True if has "arrayType" attribute
     */
    public boolean isSetArrayType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ARRAYTYPE$0) != null;
        }
    }
    
    /**
     * Sets the "arrayType" attribute
     */
    public void setArrayType(java.lang.String arrayType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ARRAYTYPE$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ARRAYTYPE$0);
            }
            target.setStringValue(arrayType);
        }
    }
    
    /**
     * Sets (as xml) the "arrayType" attribute
     */
    public void xsetArrayType(org.apache.xmlbeans.XmlString arrayType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ARRAYTYPE$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(ARRAYTYPE$0);
            }
            target.set(arrayType);
        }
    }
    
    /**
     * Unsets the "arrayType" attribute
     */
    public void unsetArrayType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ARRAYTYPE$0);
        }
    }
}

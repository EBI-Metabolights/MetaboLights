/*
 * An XML attribute type.
 * Localname: root
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.RootAttribute
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one root(@http://schemas.xmlsoap.org/soap/encoding/) attribute.
 *
 * This is a complex type.
 */
public class RootAttributeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.RootAttribute
{
    
    public RootAttributeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ROOT$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "root");
    
    
    /**
     * Gets the "root" attribute
     */
    public boolean getRoot()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ROOT$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_default_attribute_value(ROOT$0);
            }
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "root" attribute
     */
    public org.xmlsoap.schemas.soap.encoding.RootAttribute.Root xgetRoot()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.RootAttribute.Root target = null;
            target = (org.xmlsoap.schemas.soap.encoding.RootAttribute.Root)get_store().find_attribute_user(ROOT$0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.RootAttribute.Root)get_default_attribute_value(ROOT$0);
            }
            return target;
        }
    }
    
    /**
     * True if has "root" attribute
     */
    public boolean isSetRoot()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ROOT$0) != null;
        }
    }
    
    /**
     * Sets the "root" attribute
     */
    public void setRoot(boolean root)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ROOT$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ROOT$0);
            }
            target.setBooleanValue(root);
        }
    }
    
    /**
     * Sets (as xml) the "root" attribute
     */
    public void xsetRoot(org.xmlsoap.schemas.soap.encoding.RootAttribute.Root root)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.RootAttribute.Root target = null;
            target = (org.xmlsoap.schemas.soap.encoding.RootAttribute.Root)get_store().find_attribute_user(ROOT$0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.RootAttribute.Root)get_store().add_attribute_user(ROOT$0);
            }
            target.set(root);
        }
    }
    
    /**
     * Unsets the "root" attribute
     */
    public void unsetRoot()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ROOT$0);
        }
    }
    /**
     * An XML root(@http://schemas.xmlsoap.org/soap/encoding/).
     *
     * This is an atomic type that is a restriction of org.xmlsoap.schemas.soap.encoding.RootAttribute$Root.
     */
    public static class RootImpl extends org.apache.xmlbeans.impl.values.JavaBooleanHolderEx implements org.xmlsoap.schemas.soap.encoding.RootAttribute.Root
    {
        
        public RootImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected RootImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}

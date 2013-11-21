/*
 * An XML document type.
 * Localname: token
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.TokenDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one token(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class TokenDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.TokenDocument
{
    
    public TokenDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TOKEN$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "token");
    
    
    /**
     * Gets the "token" element
     */
    public org.xmlsoap.schemas.soap.encoding.Token getToken()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Token target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Token)get_store().find_element_user(TOKEN$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "token" element
     */
    public void setToken(org.xmlsoap.schemas.soap.encoding.Token token)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Token target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Token)get_store().find_element_user(TOKEN$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.Token)get_store().add_element_user(TOKEN$0);
            }
            target.set(token);
        }
    }
    
    /**
     * Appends and returns a new empty "token" element
     */
    public org.xmlsoap.schemas.soap.encoding.Token addNewToken()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Token target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Token)get_store().add_element_user(TOKEN$0);
            return target;
        }
    }
}

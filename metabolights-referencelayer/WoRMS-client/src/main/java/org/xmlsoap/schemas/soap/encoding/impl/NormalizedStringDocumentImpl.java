/*
 * An XML document type.
 * Localname: normalizedString
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.NormalizedStringDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one normalizedString(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class NormalizedStringDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.NormalizedStringDocument
{
    
    public NormalizedStringDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName NORMALIZEDSTRING$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "normalizedString");
    
    
    /**
     * Gets the "normalizedString" element
     */
    public org.xmlsoap.schemas.soap.encoding.NormalizedString getNormalizedString()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.NormalizedString target = null;
            target = (org.xmlsoap.schemas.soap.encoding.NormalizedString)get_store().find_element_user(NORMALIZEDSTRING$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "normalizedString" element
     */
    public void setNormalizedString(org.xmlsoap.schemas.soap.encoding.NormalizedString normalizedString)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.NormalizedString target = null;
            target = (org.xmlsoap.schemas.soap.encoding.NormalizedString)get_store().find_element_user(NORMALIZEDSTRING$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.NormalizedString)get_store().add_element_user(NORMALIZEDSTRING$0);
            }
            target.set(normalizedString);
        }
    }
    
    /**
     * Appends and returns a new empty "normalizedString" element
     */
    public org.xmlsoap.schemas.soap.encoding.NormalizedString addNewNormalizedString()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.NormalizedString target = null;
            target = (org.xmlsoap.schemas.soap.encoding.NormalizedString)get_store().add_element_user(NORMALIZEDSTRING$0);
            return target;
        }
    }
}

/*
 * An XML document type.
 * Localname: language
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.LanguageDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one language(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class LanguageDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.LanguageDocument
{
    
    public LanguageDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName LANGUAGE$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "language");
    
    
    /**
     * Gets the "language" element
     */
    public org.xmlsoap.schemas.soap.encoding.Language getLanguage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Language target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Language)get_store().find_element_user(LANGUAGE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "language" element
     */
    public void setLanguage(org.xmlsoap.schemas.soap.encoding.Language language)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Language target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Language)get_store().find_element_user(LANGUAGE$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.Language)get_store().add_element_user(LANGUAGE$0);
            }
            target.set(language);
        }
    }
    
    /**
     * Appends and returns a new empty "language" element
     */
    public org.xmlsoap.schemas.soap.encoding.Language addNewLanguage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.Language target = null;
            target = (org.xmlsoap.schemas.soap.encoding.Language)get_store().add_element_user(LANGUAGE$0);
            return target;
        }
    }
}

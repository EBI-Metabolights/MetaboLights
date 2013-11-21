/*
 * An XML document type.
 * Localname: hexBinary
 * Namespace: http://schemas.xmlsoap.org/soap/encoding/
 * Java type: org.xmlsoap.schemas.soap.encoding.HexBinaryDocument
 *
 * Automatically generated - do not modify.
 */
package org.xmlsoap.schemas.soap.encoding.impl;
/**
 * A document containing one hexBinary(@http://schemas.xmlsoap.org/soap/encoding/) element.
 *
 * This is a complex type.
 */
public class HexBinaryDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.xmlsoap.schemas.soap.encoding.HexBinaryDocument
{
    
    public HexBinaryDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName HEXBINARY$0 = 
        new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "hexBinary");
    
    
    /**
     * Gets the "hexBinary" element
     */
    public org.xmlsoap.schemas.soap.encoding.HexBinary getHexBinary()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.HexBinary target = null;
            target = (org.xmlsoap.schemas.soap.encoding.HexBinary)get_store().find_element_user(HEXBINARY$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "hexBinary" element
     */
    public void setHexBinary(org.xmlsoap.schemas.soap.encoding.HexBinary hexBinary)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.HexBinary target = null;
            target = (org.xmlsoap.schemas.soap.encoding.HexBinary)get_store().find_element_user(HEXBINARY$0, 0);
            if (target == null)
            {
                target = (org.xmlsoap.schemas.soap.encoding.HexBinary)get_store().add_element_user(HEXBINARY$0);
            }
            target.set(hexBinary);
        }
    }
    
    /**
     * Appends and returns a new empty "hexBinary" element
     */
    public org.xmlsoap.schemas.soap.encoding.HexBinary addNewHexBinary()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.xmlsoap.schemas.soap.encoding.HexBinary target = null;
            target = (org.xmlsoap.schemas.soap.encoding.HexBinary)get_store().add_element_user(HEXBINARY$0);
            return target;
        }
    }
}

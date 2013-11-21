/*
 * An XML document type.
 * Localname: getAphiaRecordByExtID
 * Namespace: http://aphia/v1.0/AphiaRecord
 * Java type: aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiarecord.impl;
/**
 * A document containing one getAphiaRecordByExtID(@http://aphia/v1.0/AphiaRecord) element.
 *
 * This is a complex type.
 */
public class GetAphiaRecordByExtIDDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDDocument
{
    
    public GetAphiaRecordByExtIDDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETAPHIARECORDBYEXTID$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/AphiaRecord", "getAphiaRecordByExtID");
    
    
    /**
     * Gets the "getAphiaRecordByExtID" element
     */
    public aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDDocument.GetAphiaRecordByExtID getGetAphiaRecordByExtID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDDocument.GetAphiaRecordByExtID target = null;
            target = (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDDocument.GetAphiaRecordByExtID)get_store().find_element_user(GETAPHIARECORDBYEXTID$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAphiaRecordByExtID" element
     */
    public void setGetAphiaRecordByExtID(aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDDocument.GetAphiaRecordByExtID getAphiaRecordByExtID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDDocument.GetAphiaRecordByExtID target = null;
            target = (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDDocument.GetAphiaRecordByExtID)get_store().find_element_user(GETAPHIARECORDBYEXTID$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDDocument.GetAphiaRecordByExtID)get_store().add_element_user(GETAPHIARECORDBYEXTID$0);
            }
            target.set(getAphiaRecordByExtID);
        }
    }
    
    /**
     * Appends and returns a new empty "getAphiaRecordByExtID" element
     */
    public aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDDocument.GetAphiaRecordByExtID addNewGetAphiaRecordByExtID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDDocument.GetAphiaRecordByExtID target = null;
            target = (aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDDocument.GetAphiaRecordByExtID)get_store().add_element_user(GETAPHIARECORDBYEXTID$0);
            return target;
        }
    }
    /**
     * An XML getAphiaRecordByExtID(@http://aphia/v1.0/AphiaRecord).
     *
     * This is a complex type.
     */
    public static class GetAphiaRecordByExtIDImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecord.GetAphiaRecordByExtIDDocument.GetAphiaRecordByExtID
    {
        
        public GetAphiaRecordByExtIDImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName ID$0 = 
            new javax.xml.namespace.QName("", "id");
        private static final javax.xml.namespace.QName TYPE$2 = 
            new javax.xml.namespace.QName("", "type");
        
        
        /**
         * Gets the "id" element
         */
        public java.lang.String getId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ID$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "id" element
         */
        public org.apache.xmlbeans.XmlString xgetId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ID$0, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "id" element
         */
        public boolean isNilId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ID$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * Sets the "id" element
         */
        public void setId(java.lang.String id)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ID$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ID$0);
                }
                target.setStringValue(id);
            }
        }
        
        /**
         * Sets (as xml) the "id" element
         */
        public void xsetId(org.apache.xmlbeans.XmlString id)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ID$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ID$0);
                }
                target.set(id);
            }
        }
        
        /**
         * Nils the "id" element
         */
        public void setNilId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ID$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ID$0);
                }
                target.setNil();
            }
        }
        
        /**
         * Gets the "type" element
         */
        public java.lang.String getType()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TYPE$2, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "type" element
         */
        public org.apache.xmlbeans.XmlString xgetType()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TYPE$2, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "type" element
         */
        public boolean isNilType()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TYPE$2, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * Sets the "type" element
         */
        public void setType(java.lang.String type)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TYPE$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TYPE$2);
                }
                target.setStringValue(type);
            }
        }
        
        /**
         * Sets (as xml) the "type" element
         */
        public void xsetType(org.apache.xmlbeans.XmlString type)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TYPE$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TYPE$2);
                }
                target.set(type);
            }
        }
        
        /**
         * Nils the "type" element
         */
        public void setNilType()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TYPE$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TYPE$2);
                }
                target.setNil();
            }
        }
    }
}

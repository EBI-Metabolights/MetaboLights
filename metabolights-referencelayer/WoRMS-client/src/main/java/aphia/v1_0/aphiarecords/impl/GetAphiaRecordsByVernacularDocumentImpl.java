/*
 * An XML document type.
 * Localname: getAphiaRecordsByVernacular
 * Namespace: http://aphia/v1.0/AphiaRecords
 * Java type: aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiarecords.impl;
/**
 * A document containing one getAphiaRecordsByVernacular(@http://aphia/v1.0/AphiaRecords) element.
 *
 * This is a complex type.
 */
public class GetAphiaRecordsByVernacularDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularDocument
{
    
    public GetAphiaRecordsByVernacularDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETAPHIARECORDSBYVERNACULAR$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/AphiaRecords", "getAphiaRecordsByVernacular");
    
    
    /**
     * Gets the "getAphiaRecordsByVernacular" element
     */
    public aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularDocument.GetAphiaRecordsByVernacular getGetAphiaRecordsByVernacular()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularDocument.GetAphiaRecordsByVernacular target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularDocument.GetAphiaRecordsByVernacular)get_store().find_element_user(GETAPHIARECORDSBYVERNACULAR$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAphiaRecordsByVernacular" element
     */
    public void setGetAphiaRecordsByVernacular(aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularDocument.GetAphiaRecordsByVernacular getAphiaRecordsByVernacular)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularDocument.GetAphiaRecordsByVernacular target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularDocument.GetAphiaRecordsByVernacular)get_store().find_element_user(GETAPHIARECORDSBYVERNACULAR$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularDocument.GetAphiaRecordsByVernacular)get_store().add_element_user(GETAPHIARECORDSBYVERNACULAR$0);
            }
            target.set(getAphiaRecordsByVernacular);
        }
    }
    
    /**
     * Appends and returns a new empty "getAphiaRecordsByVernacular" element
     */
    public aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularDocument.GetAphiaRecordsByVernacular addNewGetAphiaRecordsByVernacular()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularDocument.GetAphiaRecordsByVernacular target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularDocument.GetAphiaRecordsByVernacular)get_store().add_element_user(GETAPHIARECORDSBYVERNACULAR$0);
            return target;
        }
    }
    /**
     * An XML getAphiaRecordsByVernacular(@http://aphia/v1.0/AphiaRecords).
     *
     * This is a complex type.
     */
    public static class GetAphiaRecordsByVernacularImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecords.GetAphiaRecordsByVernacularDocument.GetAphiaRecordsByVernacular
    {
        
        public GetAphiaRecordsByVernacularImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName VERNACULAR$0 = 
            new javax.xml.namespace.QName("", "vernacular");
        private static final javax.xml.namespace.QName LIKE$2 = 
            new javax.xml.namespace.QName("", "like");
        private static final javax.xml.namespace.QName OFFSET$4 = 
            new javax.xml.namespace.QName("", "offset");
        
        
        /**
         * Gets the "vernacular" element
         */
        public java.lang.String getVernacular()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VERNACULAR$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "vernacular" element
         */
        public org.apache.xmlbeans.XmlString xgetVernacular()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(VERNACULAR$0, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "vernacular" element
         */
        public boolean isNilVernacular()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(VERNACULAR$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * Sets the "vernacular" element
         */
        public void setVernacular(java.lang.String vernacular)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VERNACULAR$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(VERNACULAR$0);
                }
                target.setStringValue(vernacular);
            }
        }
        
        /**
         * Sets (as xml) the "vernacular" element
         */
        public void xsetVernacular(org.apache.xmlbeans.XmlString vernacular)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(VERNACULAR$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(VERNACULAR$0);
                }
                target.set(vernacular);
            }
        }
        
        /**
         * Nils the "vernacular" element
         */
        public void setNilVernacular()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(VERNACULAR$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(VERNACULAR$0);
                }
                target.setNil();
            }
        }
        
        /**
         * Gets the "like" element
         */
        public boolean getLike()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LIKE$2, 0);
                if (target == null)
                {
                    return false;
                }
                return target.getBooleanValue();
            }
        }
        
        /**
         * Gets (as xml) the "like" element
         */
        public org.apache.xmlbeans.XmlBoolean xgetLike()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(LIKE$2, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "like" element
         */
        public boolean isNilLike()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(LIKE$2, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * Sets the "like" element
         */
        public void setLike(boolean like)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LIKE$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LIKE$2);
                }
                target.setBooleanValue(like);
            }
        }
        
        /**
         * Sets (as xml) the "like" element
         */
        public void xsetLike(org.apache.xmlbeans.XmlBoolean like)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(LIKE$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(LIKE$2);
                }
                target.set(like);
            }
        }
        
        /**
         * Nils the "like" element
         */
        public void setNilLike()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(LIKE$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(LIKE$2);
                }
                target.setNil();
            }
        }
        
        /**
         * Gets the "offset" element
         */
        public int getOffset()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OFFSET$4, 0);
                if (target == null)
                {
                    return 0;
                }
                return target.getIntValue();
            }
        }
        
        /**
         * Gets (as xml) the "offset" element
         */
        public org.apache.xmlbeans.XmlInt xgetOffset()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlInt target = null;
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OFFSET$4, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "offset" element
         */
        public boolean isNilOffset()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlInt target = null;
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OFFSET$4, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * Sets the "offset" element
         */
        public void setOffset(int offset)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OFFSET$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OFFSET$4);
                }
                target.setIntValue(offset);
            }
        }
        
        /**
         * Sets (as xml) the "offset" element
         */
        public void xsetOffset(org.apache.xmlbeans.XmlInt offset)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlInt target = null;
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OFFSET$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(OFFSET$4);
                }
                target.set(offset);
            }
        }
        
        /**
         * Nils the "offset" element
         */
        public void setNilOffset()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlInt target = null;
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OFFSET$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(OFFSET$4);
                }
                target.setNil();
            }
        }
    }
}

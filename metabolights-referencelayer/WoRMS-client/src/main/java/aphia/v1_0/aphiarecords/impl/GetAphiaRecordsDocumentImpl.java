/*
 * An XML document type.
 * Localname: getAphiaRecords
 * Namespace: http://aphia/v1.0/AphiaRecords
 * Java type: aphia.v1_0.aphiarecords.GetAphiaRecordsDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiarecords.impl;
/**
 * A document containing one getAphiaRecords(@http://aphia/v1.0/AphiaRecords) element.
 *
 * This is a complex type.
 */
public class GetAphiaRecordsDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecords.GetAphiaRecordsDocument
{
    
    public GetAphiaRecordsDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETAPHIARECORDS$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/AphiaRecords", "getAphiaRecords");
    
    
    /**
     * Gets the "getAphiaRecords" element
     */
    public aphia.v1_0.aphiarecords.GetAphiaRecordsDocument.GetAphiaRecords getGetAphiaRecords()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaRecordsDocument.GetAphiaRecords target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaRecordsDocument.GetAphiaRecords)get_store().find_element_user(GETAPHIARECORDS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAphiaRecords" element
     */
    public void setGetAphiaRecords(aphia.v1_0.aphiarecords.GetAphiaRecordsDocument.GetAphiaRecords getAphiaRecords)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaRecordsDocument.GetAphiaRecords target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaRecordsDocument.GetAphiaRecords)get_store().find_element_user(GETAPHIARECORDS$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.aphiarecords.GetAphiaRecordsDocument.GetAphiaRecords)get_store().add_element_user(GETAPHIARECORDS$0);
            }
            target.set(getAphiaRecords);
        }
    }
    
    /**
     * Appends and returns a new empty "getAphiaRecords" element
     */
    public aphia.v1_0.aphiarecords.GetAphiaRecordsDocument.GetAphiaRecords addNewGetAphiaRecords()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaRecordsDocument.GetAphiaRecords target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaRecordsDocument.GetAphiaRecords)get_store().add_element_user(GETAPHIARECORDS$0);
            return target;
        }
    }
    /**
     * An XML getAphiaRecords(@http://aphia/v1.0/AphiaRecords).
     *
     * This is a complex type.
     */
    public static class GetAphiaRecordsImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecords.GetAphiaRecordsDocument.GetAphiaRecords
    {
        
        public GetAphiaRecordsImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName SCIENTIFICNAME$0 = 
            new javax.xml.namespace.QName("", "scientificname");
        private static final javax.xml.namespace.QName LIKE$2 = 
            new javax.xml.namespace.QName("", "like");
        private static final javax.xml.namespace.QName FUZZY$4 = 
            new javax.xml.namespace.QName("", "fuzzy");
        private static final javax.xml.namespace.QName MARINEONLY$6 = 
            new javax.xml.namespace.QName("", "marine_only");
        private static final javax.xml.namespace.QName OFFSET$8 = 
            new javax.xml.namespace.QName("", "offset");
        
        
        /**
         * Gets the "scientificname" element
         */
        public java.lang.String getScientificname()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SCIENTIFICNAME$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "scientificname" element
         */
        public org.apache.xmlbeans.XmlString xgetScientificname()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SCIENTIFICNAME$0, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "scientificname" element
         */
        public boolean isNilScientificname()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SCIENTIFICNAME$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * Sets the "scientificname" element
         */
        public void setScientificname(java.lang.String scientificname)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SCIENTIFICNAME$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SCIENTIFICNAME$0);
                }
                target.setStringValue(scientificname);
            }
        }
        
        /**
         * Sets (as xml) the "scientificname" element
         */
        public void xsetScientificname(org.apache.xmlbeans.XmlString scientificname)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SCIENTIFICNAME$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SCIENTIFICNAME$0);
                }
                target.set(scientificname);
            }
        }
        
        /**
         * Nils the "scientificname" element
         */
        public void setNilScientificname()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SCIENTIFICNAME$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SCIENTIFICNAME$0);
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
         * Gets the "fuzzy" element
         */
        public boolean getFuzzy()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FUZZY$4, 0);
                if (target == null)
                {
                    return false;
                }
                return target.getBooleanValue();
            }
        }
        
        /**
         * Gets (as xml) the "fuzzy" element
         */
        public org.apache.xmlbeans.XmlBoolean xgetFuzzy()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(FUZZY$4, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "fuzzy" element
         */
        public boolean isNilFuzzy()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(FUZZY$4, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * Sets the "fuzzy" element
         */
        public void setFuzzy(boolean fuzzy)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FUZZY$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FUZZY$4);
                }
                target.setBooleanValue(fuzzy);
            }
        }
        
        /**
         * Sets (as xml) the "fuzzy" element
         */
        public void xsetFuzzy(org.apache.xmlbeans.XmlBoolean fuzzy)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(FUZZY$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(FUZZY$4);
                }
                target.set(fuzzy);
            }
        }
        
        /**
         * Nils the "fuzzy" element
         */
        public void setNilFuzzy()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(FUZZY$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(FUZZY$4);
                }
                target.setNil();
            }
        }
        
        /**
         * Gets the "marine_only" element
         */
        public boolean getMarineOnly()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MARINEONLY$6, 0);
                if (target == null)
                {
                    return false;
                }
                return target.getBooleanValue();
            }
        }
        
        /**
         * Gets (as xml) the "marine_only" element
         */
        public org.apache.xmlbeans.XmlBoolean xgetMarineOnly()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(MARINEONLY$6, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "marine_only" element
         */
        public boolean isNilMarineOnly()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(MARINEONLY$6, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * Sets the "marine_only" element
         */
        public void setMarineOnly(boolean marineOnly)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MARINEONLY$6, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MARINEONLY$6);
                }
                target.setBooleanValue(marineOnly);
            }
        }
        
        /**
         * Sets (as xml) the "marine_only" element
         */
        public void xsetMarineOnly(org.apache.xmlbeans.XmlBoolean marineOnly)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(MARINEONLY$6, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(MARINEONLY$6);
                }
                target.set(marineOnly);
            }
        }
        
        /**
         * Nils the "marine_only" element
         */
        public void setNilMarineOnly()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(MARINEONLY$6, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(MARINEONLY$6);
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
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OFFSET$8, 0);
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
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OFFSET$8, 0);
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
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OFFSET$8, 0);
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
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OFFSET$8, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OFFSET$8);
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
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OFFSET$8, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(OFFSET$8);
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
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OFFSET$8, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(OFFSET$8);
                }
                target.setNil();
            }
        }
    }
}

/*
 * An XML document type.
 * Localname: getAphiaChildrenByID
 * Namespace: http://aphia/v1.0/AphiaRecords
 * Java type: aphia.v1_0.aphiarecords.GetAphiaChildrenByIDDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiarecords.impl;
/**
 * A document containing one getAphiaChildrenByID(@http://aphia/v1.0/AphiaRecords) element.
 *
 * This is a complex type.
 */
public class GetAphiaChildrenByIDDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecords.GetAphiaChildrenByIDDocument
{
    
    public GetAphiaChildrenByIDDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETAPHIACHILDRENBYID$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/AphiaRecords", "getAphiaChildrenByID");
    
    
    /**
     * Gets the "getAphiaChildrenByID" element
     */
    public aphia.v1_0.aphiarecords.GetAphiaChildrenByIDDocument.GetAphiaChildrenByID getGetAphiaChildrenByID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaChildrenByIDDocument.GetAphiaChildrenByID target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaChildrenByIDDocument.GetAphiaChildrenByID)get_store().find_element_user(GETAPHIACHILDRENBYID$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAphiaChildrenByID" element
     */
    public void setGetAphiaChildrenByID(aphia.v1_0.aphiarecords.GetAphiaChildrenByIDDocument.GetAphiaChildrenByID getAphiaChildrenByID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaChildrenByIDDocument.GetAphiaChildrenByID target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaChildrenByIDDocument.GetAphiaChildrenByID)get_store().find_element_user(GETAPHIACHILDRENBYID$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.aphiarecords.GetAphiaChildrenByIDDocument.GetAphiaChildrenByID)get_store().add_element_user(GETAPHIACHILDRENBYID$0);
            }
            target.set(getAphiaChildrenByID);
        }
    }
    
    /**
     * Appends and returns a new empty "getAphiaChildrenByID" element
     */
    public aphia.v1_0.aphiarecords.GetAphiaChildrenByIDDocument.GetAphiaChildrenByID addNewGetAphiaChildrenByID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaChildrenByIDDocument.GetAphiaChildrenByID target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaChildrenByIDDocument.GetAphiaChildrenByID)get_store().add_element_user(GETAPHIACHILDRENBYID$0);
            return target;
        }
    }
    /**
     * An XML getAphiaChildrenByID(@http://aphia/v1.0/AphiaRecords).
     *
     * This is a complex type.
     */
    public static class GetAphiaChildrenByIDImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecords.GetAphiaChildrenByIDDocument.GetAphiaChildrenByID
    {
        
        public GetAphiaChildrenByIDImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName APHIAID$0 = 
            new javax.xml.namespace.QName("", "AphiaID");
        private static final javax.xml.namespace.QName OFFSET$2 = 
            new javax.xml.namespace.QName("", "offset");
        private static final javax.xml.namespace.QName MARINEONLY$4 = 
            new javax.xml.namespace.QName("", "marine_only");
        
        
        /**
         * Gets the "AphiaID" element
         */
        public int getAphiaID()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(APHIAID$0, 0);
                if (target == null)
                {
                    return 0;
                }
                return target.getIntValue();
            }
        }
        
        /**
         * Gets (as xml) the "AphiaID" element
         */
        public org.apache.xmlbeans.XmlInt xgetAphiaID()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlInt target = null;
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(APHIAID$0, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "AphiaID" element
         */
        public boolean isNilAphiaID()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlInt target = null;
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(APHIAID$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * Sets the "AphiaID" element
         */
        public void setAphiaID(int aphiaID)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(APHIAID$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(APHIAID$0);
                }
                target.setIntValue(aphiaID);
            }
        }
        
        /**
         * Sets (as xml) the "AphiaID" element
         */
        public void xsetAphiaID(org.apache.xmlbeans.XmlInt aphiaID)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlInt target = null;
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(APHIAID$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(APHIAID$0);
                }
                target.set(aphiaID);
            }
        }
        
        /**
         * Nils the "AphiaID" element
         */
        public void setNilAphiaID()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlInt target = null;
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(APHIAID$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(APHIAID$0);
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
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OFFSET$2, 0);
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
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OFFSET$2, 0);
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
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OFFSET$2, 0);
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
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OFFSET$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OFFSET$2);
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
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OFFSET$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(OFFSET$2);
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
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OFFSET$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(OFFSET$2);
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
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MARINEONLY$4, 0);
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
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(MARINEONLY$4, 0);
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
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(MARINEONLY$4, 0);
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
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MARINEONLY$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MARINEONLY$4);
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
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(MARINEONLY$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(MARINEONLY$4);
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
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(MARINEONLY$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(MARINEONLY$4);
                }
                target.setNil();
            }
        }
    }
}

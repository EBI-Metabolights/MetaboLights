/*
 * An XML document type.
 * Localname: getAphiaRecordsByDate
 * Namespace: http://aphia/v1.0/AphiaRecords
 * Java type: aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiarecords.impl;
/**
 * A document containing one getAphiaRecordsByDate(@http://aphia/v1.0/AphiaRecords) element.
 *
 * This is a complex type.
 */
public class GetAphiaRecordsByDateDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument
{
    
    public GetAphiaRecordsByDateDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETAPHIARECORDSBYDATE$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/AphiaRecords", "getAphiaRecordsByDate");
    
    
    /**
     * Gets the "getAphiaRecordsByDate" element
     */
    public aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument.GetAphiaRecordsByDate getGetAphiaRecordsByDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument.GetAphiaRecordsByDate target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument.GetAphiaRecordsByDate)get_store().find_element_user(GETAPHIARECORDSBYDATE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAphiaRecordsByDate" element
     */
    public void setGetAphiaRecordsByDate(aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument.GetAphiaRecordsByDate getAphiaRecordsByDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument.GetAphiaRecordsByDate target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument.GetAphiaRecordsByDate)get_store().find_element_user(GETAPHIARECORDSBYDATE$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument.GetAphiaRecordsByDate)get_store().add_element_user(GETAPHIARECORDSBYDATE$0);
            }
            target.set(getAphiaRecordsByDate);
        }
    }
    
    /**
     * Appends and returns a new empty "getAphiaRecordsByDate" element
     */
    public aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument.GetAphiaRecordsByDate addNewGetAphiaRecordsByDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument.GetAphiaRecordsByDate target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument.GetAphiaRecordsByDate)get_store().add_element_user(GETAPHIARECORDSBYDATE$0);
            return target;
        }
    }
    /**
     * An XML getAphiaRecordsByDate(@http://aphia/v1.0/AphiaRecords).
     *
     * This is a complex type.
     */
    public static class GetAphiaRecordsByDateImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecords.GetAphiaRecordsByDateDocument.GetAphiaRecordsByDate
    {
        
        public GetAphiaRecordsByDateImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName STARTDATE$0 = 
            new javax.xml.namespace.QName("", "startdate");
        private static final javax.xml.namespace.QName ENDDATE$2 = 
            new javax.xml.namespace.QName("", "enddate");
        private static final javax.xml.namespace.QName MARINEONLY$4 = 
            new javax.xml.namespace.QName("", "marine_only");
        private static final javax.xml.namespace.QName OFFSET$6 = 
            new javax.xml.namespace.QName("", "offset");
        
        
        /**
         * Gets the "startdate" element
         */
        public java.lang.String getStartdate()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STARTDATE$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "startdate" element
         */
        public org.apache.xmlbeans.XmlString xgetStartdate()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STARTDATE$0, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "startdate" element
         */
        public boolean isNilStartdate()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STARTDATE$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * Sets the "startdate" element
         */
        public void setStartdate(java.lang.String startdate)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STARTDATE$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STARTDATE$0);
                }
                target.setStringValue(startdate);
            }
        }
        
        /**
         * Sets (as xml) the "startdate" element
         */
        public void xsetStartdate(org.apache.xmlbeans.XmlString startdate)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STARTDATE$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STARTDATE$0);
                }
                target.set(startdate);
            }
        }
        
        /**
         * Nils the "startdate" element
         */
        public void setNilStartdate()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STARTDATE$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STARTDATE$0);
                }
                target.setNil();
            }
        }
        
        /**
         * Gets the "enddate" element
         */
        public java.lang.String getEnddate()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ENDDATE$2, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "enddate" element
         */
        public org.apache.xmlbeans.XmlString xgetEnddate()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ENDDATE$2, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "enddate" element
         */
        public boolean isNilEnddate()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ENDDATE$2, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * Sets the "enddate" element
         */
        public void setEnddate(java.lang.String enddate)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ENDDATE$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ENDDATE$2);
                }
                target.setStringValue(enddate);
            }
        }
        
        /**
         * Sets (as xml) the "enddate" element
         */
        public void xsetEnddate(org.apache.xmlbeans.XmlString enddate)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ENDDATE$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ENDDATE$2);
                }
                target.set(enddate);
            }
        }
        
        /**
         * Nils the "enddate" element
         */
        public void setNilEnddate()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ENDDATE$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ENDDATE$2);
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
        
        /**
         * Gets the "offset" element
         */
        public int getOffset()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OFFSET$6, 0);
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
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OFFSET$6, 0);
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
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OFFSET$6, 0);
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
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OFFSET$6, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OFFSET$6);
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
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OFFSET$6, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(OFFSET$6);
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
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(OFFSET$6, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(OFFSET$6);
                }
                target.setNil();
            }
        }
    }
}

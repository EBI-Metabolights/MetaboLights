/*
 * An XML document type.
 * Localname: getAphiaRecordByID
 * Namespace: http://aphia/v1.0/AphiaRecord
 * Java type: aphia.v1_0.aphiarecord.GetAphiaRecordByIDDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiarecord.impl;
/**
 * A document containing one getAphiaRecordByID(@http://aphia/v1.0/AphiaRecord) element.
 *
 * This is a complex type.
 */
public class GetAphiaRecordByIDDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecord.GetAphiaRecordByIDDocument
{
    
    public GetAphiaRecordByIDDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETAPHIARECORDBYID$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/AphiaRecord", "getAphiaRecordByID");
    
    
    /**
     * Gets the "getAphiaRecordByID" element
     */
    public aphia.v1_0.aphiarecord.GetAphiaRecordByIDDocument.GetAphiaRecordByID getGetAphiaRecordByID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecord.GetAphiaRecordByIDDocument.GetAphiaRecordByID target = null;
            target = (aphia.v1_0.aphiarecord.GetAphiaRecordByIDDocument.GetAphiaRecordByID)get_store().find_element_user(GETAPHIARECORDBYID$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAphiaRecordByID" element
     */
    public void setGetAphiaRecordByID(aphia.v1_0.aphiarecord.GetAphiaRecordByIDDocument.GetAphiaRecordByID getAphiaRecordByID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecord.GetAphiaRecordByIDDocument.GetAphiaRecordByID target = null;
            target = (aphia.v1_0.aphiarecord.GetAphiaRecordByIDDocument.GetAphiaRecordByID)get_store().find_element_user(GETAPHIARECORDBYID$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.aphiarecord.GetAphiaRecordByIDDocument.GetAphiaRecordByID)get_store().add_element_user(GETAPHIARECORDBYID$0);
            }
            target.set(getAphiaRecordByID);
        }
    }
    
    /**
     * Appends and returns a new empty "getAphiaRecordByID" element
     */
    public aphia.v1_0.aphiarecord.GetAphiaRecordByIDDocument.GetAphiaRecordByID addNewGetAphiaRecordByID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecord.GetAphiaRecordByIDDocument.GetAphiaRecordByID target = null;
            target = (aphia.v1_0.aphiarecord.GetAphiaRecordByIDDocument.GetAphiaRecordByID)get_store().add_element_user(GETAPHIARECORDBYID$0);
            return target;
        }
    }
    /**
     * An XML getAphiaRecordByID(@http://aphia/v1.0/AphiaRecord).
     *
     * This is a complex type.
     */
    public static class GetAphiaRecordByIDImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecord.GetAphiaRecordByIDDocument.GetAphiaRecordByID
    {
        
        public GetAphiaRecordByIDImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName APHIAID$0 = 
            new javax.xml.namespace.QName("", "AphiaID");
        
        
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
    }
}

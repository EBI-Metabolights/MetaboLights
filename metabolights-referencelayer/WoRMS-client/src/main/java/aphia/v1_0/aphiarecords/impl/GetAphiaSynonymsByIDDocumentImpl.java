/*
 * An XML document type.
 * Localname: getAphiaSynonymsByID
 * Namespace: http://aphia/v1.0/AphiaRecords
 * Java type: aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiarecords.impl;
/**
 * A document containing one getAphiaSynonymsByID(@http://aphia/v1.0/AphiaRecords) element.
 *
 * This is a complex type.
 */
public class GetAphiaSynonymsByIDDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDDocument
{
    
    public GetAphiaSynonymsByIDDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETAPHIASYNONYMSBYID$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/AphiaRecords", "getAphiaSynonymsByID");
    
    
    /**
     * Gets the "getAphiaSynonymsByID" element
     */
    public aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDDocument.GetAphiaSynonymsByID getGetAphiaSynonymsByID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDDocument.GetAphiaSynonymsByID target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDDocument.GetAphiaSynonymsByID)get_store().find_element_user(GETAPHIASYNONYMSBYID$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAphiaSynonymsByID" element
     */
    public void setGetAphiaSynonymsByID(aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDDocument.GetAphiaSynonymsByID getAphiaSynonymsByID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDDocument.GetAphiaSynonymsByID target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDDocument.GetAphiaSynonymsByID)get_store().find_element_user(GETAPHIASYNONYMSBYID$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDDocument.GetAphiaSynonymsByID)get_store().add_element_user(GETAPHIASYNONYMSBYID$0);
            }
            target.set(getAphiaSynonymsByID);
        }
    }
    
    /**
     * Appends and returns a new empty "getAphiaSynonymsByID" element
     */
    public aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDDocument.GetAphiaSynonymsByID addNewGetAphiaSynonymsByID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDDocument.GetAphiaSynonymsByID target = null;
            target = (aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDDocument.GetAphiaSynonymsByID)get_store().add_element_user(GETAPHIASYNONYMSBYID$0);
            return target;
        }
    }
    /**
     * An XML getAphiaSynonymsByID(@http://aphia/v1.0/AphiaRecords).
     *
     * This is a complex type.
     */
    public static class GetAphiaSynonymsByIDImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiarecords.GetAphiaSynonymsByIDDocument.GetAphiaSynonymsByID
    {
        
        public GetAphiaSynonymsByIDImpl(org.apache.xmlbeans.SchemaType sType)
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

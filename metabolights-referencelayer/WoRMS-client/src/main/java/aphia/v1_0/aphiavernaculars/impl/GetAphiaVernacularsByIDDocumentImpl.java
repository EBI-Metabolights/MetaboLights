/*
 * An XML document type.
 * Localname: getAphiaVernacularsByID
 * Namespace: http://aphia/v1.0/AphiaVernaculars
 * Java type: aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiavernaculars.impl;
/**
 * A document containing one getAphiaVernacularsByID(@http://aphia/v1.0/AphiaVernaculars) element.
 *
 * This is a complex type.
 */
public class GetAphiaVernacularsByIDDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument
{
    
    public GetAphiaVernacularsByIDDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETAPHIAVERNACULARSBYID$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/AphiaVernaculars", "getAphiaVernacularsByID");
    
    
    /**
     * Gets the "getAphiaVernacularsByID" element
     */
    public aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument.GetAphiaVernacularsByID getGetAphiaVernacularsByID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument.GetAphiaVernacularsByID target = null;
            target = (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument.GetAphiaVernacularsByID)get_store().find_element_user(GETAPHIAVERNACULARSBYID$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAphiaVernacularsByID" element
     */
    public void setGetAphiaVernacularsByID(aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument.GetAphiaVernacularsByID getAphiaVernacularsByID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument.GetAphiaVernacularsByID target = null;
            target = (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument.GetAphiaVernacularsByID)get_store().find_element_user(GETAPHIAVERNACULARSBYID$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument.GetAphiaVernacularsByID)get_store().add_element_user(GETAPHIAVERNACULARSBYID$0);
            }
            target.set(getAphiaVernacularsByID);
        }
    }
    
    /**
     * Appends and returns a new empty "getAphiaVernacularsByID" element
     */
    public aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument.GetAphiaVernacularsByID addNewGetAphiaVernacularsByID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument.GetAphiaVernacularsByID target = null;
            target = (aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument.GetAphiaVernacularsByID)get_store().add_element_user(GETAPHIAVERNACULARSBYID$0);
            return target;
        }
    }
    /**
     * An XML getAphiaVernacularsByID(@http://aphia/v1.0/AphiaVernaculars).
     *
     * This is a complex type.
     */
    public static class GetAphiaVernacularsByIDImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiavernaculars.GetAphiaVernacularsByIDDocument.GetAphiaVernacularsByID
    {
        
        public GetAphiaVernacularsByIDImpl(org.apache.xmlbeans.SchemaType sType)
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

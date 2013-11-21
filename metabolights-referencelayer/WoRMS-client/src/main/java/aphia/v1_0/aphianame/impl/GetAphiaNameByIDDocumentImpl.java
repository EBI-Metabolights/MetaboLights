/*
 * An XML document type.
 * Localname: getAphiaNameByID
 * Namespace: http://aphia/v1.0/AphiaName
 * Java type: aphia.v1_0.aphianame.GetAphiaNameByIDDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphianame.impl;
/**
 * A document containing one getAphiaNameByID(@http://aphia/v1.0/AphiaName) element.
 *
 * This is a complex type.
 */
public class GetAphiaNameByIDDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphianame.GetAphiaNameByIDDocument
{
    
    public GetAphiaNameByIDDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETAPHIANAMEBYID$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/AphiaName", "getAphiaNameByID");
    
    
    /**
     * Gets the "getAphiaNameByID" element
     */
    public aphia.v1_0.aphianame.GetAphiaNameByIDDocument.GetAphiaNameByID getGetAphiaNameByID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphianame.GetAphiaNameByIDDocument.GetAphiaNameByID target = null;
            target = (aphia.v1_0.aphianame.GetAphiaNameByIDDocument.GetAphiaNameByID)get_store().find_element_user(GETAPHIANAMEBYID$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAphiaNameByID" element
     */
    public void setGetAphiaNameByID(aphia.v1_0.aphianame.GetAphiaNameByIDDocument.GetAphiaNameByID getAphiaNameByID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphianame.GetAphiaNameByIDDocument.GetAphiaNameByID target = null;
            target = (aphia.v1_0.aphianame.GetAphiaNameByIDDocument.GetAphiaNameByID)get_store().find_element_user(GETAPHIANAMEBYID$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.aphianame.GetAphiaNameByIDDocument.GetAphiaNameByID)get_store().add_element_user(GETAPHIANAMEBYID$0);
            }
            target.set(getAphiaNameByID);
        }
    }
    
    /**
     * Appends and returns a new empty "getAphiaNameByID" element
     */
    public aphia.v1_0.aphianame.GetAphiaNameByIDDocument.GetAphiaNameByID addNewGetAphiaNameByID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphianame.GetAphiaNameByIDDocument.GetAphiaNameByID target = null;
            target = (aphia.v1_0.aphianame.GetAphiaNameByIDDocument.GetAphiaNameByID)get_store().add_element_user(GETAPHIANAMEBYID$0);
            return target;
        }
    }
    /**
     * An XML getAphiaNameByID(@http://aphia/v1.0/AphiaName).
     *
     * This is a complex type.
     */
    public static class GetAphiaNameByIDImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphianame.GetAphiaNameByIDDocument.GetAphiaNameByID
    {
        
        public GetAphiaNameByIDImpl(org.apache.xmlbeans.SchemaType sType)
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

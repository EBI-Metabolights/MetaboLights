/*
 * An XML document type.
 * Localname: getAphiaID
 * Namespace: http://aphia/v1.0/AphiaID
 * Java type: aphia.v1_0.aphiaid.GetAphiaIDDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.aphiaid.impl;
/**
 * A document containing one getAphiaID(@http://aphia/v1.0/AphiaID) element.
 *
 * This is a complex type.
 */
public class GetAphiaIDDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiaid.GetAphiaIDDocument
{
    
    public GetAphiaIDDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETAPHIAID$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/AphiaID", "getAphiaID");
    
    
    /**
     * Gets the "getAphiaID" element
     */
    public aphia.v1_0.aphiaid.GetAphiaIDDocument.GetAphiaID getGetAphiaID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiaid.GetAphiaIDDocument.GetAphiaID target = null;
            target = (aphia.v1_0.aphiaid.GetAphiaIDDocument.GetAphiaID)get_store().find_element_user(GETAPHIAID$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAphiaID" element
     */
    public void setGetAphiaID(aphia.v1_0.aphiaid.GetAphiaIDDocument.GetAphiaID getAphiaID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiaid.GetAphiaIDDocument.GetAphiaID target = null;
            target = (aphia.v1_0.aphiaid.GetAphiaIDDocument.GetAphiaID)get_store().find_element_user(GETAPHIAID$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.aphiaid.GetAphiaIDDocument.GetAphiaID)get_store().add_element_user(GETAPHIAID$0);
            }
            target.set(getAphiaID);
        }
    }
    
    /**
     * Appends and returns a new empty "getAphiaID" element
     */
    public aphia.v1_0.aphiaid.GetAphiaIDDocument.GetAphiaID addNewGetAphiaID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.aphiaid.GetAphiaIDDocument.GetAphiaID target = null;
            target = (aphia.v1_0.aphiaid.GetAphiaIDDocument.GetAphiaID)get_store().add_element_user(GETAPHIAID$0);
            return target;
        }
    }
    /**
     * An XML getAphiaID(@http://aphia/v1.0/AphiaID).
     *
     * This is a complex type.
     */
    public static class GetAphiaIDImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.aphiaid.GetAphiaIDDocument.GetAphiaID
    {
        
        public GetAphiaIDImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName SCIENTIFICNAME$0 = 
            new javax.xml.namespace.QName("", "scientificname");
        private static final javax.xml.namespace.QName MARINEONLY$2 = 
            new javax.xml.namespace.QName("", "marine_only");
        
        
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
         * Gets the "marine_only" element
         */
        public boolean getMarineOnly()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MARINEONLY$2, 0);
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
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(MARINEONLY$2, 0);
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
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(MARINEONLY$2, 0);
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
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MARINEONLY$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MARINEONLY$2);
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
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(MARINEONLY$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(MARINEONLY$2);
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
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(MARINEONLY$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(MARINEONLY$2);
                }
                target.setNil();
            }
        }
    }
}

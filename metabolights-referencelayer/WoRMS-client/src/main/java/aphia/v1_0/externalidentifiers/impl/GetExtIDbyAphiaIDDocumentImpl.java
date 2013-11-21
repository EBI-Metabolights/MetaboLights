/*
 * An XML document type.
 * Localname: getExtIDbyAphiaID
 * Namespace: http://aphia/v1.0/externalidentifiers
 * Java type: aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDDocument
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.externalidentifiers.impl;
/**
 * A document containing one getExtIDbyAphiaID(@http://aphia/v1.0/externalidentifiers) element.
 *
 * This is a complex type.
 */
public class GetExtIDbyAphiaIDDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDDocument
{
    
    public GetExtIDbyAphiaIDDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETEXTIDBYAPHIAID$0 = 
        new javax.xml.namespace.QName("http://aphia/v1.0/externalidentifiers", "getExtIDbyAphiaID");
    
    
    /**
     * Gets the "getExtIDbyAphiaID" element
     */
    public aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDDocument.GetExtIDbyAphiaID getGetExtIDbyAphiaID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDDocument.GetExtIDbyAphiaID target = null;
            target = (aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDDocument.GetExtIDbyAphiaID)get_store().find_element_user(GETEXTIDBYAPHIAID$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getExtIDbyAphiaID" element
     */
    public void setGetExtIDbyAphiaID(aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDDocument.GetExtIDbyAphiaID getExtIDbyAphiaID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDDocument.GetExtIDbyAphiaID target = null;
            target = (aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDDocument.GetExtIDbyAphiaID)get_store().find_element_user(GETEXTIDBYAPHIAID$0, 0);
            if (target == null)
            {
                target = (aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDDocument.GetExtIDbyAphiaID)get_store().add_element_user(GETEXTIDBYAPHIAID$0);
            }
            target.set(getExtIDbyAphiaID);
        }
    }
    
    /**
     * Appends and returns a new empty "getExtIDbyAphiaID" element
     */
    public aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDDocument.GetExtIDbyAphiaID addNewGetExtIDbyAphiaID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDDocument.GetExtIDbyAphiaID target = null;
            target = (aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDDocument.GetExtIDbyAphiaID)get_store().add_element_user(GETEXTIDBYAPHIAID$0);
            return target;
        }
    }
    /**
     * An XML getExtIDbyAphiaID(@http://aphia/v1.0/externalidentifiers).
     *
     * This is a complex type.
     */
    public static class GetExtIDbyAphiaIDImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.externalidentifiers.GetExtIDbyAphiaIDDocument.GetExtIDbyAphiaID
    {
        
        public GetExtIDbyAphiaIDImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName APHIAID$0 = 
            new javax.xml.namespace.QName("", "AphiaID");
        private static final javax.xml.namespace.QName TYPE$2 = 
            new javax.xml.namespace.QName("", "type");
        
        
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

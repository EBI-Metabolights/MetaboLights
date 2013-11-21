/*
 * XML Type:  Classification
 * Namespace: http://aphia/v1.0
 * Java type: aphia.v1_0.Classification
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.impl;
/**
 * An XML Classification(@http://aphia/v1.0).
 *
 * This is a complex type.
 */
public class ClassificationImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.Classification
{
    
    public ClassificationImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName APHIAID$0 = 
        new javax.xml.namespace.QName("", "AphiaID");
    private static final javax.xml.namespace.QName RANK$2 = 
        new javax.xml.namespace.QName("", "rank");
    private static final javax.xml.namespace.QName SCIENTIFICNAME$4 = 
        new javax.xml.namespace.QName("", "scientificname");
    private static final javax.xml.namespace.QName CHILD$6 = 
        new javax.xml.namespace.QName("", "child");
    
    
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
     * Gets the "rank" element
     */
    public java.lang.String getRank()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RANK$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "rank" element
     */
    public org.apache.xmlbeans.XmlString xgetRank()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RANK$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "rank" element
     */
    public void setRank(java.lang.String rank)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RANK$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RANK$2);
            }
            target.setStringValue(rank);
        }
    }
    
    /**
     * Sets (as xml) the "rank" element
     */
    public void xsetRank(org.apache.xmlbeans.XmlString rank)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RANK$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(RANK$2);
            }
            target.set(rank);
        }
    }
    
    /**
     * Gets the "scientificname" element
     */
    public java.lang.String getScientificname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SCIENTIFICNAME$4, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SCIENTIFICNAME$4, 0);
            return target;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SCIENTIFICNAME$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SCIENTIFICNAME$4);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SCIENTIFICNAME$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SCIENTIFICNAME$4);
            }
            target.set(scientificname);
        }
    }
    
    /**
     * Gets the "child" element
     */
    public aphia.v1_0.Classification getChild()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.Classification target = null;
            target = (aphia.v1_0.Classification)get_store().find_element_user(CHILD$6, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "child" element
     */
    public void setChild(aphia.v1_0.Classification child)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.Classification target = null;
            target = (aphia.v1_0.Classification)get_store().find_element_user(CHILD$6, 0);
            if (target == null)
            {
                target = (aphia.v1_0.Classification)get_store().add_element_user(CHILD$6);
            }
            target.set(child);
        }
    }
    
    /**
     * Appends and returns a new empty "child" element
     */
    public aphia.v1_0.Classification addNewChild()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aphia.v1_0.Classification target = null;
            target = (aphia.v1_0.Classification)get_store().add_element_user(CHILD$6);
            return target;
        }
    }
}

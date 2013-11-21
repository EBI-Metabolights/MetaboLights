/*
 * XML Type:  Source
 * Namespace: http://aphia/v1.0
 * Java type: aphia.v1_0.Source
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.impl;
/**
 * An XML Source(@http://aphia/v1.0).
 *
 * This is a complex type.
 */
public class SourceImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.Source
{
    
    public SourceImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SOURCEID$0 = 
        new javax.xml.namespace.QName("", "source_id");
    private static final javax.xml.namespace.QName USE$2 = 
        new javax.xml.namespace.QName("", "use");
    private static final javax.xml.namespace.QName REFERENCE$4 = 
        new javax.xml.namespace.QName("", "reference");
    private static final javax.xml.namespace.QName PAGE$6 = 
        new javax.xml.namespace.QName("", "page");
    private static final javax.xml.namespace.QName URL$8 = 
        new javax.xml.namespace.QName("", "url");
    private static final javax.xml.namespace.QName LINK$10 = 
        new javax.xml.namespace.QName("", "link");
    private static final javax.xml.namespace.QName FULLTEXT$12 = 
        new javax.xml.namespace.QName("", "fulltext");
    
    
    /**
     * Gets the "source_id" element
     */
    public int getSourceId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SOURCEID$0, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "source_id" element
     */
    public org.apache.xmlbeans.XmlInt xgetSourceId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SOURCEID$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "source_id" element
     */
    public void setSourceId(int sourceId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SOURCEID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SOURCEID$0);
            }
            target.setIntValue(sourceId);
        }
    }
    
    /**
     * Sets (as xml) the "source_id" element
     */
    public void xsetSourceId(org.apache.xmlbeans.XmlInt sourceId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SOURCEID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(SOURCEID$0);
            }
            target.set(sourceId);
        }
    }
    
    /**
     * Gets the "use" element
     */
    public java.lang.String getUse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "use" element
     */
    public org.apache.xmlbeans.XmlString xgetUse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USE$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "use" element
     */
    public void setUse(java.lang.String use)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(USE$2);
            }
            target.setStringValue(use);
        }
    }
    
    /**
     * Sets (as xml) the "use" element
     */
    public void xsetUse(org.apache.xmlbeans.XmlString use)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(USE$2);
            }
            target.set(use);
        }
    }
    
    /**
     * Gets the "reference" element
     */
    public java.lang.String getReference()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REFERENCE$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "reference" element
     */
    public org.apache.xmlbeans.XmlString xgetReference()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REFERENCE$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "reference" element
     */
    public void setReference(java.lang.String reference)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REFERENCE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(REFERENCE$4);
            }
            target.setStringValue(reference);
        }
    }
    
    /**
     * Sets (as xml) the "reference" element
     */
    public void xsetReference(org.apache.xmlbeans.XmlString reference)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REFERENCE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REFERENCE$4);
            }
            target.set(reference);
        }
    }
    
    /**
     * Gets the "page" element
     */
    public java.lang.String getPage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAGE$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "page" element
     */
    public org.apache.xmlbeans.XmlString xgetPage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAGE$6, 0);
            return target;
        }
    }
    
    /**
     * Sets the "page" element
     */
    public void setPage(java.lang.String page)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAGE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PAGE$6);
            }
            target.setStringValue(page);
        }
    }
    
    /**
     * Sets (as xml) the "page" element
     */
    public void xsetPage(org.apache.xmlbeans.XmlString page)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAGE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PAGE$6);
            }
            target.set(page);
        }
    }
    
    /**
     * Gets the "url" element
     */
    public java.lang.String getUrl()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(URL$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "url" element
     */
    public org.apache.xmlbeans.XmlString xgetUrl()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(URL$8, 0);
            return target;
        }
    }
    
    /**
     * Sets the "url" element
     */
    public void setUrl(java.lang.String url)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(URL$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(URL$8);
            }
            target.setStringValue(url);
        }
    }
    
    /**
     * Sets (as xml) the "url" element
     */
    public void xsetUrl(org.apache.xmlbeans.XmlString url)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(URL$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(URL$8);
            }
            target.set(url);
        }
    }
    
    /**
     * Gets the "link" element
     */
    public java.lang.String getLink()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LINK$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "link" element
     */
    public org.apache.xmlbeans.XmlString xgetLink()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LINK$10, 0);
            return target;
        }
    }
    
    /**
     * Sets the "link" element
     */
    public void setLink(java.lang.String link)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LINK$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LINK$10);
            }
            target.setStringValue(link);
        }
    }
    
    /**
     * Sets (as xml) the "link" element
     */
    public void xsetLink(org.apache.xmlbeans.XmlString link)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LINK$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LINK$10);
            }
            target.set(link);
        }
    }
    
    /**
     * Gets the "fulltext" element
     */
    public java.lang.String getFulltext()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FULLTEXT$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "fulltext" element
     */
    public org.apache.xmlbeans.XmlString xgetFulltext()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FULLTEXT$12, 0);
            return target;
        }
    }
    
    /**
     * Sets the "fulltext" element
     */
    public void setFulltext(java.lang.String fulltext)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FULLTEXT$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FULLTEXT$12);
            }
            target.setStringValue(fulltext);
        }
    }
    
    /**
     * Sets (as xml) the "fulltext" element
     */
    public void xsetFulltext(org.apache.xmlbeans.XmlString fulltext)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FULLTEXT$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FULLTEXT$12);
            }
            target.set(fulltext);
        }
    }
}

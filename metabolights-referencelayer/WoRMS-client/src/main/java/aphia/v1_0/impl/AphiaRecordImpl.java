/*
 * XML Type:  AphiaRecord
 * Namespace: http://aphia/v1.0
 * Java type: aphia.v1_0.AphiaRecord
 *
 * Automatically generated - do not modify.
 */
package aphia.v1_0.impl;
/**
 * An XML AphiaRecord(@http://aphia/v1.0).
 *
 * This is a complex type.
 */
public class AphiaRecordImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aphia.v1_0.AphiaRecord
{
    
    public AphiaRecordImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName APHIAID$0 = 
        new javax.xml.namespace.QName("", "AphiaID");
    private static final javax.xml.namespace.QName URL$2 = 
        new javax.xml.namespace.QName("", "url");
    private static final javax.xml.namespace.QName SCIENTIFICNAME$4 = 
        new javax.xml.namespace.QName("", "scientificname");
    private static final javax.xml.namespace.QName AUTHORITY$6 = 
        new javax.xml.namespace.QName("", "authority");
    private static final javax.xml.namespace.QName RANK$8 = 
        new javax.xml.namespace.QName("", "rank");
    private static final javax.xml.namespace.QName STATUS$10 = 
        new javax.xml.namespace.QName("", "status");
    private static final javax.xml.namespace.QName UNACCEPTREASON$12 = 
        new javax.xml.namespace.QName("", "unacceptreason");
    private static final javax.xml.namespace.QName VALIDAPHIAID$14 = 
        new javax.xml.namespace.QName("", "valid_AphiaID");
    private static final javax.xml.namespace.QName VALIDNAME$16 = 
        new javax.xml.namespace.QName("", "valid_name");
    private static final javax.xml.namespace.QName VALIDAUTHORITY$18 = 
        new javax.xml.namespace.QName("", "valid_authority");
    private static final javax.xml.namespace.QName KINGDOM$20 = 
        new javax.xml.namespace.QName("", "kingdom");
    private static final javax.xml.namespace.QName PHYLUM$22 = 
        new javax.xml.namespace.QName("", "phylum");
    private static final javax.xml.namespace.QName CLASS1$24 = 
        new javax.xml.namespace.QName("", "class");
    private static final javax.xml.namespace.QName ORDER$26 = 
        new javax.xml.namespace.QName("", "order");
    private static final javax.xml.namespace.QName FAMILY$28 = 
        new javax.xml.namespace.QName("", "family");
    private static final javax.xml.namespace.QName GENUS$30 = 
        new javax.xml.namespace.QName("", "genus");
    private static final javax.xml.namespace.QName CITATION$32 = 
        new javax.xml.namespace.QName("", "citation");
    private static final javax.xml.namespace.QName LSID$34 = 
        new javax.xml.namespace.QName("", "lsid");
    private static final javax.xml.namespace.QName ISMARINE$36 = 
        new javax.xml.namespace.QName("", "isMarine");
    private static final javax.xml.namespace.QName ISBRACKISH$38 = 
        new javax.xml.namespace.QName("", "isBrackish");
    private static final javax.xml.namespace.QName ISFRESHWATER$40 = 
        new javax.xml.namespace.QName("", "isFreshwater");
    private static final javax.xml.namespace.QName ISTERRESTRIAL$42 = 
        new javax.xml.namespace.QName("", "isTerrestrial");
    private static final javax.xml.namespace.QName ISEXTINCT$44 = 
        new javax.xml.namespace.QName("", "isExtinct");
    private static final javax.xml.namespace.QName MATCHTYPE$46 = 
        new javax.xml.namespace.QName("", "match_type");
    private static final javax.xml.namespace.QName MODIFIED$48 = 
        new javax.xml.namespace.QName("", "modified");
    
    
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
     * Gets the "url" element
     */
    public java.lang.String getUrl()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(URL$2, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(URL$2, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(URL$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(URL$2);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(URL$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(URL$2);
            }
            target.set(url);
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
     * Gets the "authority" element
     */
    public java.lang.String getAuthority()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUTHORITY$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "authority" element
     */
    public org.apache.xmlbeans.XmlString xgetAuthority()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AUTHORITY$6, 0);
            return target;
        }
    }
    
    /**
     * Sets the "authority" element
     */
    public void setAuthority(java.lang.String authority)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUTHORITY$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AUTHORITY$6);
            }
            target.setStringValue(authority);
        }
    }
    
    /**
     * Sets (as xml) the "authority" element
     */
    public void xsetAuthority(org.apache.xmlbeans.XmlString authority)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AUTHORITY$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(AUTHORITY$6);
            }
            target.set(authority);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RANK$8, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RANK$8, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RANK$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RANK$8);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RANK$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(RANK$8);
            }
            target.set(rank);
        }
    }
    
    /**
     * Gets the "status" element
     */
    public java.lang.String getStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATUS$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "status" element
     */
    public org.apache.xmlbeans.XmlString xgetStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATUS$10, 0);
            return target;
        }
    }
    
    /**
     * Sets the "status" element
     */
    public void setStatus(java.lang.String status)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATUS$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STATUS$10);
            }
            target.setStringValue(status);
        }
    }
    
    /**
     * Sets (as xml) the "status" element
     */
    public void xsetStatus(org.apache.xmlbeans.XmlString status)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATUS$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATUS$10);
            }
            target.set(status);
        }
    }
    
    /**
     * Gets the "unacceptreason" element
     */
    public java.lang.String getUnacceptreason()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(UNACCEPTREASON$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "unacceptreason" element
     */
    public org.apache.xmlbeans.XmlString xgetUnacceptreason()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(UNACCEPTREASON$12, 0);
            return target;
        }
    }
    
    /**
     * Sets the "unacceptreason" element
     */
    public void setUnacceptreason(java.lang.String unacceptreason)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(UNACCEPTREASON$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(UNACCEPTREASON$12);
            }
            target.setStringValue(unacceptreason);
        }
    }
    
    /**
     * Sets (as xml) the "unacceptreason" element
     */
    public void xsetUnacceptreason(org.apache.xmlbeans.XmlString unacceptreason)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(UNACCEPTREASON$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(UNACCEPTREASON$12);
            }
            target.set(unacceptreason);
        }
    }
    
    /**
     * Gets the "valid_AphiaID" element
     */
    public int getValidAphiaID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VALIDAPHIAID$14, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "valid_AphiaID" element
     */
    public org.apache.xmlbeans.XmlInt xgetValidAphiaID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(VALIDAPHIAID$14, 0);
            return target;
        }
    }
    
    /**
     * Sets the "valid_AphiaID" element
     */
    public void setValidAphiaID(int validAphiaID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VALIDAPHIAID$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(VALIDAPHIAID$14);
            }
            target.setIntValue(validAphiaID);
        }
    }
    
    /**
     * Sets (as xml) the "valid_AphiaID" element
     */
    public void xsetValidAphiaID(org.apache.xmlbeans.XmlInt validAphiaID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(VALIDAPHIAID$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(VALIDAPHIAID$14);
            }
            target.set(validAphiaID);
        }
    }
    
    /**
     * Gets the "valid_name" element
     */
    public java.lang.String getValidName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VALIDNAME$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "valid_name" element
     */
    public org.apache.xmlbeans.XmlString xgetValidName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(VALIDNAME$16, 0);
            return target;
        }
    }
    
    /**
     * Sets the "valid_name" element
     */
    public void setValidName(java.lang.String validName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VALIDNAME$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(VALIDNAME$16);
            }
            target.setStringValue(validName);
        }
    }
    
    /**
     * Sets (as xml) the "valid_name" element
     */
    public void xsetValidName(org.apache.xmlbeans.XmlString validName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(VALIDNAME$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(VALIDNAME$16);
            }
            target.set(validName);
        }
    }
    
    /**
     * Gets the "valid_authority" element
     */
    public java.lang.String getValidAuthority()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VALIDAUTHORITY$18, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "valid_authority" element
     */
    public org.apache.xmlbeans.XmlString xgetValidAuthority()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(VALIDAUTHORITY$18, 0);
            return target;
        }
    }
    
    /**
     * Sets the "valid_authority" element
     */
    public void setValidAuthority(java.lang.String validAuthority)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VALIDAUTHORITY$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(VALIDAUTHORITY$18);
            }
            target.setStringValue(validAuthority);
        }
    }
    
    /**
     * Sets (as xml) the "valid_authority" element
     */
    public void xsetValidAuthority(org.apache.xmlbeans.XmlString validAuthority)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(VALIDAUTHORITY$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(VALIDAUTHORITY$18);
            }
            target.set(validAuthority);
        }
    }
    
    /**
     * Gets the "kingdom" element
     */
    public java.lang.String getKingdom()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(KINGDOM$20, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "kingdom" element
     */
    public org.apache.xmlbeans.XmlString xgetKingdom()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(KINGDOM$20, 0);
            return target;
        }
    }
    
    /**
     * Sets the "kingdom" element
     */
    public void setKingdom(java.lang.String kingdom)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(KINGDOM$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(KINGDOM$20);
            }
            target.setStringValue(kingdom);
        }
    }
    
    /**
     * Sets (as xml) the "kingdom" element
     */
    public void xsetKingdom(org.apache.xmlbeans.XmlString kingdom)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(KINGDOM$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(KINGDOM$20);
            }
            target.set(kingdom);
        }
    }
    
    /**
     * Gets the "phylum" element
     */
    public java.lang.String getPhylum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PHYLUM$22, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "phylum" element
     */
    public org.apache.xmlbeans.XmlString xgetPhylum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PHYLUM$22, 0);
            return target;
        }
    }
    
    /**
     * Sets the "phylum" element
     */
    public void setPhylum(java.lang.String phylum)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PHYLUM$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PHYLUM$22);
            }
            target.setStringValue(phylum);
        }
    }
    
    /**
     * Sets (as xml) the "phylum" element
     */
    public void xsetPhylum(org.apache.xmlbeans.XmlString phylum)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PHYLUM$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PHYLUM$22);
            }
            target.set(phylum);
        }
    }
    
    /**
     * Gets the "class" element
     */
    public java.lang.String getClass1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLASS1$24, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "class" element
     */
    public org.apache.xmlbeans.XmlString xgetClass1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLASS1$24, 0);
            return target;
        }
    }
    
    /**
     * Sets the "class" element
     */
    public void setClass1(java.lang.String class1)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLASS1$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CLASS1$24);
            }
            target.setStringValue(class1);
        }
    }
    
    /**
     * Sets (as xml) the "class" element
     */
    public void xsetClass1(org.apache.xmlbeans.XmlString class1)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLASS1$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CLASS1$24);
            }
            target.set(class1);
        }
    }
    
    /**
     * Gets the "order" element
     */
    public java.lang.String getOrder()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ORDER$26, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "order" element
     */
    public org.apache.xmlbeans.XmlString xgetOrder()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ORDER$26, 0);
            return target;
        }
    }
    
    /**
     * Sets the "order" element
     */
    public void setOrder(java.lang.String order)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ORDER$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ORDER$26);
            }
            target.setStringValue(order);
        }
    }
    
    /**
     * Sets (as xml) the "order" element
     */
    public void xsetOrder(org.apache.xmlbeans.XmlString order)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ORDER$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ORDER$26);
            }
            target.set(order);
        }
    }
    
    /**
     * Gets the "family" element
     */
    public java.lang.String getFamily()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAMILY$28, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "family" element
     */
    public org.apache.xmlbeans.XmlString xgetFamily()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAMILY$28, 0);
            return target;
        }
    }
    
    /**
     * Sets the "family" element
     */
    public void setFamily(java.lang.String family)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAMILY$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FAMILY$28);
            }
            target.setStringValue(family);
        }
    }
    
    /**
     * Sets (as xml) the "family" element
     */
    public void xsetFamily(org.apache.xmlbeans.XmlString family)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAMILY$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FAMILY$28);
            }
            target.set(family);
        }
    }
    
    /**
     * Gets the "genus" element
     */
    public java.lang.String getGenus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GENUS$30, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "genus" element
     */
    public org.apache.xmlbeans.XmlString xgetGenus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(GENUS$30, 0);
            return target;
        }
    }
    
    /**
     * Sets the "genus" element
     */
    public void setGenus(java.lang.String genus)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GENUS$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(GENUS$30);
            }
            target.setStringValue(genus);
        }
    }
    
    /**
     * Sets (as xml) the "genus" element
     */
    public void xsetGenus(org.apache.xmlbeans.XmlString genus)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(GENUS$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(GENUS$30);
            }
            target.set(genus);
        }
    }
    
    /**
     * Gets the "citation" element
     */
    public java.lang.String getCitation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CITATION$32, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "citation" element
     */
    public org.apache.xmlbeans.XmlString xgetCitation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CITATION$32, 0);
            return target;
        }
    }
    
    /**
     * Sets the "citation" element
     */
    public void setCitation(java.lang.String citation)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CITATION$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CITATION$32);
            }
            target.setStringValue(citation);
        }
    }
    
    /**
     * Sets (as xml) the "citation" element
     */
    public void xsetCitation(org.apache.xmlbeans.XmlString citation)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CITATION$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CITATION$32);
            }
            target.set(citation);
        }
    }
    
    /**
     * Gets the "lsid" element
     */
    public java.lang.String getLsid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LSID$34, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "lsid" element
     */
    public org.apache.xmlbeans.XmlString xgetLsid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LSID$34, 0);
            return target;
        }
    }
    
    /**
     * Sets the "lsid" element
     */
    public void setLsid(java.lang.String lsid)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LSID$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LSID$34);
            }
            target.setStringValue(lsid);
        }
    }
    
    /**
     * Sets (as xml) the "lsid" element
     */
    public void xsetLsid(org.apache.xmlbeans.XmlString lsid)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LSID$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LSID$34);
            }
            target.set(lsid);
        }
    }
    
    /**
     * Gets the "isMarine" element
     */
    public int getIsMarine()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISMARINE$36, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "isMarine" element
     */
    public org.apache.xmlbeans.XmlInt xgetIsMarine()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(ISMARINE$36, 0);
            return target;
        }
    }
    
    /**
     * Sets the "isMarine" element
     */
    public void setIsMarine(int isMarine)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISMARINE$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ISMARINE$36);
            }
            target.setIntValue(isMarine);
        }
    }
    
    /**
     * Sets (as xml) the "isMarine" element
     */
    public void xsetIsMarine(org.apache.xmlbeans.XmlInt isMarine)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(ISMARINE$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(ISMARINE$36);
            }
            target.set(isMarine);
        }
    }
    
    /**
     * Gets the "isBrackish" element
     */
    public int getIsBrackish()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISBRACKISH$38, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "isBrackish" element
     */
    public org.apache.xmlbeans.XmlInt xgetIsBrackish()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(ISBRACKISH$38, 0);
            return target;
        }
    }
    
    /**
     * Sets the "isBrackish" element
     */
    public void setIsBrackish(int isBrackish)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISBRACKISH$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ISBRACKISH$38);
            }
            target.setIntValue(isBrackish);
        }
    }
    
    /**
     * Sets (as xml) the "isBrackish" element
     */
    public void xsetIsBrackish(org.apache.xmlbeans.XmlInt isBrackish)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(ISBRACKISH$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(ISBRACKISH$38);
            }
            target.set(isBrackish);
        }
    }
    
    /**
     * Gets the "isFreshwater" element
     */
    public int getIsFreshwater()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISFRESHWATER$40, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "isFreshwater" element
     */
    public org.apache.xmlbeans.XmlInt xgetIsFreshwater()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(ISFRESHWATER$40, 0);
            return target;
        }
    }
    
    /**
     * Sets the "isFreshwater" element
     */
    public void setIsFreshwater(int isFreshwater)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISFRESHWATER$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ISFRESHWATER$40);
            }
            target.setIntValue(isFreshwater);
        }
    }
    
    /**
     * Sets (as xml) the "isFreshwater" element
     */
    public void xsetIsFreshwater(org.apache.xmlbeans.XmlInt isFreshwater)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(ISFRESHWATER$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(ISFRESHWATER$40);
            }
            target.set(isFreshwater);
        }
    }
    
    /**
     * Gets the "isTerrestrial" element
     */
    public int getIsTerrestrial()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISTERRESTRIAL$42, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "isTerrestrial" element
     */
    public org.apache.xmlbeans.XmlInt xgetIsTerrestrial()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(ISTERRESTRIAL$42, 0);
            return target;
        }
    }
    
    /**
     * Sets the "isTerrestrial" element
     */
    public void setIsTerrestrial(int isTerrestrial)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISTERRESTRIAL$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ISTERRESTRIAL$42);
            }
            target.setIntValue(isTerrestrial);
        }
    }
    
    /**
     * Sets (as xml) the "isTerrestrial" element
     */
    public void xsetIsTerrestrial(org.apache.xmlbeans.XmlInt isTerrestrial)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(ISTERRESTRIAL$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(ISTERRESTRIAL$42);
            }
            target.set(isTerrestrial);
        }
    }
    
    /**
     * Gets the "isExtinct" element
     */
    public int getIsExtinct()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISEXTINCT$44, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "isExtinct" element
     */
    public org.apache.xmlbeans.XmlInt xgetIsExtinct()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(ISEXTINCT$44, 0);
            return target;
        }
    }
    
    /**
     * Sets the "isExtinct" element
     */
    public void setIsExtinct(int isExtinct)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISEXTINCT$44, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ISEXTINCT$44);
            }
            target.setIntValue(isExtinct);
        }
    }
    
    /**
     * Sets (as xml) the "isExtinct" element
     */
    public void xsetIsExtinct(org.apache.xmlbeans.XmlInt isExtinct)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(ISEXTINCT$44, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(ISEXTINCT$44);
            }
            target.set(isExtinct);
        }
    }
    
    /**
     * Gets the "match_type" element
     */
    public java.lang.String getMatchType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MATCHTYPE$46, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "match_type" element
     */
    public org.apache.xmlbeans.XmlString xgetMatchType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MATCHTYPE$46, 0);
            return target;
        }
    }
    
    /**
     * Sets the "match_type" element
     */
    public void setMatchType(java.lang.String matchType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MATCHTYPE$46, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MATCHTYPE$46);
            }
            target.setStringValue(matchType);
        }
    }
    
    /**
     * Sets (as xml) the "match_type" element
     */
    public void xsetMatchType(org.apache.xmlbeans.XmlString matchType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MATCHTYPE$46, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MATCHTYPE$46);
            }
            target.set(matchType);
        }
    }
    
    /**
     * Gets the "modified" element
     */
    public java.lang.String getModified()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MODIFIED$48, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "modified" element
     */
    public org.apache.xmlbeans.XmlString xgetModified()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MODIFIED$48, 0);
            return target;
        }
    }
    
    /**
     * Sets the "modified" element
     */
    public void setModified(java.lang.String modified)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MODIFIED$48, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MODIFIED$48);
            }
            target.setStringValue(modified);
        }
    }
    
    /**
     * Sets (as xml) the "modified" element
     */
    public void xsetModified(org.apache.xmlbeans.XmlString modified)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MODIFIED$48, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MODIFIED$48);
            }
            target.set(modified);
        }
    }
}

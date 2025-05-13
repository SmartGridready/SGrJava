package com.smartgridready.driver.api.contacts;

/**
 * Defines the interface of a digital contacts interface driver factory.
 */
public interface GenDriverAPI4ContactsFactory {

    /**
     * Creates a driver instance.
     * @param nrOfContacts the number of contacts
     * @return a new instance of {@link GenDriverAPI4Contacts}
     */
    public GenDriverAPI4Contacts create(int nrOfContacts);
    
    /**
     * Creates a driver instance.
     * @param nrOfContacts the number of contacts
     * @param stabilizationTimeMs the time contacts need to get to a stable state, in ms
     * @return a new instance of {@link GenDriverAPI4Contacts}
     */
    public GenDriverAPI4Contacts create(int nrOfContacts, long stabilizationTimeMs);
}

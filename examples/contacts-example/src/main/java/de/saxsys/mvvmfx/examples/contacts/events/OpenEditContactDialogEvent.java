package de.saxsys.mvvmfx.examples.contacts.events;

import de.saxsys.mvvmfx.examples.contacts.ui.editcontact.EditContactDialog;

/**
 * CDI event class that is used to indicate that the {@link EditContactDialog} dialog should be opened.
 * 
 * It contains the id of the contact to edit.
 */
public class OpenEditContactDialogEvent {
	
	private final String contactId;
	
	/**
	 * @param contactId
	 *            the id of the contact to edit.
	 */
	public OpenEditContactDialogEvent(String contactId) {
		this.contactId = contactId;
	}
	
	public String getContactId() {
		return contactId;
	}
}

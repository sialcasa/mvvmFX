package de.saxsys.mvvmfx.contacts.events;

public class OpenEditContactDialogEvent {
	
	private final String contactId;

	/**
	 * @param contactId the id of the contact to edit.
	 */
	public OpenEditContactDialogEvent(String contactId) {
		this.contactId = contactId;
	}

	public String getContactId() {
		return contactId;
	}
}

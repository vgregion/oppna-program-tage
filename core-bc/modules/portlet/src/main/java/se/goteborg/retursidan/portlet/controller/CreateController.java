package se.goteborg.retursidan.portlet.controller;

import se.goteborg.retursidan.model.entity.ContactInterface;
import se.goteborg.retursidan.model.entity.Person;
import se.vgregion.ldapservice.LdapUser;

import javax.portlet.PortletRequest;

/**
 * @author Patrik Björk
 */
public class CreateController extends BaseController {

    protected void populateContactInfoFromLdap(PortletRequest portletRequest, ContactInterface entry) {
        try {

            String userId = getUserId(portletRequest);

            // Try to fill in user information, as much as possible.

            if (entry.getContact() == null) {
                entry.setContact(new Person());
            }

            Person person = modelService.getPerson(userId);

            Person contact = entry.getContact();

            if (person != null) {
                contact.setPhone(person.getPhone());
            }

            LdapUser ldapUser = userDirectoryService.getLdapUserByUid(userId);

            if (ldapUser != null) {
                String displayname = ldapUser.getAttributeValue("displayname");
                String mail = ldapUser.getAttributeValue("mail");

                if (contact.getName() == null) {
                    contact.setName(displayname);
                }

                if (contact.getEmail() == null) {
                    contact.setEmail(mail);
                }
            } else if (person != null) {
                if (contact.getName() == null) {
                    contact.setName(person.getName());
                }

                if (contact.getEmail() == null) {
                    contact.setEmail(person.getEmail());
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

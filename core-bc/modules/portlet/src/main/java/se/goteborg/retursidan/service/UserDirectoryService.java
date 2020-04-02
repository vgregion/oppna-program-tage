package se.goteborg.retursidan.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.vgregion.ldapservice.LdapUser;
import se.vgregion.ldapservice.SimpleLdapUser;

import javax.annotation.PostConstruct;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static org.springframework.ldap.support.LdapUtils.closeContext;

/**
 * @author Patrik Björk
 */
@Service
public class UserDirectoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDirectoryService.class);

    @Value("${BASE}")
    private String ldapBase;

    @Value("${BIND_DN}")
    private String bindDN;

    @Value("${BIND_URL}")
    private String bindUrl;

    @Value("${BIND_PW}")
    private String bindPassword;

    private Hashtable env;

    public UserDirectoryService() {
    }

    @PostConstruct
    public void init() {
        env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, bindUrl);
        env.put(Context.REFERRAL, "follow");
        env.put("com.sun.jndi.ldap.connect.pool", "true");
        if (bindDN != null) {
            env.put(Context.SECURITY_PRINCIPAL, bindDN);
            env.put(Context.SECURITY_CREDENTIALS, bindPassword);
        }
    }

    public LdapUser getLdapUserByUid(String vgrId) {
        LOGGER.info("getLdapUserByUid: " + vgrId);

        LdapUser ldapUser = getLdapUser(ldapBase, "(&(objectCategory=Person)(sAMAccountName=" + vgrId + "))");

        return ldapUser;
    }

    public LdapUser getLdapUser(String base, String filter) {
        DirContext dirContext = null;
        try {
            SearchControls sc = new SearchControls();
            sc.setCountLimit(1);
            sc.setDerefLinkFlag(false);
            sc.setReturningAttributes(new String[]{"displayname", "mail", "department", "division"});
            sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
            dirContext = getBaseContext();
            NamingEnumeration results = dirContext.search(base, filter, sc);
            List entries = new ArrayList();

            while (results.hasMore()) {
                SearchResult oneRes = (SearchResult) results.next();
                String dn = oneRes.getNameInNamespace();
                SimpleLdapUser ldapUser = new SimpleLdapUser(dn);

                ldapUser.setMail((String) getAttributeValueSafely(oneRes, "mail"));
                ldapUser.setAttributeValue("displayname", getAttributeValueSafely(oneRes, "displayname"));
                ldapUser.setAttributeValue("department", getAttributeValueSafely(oneRes, "department"));
                ldapUser.setAttributeValue("division", getAttributeValueSafely(oneRes, "division"));

                entries.add(ldapUser);

                break;
            }

            if (entries.size() > 1) {
                throw new RuntimeException("Entry is not unique: " + filter);
            } else if (entries.size() == 0) {
                return null;
            }

            return (LdapUser) entries.get(0);
        } catch (Exception e) {
            throw new RuntimeException("Search failed: base=" + base + " filter=" + filter, e);
        } finally {
            closeContext(dirContext);
        }
    }

    private Object getAttributeValueSafely(SearchResult oneRes, String key) throws NamingException {
        Attribute attribute = oneRes.getAttributes().get(key);

        if (attribute == null) {
            return null;
        }

        return attribute.get();
    }

    private DirContext getBaseContext() {
        try {
            return new InitialDirContext(env);
        } catch (Exception e) {
            throw new RuntimeException("Bind failed", e);
        }
    }

}

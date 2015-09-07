package org.mobicents.servlet.restcomm.identity.keycloak;

import org.keycloak.adapters.HttpFacade.Request;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.mobicents.servlet.restcomm.identity.configuration.IdentityConfigurationSet.IdentityMode;
import org.mobicents.servlet.restcomm.identity.configuration.IdentityConfigurator;
import org.mobicents.servlet.restcomm.identity.configuration.IdentityConfigurator.IdentityNotSet;

public class RestcommConfKeycloakResolver implements KeycloakConfigResolver {

    //private final Map<String, KeycloakDeployment> cache = new ConcurrentHashMap<String, KeycloakDeployment>();
    private KeycloakDeployment cache;

    public RestcommConfKeycloakResolver() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public KeycloakDeployment resolve(Request request) {
        IdentityConfigurator configurator = IdentityConfigurator.getInstance();
        if ( configurator.getMode() == IdentityMode.init ) {
            // no caching here if we're in init mode
            KeycloakDeployment deployment = new KeycloakDeployment();
            // return an empty deployment
            return deployment;
        } else {
            try {
                cache = KeycloakDeploymentBuilder.build(configurator.getRestcommConfig());
            } catch (IdentityNotSet e) {
                throw new IllegalStateException("No cloud identity set in restcomm.xml");
            }
        }
        return cache;
    }

}
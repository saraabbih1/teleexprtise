package ma.youcode.teleexpertise.security;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

import ma.youcode.teleexpertise.entity.Utilisateur;
import ma.youcode.teleexpertise.repository.UtilisateurRepository;

import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class BasicAuthFilter implements ContainerRequestFilter {

    private final UtilisateurRepository utilisateurRepository =
            new UtilisateurRepository();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authorization =
                requestContext.getHeaderString("Authorization");

        // 1. Vérifier que l'Authorization existe et qu'elle utilise Basic Authentication
        if (authorization == null || !authorization.startsWith("Basic ")) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build()
            );
            return;
        }

        // 2. Récupérer la partie encodée après Basic
        String encodedCredentials =
                authorization.substring("Basic ".length());

        String credentials = new String(
                Base64.getDecoder().decode(encodedCredentials)
        );

        String[] parts = credentials.split(":", 2);

        if (parts.length != 2) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build()
            );
            return;
        }

        String username = parts[0];
        String password = parts[1];

        Utilisateur utilisateur =
                utilisateurRepository.findByUsername(username);

        if (utilisateur == null) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build()
            );
            return;
        }

        if (!BCrypt.checkpw(password, utilisateur.getPassword())) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build()
            );
            return;
        }

      
        requestContext.setSecurityContext(new SecurityContext() {

            @Override
            public Principal getUserPrincipal() {
                return () -> utilisateur.getUsername();
            }

            @Override
            public boolean isUserInRole(String role) {
                return utilisateur.getRole().name().equals(role);
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public String getAuthenticationScheme() {
                return "Basic";
            }
        });
    }
}
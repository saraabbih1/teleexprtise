
package ma.youcode.teleexpertise.security;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

import ma.youcode.teleexpertise.entity.Utilisateur;
import ma.youcode.teleexpertise.repository.UtilisateurRepository;

import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Base64;
import java.util.Map;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class BasicAuthFilter implements ContainerRequestFilter {

    private final UtilisateurRepository utilisateurRepository =
            new UtilisateurRepository();

    @Override
    public void filter(ContainerRequestContext requestContext)
            throws IOException {

        String authorization =
                requestContext.getHeaderString("Authorization");

        // 1. Vérifier l'en-tête Authorization
        if (authorization == null
                || !authorization.startsWith("Basic ")) {

            requestContext.abortWith(
                    unauthorized("Authorization Basic requise")
            );
            return;
        }

        // 2. Récupérer les credentials encodés
        String encodedCredentials =
                authorization.substring("Basic ".length());

        // 3. Décoder Base64
        String credentials;

        try {
            credentials = new String(
                    Base64.getDecoder().decode(encodedCredentials),
                    StandardCharsets.UTF_8
            );
        } catch (IllegalArgumentException e) {
            requestContext.abortWith(
                    unauthorized("Authorization Basic invalide")
            );
            return;
        }

        // 4. Séparer username et password
        String[] parts = credentials.split(":", 2);

        if (parts.length != 2) {
            requestContext.abortWith(
                    unauthorized("Identifiants invalides")
            );
            return;
        }

        String username = parts[0];
        String password = parts[1];

        // 5. Rechercher l'utilisateur
        Utilisateur utilisateur =
                utilisateurRepository.findByUsername(username);

        if (utilisateur == null) {
            requestContext.abortWith(
                    unauthorized("Identifiants invalides")
            );
            return;
        }

        // 6. Vérifier le mot de passe avec BCrypt
        if (!BCrypt.checkpw(password, utilisateur.getPassword())) {
            requestContext.abortWith(
                    unauthorized("Identifiants invalides")
            );
            return;
        }

        // 7. Définir l'identité et le rôle de l'utilisateur
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
                return requestContext.getSecurityContext().isSecure();
            }

            @Override
            public String getAuthenticationScheme() {
                return "Basic";
            }
        });
    }

    // Réponse JSON pour les erreurs 401
    private Response unauthorized(String message) {
        return Response.status(Response.Status.UNAUTHORIZED)
                .type(MediaType.APPLICATION_JSON)
                .entity(Map.of(
                        "status", 401,
                        "error", "Unauthorized",
                        "message", message
                ))
                .build();
    }
}
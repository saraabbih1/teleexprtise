package ma.youcode.teleexpertise.config;

import java.util.Set;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import ma.youcode.teleexpertise.resource.PingResource;
import ma.youcode.teleexpertise.resource.DemandeExpertiseResource;

@ApplicationPath("/api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(
                PingResource.class,
                DemandeExpertiseResource.class
        );
    }
}
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Permite el envío de credenciales/tokens
        config.setAllowCredentials(true);

        config.addAllowedOrigin("https://satori-frontend-production.up.railway.app");
        // config.addAllowedOrigin("http://localhost:4200"); // pruebas locales

        // Permitir todos los encabezados y métodos (GET, POST, OPTIONS, etc.)
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        // Aplica esta configuración a todas las rutas de tu API
        source.registerCorsConfiguration("/api/**", config);

        return new CorsFilter(source);
    }
}
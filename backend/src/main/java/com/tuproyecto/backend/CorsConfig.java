import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public FilterRegistrationBean<CorsFilter> customCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Permitimos credenciales
        config.setAllowCredentials(true);

        // Usamos pattern con "*" para que acepte tu URL sin importar cómo la formatee el proxy
        config.addAllowedOriginPattern("*");

        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        // Aplicamos a ABSOLUTAMENTE TODAS las rutas (usamos /** en lugar de /api/** por seguridad)
        source.registerCorsConfiguration("/**", config);

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));

        // 🚀 ESTA ES LA MAGIA: Forzamos a que este filtro sea el #1 en ejecutarse
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return bean;
    }
}
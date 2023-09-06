package io.dpopkov.bepomidor.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfiguration : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        // TODO: всё разрешено только на время разработки frontend, после завершения убрать.
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*")
    }
}

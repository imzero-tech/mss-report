package com.example.mss.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebMvc
@EnableAspectJAutoProxy
public class WebMvcConfiguration implements WebMvcConfigurer {
    protected String DEFAULT_RESOURCES_PATH = "/**";
    protected static final String[] DEFAULT_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/",
            "classpath:/resources/",
            "classpath:/static/",
            "classpath:/public/"
    };

    private static final String[] EXCLUDE_LOGIN_PATH_PATTERNS = {
            "/favicon.ico",
            "/css/**",
            "/images/**",
            "/js/**",
            "/fonts/**",
            "/less/**",
            "/sample/**",
            "/webfonts/**",
            "/webjars/**",
            "/api/**",
            "/ping",
            "/error",
            "/login*"
    };

    private final WebProperties webProperties;

    private final ResourceLoader resourceLoader;

    @Override
    public final void addResourceHandlers(ResourceHandlerRegistry registry) {
        ResourceHandlerRegistration resourceHandlerRegistration = registry.addResourceHandler(DEFAULT_RESOURCES_PATH);
        resourceHandlerRegistration.addResourceLocations(DEFAULT_RESOURCE_LOCATIONS);
        resourceHandlerRegistration.setCacheControl(webProperties.getResources().getCache().getCachecontrol().toHttpCacheControl());

        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/")
                .setCachePeriod(0) //Set to 0 in order to send cache headers that prevent caching
        ;
        registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/").resourceChain(false);
    }

    @Bean
    public SimpleUrlHandlerMapping staticResourceHandlerMapping(ResourceHttpRequestHandler resourceHttpRequestHandler) {
        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
        simpleUrlHandlerMapping.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        simpleUrlHandlerMapping.setUrlMap(Collections.singletonMap("/favicon.ico", resourceHttpRequestHandler));
        simpleUrlHandlerMapping.setUrlMap(Collections.singletonMap("/robots.txt", resourceHttpRequestHandler));
        return simpleUrlHandlerMapping;
    }

    @Bean
    protected ResourceHttpRequestHandler staticResourceRequestHandler() {
        ResourceHttpRequestHandler requestHandler = new ResourceHttpRequestHandler();
        requestHandler.setLocations(staticResourceResolveLocations());
        return requestHandler;
    }

    // 템플릿 사용을 위한 정적 리소스 처리
    private List<Resource> staticResourceResolveLocations() {
        List<Resource> locations = new ArrayList<>(DEFAULT_RESOURCE_LOCATIONS.length + 1);

        Arrays.stream(DEFAULT_RESOURCE_LOCATIONS).map(this.resourceLoader::getResource).forEach(locations::add);
        locations.add(new ClassPathResource("/"));
        return Collections.unmodifiableList(locations);
    }
}

package org.gezixi.regis.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

public class WebMvcConfig extends WebMvcConfigurationSupport {
    /**
     * 静态资源映射
     */
    @Override
    protected void addResourceHandler(ResourceHandlerRegistry registry) {
        super.addResourceHandler(registry);
    }
}

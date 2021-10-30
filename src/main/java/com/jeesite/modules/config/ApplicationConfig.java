package com.jeesite.modules.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pengxincheng
 * @date 2021/10/30 1:20 下午
 */
@Configuration
public class ApplicationConfig {

    @Bean(name = "freeMarkerConfigurer")
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setDefaultEncoding("UTF-8");
        configurer.setTemplateLoaderPath("classpath:/template");
        Map<String, Object> variables = new HashMap<>();
        variables.put("xml_escape", "fmXmlEscape");
        configurer.setFreemarkerVariables(variables);
        return configurer;
    }
}

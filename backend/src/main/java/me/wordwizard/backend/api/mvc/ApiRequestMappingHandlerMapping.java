package me.wordwizard.backend.api.mvc;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

public class ApiRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
    private static final String API_BASE_PATH = "api";

    @Override
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
        Class<?> beanType = method.getDeclaringClass();
        if (AnnotationUtils.findAnnotation(beanType, RestController.class) != null) {
            PatternsRequestCondition apiPattern = new PatternsRequestCondition(API_BASE_PATH)
                    .combine(mapping.getPatternsCondition());

            mapping = new RequestMappingInfo(mapping.getName(), apiPattern,
                    mapping.getMethodsCondition(), mapping.getParamsCondition(),
                    mapping.getHeadersCondition(), mapping.getConsumesCondition(),
                    mapping.getProducesCondition(), mapping.getCustomCondition());
        }

        super.registerHandlerMethod(handler, method, mapping);
    }
}

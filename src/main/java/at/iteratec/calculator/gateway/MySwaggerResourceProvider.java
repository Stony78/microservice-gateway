package at.iteratec.calculator.gateway;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Primary
public class MySwaggerResourceProvider implements SwaggerResourcesProvider {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private EurekaClient discoveryClient;

    @Override
    public List get() {
        return discoveryClient.getApplications().getRegisteredApplications()
                .stream()
                .map(p -> this.createSwaggerResource(p))
                .collect(Collectors.toList());
    }

    private SwaggerResource createSwaggerResource(Application app) {
        SwaggerResource swaggerResource = new SwaggerResource();
        String name = app.getName().toLowerCase();
        swaggerResource.setName(name);
        swaggerResource.setLocation("/" + name + "/v2/api-docs");
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
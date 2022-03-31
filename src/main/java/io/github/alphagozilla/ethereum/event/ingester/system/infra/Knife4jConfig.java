package io.github.alphagozilla.ethereum.event.ingester.system.infra;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author AlphaGodzilla
 * @date 2022/3/30 14:21
 */
@Configuration
public class Knife4jConfig {
    @Resource
    private OpenApiExtensionResolver openApiExtensionResolver;

    @Bean
    public Docket manageDocket() {
        return directModelSubstitute(new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("管理接口")
                        .build())
                .groupName("默认接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage("io.github.alphagozilla.ethereum.event.ingester.manage.api.http"))
                .paths(PathSelectors.any())
                .build()
                .extensions(openApiExtensionResolver.buildSettingExtensions()));
    }

    private Docket directModelSubstitute(Docket docket) {
        return docket
                .directModelSubstitute(LocalDateTime.class, String.class)
                .directModelSubstitute(Timestamp.class, String.class)
                .directModelSubstitute(BigDecimal.class, String.class)
                .directModelSubstitute(BigInteger.class, String.class);
    }
}

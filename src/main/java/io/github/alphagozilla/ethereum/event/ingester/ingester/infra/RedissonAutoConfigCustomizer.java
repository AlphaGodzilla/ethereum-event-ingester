package io.github.alphagozilla.ethereum.event.ingester.ingester.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.client.codec.Codec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Slf4j
@Configuration
public class RedissonAutoConfigCustomizer implements RedissonAutoConfigurationCustomizer {
    @Resource
    private ObjectMapper objectMapper;

    @Bean
    public Codec jsonJacksonCodec() {
        return new JsonJacksonCodec(objectMapper);
    }

    @Override
    public void customize(Config config) {
        config.setCodec(jsonJacksonCodec());
        log.info("redisson配置序列化器完成");
    }
}

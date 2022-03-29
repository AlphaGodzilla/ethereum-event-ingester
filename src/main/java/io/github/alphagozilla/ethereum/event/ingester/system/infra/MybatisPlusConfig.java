package io.github.alphagozilla.ethereum.event.ingester.system.infra;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import io.github.alphagozilla.ethereum.event.ingester.ingester.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author AlphaGodzilla
 * @date 2021/11/24 11:47
 */
@Slf4j
@Configuration
public class MybatisPlusConfig {
    @Bean
    public IdentifierGenerator identifierGenerator() {
        log.info("配置MybatisPlus的ID生成器Bean");
        return new IdentifierGenerator() {
            @Override
            public Number nextId(Object entity) {
                return IdUtil.nextId();
            }

            @Override
            public String nextUUID(Object entity) {
                return IdUtil.uuid();
            }
        };
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        final PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        paginationInnerInterceptor.setMaxLimit(500L);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }
}

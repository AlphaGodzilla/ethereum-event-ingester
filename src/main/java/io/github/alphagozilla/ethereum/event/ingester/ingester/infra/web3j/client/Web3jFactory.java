package io.github.alphagozilla.ethereum.event.ingester.ingester.infra.web3j.client;

import io.github.alphagozilla.ethereum.event.ingester.ingester.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.http.HttpService;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author AlphaGodzilla
 * @date 2021/12/29 15:38
 */
@Slf4j
@Component
public class Web3jFactory {
    @Value("${web3j.endpoint}")
    private String endpoint;
    @Value("${web3j.timeout}")
    private Duration timeout;
    @Value("${web3j.log}")
    private boolean logEnable;
    @Resource
    private Web3jRateLimiter rateLimiter;

    private Web3j web3jInstant;

    public Web3j getClient() {
        if (web3jInstant == null) {
            synchronized (this) {
                if (web3jInstant == null) {
                    web3jInstant = newWeb3j(endpoint, logEnable, timeout);
                }
            }
        }
        return web3jInstant;
    }

    private Web3j newWeb3j(String address, boolean logEnable, Duration timeout) {
        Web3jService web3jService = buildService(address, logEnable, timeout);
        log.info("Building service for endpoint: " + address);
        return Web3j.build(web3jService);
    }

    private Web3jService buildService(String endpoint, boolean logEnable, Duration timeout) {
        Web3jService web3jService = null;
        if (StringUtil.isEmpty(endpoint)) {
            web3jService = new HttpService(createOkHttpClient(logEnable, timeout));
        }else if (endpoint.startsWith("http")) {
            web3jService = new HttpService(endpoint, createOkHttpClient(logEnable, timeout), false);
        }
        if (web3jService == null) {
            throw new IllegalArgumentException("web3jService build error clintAddress is " + endpoint);
        }
        return web3jService;
    }

    private OkHttpClient createOkHttpClient(boolean logEnable, Duration timeout) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        configureLogging(builder, logEnable);
        configureTimeouts(builder, timeout);
        configRateLimiter(builder);
        return builder.build();
    }

    private void configureTimeouts(OkHttpClient.Builder builder, Duration timeout) {
        long tos = timeout.toSeconds();
        builder.connectTimeout(tos, TimeUnit.SECONDS);
        // Sets the socket timeout too
        builder.readTimeout(tos, TimeUnit.SECONDS);
        builder.writeTimeout(tos, TimeUnit.SECONDS);
    }

    private void configureLogging(OkHttpClient.Builder builder, boolean logEnable) {
        if (!logEnable) {
            return;
        }
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(log::info);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(logging);
    }

    private void configRateLimiter(OkHttpClient.Builder builder) {
        if (rateLimiter != null) {
            builder.addInterceptor(rateLimiter);
        }
    }
}

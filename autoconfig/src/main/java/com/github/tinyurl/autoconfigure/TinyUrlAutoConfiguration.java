package com.github.tinyurl.autoconfigure;

import com.github.tinyurl.autoconfigure.client.TinyUrlClientImpl;
import com.github.tinyurl.autoconfigure.client.TinyUrlClientConfig;
import com.github.tinyurl.client.TinyUrlClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 短链接服务自动配置
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/15
 */
@Configuration
@ConditionalOnClass(TinyUrlClient.class)
@EnableConfigurationProperties(TinyUrlProperties.class)
public class TinyUrlAutoConfiguration {
    @Resource
    private TinyUrlProperties tinyUrlProperties;

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    @ConditionalOnMissingBean
    public TinyUrlClientConfig clientConfig() {
        TinyUrlClientConfig clientConfig = new TinyUrlClientConfig();
        clientConfig.setHost(tinyUrlProperties.getHost());
        clientConfig.setAppId(tinyUrlProperties.getAppId());
        clientConfig.setKey(tinyUrlProperties.getKey());
        return clientConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public TinyUrlClient client(TinyUrlClientConfig clientConfig, RestTemplate restTemplate) {
        TinyUrlClientImpl tinyUrlClient = new TinyUrlClientImpl(clientConfig, restTemplate);
        tinyUrlClient.initialize();
        return tinyUrlClient;
    }
}

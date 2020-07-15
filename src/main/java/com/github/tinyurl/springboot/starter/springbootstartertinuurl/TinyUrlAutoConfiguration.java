package com.github.tinyurl.springboot.starter.springbootstartertinuurl;

import com.github.tinyurl.springboot.starter.springbootstartertinuurl.client.TinyUrlClientImpl;
import com.github.tinyurl.springboot.starter.springbootstartertinuurl.client.TinyUrlClientConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 短链接服务自动配置
 *
 * @author jiquanxi
 * @date 2020/07/15
 */
@Configuration
@ConditionalOnClass(TinyUrlClientImpl.class)
@EnableConfigurationProperties(TinyUrlProperties.class)
public class TinyUrlAutoConfiguration {
    @Resource
    private TinyUrlProperties tinyUrlProperties;

    @Bean
    @ConditionalOnMissingBean
    public TinyUrlClientConfig clientConfig() {
        TinyUrlClientConfig clientConfig = new TinyUrlClientConfig();
        clientConfig.setHost(tinyUrlProperties.getHost());
        clientConfig.setPort(tinyUrlProperties.getPort());
        clientConfig.setAppId(tinyUrlProperties.getAppId());
        clientConfig.setKey(tinyUrlProperties.getKey());
        return clientConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public TinyUrlClientImpl client(TinyUrlClientConfig clientConfig) {
        return new TinyUrlClientImpl(clientConfig);
    }
}

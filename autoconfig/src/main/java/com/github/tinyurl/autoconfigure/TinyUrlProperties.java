package com.github.tinyurl.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置属性
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/15
 */
@Data
@Component
@ConfigurationProperties(prefix = "tinyurl")
public class TinyUrlProperties {
    /**
     * 域名
     */
    private String host;

    /**
     * appid
     */
    private String appId;

    /**
     * app密钥
     */
    private String key;
}

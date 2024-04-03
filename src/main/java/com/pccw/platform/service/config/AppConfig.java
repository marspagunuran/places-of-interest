/** */
package com.pccw.platform.service.config;

import com.pccw.platform.service.model.AppProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** @author dxiong */
@Configuration
public class AppConfig {

  @Bean
  @ConfigurationProperties(prefix = "app")
  public AppProperties properties() {
    return new AppProperties();
  }
}

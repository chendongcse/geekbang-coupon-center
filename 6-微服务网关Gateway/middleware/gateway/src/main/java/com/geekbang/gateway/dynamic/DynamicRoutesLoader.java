package com.geekbang.gateway.dynamic;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DynamicRoutesLoader implements InitializingBean {

    @Autowired
    private NacosConfigManager configService;

    @Autowired
    private NacosConfigProperties configProps;

    @Autowired
    private DynamicRoutesListener dynamicRoutesListener;

    private static final String ROUTES_CONFIG = "routes-config.json";

    @Override
    public void afterPropertiesSet() throws Exception {
        String routes = configService.getConfigService().getConfig(
                ROUTES_CONFIG, configProps.getGroup(), 10000);
        dynamicRoutesListener.receiveConfigInfo(routes);

        configService.getConfigService().addListener(ROUTES_CONFIG,
                configProps.getGroup(),
                dynamicRoutesListener);
    }

}

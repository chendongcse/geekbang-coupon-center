package com.geekbang.gateway.dynamic;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.listener.Listener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executor;

@Slf4j
@Component
public class DynamicRoutesListener implements Listener {

    @Autowired
    private GatewayService gatewayService;

    @Override
    public Executor getExecutor() {
        log.info("getExecutor");
        return null;
    }

    @Override
    public void receiveConfigInfo(String configInfo) {
        log.info("received routes changes {}", configInfo);

        List<RouteDefinition> definitionList = JSON.parseArray(configInfo, RouteDefinition.class);
        gatewayService.updateRoutes(definitionList);
    }
}

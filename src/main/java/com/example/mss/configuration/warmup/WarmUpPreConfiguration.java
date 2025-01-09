package com.example.mss.configuration.warmup;

import com.example.mss.application.preload.service.PreLoadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * spring.config.activate.on-profile: test 일 경우에만 data를 등록한다.
 */
@Profile("test")
@Slf4j
@Component
@RequiredArgsConstructor
public class WarmUpPreConfiguration implements ApplicationListener<ContextRefreshedEvent> {

    private final PreLoadService preLoadService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        enablingWarmUpRequests();

        stopWatch.stop();
        log.info("WarmUp Pre Execution time : {} ms", stopWatch.getTotalTimeMillis());
    }

    protected void enablingWarmUpRequests() {
        // warm up 작성
        preLoadService.preLoad();
    }
}

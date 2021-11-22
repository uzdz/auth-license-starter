package com.uzdz.license;

import com.uzdz.license.aop.LicenseScanAspect;
import com.uzdz.license.api.ServerInfoController;
import com.uzdz.license.config.LicenseConfig;
import com.uzdz.license.listener.LicenseCheckListener;
import com.uzdz.license.thread.LicenseThreadCheck;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * License 安装器
 * @author ：uzdz
 */
@Configuration
@Import({ServerInfoController.class, LicenseCheckListener.class,
        LicenseScanAspect.class, LicenseThreadCheck.class})
@EnableConfigurationProperties({ LicenseConfig.class })
public class LicenseAutoConfiguration {
}

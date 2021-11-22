package com.uzdz.license.thread;

import com.uzdz.license.listener.LicenseCheckListener;
import com.uzdz.license.manager.LicenseVerify;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 任务证书检查器
 *
 * @author ：uzdz
 */
@AutoConfigureAfter({ LicenseCheckListener.class})
public class LicenseThreadCheck {
    private static Logger logger = LogManager.getLogger(LicenseThreadCheck.class);

    private static final ScheduledExecutorService check = Executors.newScheduledThreadPool(1, new ThreadNamedFactory("uzdz-license-schedule"));

    public static volatile boolean effective = true;

    static {
        check.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {

                logger.info("开始检查License授权状态");

                LicenseVerify licenseVerify = new LicenseVerify();

                //校验证书是否有效
                boolean verifyResult = licenseVerify.verify();

                if (verifyResult) {
                    effective = true;
                } else {
                    effective = false;
                }

                logger.info("License授权状态：" + effective);
            }
        }, 5, 60, TimeUnit.SECONDS);
    }
}

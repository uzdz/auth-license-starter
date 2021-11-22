package com.uzdz.license.listener;

import com.uzdz.license.config.LicenseConfig;
import com.uzdz.license.manager.LicenseVerify;
import com.uzdz.license.manager.LicenseVerifyParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * 在项目启动时安装证书
 *
 * @author terri
 * @date 2020/10/09
 * @since 1.0.0
 */

public class LicenseCheckListener {

    private static Logger logger = LogManager.getLogger(LicenseCheckListener.class);

    @Autowired
    private LicenseConfig licenseConfig;

    @PostConstruct
    private void init() {

        if(StringUtils.isNotBlank(licenseConfig.getLicensePath())){
            logger.info("++++++++ 开始安装证书 ++++++++");

            LicenseVerifyParam param = new LicenseVerifyParam();
            param.setSubject(licenseConfig.getSubject());
            param.setPublicAlias(licenseConfig.getPublicAlias());
            param.setStorePass(licenseConfig.getStorePass());
            param.setLicensePath(licenseConfig.getLicensePath());
            param.setPublicKeysStorePath(licenseConfig.getPublicKeysStorePath());

            LicenseVerify licenseVerify = new LicenseVerify();
            //安装证书
            licenseVerify.install(param);

            logger.info("++++++++ 证书安装结束 ++++++++");
        } else {
            logger.info("++++++++ 未设置证书地址等配置，请前往设置 ++++++++");
        }
    }
}

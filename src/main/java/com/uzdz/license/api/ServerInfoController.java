package com.uzdz.license.api;

import com.uzdz.license.config.LicenseConfig;
import com.uzdz.license.license.AbstractServerInfos;
import com.uzdz.license.license.LinuxServerInfos;
import com.uzdz.license.license.MacServerInfos;
import com.uzdz.license.license.WindowsServerInfos;
import com.uzdz.license.LicenseCheckModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 服务器信息暴露
 * @author terri
 * @date 2020/10/09
 * @since 1.0.0
 */
@RestController
@RequestMapping("/license")
public class ServerInfoController {

    @Autowired
    private LicenseConfig licenseConfig;

    /**
     * 获取服务器硬件信息
     * @author terri
     * @date 2020/10/09
     * @since 1.0.0
     * @param osName 操作系统类型，如果为空则自动判断
     * @return com.ccx.models.license.LicenseCheckModel
     */
    @RequestMapping(value = "/getServerInfos",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public LicenseCheckModel getServerInfos(@RequestParam(value = "osName",required = false) String osName) {

        if (!licenseConfig.isServerInfo()) {
            return null;
        }

        //操作系统类型
        if(StringUtils.isBlank(osName)){
            osName = System.getProperty("os.name");
        }
        osName = osName.toLowerCase();

        AbstractServerInfos abstractServerInfos = null;

        //根据不同操作系统类型选择不同的数据获取方法
        if (osName.startsWith("windows")) {
            abstractServerInfos = new WindowsServerInfos();
        } else if (osName.startsWith("linux")) {
            abstractServerInfos = new LinuxServerInfos();
        } else if (osName.startsWith("mac os x")) {
            abstractServerInfos = new MacServerInfos();
        } else{//其他服务器类型
            abstractServerInfos = new LinuxServerInfos();
        }

        return abstractServerInfos.getServerInfos();
    }

}

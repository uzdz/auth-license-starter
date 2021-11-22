package com.uzdz.license.aop;

import com.uzdz.license.exception.LicenseFailedException;
import com.uzdz.license.thread.LicenseThreadCheck;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 证书验证
 * @author uzdz
 */
@Aspect
public class LicenseScanAspect {


    @Pointcut("@annotation(com.uzdz.license.annotations.LicenseScan)")
    public void selector(){}

    @Around("selector()")
    public Object before(ProceedingJoinPoint point) throws Throwable {

        if (LicenseThreadCheck.effective) {
            return point.proceed();
        }

        throw new LicenseFailedException("您的证书无效，请核查服务器是否取得授权或重新申请证书！");
    }
}

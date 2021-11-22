# 授权启动器使用手册

## 第一步：引入Maven依赖

一旦引入依赖，凡事以`@LicenseScan`描述的方法，均会启动License授权检查（授权失败会直接触发异常中断请求）。

```xml
<dependency>
	<groupId>com.uzdz.licencse</groupId>
	<artifactId>auth-license-starter</artifactId>
	<version>1.0.0</version>
</dependency>
```

## 第二步：增加License配置

以下参数用于License文件及存储设置，不配置则会直接导致License启动器检查失败。

需要配置在`bootstrap.yaml`或Nacos配置中心等配置文件或系统中，详细可参考`com.uzdz.license.config.LicenseConfig`配置类。

```yaml
uzdz:
  license:
    # 是否开启服务器配置查看接口
    serverInfo: true
    subject: ${spring.application.name}
    publicAlias: publicCert
    storePass: public_password1234
    licensePath: /Users/uzdz/Downloads/expired.lic
    publicKeysStorePath: /Users/uzdz/lisence/publicCerts.store
```

## 第三步：方法配置License授权检查（@LicenseScan注解）

`@LicenseScan`注解用于修饰方法，被`@LicenseScan`修饰的方法即会被AOP代理，每次请求均会进行授权检查。

以下例子中，`systemEnums`方法被`@LicenseScan`注解修饰，则`systemEnums`方法会直接被授权检查器监听，该方法的每次Http请求均会判断是否License授权成功。

```java
@Api(tags = "系统信息服务接口")
@RestController
public class SystemApi {

    @Autowired
    private BaseSystemFeign baseSystemFeign;

    @LicenseScan
    @GetMapping(value = "/systemEnums")
    @ApiOperation(value = "系统状态码", notes = "当前服务全部状态码")
    @ApiResponses({ @ApiResponse(code = 200, message = "操作成功", response = List.class) })
    public List<CommonEnumResponse> systemEnums() {
        return RCMUtil.getSystemCommonEnums();
    }
}
```

## 第四步：异常处理

当被`@LicenseScan`注解修饰的方法被请求时，如果授权证书失效或检查失败，则会触发`LicenseFailedException`异常，建议使用全局异常拦截器`@ControllerAdvice`进行统一处理。

```java
/**
 * 全局异常
 *
 * @author uzdz
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * License授权失败
     */
    @ExceptionHandler(value = LicenseFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public BaseResponse serviceExceptionHandler(HttpServletRequest request, Exception ex) {

        logger(request, ex);
        return RCMUtil.build(CommonRCM.INTERFACE_PROCESS_ERROR, ex.getMessage);
    }
}

```

# 注意事项

考虑性能问题，License授权由异步线程检查刷新，所以无需考虑增加`@LicenseScan`的方法会受到License检查性能影响。详情参考：`com.uzdz.license.thread.LicenseThreadCheck`实现。
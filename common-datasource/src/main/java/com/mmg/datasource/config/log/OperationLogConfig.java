package com.mmg.datasource.config.log;

import com.alibaba.fastjson.JSON;
import com.mmg.commons.config.exception.ApiErrorCode;
import com.mmg.commons.config.exception.ApiException;
import com.mmg.commons.config.module.CommonRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: fan
 * @Date: 2021/6/28
 * @Description: 操作日志
 */
@Aspect
@Component
@Slf4j
public class OperationLogConfig {

    @Pointcut("@annotation(io.swagger.annotations.ApiOperation)")
    public void executeService() {
    }

    //请求信息
    @Before("executeService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("请求开始====================================================================================");
        //接收请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        log.info("X-Forwarded-For : " + request.getHeader("X-Forwarded-For"));
        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        //请求参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        log.info("REQUEST_PARAMETER: {} ", JSON.toJSONString(parameterMap));
        //参数缺失判断
        List<Object> args = Arrays.stream(joinPoint.getArgs())
                .filter(arg -> arg instanceof CommonRequest)
                .collect(Collectors.toList());
        log.info("METHOD_ARGS: {}", JSON.toJSONString(args));
        args.forEach(arg -> {
            Class c = arg.getClass();
            Field[] fields = c.getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
                    if (apiModelProperty != null && apiModelProperty.required()) {
                        if (field.get(arg) == null) {
                            throw new ApiException(ApiErrorCode.BAD_REQUEST, String.format("参数%s缺失", field.getName()));
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //请求效率
    @Around(value = "executeService()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //计算方法执行时间
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        log.debug("return : " + proceed);
        log.info(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName()
                + "SPEND TIME　:　" + (System.currentTimeMillis() - startTime) + "ms");
        return proceed;
    }

    //抛出异常时
    @AfterThrowing(value = "executeService()", throwing = "e")
    public void doBefore(JoinPoint joinPoint, Exception e) {
        log.error("ERROR MESSAGE: {}", e.getMessage());
        log.info("错误请求结束====================================================================================");
    }

    //正常请求结束后
    @AfterReturning("executeService()")
    public void doAfterReturning(JoinPoint joinPoint) {
        log.info("请求结束====================================================================================");
    }

}

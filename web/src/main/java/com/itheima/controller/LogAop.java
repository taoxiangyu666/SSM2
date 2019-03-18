package com.itheima.controller;

import com.itheima.domain.SysLog;
import com.itheima.service.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysLogService sysLogService;

    private Date startTime;  //访问时间
    private Class executionClass;//访问的类
    private Method executionMethod;//访问类的方法

    //前置通知
    @Before("execution(* com.itheima.controller.*.*(..))")
    public void doBefore(JoinPoint joinPoint) throws NoSuchMethodException {
        //访问时间
        startTime = new Date();
        //访问的类
        executionClass = joinPoint.getTarget().getClass();
        //访问的方法名
        String methodName = joinPoint.getSignature().getName();
        //获取方法的参数
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            //无参数
            executionMethod = executionClass.getMethod(methodName);
        } else {
            //有参数
            Class[] classAges = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                classAges[i] = args[i].getClass();
            }
            executionMethod = executionClass.getMethod(methodName, classAges);
        }

    }

    //后置通知
    @After("execution(* com.itheima.controller.*.*(..))")
    public void doAfter() {
        //访问时长
        long time = new Date().getTime() - startTime.getTime();
        //url
        String url = "";
        if (executionClass != null && executionMethod != null && executionClass != LogAop.class) {
            //获得类上的@RequestMapping("/user)
            RequestMapping classAnnotation = (RequestMapping) executionClass.getAnnotation(RequestMapping.class);
            if (classAnnotation != null) {
                String[] classValue = classAnnotation.value();
                //获得方法上的@RequestMapping("/user)
                RequestMapping methodAnnotation = executionMethod.getAnnotation(RequestMapping.class);
                if (methodAnnotation != null) {
                    String[] methodValue = methodAnnotation.value();
                    url = classValue[0] + methodValue[0];
                }
            }

        }
            //访问者的ip
            String ip = request.getRemoteAddr();
            //访问者名字
            SecurityContext context = SecurityContextHolder.getContext();
            User user = (User) context.getAuthentication().getPrincipal();
            String username = user.getUsername();

            //封装信息
            SysLog sysLog = new SysLog();
            sysLog.setExecutionTime(time);
            sysLog.setIp(ip);
            sysLog.setMethod("[类名]" + executionClass.getName() + "[方法名]" + executionMethod.getName());
            sysLog.setUrl(url);
            sysLog.setUsername(username);
            sysLog.setVisitTime(startTime);
            if (!url.contains("/sysLog/findAll.do")) {
                sysLogService.save(sysLog);
            }
    }
}

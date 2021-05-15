package cn.wolfcode.crm.util;

import cn.wolfcode.crm.domain.SystemLog;
import cn.wolfcode.crm.service.ISystemLogService;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class SystemLogUtil {
    @Autowired
    private ISystemLogService logService;

    //保存日志
    public void writeLog(JoinPoint joinPoint) {
        //日志的service不记录(避免死循环)
        if (joinPoint.getTarget() instanceof ISystemLogService) {
            return;
        }

        SystemLog log = new SystemLog();
        //操作用户
        log.setOpUser(UserContext.getCurrentUser());
        //操作时间
        log.setOpTime(new Date());
        //登录IP
        log.setOpIp(UserContext.getRequest().getRemoteAddr());
        //功能
        String ClzName = joinPoint.getTarget().getClass().getName();
        String MethodName = joinPoint.getSignature().getName();
        log.setFunction(ClzName + ":" + MethodName);
        //参数(json)
        //没有参数或者文件上传的时候,把参数设置为空字符串
        Object[] args = joinPoint.getArgs();
        if (args.length == 0 || "importFile".equals(MethodName)) {
            log.setParams("");
        } else {
            log.setParams(JSONUtil.toJSON(args));
        }

        //保存到数据库
        logService.save(log);
    }
}

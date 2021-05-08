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
        if(joinPoint.getTarget() instanceof ISystemLogService){
            return;
        }
        //文件上传时候特殊处理
        if("importXlsx".equals(joinPoint.getSignature().getName())){
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
        String function = joinPoint.getTarget().getClass().getName() + ":" + joinPoint.getSignature().getName();
        log.setFunction(function);
        //参数(json)
        log.setParams(JSONUtil.toJSON(joinPoint.getArgs()));

        //保存到数据库
        logService.save(log);
    }
}

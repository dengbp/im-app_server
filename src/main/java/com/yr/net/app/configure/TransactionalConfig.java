package com.yr.net.app.configure;

import com.yr.net.app.common.exception.AppException;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dengbp
 * @ClassName TransactionalConfig
 * @Description 全局事务
 * @date 2020-12-14 19:45
 */

@Aspect
@Configuration
public class TransactionalConfig {

    /**
     * 配置方法过期时间，默认-1,永不超时
     */
    private final static int METHOD_TIME_OUT = 120000;

    /**
     * 配置切入点表达式
     */
    private static final String POINTCUT_EXPRESSION = "execution(* com.segi.license.*.service..*.*(..))";

    /**
     * 事务管理器
     */
    @Resource
    private PlatformTransactionManager transactionManager;


    @Bean
    public TransactionInterceptor txAdvice() {
        /*事务管理规则，声明具备事务管理的方法名**/
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        /*只读事务，不做更新操作*/
        RuleBasedTransactionAttribute readOnly = new RuleBasedTransactionAttribute();
        readOnly.setReadOnly(true);
        readOnly.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
        /*当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务*/
        RuleBasedTransactionAttribute required = new RuleBasedTransactionAttribute();
        /*抛出异常后执行切点回滚,这边你可以更换异常的类型*/
        required.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(AppException.class)));
        /*PROPAGATION_REQUIRED:事务隔离性为1，若当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。这是默认值*/
        required.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        /*设置事务失效时间，如果超过5秒，则回滚事务*/
        required.setTimeout(METHOD_TIME_OUT);
        Map<String, TransactionAttribute> attributesMap = new HashMap<>(30);
        //设置增删改上传等使用事务
        attributesMap.put("save*", required);
        attributesMap.put("remove*", required);
        attributesMap.put("update*", required);
        attributesMap.put("batch*", required);
        attributesMap.put("clear*", required);
        attributesMap.put("add*", required);
        attributesMap.put("append*", required);
        attributesMap.put("modify*", required);
        attributesMap.put("edit*", required);
        attributesMap.put("insert*", required);
        attributesMap.put("pay*", required);
        attributesMap.put("delete*", required);
        attributesMap.put("do*", required);
        attributesMap.put("create*", required);
        attributesMap.put("build*", required);
        attributesMap.put("import*", required);
        attributesMap.put("set*", required);
        //查询开启只读
        attributesMap.put("select*", readOnly);
        attributesMap.put("get*", readOnly);
        attributesMap.put("valid*", readOnly);
        attributesMap.put("list*", readOnly);
        attributesMap.put("count*", readOnly);
        attributesMap.put("find*", readOnly);
        attributesMap.put("load*", readOnly);
        attributesMap.put("search*", readOnly);
        source.setNameMap(attributesMap);
        return new TransactionInterceptor(transactionManager, source);
    }

    /**
     * 设置切面=切点pointcut+通知TxAdvice
     */
    @Bean
    public Advisor txAdviceAdvisor() {
        /* 声明切点的面：切面就是通知和切入点的结合。通知和切入点共同定义了关于切面的全部内容——它的功能、在何时和何地完成其功能*/
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        /*声明和设置需要拦截的方法,用切点语言描写*/
        pointcut.setExpression(POINTCUT_EXPRESSION);
        /*设置切面=切点pointcut+通知TxAdvice*/
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}

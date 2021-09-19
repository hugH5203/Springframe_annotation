package in2021winter.annotation.annotionFacfory;
import in2021winter.annotation.annotationUtils.TransActionManager;
import in2021winter.annotation.annotationService.IService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**用于作为注解的动态代理类，环绕通知方式
 * @author HuangHai
 * @date 2021/2/6 22:57
 */

@Component("proxyService")
@Aspect   //表示当前类是一个切面类
public class BeanFactory {

    @Autowired
    private TransActionManager trans;

    @Autowired
    private IService service;

    //配置切入点表达式（在一个空方法上面），可以配置多个，但是得在不同的方法上，一个切入点表达式占一个空方法
    @Pointcut("execution(* in2021winter.annotation.annotationService.UserService.*(..))")
    private void point1(){}

    /**
     * xml配置的环绕通知会出现调用顺序问题，注解的前置后置等通知配置麻烦。所以最好是xml用前置后置等通知，而注解用环绕通知比较好。
     * 环绕通知可以看成是通知与切入点的结合体，可以看成是整个被强化的方法
     * xml之所以会出现调用顺序问题是因为配置后spring没有明确的调用方法所以才有了下面这个spring为我们提供的接口：
     * ProceedingJoinPoint，该接口只有一个方法proceed(),此方法就相当于明确调用切入掉方法。
     * 该接口可以作为环绕通知的参数来使用，在程序执行时spring框架会为我们提供该接口的实现类供我们使用
     * @param point
     */
    @Around(value = "point1()")
    public Object aroundPrintLog(ProceedingJoinPoint point){
        Object rtValue=null;  //返回值
        try {
        Object[] args=point.getArgs();//获取要调用的方法的返回值
        trans.beginTransAction();//前置通知
            rtValue= point.proceed(args);
            trans.commit();  //后置通知
            return rtValue;//明确调用切入点方法,类似于method.invoke
        } catch (Throwable throwable) {  //这里的异常类必须是老祖宗throwable类，因为Exception挡不住它
            trans.rollBack();  //异常回滚通知
            System.out.println("事务回滚");
            throw new RuntimeException(throwable);  //这里抛出运行异常是为了防止出了异常还要我返回一个值
        }finally {
            trans.release();  //释放连接
        }




    }

}

package org.meihuai.springmvc.test.bean;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

/**
 * @author Created by meihuai.liang
 * @version 1.0.0.0
 * @date 2018/4/16 23:36
 * @since 1.0
 */
@Component
public class MyRefreshListener implements ApplicationListener {
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent){
            System.out.println("context...refresh..listener");
            StackTraceElement elements[] = Thread.currentThread().getStackTrace();
            for (int i = 0; i < elements.length; i++) {
                StackTraceElement stackTraceElement = elements[i];
                String className = stackTraceElement.getClassName();
                String methodName = stackTraceElement.getMethodName();
                String fileName = stackTraceElement.getFileName();
                int lineNumber = stackTraceElement.getLineNumber();
                System.out.println("StackTraceElement数组下标 i=" + i + ",fileName=" + fileName + ",className=" + className + ",methodName=" + methodName + ",lineNumber=" + lineNumber);
            }
        }
        if (event instanceof ContextStartedEvent){
            System.out.println("context...started..listener");
        }

        if (event instanceof ContextClosedEvent){
            System.out.println("context...close..listener");
        }

        if (event instanceof ContextStoppedEvent){
            System.out.println("context...stop..listener");
        }
    }

}

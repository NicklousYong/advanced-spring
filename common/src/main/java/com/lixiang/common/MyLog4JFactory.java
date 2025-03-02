package com.lixiang.common;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class MyLog4JFactory {

    //配置log4j的工厂，获取对应的log4j

    public static Logger getLogger(){

        String callerClassName = getCallerClassName();
        Logger logger = Logger.getLogger(callerClassName);
        logger.setLevel(Level.toLevel(10000));//对应debug
        return logger;
    }

    private static String getCallerClassName(){
        Throwable throwable = new Throwable();
        StackTraceElement[] stackTraceElements = throwable.getStackTrace();
        return stackTraceElements[2].getClassName();
    }


}

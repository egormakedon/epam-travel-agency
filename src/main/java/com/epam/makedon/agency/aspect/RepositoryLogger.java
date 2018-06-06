package com.epam.makedon.agency.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class RepositoryLogger {

    @Autowired
    private Logger logger;

    @Pointcut("within(com.epam.makedon.agency.repository.databaseimpl..*)")
    public void databaseRepository() {}

    @Pointcut("execution(public * add(..))")
    private void addMethods() {}

    @Pointcut("execution(public * get(..))")
    private void getMethods() {}

    @Pointcut("execution(public * remove(..))")
    private void removeMethods() {}

    @Pointcut("execution(public * update(..))")
    private void updateMethods() {}

    @Before("databaseRepository() && addMethods()")
    public void beforeLogAddMethods(JoinPoint point) {
        logger.info("start add method in " + point.getTarget().getClass().getSimpleName());
    }

    @Before("databaseRepository() && getMethods()")
    public void beforeLogGetMethods(JoinPoint point) {
        logger.info("start get method in " + point.getTarget().getClass().getSimpleName());
    }

    @Before("databaseRepository() && removeMethods()")
    public void beforeLogRemoveMethods(JoinPoint point) {
        logger.info("start remove method in " + point.getTarget().getClass().getSimpleName());
    }

    @Before("databaseRepository() && updateMethods()")
    public void beforeLogUpdateMethods(JoinPoint point) {
        logger.info("start update method in " + point.getTarget().getClass().getSimpleName());
    }

    @AfterReturning(pointcut = "databaseRepository() && addMethods()", returning = "retVal")
    public void afterReturingLogAddReturnValue(JoinPoint point, Object retVal) {
        logger.info("return value in add method " + retVal + " in class " +  point.getTarget().getClass().getSimpleName());
    }

    @AfterThrowing(pointcut = "databaseRepository() && addMethods()", throwing = "th")
    public void afterThrowingLogAddExc(JoinPoint point, Throwable th) {
        logger.info("throwing value = " + th.getMessage() + " in class = " + point.getTarget().getClass().getSimpleName());
    }

    @AfterThrowing(pointcut = "databaseRepository() && getMethods()", throwing = "th")
    public void afterThrowingLogGetExc(JoinPoint point, Throwable th) {
        logger.info("throwing value = " + th.getMessage() + " in class = " + point.getTarget().getClass().getSimpleName());
    }

    @AfterThrowing(pointcut = "databaseRepository() && removeMethods()", throwing = "th")
    public void afterThrowingLogRemoveExc(JoinPoint point, Throwable th) {
        logger.info("throwing value = " + th.getMessage() + " in class = " + point.getTarget().getClass().getSimpleName());
    }

    @AfterThrowing(pointcut = "databaseRepository() && updateMethods()", throwing = "th")
    public void afterThrowingLogUpdateExc(JoinPoint point, Throwable th) {
        logger.info("throwing value = " + th.getMessage() + " in class = " + point.getTarget().getClass().getSimpleName());
    }
}
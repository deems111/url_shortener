package org.example.shortener.aop.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Log execution time of a method and log exception
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@LogExecutionTime
@LogException
public @interface LogAll {
}

package us.lsi.dp1.newcorporder.match;

import org.springframework.aot.hint.annotation.Reflective;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
@Reflective
public @interface VerifyCurrentTurn {}

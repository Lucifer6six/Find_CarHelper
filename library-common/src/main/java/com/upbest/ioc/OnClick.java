package com.upbest.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 本类作用: View事件注解 Annotation
 * Created by kGY on 2017/8/25.
 * Email 18252032703@163.com
 * qq 827746955 mobile 18252032703
 * Thank you for watching my code
 */

@Target(ElementType.METHOD) // 目标为止 属性的位置 代表Annotation的位置 FIELD 代表的属性 TYPE 放在类上
@Retention(RetentionPolicy.RUNTIME) // 什么时候生效 运行时 SOURCE 源码资源
public @interface OnClick {
    // 获取value
    int[] value();
}

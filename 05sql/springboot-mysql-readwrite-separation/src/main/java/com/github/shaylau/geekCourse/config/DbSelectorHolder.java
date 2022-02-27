package com.github.shaylau.geekCourse.config;

import com.github.shaylau.geekCourse.enums.DbTypeEnum;
import org.springframework.stereotype.Component;

/**
 * 当前线程使用的DB
 *
 * @author ShayLau
 * @date 2022/2/27 9:59 PM
 */
@Component
public class DbSelectorHolder {

    /**
     * 选择的数据库
     */
    private static ThreadLocal<DbTypeEnum> select = new ThreadLocal();


    public static void setSelect(DbTypeEnum typeEnum) {
        select.set(typeEnum);
    }

    public static DbTypeEnum getSelect() {
        return select.get();
    }

    public static void remove() {
        select.remove();
    }
}

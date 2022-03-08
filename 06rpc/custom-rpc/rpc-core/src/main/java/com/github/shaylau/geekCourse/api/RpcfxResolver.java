package com.github.shaylau.geekCourse.api;

public interface RpcfxResolver<T> {

    Object resolve(T t);

}

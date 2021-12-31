package com.github.shaylau.geekCourse;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * my define class Loader
 *
 * <description>
 * 自定义一个 Classloader，加载一个 Hello.xlass 文件，
 * 执行 Hello 方法，此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件
 * </description>
 *
 * @author ShayLau
 * @date 2021/12/30 4:26 PM
 */
public class MyClassLoader extends ClassLoader {

    /**
     * 待解码文件-文件位置
     */
    String filePath = System.getProperty("user.dir") + "/01jvm/src/main/java/com/github/shaylau/geekCourse/Hello.xlass";
    /**
     * 二进制文件名
     */
    static String binaryName = "Hello";
    /**
     * 解码文件-方法名
     */
    static String runMethodName = "hello";

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> clazz = myClassLoader.findClass(binaryName);
        Method method = clazz.getDeclaredMethod(runMethodName);
        Object obj = clazz.newInstance();
        method.invoke(obj, null);
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File(filePath);
        InputStream fileInputStream = null;
        byte[] bytes = new byte[1024];
        int length = 0;
        if (file.exists()) {
            try {
                fileInputStream = new FileInputStream(file);
                length = fileInputStream.read(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    assert fileInputStream != null;
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return defineClass(name, decode(bytes), 0, length);
        }
        throw new ClassNotFoundException();
    }

    /**
     * 字节数组解码
     *
     * @param bytes 字节数组
     * @return
     */
    private byte[] decode(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
//            bytes[i] = (byte) (255 - bytes[i] & 0xFF);
            bytes[i] = (byte) ~bytes[i];
        }
        return bytes;
    }
}

package week1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;


public class HelloClassLoader extends ClassLoader {
    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = new HelloClassLoader();
        Class<?> clazz = classLoader.loadClass("Hello");
        Object instance = clazz.getDeclaredConstructor().newInstance();
        Method method = clazz.getMethod("hello");
        method.invoke(instance);
    }


    @Override
    protected Class<?> findClass(String name) {
        InputStream inputStream = this.getClass().getResourceAsStream("/main/resources/Hello.xlass");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len = 0;
        byte[] bytes = new byte[1024];
        try {
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            byte[] classBytes = outputStream.toByteArray();
            byte[] byteClass = decode(classBytes);
            Class<?> helloClass = defineClass(name, byteClass, 0, byteClass.length);
            return helloClass;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    private static byte[] decode(byte[] byteArray) {
        byte[] tragetByte = new byte[byteArray.length];
        int tragetLen = tragetByte.length;
        for (int i = 0; i < tragetLen; i++) {
            tragetByte[i] = (byte) (255 - byteArray[i]);
        }
        return tragetByte;
    }

}

package test.dlmu.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Vector;

public class GenericReflect {
	
	public static void main(String[] args) throws Exception {
		// 获取fun方法
		Method method = GenericReflect.class.getMethod("fun", Vector.class);
		// Type类型是Calss的父类，Method类中提供了获取泛型参数的方法
		Type[] types = method.getGenericParameterTypes();
		// ParameterizedType是Type的子类
		ParameterizedType pType = (ParameterizedType) types[0];
		// 获取泛型的原始类型
		System.out.println(pType.getRawType());
		// 获取泛型的参数变量
		System.out.println(pType.getActualTypeArguments()[0]);
	}

	public static void fun(Vector<Date> vd) {
	}
}
package test.dlmu.testjava;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import sun.misc.Unsafe;

public class TestSizeOf {
	/**对象头部的大小 */
	private static final int OBJECT_HEADER_SIZE = 8;
	/**对象占用内存的最小值*/
	private static final int MINIMUM_OBJECT_SIZE = 8;
	/**对象按多少字节的粒度进行对齐*/
	private static final int OBJECT_ALIGNMENT = 8;
	
	@SuppressWarnings("restriction")
	public static long sizeOf(Object obj) {
		//获得Unsafe实例
		Unsafe unsafe;
        try {
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            unsafe = (Unsafe)unsafeField.get(null);
        } catch (Throwable t) {
            unsafe = null;
        }
        
        //判断对象是否为数组
        if (obj.getClass().isArray()) {
            Class<?> klazz = obj.getClass();
			int base = unsafe.arrayBaseOffset(klazz);
            int scale = unsafe.arrayIndexScale(klazz);
            long size = base + (scale * Array.getLength(obj));
            if ((size % OBJECT_ALIGNMENT) != 0) {
                size += OBJECT_ALIGNMENT - (size % OBJECT_ALIGNMENT);
            }
            return Math.max(MINIMUM_OBJECT_SIZE, size);
        } else {
        	//如果数组对象则迭代遍历该对象的父类，找到最后一个非静态字段的偏移量
            for (Class<?> klazz = obj.getClass(); klazz != null; klazz = klazz.getSuperclass()) {
                long lastFieldOffset = -1;
                for (Field f : klazz.getDeclaredFields()) {
                    if (!Modifier.isStatic(f.getModifiers())) {
                        lastFieldOffset = Math.max(lastFieldOffset, unsafe.objectFieldOffset(f));
                    }
                }
                if (lastFieldOffset > 0) {
                    lastFieldOffset += 1;
                    if ((lastFieldOffset % OBJECT_ALIGNMENT) != 0) {
                        lastFieldOffset += OBJECT_ALIGNMENT - (lastFieldOffset % OBJECT_ALIGNMENT);
                    }
                    return Math.max(MINIMUM_OBJECT_SIZE, lastFieldOffset);
                }
            }
            //该对象没有任何属性
            long size = OBJECT_HEADER_SIZE;
            if ((size % OBJECT_ALIGNMENT) != 0) {
                size += OBJECT_ALIGNMENT - (size % OBJECT_ALIGNMENT);
            }
            return Math.max(MINIMUM_OBJECT_SIZE, size);
        }
    }
	
	public static void main(String[] args) throws InterruptedException {
		MyClass obj = new MyClass();
		obj.rr.add("2222");obj.rr.add(new String("4sdr"));
		System.out.println(TestSizeOf.sizeOf(obj));//输出32
		///Thread.sleep(100000);//阻塞线程，为了使用jmap工具
	}

	public static class MyClass {
		private byte a;
		private int c;
		private boolean d;
		private long e;
		private Object f; 
		private int aa;
		private List rr = new ArrayList();
	}

}

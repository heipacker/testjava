package test.dlmu.protoc.test;

import java.util.Iterator;
import java.util.List;

import test.dlmu.protoc.test.Foo.FooRequest;
import test.dlmu.protoc.test.Foo.FooResponse;
import test.dlmu.protoc.test.Foo.FooService.BlockingInterface;

import com.google.protobuf.BlockingService;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.protobuf.Message;
import com.google.protobuf.ServiceException;
/**
 * protobuf service 阻塞方法测试
 * @author Administrator
 *
 */
public class MyFooTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//查看所有的service
		List<ServiceDescriptor> serviceList = Foo.getDescriptor().getServices();
		for(Iterator<ServiceDescriptor> it = serviceList.iterator(); it.hasNext(); ){
			ServiceDescriptor md = it.next();
			System.out.println(md.getFullName());
		}
		//查看所有的Method
		List<MethodDescriptor> methodList = Foo.FooService.getDescriptor().getMethods();
		for(Iterator<MethodDescriptor> it = methodList.iterator(); it.hasNext(); ){
			MethodDescriptor md = it.next();
			System.out.println(md.getFullName());
		}
		//Subclass Foo and implement its methods as appropriate, then hand instances 
		//of your subclass directly to the RPC server implementation. This is usually easiest, but some consider it less "pure".
		BlockingInterface impl = new FooServiceImpl();
		
		//Implement Foo.Interface and use Foo.newReflectiveService(Foo.Interface) 
		//to construct a Service wrapping it, then pass the wrapper to your RPC implementation.
		BlockingService bs = Foo.FooService.newReflectiveBlockingService(impl);
		
		List<MethodDescriptor> methodList1 = bs.getDescriptorForType().getMethods();
		for(Iterator<MethodDescriptor> it = methodList1.iterator(); it.hasNext(); ){
			MethodDescriptor md = it.next();
			System.out.println(md.getFullName());
		}
		System.out.println("----------------------------------------------------");
		MethodDescriptor method = bs.getDescriptorForType().findMethodByName("Bar");
		//参数 一搬通过FooRequest.parseFrom(data); 从流中读取或者byte[]读取等
		FooRequest.Builder builder = FooRequest.newBuilder();
		//此处我直接赋值
		builder.setField(FooRequest.getDescriptor().findFieldByName("indata"), "2222");
		try {
			//调用服务方法
			Message message = bs.callBlockingMethod(method, null, builder.build());
			System.out.println(message.getField(FooResponse.getDescriptor().findFieldByName("outdata")));
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//此处可以直接调用
		try {
			System.out.println(impl.bar(null, builder.build()).getOutdata());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
		Foo.FooService.Stub stub = Foo.FooService.newStub(null);
	}

}

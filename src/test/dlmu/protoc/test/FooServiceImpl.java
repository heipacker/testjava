package test.dlmu.protoc.test;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;

import test.dlmu.protoc.test.Foo.FooRequest;
import test.dlmu.protoc.test.Foo.FooResponse;
import test.dlmu.protoc.test.Foo.FooService.BlockingInterface;

public class FooServiceImpl implements BlockingInterface {

	@Override
	public FooResponse bar(RpcController controller, FooRequest request) throws ServiceException {
		String indata = request.getIndata();
		System.out.println("FooServiceImpl:" + indata);
		FooResponse.Builder builder = FooResponse.newBuilder();
		builder.setOutdata(indata);
		return builder.build();
	}

}

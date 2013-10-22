package test.dlmu.protoc.test;

import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcChannel;
import com.google.protobuf.RpcController;

public class RpcChannelImpl implements RpcChannel{

	@Override
	public void callMethod(MethodDescriptor method, RpcController controller, Message request, Message responsePrototype, RpcCallback<Message> done) {
		// TODO Auto-generated method stub
		
	}

}

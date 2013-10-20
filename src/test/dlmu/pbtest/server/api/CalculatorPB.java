package test.dlmu.pbtest.server.api;

import test.dlmu.pbtest.proto.Calculator.CalculatorService.BlockingInterface;
/**
 * 过渡接口 
 * 这里主要是屏蔽test.dlmu.pbtest.proto.Calculator.CalculatorService.BlockingInterface
 * 等于使用了别名
 * @author Administrator
 *
 */
public interface CalculatorPB extends BlockingInterface{

}

package test.dlmu.jstorm.example.sequence.spout;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

import test.dlmu.jstorm.example.TpsCounter;
import test.dlmu.jstorm.example.sequence.SequenceTopologyDef;
import test.dlmu.jstorm.example.sequence.bean.Pair;
import test.dlmu.jstorm.example.sequence.bean.PairMaker;
import test.dlmu.jstorm.example.sequence.bean.TradeCustomer;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class SequenceSpout implements IRichSpout {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	public static final Logger LOG = Logger.getLogger(SequenceSpout.class);

	SpoutOutputCollector collector;

	// I special use long not AtomicLong to check competition
	private long tupleId;
	private long succeedCount;
	private long failedCount;

	private TpsCounter tpsCounter;

	private boolean isFinished;

	private boolean isLimited = false;

	public boolean isDistributed() {
		return true;
	}

	public SequenceSpout() {

	}

	public SequenceSpout(boolean isLimited) {
		this.isLimited = isLimited;
	}

	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;

		isFinished = false;

		tpsCounter = new TpsCounter(context.getThisComponentId() + ":" + context.getThisTaskId());

		LOG.info("Finish open");

	}

	private AtomicLong tradeSum = new AtomicLong(0);
	private AtomicLong customerSum = new AtomicLong(0);

	public void emit() {
		Pair trade = PairMaker.makeTradeInstance();
		Pair customer = PairMaker.makeCustomerInstance();

		TradeCustomer tradeCustomer = new TradeCustomer();
		tradeCustomer.setTrade(trade);
		tradeCustomer.setCustomer(customer);

		tradeSum.addAndGet(trade.getValue());
		customerSum.addAndGet(customer.getValue());

		collector.emit(new Values(tupleId, tradeCustomer), Long.valueOf(tupleId));

		tupleId++;

		tpsCounter.count();
	}

	public void nextTuple() {
		if (isLimited == false) {
			emit();

			return;
		}

		if (isFinished == true) {
			return;
		}

		if (tupleId > SequenceTopologyDef.MAX_MESSAGE_COUNT) {
			isFinished = true;
			return;
		}

	}

	public void close() {
		tpsCounter.cleanup();
		LOG.info("Sending :" + tupleId + ", success:" + succeedCount + ", failed:" + failedCount);
		LOG.info("tradeSum:" + tradeSum + ",cumsterSum" + customerSum);
	}

	public void ack(Object id) {
		Long tupleId = (Long) id;

		// if (tupleId != this.tupleId - 1) {
		// LOG.error("Not sync, current :" + this.tupleId + ",ack:" + tupleId);
		// }

		succeedCount++;

		return;
	}

	public void fail(Object id) {

		failedCount++;
		Long failId = (Long) id;
		LOG.info("Failed to handle " + failId);

		return;
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("ID", "RECORD"));
	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub
		LOG.info("Start active");
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		LOG.info("Start deactive");

	}

}
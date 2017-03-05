package com.boleyn.netstudy.lession07;

import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler<LongEvent>{

	@Override
	public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
		System.out.println(event.getValue());
	}

}

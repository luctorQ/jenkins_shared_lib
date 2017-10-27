package com.hastingsdirect.pipeline.history

class HistoryContext {
	def history=[]
	
	void addEvent(String msg) {
		history<<[date:new Date(),msg:msg]
	}	
}

package com.hastingsdirect.pipeline.history

class HistoryContext implements Serializable {
	def history=[]
	
	void addEvent(String msg) {
		history<<[date:new Date(),msg:msg]
	}	
	
	List listEvents() {
		return history;
	}
}

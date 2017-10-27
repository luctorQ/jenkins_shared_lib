class pipelineHistory implements Serializable {
	private List history=[]
	
	def addEvent(String msg) {
		history<<[msg:msg,date:new Date()]
	}
	
	def getHistory() {
		return history
	}
}
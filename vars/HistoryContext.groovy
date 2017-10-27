
class HistoryContext implements Serializable {
	private List history=[]
	
	def addEvent(String msg) {
		history<<[date:new Date(),msg:msg]
	}	
	
	def listEvents() {
		return history;
	}
}

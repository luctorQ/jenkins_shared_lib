class event implements Serializable {
	private List history=[]
	
	def add(String msg) {
		history<<[msg:msg,date:new Date()]
	}
	
	def getList() {
		return history
	}
}
class events implements Serializable {
	private List history=[]
	public events(scr) {
		println 'events pr:'+scr
	}
	def add(String msg) {
		history<<[msg:msg,date:new Date()]
	}
	
	def getList() {
		return history
	}
}
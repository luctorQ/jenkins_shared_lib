class events implements Serializable {
	private List history=[]
	
	def add(String msg) {
		history<<[msg:msg,date:new Date()]
		env.EV=env.EV:?''
		env.EV+=msg+'|'
	}
	
	def getList() {
		return history
	}
}
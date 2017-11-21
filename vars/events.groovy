class events implements Serializable {
	private List history=[]

	def add(String msg) {
		this.addNamed(msg:msg)
	}

	def addNamed(Map params) {
		def eventData=params;
		eventData[date:new Date()]
//		eventData[date:new Date(), type:params[type]?:'GENERAL']
		println ('event Data:'+eventData)
		history<<eventData
	}

	def getList() {
		return history
	}
}
class events implements Serializable {
	private List history=[]

	def add(String msg) {
		this.addNamed(msg:msg)
	}

	def addNamed(Map params=[type:'GENERAL']) {
		def eventData=params
		println ('event Data:'+eventData)
//		eventData[date:new Date(), type:params[type]?:'GENERAL']
		history<<eventData
	}

	def getList() {
		return history
	}
}
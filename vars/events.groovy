class events implements Serializable {
	private List history=[]

	def add(String msg,String type,ref) {
		this.addNamed(msg:msg,type:type,ref:ref)
	}

	def addNamed(Map params) {
		def eventData=params
		eventData[date:new Date(), type:params[type]?:'GENERAL']
		history<<eventData
	}

	def getList() {
		return history
	}
}
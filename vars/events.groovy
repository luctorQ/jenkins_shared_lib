class events implements Serializable {
	private List history=[]

	def add(String msg,String type,ref) {
		this.add([msg:msg,date:new Date(),type:type,ref:ref])
	}

	def add(Map params=[String msg:null,String type='GENERAL',ref:null]) {
		def eventData=params
		eventData[date:new Date()]
		history<<eventData
	}

	def getList() {
		return history
	}
}
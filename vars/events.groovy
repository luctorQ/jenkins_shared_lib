class events implements Serializable {
	private List history=[]

	def add(String msg,String type='Default') {
		this.addNamed(msg:msg,type:type)
	}

	def addNamed(Map params) {
		def eventData=[
			msg:params.msg,
			date:new Date(),
			type:params.type?:'GENERAL',
			ref:params.ref
		];

		//		eventData[date:new Date()]
		//		eventData[date:new Date(), type:params[type]?:'GENERAL']
		println ('event Data:'+eventData)
		history<<eventData
	}

	def getList() {
		return history
	}
}
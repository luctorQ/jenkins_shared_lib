class events implements Serializable {
	private List history=[]

	def add(String msg,String type,ref) {
		Map params=[
				msg:msg,
				date:new Date(),
				type:type,
				ref:ref
			]
		add(params)
	}

	def add(Map params=[type='GENERAL']) {
		def eventData=params
		eventData[date:new Date()]
		history<<eventData
	}

	def getList() {
		return history
	}
}
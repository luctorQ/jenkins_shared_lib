class events implements Serializable {
	private List history=[]
	
	def add(String msg,String type='GENERAL') {
		this.add(msg:msg,type:type)
	}
	
	def add(Map params) {
		def eventData=[
			msg:params.msg,
			date:new Date(),
			type:params.type?:'GENERAL',
			ref:params.ref
		];
		history<<eventData
	}
	
	def addAll(List events) {
		events.each({
			history<<it
		})
	}
		
	def getList() {
		return history
	}
}
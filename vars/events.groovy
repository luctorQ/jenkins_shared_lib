class events implements Serializable {
	private List history=[]
	
    def add(String msg,String type,ref) {
        this.add([msg:msg,date:new Date(),type:type,ref:ref])
    }
    
    def add(Map params=[msg:null,type='GENERAL',ref:null]) {
        def eventData=[msg:msg,date:new Date(),type:type,ref:ref]
        history<<eventData
    }
	
	def getList() {
		return history
	}
}
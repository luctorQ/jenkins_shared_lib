class acme implements Serializable {
	private List history=[]
	private String name
	def setName(value) {
		name = value
	}
	def getName() {
		name
	}
	
	def caution(message) {
		echo "Hello, ${name}! CAUTION: ${message}"
	}
	
	def addEvent(String msg) {
		history<<msg
	}
	
	def getHistory() {
		return history
	}
}
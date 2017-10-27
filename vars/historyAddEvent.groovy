import com.hastingsdirect.pipeline.history.HistoryContext

def call() {
	println 'thisddd:'+this
	if(historyContext==null) {
		historyContext=new HistoryContext()
	}
	historyContext.addEvent(msg)
}
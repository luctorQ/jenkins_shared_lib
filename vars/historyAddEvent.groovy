import com.hastingsdirect.pipeline.history.HistoryContext

def call(String msg) {
	println 'thisddd:'+this
	if(historyContext==null) {
		historyContext=new HistoryContext()
	}
	historyContext.addEvent(msg)
}
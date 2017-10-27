import com.hastingsdirect.pipeline.history.HistoryContext

def call(String event mssg) {
	if(historyContext==null) {
		historyContext=new HistoryContext()
	}
	historyContext.addEvent(msg)
}
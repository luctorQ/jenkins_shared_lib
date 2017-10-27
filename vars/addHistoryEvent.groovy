import com.hastingsdirect.pipeline.history.HistoryContext

def call(String event mssg) {
	historyContext=new HistoryContext()
	historyContext.addEvent(msg)
}
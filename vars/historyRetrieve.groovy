import com.hastingsdirect.pipeline.history.HistoryContext

def call(String event msg) {
	if(historyContext!=null) {
		historyContext.listEvents()
	}
	return []
}
import com.hastingsdirect.pipeline.history.HistoryContext

def call() {
	if(historyContext!=null) {
		historyContext.listEvents()
	}
	return []
}
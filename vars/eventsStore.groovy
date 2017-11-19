def call() {
	println 'eventsStore class:'+this.getClass()
	println 'eventsStore this:'+this
	println 'events this:'+this.events
	println 'events:'+events
	
	println 'allmethods:'+this.metaClass.methods*.name.sort().unique()
	
	println 'env.pipelineEvents:'+env.pipelineEvents
	pipelineEvents.add('inscript add')
	
	events.add('abc')
	env.EVENTS_HISTORY=events.list
	println 'inside events history:'+events.list
}
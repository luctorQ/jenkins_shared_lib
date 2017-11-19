def call() {
	println 'eventsStore class:'+this.getClass()
	println 'eventsStore this:'+this
	println 'events this:'+this.events
	println 'events:'+events
	
	println 'env.pipelineEvents:'+env.pipelineEvents
	events=env.pipelineEvents
	
	events.add('abc')
	env.EVENTS_HISTORY=events.list
	println 'inside events history:'+events.list
}
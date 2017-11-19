def call() {
	
	println 'eventsStore class:'+this.getClass()
	println 'eventsStore this:'+this
	println 'events:'+this.events
	println 'events class:'+this.events.getClass
	
	events.add('abc')
	env.EVENTS_HISTORY=events.list
	println 'inside events history:'+events.list
}
def call() {
	
	println 'eventsStore this:'+this.getClass()
	println 'eventsStore this:'+this
	println 'events:'+this.events
	
	events.add('abc')
	env.EVENTS_HISTORY=events.list
	println 'inside events history:'+events.list
}
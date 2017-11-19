def call() {
	
	println 'eventsStore this:'+this
	events.add('abc')
	env.EVENTS_HISTORY=events.list
	println 'inside events history:'+events.list
}
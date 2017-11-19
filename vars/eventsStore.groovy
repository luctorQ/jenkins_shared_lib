def call() {
	events.add('abc')
	env.EVENTS_HISTORY=events.list
}
import org.boon.json.JsonFactory;

def call() {
	println 'eventsStore class:'+this.getClass()
	println 'eventsStore superclass:'+this.getClass().getSuperclass()
	println 'eventsStore this:'+this
	println 'events this:'+this.events
	println 'events:'+events
	
	println 'binding variables:'+this.getBinding().getVariables()
	println 'binding steps:'+this.getBinding().getVariables().steps
	
	events.add('abc')
	
	List restored=eventsRestore()
	
	restored.addAll(events.list)
	
	println 'restored:'+restored
	
	env.EVENTS_HISTORY=JsonFactory.toJson(restored)
	println 'inside events history:'+events.list
}
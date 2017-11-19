import org.boon.Boon
import org.boon.json.JsonFactory;
import com.hastingsdirect.ep.Event;



def call(eventsList) {
	println 'eventsStore class:'+this.getClass()
	println 'eventsStore superclass:'+this.getClass().getSuperclass()
	println 'eventsStore this:'+this
	println 'events this:'+this.events
	println 'events:'+events
	
	println 'binding variables:'+this.getBinding().getVariables()
	println 'binding steps:'+this.getBinding().getVariables().steps
	
	events.add('abc')
	
	def eventsHistory=env.EVENTS_HISTORY?:'[]'
	def restored=Boon.fromJson(eventsHistory,List.class,Event.class)
	restored=[restored, eventsList,events.list].flatten()
	
	println 'restored:'+restored
	
	env.EVENTS_HISTORY=JsonFactory.toJson(restored)
	
	println('env.events_History after toJson:'+env.EVENTS_HISTORY)
	println 'inside events history:'+events.list
}
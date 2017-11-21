import java.util.Map

import org.boon.Boon
import org.boon.json.JsonFactory;

def createEvent(Map params) {
		def eventData=[
			msg:params.msg,
			date:new Date(),
			type:params.type?:'GENERAL',
			ref:params.ref
		];
		return eventData
	}

	
def call(Map params) {
	def event=createEvent(params)	
	def allEvents=eventsRestore()
	allEvents<<event
	env.EVENTS_HISTORY=JsonFactory.toJson(allEvents)
}

def call(String msg,String type='GENERAL') {
	def event=createEvent(msg:msg,type:type)
	def allEvents=eventsRestore()
	allEvents<<event
	env.EVENTS_HISTORY=JsonFactory.toJson(allEvents)
}

def call(List eventsList) {
	def allEvents=eventsRestore()
	def combinedEvents=[allEvents,eventsList].flatten()
	env.EVENTS_HISTORY=JsonFactory.toJson(combinedEvents)
}
import java.util.Map

import org.boon.Boon
import org.boon.json.JsonFactory;

def convert(val) {
	println 'convert1:'+val
	println 'convert2:'+val?val.getClass():null
	if(val && val instanceof GString) {
		return val.toString()
	}else {
		return val
	}
}

def createEvent(Map params) {
		def eventData=[
			msg:convert(params.msg),
			date:new Date(),
			type:convert(params.type)?:'GENERAL',
			ref:convert(params.ref)
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
import java.util.Map
import org.boon.Boon
import org.boon.json.JsonFactory;

def sanitize(val) {
	if(val && val instanceof GString) {
		return val.toString()
	}else {
		return val
	}
}

def createEvent(Map params) {
	def eventData=[
		msg:sanitize(params.msg),
		date:new Date(),
		type:sanitize(params.type)?:'GENERAL',
		ref:sanitize(params.ref)
	];
	try {
		JsonFactory.toJson(eventData)
	}catch(e) {
		throw new hudson.AbortException('Error serializing event with params:'+params+' exception:'+e)
	}
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
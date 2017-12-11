import com.hastingsdirect.SOAPWebservice

def call(wsdl, action, user, pass) {
  String calledAction = "<tool:clearCache><tool:cache>${action}</tool:cache></tool:clearCache>"
  def soap = new SOAPWebservice(wsdl, calledAction, user, pass)
  def result = soap.callService()
  return result
}

import com.hastingsdirect.SOAPWebservice

def call(wsdl, user, pass) {
  String calledAction = "<tool:clearAllCaches/>"
  def soap = new SOAPWebservice(wsdl, calledAction, user, pass)
  def result = soap.callService()
  return result
}

@Grab('com.github.groovy-wslite:groovy-wslite:1.1.2')
import wslite.soap.*

def call(wsdl, action, user, password) {
  def client = new SOAPClient(wsdl)
  String envelope = ''
  envelope += '<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:soap1="http://guidewire.com/ws/soapheaders" xmlns:tool="http://hastings.com/pc/ws/com/hastings/integration/tools">'
  envelope += "<soap:Header>"
  envelope += "<soap1:authentication>"
  envelope += "<soap1:username>${user}</soap1:username>"
  envelope += "<soap1:password>${password}</soap1:password>"
  envelope += "</soap1:authentication>"
  envelope += "</soap:Header>"
  envelope += "<soap:Body>"
  envelope += action
  envelope += "</soap:Body>"
  envelope += "</soap:Envelope>"

  def response = client.send(connectTimeout:5000, readTimeout:5000, envelope)

  return response
}
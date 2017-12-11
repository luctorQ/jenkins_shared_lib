package com.hastingsdirect

@Grab('com.github.groovy-wslite:groovy-wslite:1.1.2')
import wslite.soap.*
import groovy.xml.*

public class SOAPWebservice implements Serializable {

  SOAPClient client
  String envelope

  public SOAPWebservice(String wsdl, String action, String user, String pass) {
    envelope = envelopeTemplate(user, pass, action)
    client = new SOAPClient(wsdl)
  }

  public String callService() {
    def result = client.send(connectTimeout:5000, readTimeout:5000, envelope)
    return result
  }

  @NonCPS
  def envelopeTemplate(user, pass, action) {
    String envelope = ''
    envelope += '<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:soap1="http://guidewire.com/ws/soapheaders" xmlns:tool="http://hastings.com/pc/ws/com/hastings/integration/tools">'
    envelope += "<soap:Header>"
    envelope += "<soap1:authentication>"
    envelope += "<soap1:username>${user}</soap1:username>"
    envelope += "<soap1:password>${pass}</soap1:password>"
    envelope += "</soap1:authentication>"
    envelope += "</soap:Header>"
    envelope += "<soap:Body>"
    envelope += "${action}"
    envelope += "</soap:Body>"
    envelope += "</soap:Envelope>"

    return envelope as String
  }
}
package com.hastingsdirect.params

import java.net.InetAddress
import com.hastingsdirect.Sql

@Singleton
class GlobalParameters implements Serializable {

  private LinkedHashMap params;

  static void main() {
    Sql sqlfn = new Sql()
    sqlfn.createConnection()
    LinkedHashMap params = [:]

    def localhost = InetAddress.getLocalHost();

    def globalConfigMap = sqlfn.getGlobalConfigByServerName("Global")
    def serverConfigMap = sqlfn.getGlobalConfigByServerName(localhost)

    for (row in globalConfigMap) {
      params["${row.Name}"] = row.Value
    }

    for (row in serverConfigMap) {
      params["${row.Name}"] = row.Value
    }

    GlobalParameters p = new GlobalParameters()
    p.setParams(params);

  }
  
  void setParams(p) {
    this.params = p
  }

  LinkedHashMap getParams() {
    return this.params
  }
}

import com.hastingsdirect.Sql

ArrayList call() {
  Sql sqlUtil = new Sql()

  sqlUtil.createConnection()
  def result = sqlUtil.getBackupSets()
  sqlUtil.close()
  sqlUtil = null

  return result

}
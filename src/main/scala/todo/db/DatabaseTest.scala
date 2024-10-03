package todo.db
import todo.db.DatabaseConnector

object DatabaseTest {
  def testConnection():Unit = {
    try {
      val connection = DatabaseConnector.getConnection
      println("Connection successful!")
      connection.close()
    }catch{
      case e:Exception =>
        println("Connection failed!")
        e.printStackTrace()

    }
  }
}

package todo.db

import todo.Task

import java.sql.{Connection, DriverManager, PreparedStatement}

object DatabaseConnector {
  private val url = "jdbc:postgresql://localhost:5432/todolist_db"
  private val username = "postgres"
  private val password = "1221"

  def getConnection: Connection = {
    DriverManager.getConnection(url,username,password)
  }

    

  }





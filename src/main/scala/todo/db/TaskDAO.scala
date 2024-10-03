package todo.db

import todo.Task
import todo.db.DatabaseConnector

import java.sql.PreparedStatement
import java.sql.ResultSet


object TaskDAO {
  def addTaskToDBFromBeginning(task: Task):Unit = {
    val insertSQL= "INSERT INTO tasks(id, description, is_completed) VALUES(?,?,?)"

    val connection = DatabaseConnector.getConnection
    val preparedStatement:PreparedStatement = connection.prepareStatement(insertSQL)

    preparedStatement.setInt(1, task.id)
    preparedStatement.setString(2, task.description)
    preparedStatement.setBoolean(3, task.isCompleted)

    preparedStatement.executeUpdate()

    preparedStatement.close()
    connection.close()


  }

  def clearTasks():Unit = {

    val dropSQL = "TRUNCATE TABLE tasks RESTART IDENTITY"
    val connection = DatabaseConnector.getConnection
    var statement:java.sql.Statement = null

    try{
      statement = connection.createStatement()
      statement.executeUpdate(dropSQL)
    }finally{
      if (statement != null) statement.close()
      if (connection != null) connection.close()
    }
  }

  def getAllTasks():List[Task] ={
    val selectSQL = "SELECT id, description, is_completed FROM tasks"

    val connection = DatabaseConnector.getConnection
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery(selectSQL)

    try {
      val tasks = Iterator.continually((resultSet, resultSet.next())).takeWhile(_._2)
        .map { case (rs, _) =>
          Task(
            rs.getInt("id"),
            rs.getString("description"),
            rs.getBoolean("is_completed")
          )
        }.toList
      tasks
    } finally {
      resultSet.close()
      statement.close()
      connection.close()
      }

  }

  def addNewTaskToDb(task: Task): Unit = {
    val connection = DatabaseConnector.getConnection

    val setvalSQL = "SELECT setval('tasks_id_seq',(SELECT MAX(id) FROM tasks) , true)"
    val insertSQL = "INSERT INTO tasks (description, is_completed) VALUES (?,?)"



    try {
      val preparedStatement: PreparedStatement = connection.prepareStatement(insertSQL)
      val statement = connection.createStatement()

      preparedStatement.setString(1, task.description)
      preparedStatement.setBoolean(2, task.isCompleted)

      preparedStatement.execute()
      statement.execute(setvalSQL)

      statement.close()
      preparedStatement.close()

    } finally {

      connection.close()

    }
  }



}

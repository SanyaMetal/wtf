package todo

import java.io.{File, PrintWriter}
import scala.io.Source
import todo.db.TaskDAO


class TaskManager {
  private var tasks:List[Task] = List()
  private var nextId: Int = 1

  def addTask(description: String): Task = {
    val task = Task(nextId, description)
    tasks = tasks :+ task
    nextId += 1
    task
  }

  def removeTask(id: Int): Option[Task] = {
    val (toRemove, remaining) = tasks.partition(_.id ==id)
    nextId = 1
    tasks = remaining.map{task=>
     task.id = nextId
     nextId += 1
     task
   }


    toRemove.headOption

  }

  def completeTask(id:Int): Option[Task] = {
    tasks.find(_.id ==id).map{task =>
      val updatedTask = task.copy(isCompleted = true)
      tasks = tasks.map(t => if (t.id==id) updatedTask else t)
      updatedTask
    }
  }
  def listTasks(): List[Task] = tasks

  def saveToFile (filename: String): Unit = {
    val writer = new PrintWriter(File(filename))
    tasks.foreach{task=>
      writer.println(s"${task.id},${task.description},${task.isCompleted}")
    }
    writer.close()

  }

  def loadFromFile(filename: String): Unit = {
    val source = Source.fromFile(filename)
    tasks = source.getLines().toList.map{line =>
      val splitLine = line.split(",")
      val id:Int = splitLine(0).trim.toInt
      val description: String = splitLine(1)
      val isCompleted:Boolean = splitLine(2).trim.toBoolean
      Task(id, description, isCompleted)
    }
    source.close()
  }

  def saveTasksToDb(): Unit = tasks.foreach(task=>TaskDAO.addTaskToDBFromBeginning(task))

  def getTasksFromDb(): List[Task] =  {
    tasks = TaskDAO.getAllTasks()
    tasks
  }  
  
  def clearTasksFromDb():Unit = TaskDAO.clearTasks()

  def continueTasksToDb(): Unit = {
    tasks.foreach(task => TaskDAO.addNewTaskToDb(task))    
    
  }
    




}

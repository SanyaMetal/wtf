package todo

import scala.io.StdIn
import todo.db.DatabaseTest

object TodoApp extends App {

  DatabaseTest.testConnection()



    val taskManager = new TaskManager
    var running = true

    while (running){
      println("\n1. Добавить задачу")
      println("2. Удалить задачу")
      println("3. Пометить задачу как выполненную")
      println("4. Показать задачи")
      println("5. Сохранить задачи")
      println("6. Загрузить задачи")
      println("7. Сохранить задачи в БД")
      println("8. Загрузить задачи из БД")
      println("9. Очистить БД")
      println("10. Внести задачи в БД")
      println("\n0. Выход")
      
      



      StdIn.readLine("Выберите действие: ") match {
        case "1" =>
          val description = StdIn.readLine("Введите описание задачи: ")
          val task = taskManager.addTask(description)
          println(s"Добавлена задача $task")

        case "2" =>
          val id = StdIn.readLine("Введите ID задачи для удаления: ").toInt
          taskManager.removeTask(id) match{
            case Some(task) => println(s"Удалена задача: $task")
            case None => println("Задача не найдена")
          }

        case "3" =>
          val id = StdIn.readLine("Введите ID задачи для выполнения: ").toInt
          taskManager.completeTask(id) match{
            case Some(task) =>  println(s"Задача выполнена: $task")
            case None => println("Задача не найдена")
          }

        case "4" =>
          val tasks = taskManager.listTasks()
          if (tasks.isEmpty) println("Нет задач! ")
          else tasks.foreach(task => println(task))


        case "5" =>
          val file = "text.txt"
          taskManager.saveToFile(file)
          println(s"Сохранено в файл: $file")


        case "6" =>
          val file = "text.txt"
          taskManager.loadFromFile(file)
          println(s"Задачи загружены! ")

        case "7" =>
          try{
            taskManager.saveTasksToDb()
            println("Задачи добавлены в БД!")
          } catch {
            case e:Exception => println(s"Ошибка: ${e.getMessage}")
          }

        case "8" =>
          try {
            taskManager.getTasksFromDb()
            println("Задачи загружены из БД!")
          }catch{
            case e:Exception =>println(s"Ошибка: ${e.getMessage}")
          }

        case "9" =>
          try{
            taskManager.clearTasksFromDb()
            println("Задачи удалены из БД!")
          }catch{case e:Exception => println(s"Ошибка: ${e.getMessage}")
          }

        case "10" =>
          try{
            taskManager.continueTasksToDb()
            println("Новые задачи внесены в БД!")
          }catch{
            case e:Exception => println(s"Ошибка: ${e.getMessage}")
          }










      
          
          

        case "0" =>
          println("Вы действительно хотите выйти?")
          println("1. Выйти")
          println("2. Остаться")

          StdIn.readLine("Выберите действие: ") match{
            case "1" =>  running = false
            case "2" =>
          }



        case _ =>
          println("Неверный ввод, попробуйте снова.")


      }
    }
  //}
}

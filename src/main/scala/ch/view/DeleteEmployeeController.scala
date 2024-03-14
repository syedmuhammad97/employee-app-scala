package ch.view

import ch.MainApp
import ch.MainApp.stage
import ch.util.Databases.{AutoCloseableOps, deleteEmployee}
import ch.util.{Databases, DisplayMessage}
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, ButtonType, ComboBox, TextField}
import scalafx.scene.image.{Image, ImageView}
import scalafxml.core.macros.sfxml

import java.io.{File, FileOutputStream, InputStream}
import java.sql.{Connection, DriverManager, PreparedStatement, ResultSet, SQLException, Statement}

@sfxml
class DeleteEmployeeController(
                              val employeeID: ComboBox[Int],
                              val btSubmit: Button,
                              val btExit: Button,
                              ) {

  var fileName: String = _
  var imgFile: String = _

  val dm = new DisplayMessage

  val URL = "jdbc:mysql://localhost:3306/mydb"
  val USER = "root"
  val PASSWORD = ""
  var result: Int = _

  var connect: Connection = _
  var stmt: Statement = _
  var sql: String = _
  var rs: ResultSet = _

  def getResult: Int = result

  def setResult(result: Int): Unit = this.result = result

  def getRs: ResultSet = rs

  def setRs(rs: ResultSet): Unit = this.rs = rs

  def showMainMenu(event: ActionEvent): Unit = {
    MainApp.showMainMenu()
  }

/*
  //causes null pointer exception
  def previewButton(event: ActionEvent): Unit = {
    try{
      val id = employeeID.value.value
    //  viewEmployee(id)
    }catch{
      case ex: Exception =>
        dm.alert()
        ex.printStackTrace()
    }
  }

 */

  def submitButton(event: ActionEvent): Unit = {
    val dm = new DisplayMessage
    try {
      val id = employeeID.value.value
      // ask user delete confirmation, if user click okay, employee record will be deleted
      val alert = new Alert(AlertType.Confirmation) {
        initOwner(stage)
        title = "Confirmation Dialog"
        headerText = "Delete Confirmation"
        contentText = s"Are you sure you want to delete Employee $id?"
      }
      val result: Option[ButtonType] = alert.showAndWait()
      result match {
        case Some(ButtonType.OK) =>
          try {
            Databases.deleteEmployee(id)
            println("Deleted Successfully")
            dm.deleteSuccessfully(id)
          } catch {
            case e: SQLException =>
              e.printStackTrace()
          }
      }
    } catch {
      case e: Exception =>
        dm.alert()
        e.printStackTrace()
    }
  }

  /*
  //problem, null pointer exception
  def viewEmployee(id: Int): Unit = {

    if (Databases.checkIDFull(id)) {
      // if id is from FullTime Employee, display FullTime record
      Databases.viewEmployee(id)
      while (Databases.getRs.next()) {
        tfDepartment.text = Databases.getRs.getString("department")
        tfFloor.text = Databases.getRs.getString("floor")
      }
      Databases.viewFTEmployee(id)
      while (Databases.getRs.next()) {
        tfName.text = Databases.getRs.getString("name")
        tfWage.text = Databases.getRs.getInt("wages").toString
        tfComm.text = Databases.getRs.getInt("comm").toString
        tfTotalSalary.text = Databases.getRs.getInt("totalSalary").toString
        tfHours.text = ""
        tfFood.text = ""

        val is: InputStream = Databases.retrieve(id)
        try {
          val os = new FileOutputStream(new File("image.jpg"))
          val b = new Array[Byte](1024)
          var size = 0
          while ( {
            size = is.read(b)
            size != -1
          }) os.write(b, 0, size)
          val image = new Image("file:image.jpg", employeeImage.fitWidth(), employeeImage.fitHeight(), true, true)
          employeeImage.image = image
        } catch {
          case ex: Exception =>
            dm.alert()
            ex.printStackTrace()
        }
      }
    }

    if (Databases.checkIDPart(id)) {
      // if id is from PartTime Employee, display PartTime record
      Databases.viewEmployee(id)
      while (Databases.getRs.next()) {
        tfDepartment.text = Databases.getRs.getString("department")
        tfFloor.text = Databases.getRs.getString("floor")
      }
      Databases.viewPTEmployee(id)
      while (Databases.getRs.next()) {
        tfName.text = Databases.getRs.getString("name")
        tfHours.text = Databases.getRs.getInt("total_working_hour").toString
        tfFood.text = Databases.getRs.getInt("food_allowance").toString
        tfTotalSalary.text = Databases.getRs.getInt("monthly_salary").toString
        tfWage.text = ""
        tfComm.text = ""

        val is: InputStream = Databases.retrieve(id)
        try {
          val os = new FileOutputStream(new File("image.jpg"))
          val b = new Array[Byte](1024)
          var size = 0
          while ( {
            size = is.read(b)
            size != -1
          }) os.write(b, 0, size)
          val image = new Image("file:image.jpg", employeeImage.fitWidth(), employeeImage.fitHeight(), true, true)
          employeeImage.image = image
        } catch {
          case ex: Exception =>
            dm.alert()
            ex.printStackTrace()
        }
      }
    }
  }

   */

  //var list = ObservableBuffer[Integer]

  //i dont know why it wont work when I used Databases class
  //it will give me NullPointerException in while(database.getRs().next())
  //so I decided to just use the database in this class to make it work
  try {
    DriverManager.getConnection(URL, USER, PASSWORD).autoClose { connect =>
      val sql = "SELECT * FROM `department` WHERE `Employee_Id`"
      val pstmt: PreparedStatement = connect.prepareStatement(sql)
      val rs: ResultSet = pstmt.executeQuery()

      val list: ObservableBuffer[Int] = ObservableBuffer()
      while (rs.next()) {
        val id: Int = rs.getInt("Employee_Id")
        list += id
      }
      employeeID.items = list
    }
  } catch {
    case ex: Exception =>
      dm.alert()
      ex.printStackTrace()
  }



}

package ch.view

import ch.MainApp
import ch.model.{Department, FullTime}
import ch.util.Databases.{AutoCloseableOps, URL, USER}
import ch.util.{Databases, DisplayMessage, Validation}
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, ComboBox, TextField}
import scalafx.scene.image
import scalafx.scene.image.{Image, ImageView}
import scalafx.stage.{FileChooser, Stage}
import scalafxml.core.macros.sfxml

import java.io.File
import java.sql.{Connection, DriverManager, PreparedStatement, ResultSet, Statement}

@sfxml
class FullTimeModificationController(
                                      val newDepartment: ComboBox[String],
                                      val tfNewName: TextField,
                                      val tfNewWage: TextField,
                                      val tfNewComm: TextField,
                                      val employeeImage: ImageView,
                                      val btSubmit: Button,
                                      val btExit: Button,
                                      val btChoosePhoto: Button,
                                      val employeeID: ComboBox[Int],
                                      val btPreview: Button
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

  val employeeDeptList: ObservableBuffer[String] = ObservableBuffer("Engineer", "Marketing", "MIS")

  def showMainMenu(event: ActionEvent): Unit = {
    MainApp.showModify()
  }

  def choosePhotoButton(event: ActionEvent): Unit = {
    println("pressing choose button")
    val stage = new Stage
    val chooser = new FileChooser
    val fileDirectory = new File("src/main/resources/ch/image")
    chooser.setInitialDirectory(fileDirectory)
    chooser.setTitle("pick a picture")
    val file = chooser.showOpenDialog(stage)
    if (file != null) {
      fileName = file.getName

      val imgFile = new File("src/main/resources/ch/image", fileName)
      //val imgFile = "\\image\\"+fileName
      val imgUrl = imgFile.toURI.toURL.toString
      val img = new Image(imgUrl)
      //val img = new Image(imgFile)
      employeeImage.setImage(img)
    }
  }

  def submitButton(event: ActionEvent): Unit = {

    val imgFile = new File(fileName)
    val file_name = imgFile.getName
    val imgUrl = imgFile.toURI.toURL.toString
    val img = new Image(imgUrl)

    try {
      var floor: String = null
      val cat: String = "part-time"
      val id: Int = employeeID.value.value
      if (!Validation.idValidation(id)) {
        // If id is invalid, display error message
        dm.alertID()
      } else {
        val dept = newDepartment.getValue
        floor = dept match {
          case "Engineer" => "8th Floor"
          case "MIS" => "3rd Floor"
          case "Marketing" => "5th Floor"
          case _ => null
        }
        val name: String = tfNewName.text.value
        if (!Validation.nameValidation(name)) {
          // If name is invalid, display error message
          dm.alertName()
        } else {
          val wage: String = tfNewWage.text.value
          val wage1: Int = wage.toInt
          if (!Validation.wagesValidation(wage1)) {
            // If wage is invalid, display error message
            dm.alertWage()
          } else {
            val comm: String = tfNewComm.text.value
            val comm1: Int = comm.toInt
            if (!Validation.commValidation(comm1)) {
              // If comm is invalid, display error message
              dm.alertComm()
            } else {
              val department: Department = new Department(dept, floor)
              val ft: FullTime = new FullTime(department, id, name, cat, wage1, comm1)
              Databases.modifyEmployeeFT(department, ft, file_name)
              dm.modifySuccessfully()
            }
          }
        }
      }
    } catch {
      case ex: NumberFormatException =>
        dm.alert()
        ex.printStackTrace()
    }
  }

  def previewButton(event: ActionEvent): Unit = {
    MainApp.showFullTimeRecord()
  }

  // Initialize ComboBox
  newDepartment.promptText = "Choose Your Department"
  newDepartment.items = employeeDeptList


  //var list = ObservableBuffer[Integer]

  //i dont know why it wont work when I used Databases class
  //it will give me NullPointerException in while(database.getRs().next())
  //so I decided to just use the database in this class to make it work
  try {
    DriverManager.getConnection(URL, USER, PASSWORD).autoClose { connect =>
      val sql = "SELECT id FROM `full-time`"
      val pstmt: PreparedStatement = connect.prepareStatement(sql)
      val rs: ResultSet = pstmt.executeQuery()

      val list: ObservableBuffer[Int] = ObservableBuffer()
      while (rs.next()) {
        val id: Int = rs.getInt("id")
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

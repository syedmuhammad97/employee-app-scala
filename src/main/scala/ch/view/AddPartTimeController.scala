package ch.view

import ch.MainApp
import ch.util.{Databases, DisplayMessage, Validation}
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, ComboBox, TextField}
import scalafx.scene.image
import scalafx.scene.image.{Image, ImageView}
import scalafx.stage.{FileChooser, Stage}
import scalafxml.core.macros.sfxml
import ch.model.{Department, PartTime}

import java.io.File

@sfxml
class AddPartTimeController(val tfID: TextField,
                            val employeeDept: ComboBox[String],
                            val tfName: TextField,
                            val tfTotalWorkingHour: TextField,
                            val tfFoodAllowance: TextField,
                            val image: ImageView,
                            val submit: Button,
                            val exit: Button,
                            val choosePhoto: Button
                           ) {

  var fileName: String = _
  var imgFile: String = _

  val employeeDeptList: ObservableBuffer[String] = ObservableBuffer("Engineer", "Marketing", "MIS")


  def showMainMenu(event: ActionEvent): Unit = {
    MainApp.showMainMenu()
  }

  def submitButton(event: ActionEvent): Unit = {
    val dm = new DisplayMessage
    //stuck here, find out how to find fileName instead of full URL
    val imgFile = new File(fileName)
    val file_name = imgFile.getName
    val imgUrl = imgFile.toURI.toURL.toString
    val img = new Image(imgUrl)
    //val database = new Databases

    try {
      var floor: String = null
      val cat = "fulltime"
      val id: String = tfID.getText

      val id1 = id.toInt
      if (Databases.checkID(id1)) {
        // if id exists, ask the user to try again
        dm.alertCheckID()
      } else {
        if (!Validation.idValidation(id1)) {
          // if the user put an invalid id, ask the user to try again
          dm.alertID()
        } else {
          val dept = employeeDept.getValue
          floor = dept match {
            case "Engineer" => "8th Floor"
            case "MIS" => "3rd Floor"
            case "Marketing" => "5th Floor"
            case _ => null
          }
          val name = tfName.getText
          if (!Validation.nameValidation(name)) {
            // if the user put an invalid name, ask the user to try again
            dm.alertName()
          } else {
            val totalWorkingHr = tfTotalWorkingHour.getText
            val totalWorkingHour1 = totalWorkingHr.toInt
            if (!Validation.wagesValidation(totalWorkingHour1)) {
              // if the user put an invalid totalWorkingHour, ask the user to try again
              dm.alertWorkingHour()
            } else {
              val foodAllowance = tfFoodAllowance.getText
              val foodAllowance1 = foodAllowance.toInt
              if (!Validation.commValidation(foodAllowance1)) {
                // if the user put an invalid comm, ask the user to try again
                dm.alertComm()
              } else {
                val department = new Department(dept, floor)
                val pt = new PartTime(department, id1, name, cat, totalWorkingHour1, foodAllowance1)
                Databases.PTEmployee(department, pt, file_name)
                new DisplayMessage().addSuccessfully()
              }
            }
          }
        }
      }
    } catch {
      case _: NumberFormatException =>
        dm.alert()
    }
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
      //Cannot resolve symbol fileName issue
      fileName = file.getName

      val imgFile = new File("src/main/resources/ch/image", fileName)
      //val imgFile = "\\image\\"+fileName
      val imgUrl = imgFile.toURI.toURL.toString
      val img = new Image(imgUrl)
      //val img = new Image(imgFile)
      image.setImage(img)
    }

  }

  // Initialize ComboBox
  employeeDept.promptText = "Choose Your Department"
  employeeDept.items = employeeDeptList


}

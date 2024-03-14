package ch.view

import ch.MainApp
import ch.model.{Department, FullTime}
import ch.util.Databases.FTEmployee
import ch.util.{Databases, DisplayMessage, Validation}
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, ComboBox, TextField}
import scalafx.scene.image.{Image, ImageView}
import scalafx.stage.{FileChooser, Stage}
import scalafxml.core.macros.sfxml

import java.io.File
import java.lang.reflect.InvocationTargetException
import java.net.URL
import java.util.ResourceBundle

@sfxml
class AddFullTimeController(val tfID: TextField,
                            val employeeDept: ComboBox[String],
                            val tfName: TextField,
                            val tfWage: TextField,
                            val tfCommision: TextField,
                            val image: ImageView,
                            val submit: Button,
                            val exit: Button,
                            val choosePhoto: Button
) {

  var fileName: String = _
  var imgFile: String = _

  val employeeDeptList: ObservableBuffer[String] = ObservableBuffer("Engineer", "Marketing", "MIS")

  def showMainMenu(event: ActionEvent): Unit = {
    println("pressing back button")
    MainApp.showMainMenu()
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
      image.setImage(img)
    }

    //FTEmployee(dept, ft, fileName)

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
            val wage = tfWage.getText
            val wage1 = wage.toInt
            if (!Validation.wagesValidation(wage1)) {
              // if the user put an invalid wage, ask the user to try again
              dm.alertWage()
            } else {
              val comm = tfCommision.getText
              val comm1 = comm.toInt
              if (!Validation.commValidation(comm1)) {
                // if the user put an invalid comm, ask the user to try again
                dm.alertComm()
              } else {
                val department = new Department(dept, floor)
                val ft = new FullTime(department, id1, name, cat, wage1, comm1)
                Databases.FTEmployee(department, ft, file_name)
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


  // Initialize ComboBox
  employeeDept.promptText = "Choose Your Department"
  employeeDept.items = employeeDeptList

}

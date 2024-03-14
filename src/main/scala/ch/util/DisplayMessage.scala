package ch.util

import ch.MainApp.stage

import java.sql.SQLException
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.ButtonType
import ch.util.Databases

class DisplayMessage {

  def alertID(): Unit = {
    val alert = new Alert(AlertType.Error) {
      title = "Error Message"
      headerText = "There is an error with the request"
      contentText = "ID cannot be less than 1001 and more than 9999! Please try again."
    }

    alert.showAndWait()
  }

  def alertName(): Unit = {
    val alert = new Alert(AlertType.Error) {
      title = "Error Message"
      headerText = "There is an error with the request"
      contentText = "Name cannot contain symbol or number"
    }

    alert.showAndWait()
  }

  def alertWage(): Unit = {
    val alert = new Alert(AlertType.Error) {
      title = "Error Message"
      headerText = "There is an error with the request"
      contentText = "Wages cannot be less than 0"
    }

    alert.showAndWait()
  }

  def alertComm(): Unit = {
    val alert = new Alert(AlertType.Error) {
      title = "Error Message"
      headerText = "There is an error with the request"
      contentText = "Commission cannot be less than 0"
    }

    alert.showAndWait()
  }

  def alert(): Unit = {
    val alert = new Alert(AlertType.Error) {
      title = "Error Message"
      headerText = "There is an error with the request"
      contentText = "Ooops, there was an error with your input! Please try again."
    }

    alert.showAndWait()
  }

  def alertCheckID(): Unit = {
    val alert = new Alert(AlertType.Error) {
      title = "Error Message"
      headerText = "There is an error with the request"
      contentText = "Sorry, this ID has already exist, please try again."
    }

    alert.showAndWait()
  }

  def alertWorkingHour(): Unit = {
    val alert = new Alert(AlertType.Error) {
      title = "Error Message"
      headerText = "There is an error with the request"
      contentText = "Total Working Hours cannot be less than 0"
    }

    alert.showAndWait()
  }

  def alertFoodAllowance(): Unit = {
    val alert = new Alert(AlertType.Error) {
      title = "Error Message"
      headerText = "There is an error with the request"
      contentText = "Food Allowance cannot be less than 0"
    }

    alert.showAndWait()
  }

  def alertSQLEx(): Unit = {
    val alert = new Alert(AlertType.Error) {
      title = "Error Message"
      headerText = "There is an error with the request"
      contentText = "SQL Exception, please try again"
    }

    alert.showAndWait()
  }

  def modifySuccessfully(): Unit = {
    val alert = new Alert(AlertType.Information) {
      title = "Information Dialog"
      headerText = ""
      contentText = "Employee has been modify successfully!"
    }

    alert.showAndWait()
  }

  def addSuccessfully(): Unit = {
    val alert = new Alert(AlertType.Information) {
      title = "Information Dialog"
      headerText = ""
      contentText = "Employee has been added successfully!"
    }

    alert.showAndWait()
  }

  def deleteSuccessfully(id: Int): Unit = {
    val alert = new Alert(AlertType.Information) {
      title = "Delete Dialog"
      headerText = ""
      contentText = s"Employee $id has been deleted successfully!"
    }

    alert.showAndWait()
  }

}
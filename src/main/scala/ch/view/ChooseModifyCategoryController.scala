package ch.view

import ch.MainApp
import scalafx.event.ActionEvent
import scalafxml.core.macros.sfxml

@sfxml
class ChooseModifyCategoryController {

  def showMainMenu(event: ActionEvent): Unit = {
    MainApp.showMainMenu()
  }

  def fullTimeButton(event: ActionEvent): Unit = {
    MainApp.modifyFullTime()
  }

  def partTimeButton(event: ActionEvent): Unit = {
    MainApp.modifyPartTime()
  }

}

package ch.view

import ch.MainApp
import scalafx.application.Platform
import scalafx.event.ActionEvent
import scalafxml.core.macros.sfxml

@sfxml
class ChooseCategoryController {

  def showMainMenu(event: ActionEvent): Unit = {
    MainApp.showMainMenu()
  }

  def fullTimeButton(event: ActionEvent) : Unit = {
    MainApp.addFullTime()
  }

  def partTimeButton(event: ActionEvent): Unit = {
    MainApp.addPartTime()
  }

}

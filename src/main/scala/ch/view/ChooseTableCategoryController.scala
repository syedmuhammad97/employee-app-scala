package ch.view

import ch.MainApp
import scalafx.event.ActionEvent
import scalafxml.core.macros.sfxml

@sfxml
class ChooseTableCategoryController {

  def showMainMenu(event: ActionEvent): Unit = {
    MainApp.showMainMenu()
  }

  def fullTimeButton(event: ActionEvent): Unit = {
    MainApp.showFullTimeRecord()
  }

  def partTimeButton(event: ActionEvent): Unit = {
    MainApp.showPartTimeRecord()
  }



}

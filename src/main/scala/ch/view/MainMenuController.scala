package ch.view

import ch.MainApp
import scalafx.Includes._
import scalafx.application.Platform
import scalafx.event.ActionEvent
import scalafx.scene.control.Button
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml

@sfxml
class MainMenuController(
                          private val register: Button,
                          private val modify: Button,
                          private val delete: Button,
                          private val display: Button,
                          private val exit: Button,
                          private val btMedia: Button
                        ) {

  def showRegister(event: ActionEvent): Unit = {
    MainApp.showRegister()
  }

  def showModify(event: ActionEvent): Unit = {
    MainApp.showModify()
  }

  def showDelete(event: ActionEvent): Unit = {
    MainApp.showDelete()
  }

  def showMedia(event: ActionEvent): Unit = {
    MainApp.showMedia()
  }

  def showDisplay(event: ActionEvent): Unit = {
    MainApp.showDisplay()
  }

  def exitButton(event: ActionEvent): Unit = {
    Platform.exit()
  }


}

package ch.view

import ch.MainApp
import scalafx.event.ActionEvent
import scalafx.scene.control.Button
import scalafx.scene.media.{Media, MediaPlayer, MediaView}
import scalafx.stage.{FileChooser, Stage}
import scalafx.util.Duration
import scalafxml.core.macros.sfxml

import java.io.File

@sfxml
class EmployeeMediaPlayerController(
                                   var player: MediaPlayer,
                                   val video: MediaView,
                                   val btBack: Button

                                   ) {

  def showMainMenu(event: ActionEvent): Unit = {
    MainApp.showMainMenu()
  }

  def videoAction(event: ActionEvent): Unit = {
    val stage = new Stage
    val chooser = new FileChooser
    val fileDirectory = new File("src/main/resources/ch/video")
    chooser.setInitialDirectory(fileDirectory)
    chooser.setTitle("pick a video")

    val filePath = chooser.showOpenDialog(stage)
    val media = new Media(filePath.toURI.toString)
    player = new MediaPlayer(media)
    player.setAutoPlay(true)
    video.setMediaPlayer(player)

  }

  def playAction(event: ActionEvent): Unit = {
    player.play()
  }

  def stopAction(event: ActionEvent): Unit = {
    player.pause()
  }

  def rewindAction(event: ActionEvent) : Unit = {
    player.seek(Duration.Zero)
  }

}

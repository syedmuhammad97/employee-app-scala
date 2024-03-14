package ch

import ch.view.{AddFullTimeController, AddPartTimeController, ChooseCategoryController, ChooseModifyCategoryController, ChooseTableCategoryController, DeleteEmployeeController, EmployeeMediaPlayerController, FullTimeModificationController, FullTimeTableController, MainMenuController, PartTimeModificationController, PartTimeTableController}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{FXMLLoader, FXMLView, NoDependencyResolver}
import javafx.{scene => jfxs}

object MainApp extends JFXApp {
  // transform path of RootLayout.fxml to URI for resource location.
  val rootResource = getClass.getResource("view/RootLayout.fxml")
  // initialize the loader object.
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  // Load root layout from fxml file.
  loader.load();
  // retrieve the root component BorderPane from the FXML
  val roots = loader.getRoot[jfxs.layout.BorderPane]
  // initialize stage
  stage = new PrimaryStage {
    title = "EmployeeApp"
    minWidth = 600
    minHeight = 400
    scene = new Scene {
      root = roots
    }
  }

  def showMainMenu() = {
    val resource = getClass.getResource("view/MainMenu.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    val controller = loader.getController[MainMenuController#Controller]
    this.roots.setCenter(roots)
  }

  def showRegister(): Unit = {
    val resource = getClass.getResource("view/ChooseCategoryTest.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    val controller = loader.getController[ChooseCategoryController#Controller]
    this.roots.setCenter(roots)
  }

  def addFullTime(): Unit = {
    val resource = getClass.getResource("view/AddFullTime.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    val controller = loader.getController[AddFullTimeController#Controller]
    this.roots.setCenter(roots)
  }

  def addPartTime(): Unit = {
    val resource = getClass.getResource("view/AddPartTime.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    val controller = loader.getController[AddPartTimeController#Controller]
    this.roots.setCenter(roots)
  }

  def showModify(): Unit = {
    val resource = getClass.getResource("view/ChooseModifyCategory.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    val controller = loader.getController[ChooseModifyCategoryController#Controller]
    this.roots.setCenter(roots)
  }

  def modifyFullTime(): Unit = {
    val resource = getClass.getResource("view/FullTimeModification.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    val controller = loader.getController[FullTimeModificationController#Controller]
    this.roots.setCenter(roots)
  }

  def modifyPartTime(): Unit = {
    val resource = getClass.getResource("view/PartTimeModification.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    val controller = loader.getController[PartTimeModificationController#Controller]
    this.roots.setCenter(roots)
  }

  def showDelete(): Unit = {
    val resource = getClass.getResource("view/Delete2.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    val controller = loader.getController[DeleteEmployeeController#Controller]
    this.roots.setCenter(roots)
  }

  def showMedia(): Unit = {
    val resource = getClass.getResource("view/MediaPlayer.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    val controller = loader.getController[EmployeeMediaPlayerController#Controller]
    this.roots.setCenter(roots)
  }

  def showDisplay(): Unit = {
    val resource = getClass.getResource("view/ChooseTableCategory.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    val controller = loader.getController[ChooseTableCategoryController#Controller]
    this.roots.setCenter(roots)
  }

  def showFullTimeRecord(): Unit = {
    val resource = getClass.getResource("view/FullTimeTable.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    val controller = loader.getController[FullTimeTableController#Controller]
    this.roots.setCenter(roots)
  }

  def showPartTimeRecord(): Unit = {
    val resource = getClass.getResource("view/PartTimeTable.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    val controller = loader.getController[PartTimeTableController#Controller]
    this.roots.setCenter(roots)
  }

  showMainMenu()

}

package ch.view

import ch.{MainApp, model, util}
import ch.model.ModelTableFullTime
import ch.util.{Databases, DisplayMessage}
import scalafx.beans.property.{ReadOnlyObjectWrapper, ReadOnlyStringWrapper, StringProperty}
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, TableColumn, TableView}
import scalafxml.core.macros.sfxml

import java.sql.SQLException

@sfxml
class FullTimeTableController(
                             val table: TableView[ModelTableFullTime],
                             val col_id: TableColumn[ModelTableFullTime, String],
                             val col_department: TableColumn[ModelTableFullTime, String],
                             val col_floor: TableColumn[ModelTableFullTime, String],
                             val col_name: TableColumn[ModelTableFullTime, String],
                             val col_wages: TableColumn[ModelTableFullTime, String],
                             val col_comm: TableColumn[ModelTableFullTime, String],
                             val col_Salary: TableColumn[ModelTableFullTime, String],
                             val btBack: Button
                             ) {

  //val database = new Databases
  val dm = new DisplayMessage

  def backButton(event: ActionEvent): Unit = {
    MainApp.showDisplay()
  }

  val oblist: ObservableBuffer[ModelTableFullTime] = ObservableBuffer[ModelTableFullTime]()

  try{
    util.Databases.selectFTEmployeeTable()

    while(Databases.getRs.next()){
      oblist.add(new ModelTableFullTime(
        Databases.getRs.getString("Employee_Id"),
        Databases.getRs.getString("department"),
        Databases.getRs.getString("floor"),
        Databases.getRs.getString("name"),
        Databases.getRs.getString("wages"),
        Databases.getRs.getString("comm"),
        Databases.getRs.getString("totalSalary")
      ))
    }
  }catch{
    case e: SQLException =>
      dm.alertSQLEx()
      e.printStackTrace()
  }

  col_id.cellValueFactory = { cellData =>
    StringProperty(cellData.value.id)
  }

  col_department.cellValueFactory = { cellData =>
    StringProperty(cellData.value.department)
  }

  col_floor.cellValueFactory = { cellData =>
    StringProperty(cellData.value.floor)
  }

  col_name.cellValueFactory = { cellData =>
    StringProperty(cellData.value.name)
  }

  col_wages.cellValueFactory = { cellData =>
    StringProperty(cellData.value.wages)
  }

  col_comm.cellValueFactory = { cellData =>
    StringProperty(cellData.value.comm)
  }

  col_Salary.cellValueFactory = { cellData =>
    StringProperty(cellData.value.salary)
  }

  table.items = oblist



}

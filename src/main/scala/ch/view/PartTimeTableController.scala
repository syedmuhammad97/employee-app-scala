package ch.view

import ch.{MainApp, util}
import ch.model.ModelTablePartTime
import ch.util.{Databases, DisplayMessage}
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, TableColumn, TableView}
import scalafxml.core.macros.sfxml

import java.sql.SQLException

@sfxml
class PartTimeTableController(
                               val table: TableView[ModelTablePartTime],
                               val col_id: TableColumn[ModelTablePartTime, String],
                               val col_department: TableColumn[ModelTablePartTime, String],
                               val col_floor: TableColumn[ModelTablePartTime, String],
                               val col_name: TableColumn[ModelTablePartTime, String],
                               val col_hours: TableColumn[ModelTablePartTime, String],
                               val col_food: TableColumn[ModelTablePartTime, String],
                               val col_Salary: TableColumn[ModelTablePartTime, String],
                               val btBack: Button
                             ) {

  val dm = new DisplayMessage

  def backButton(event: ActionEvent): Unit = {
    MainApp.showDisplay()
  }

  val oblist: ObservableBuffer[ModelTablePartTime] = ObservableBuffer[ModelTablePartTime]()

  try {
    util.Databases.selectEmployeePTTable()

    while (Databases.getRs.next()) {
      oblist.add(new ModelTablePartTime(
        Databases.getRs.getString("Employee_Id"),
        Databases.getRs.getString("department"),
        Databases.getRs.getString("floor"),
        Databases.getRs.getString("name"),
        Databases.getRs.getString("total_working_hour"),
        Databases.getRs.getString("food_allowance"),
        Databases.getRs.getString("monthly_salary")
      ))
    }
  } catch {
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

  col_hours.cellValueFactory = { cellData =>
    StringProperty(cellData.value.totalHour)
  }

  col_food.cellValueFactory = { cellData =>
    StringProperty(cellData.value.foodAllowance)
  }

  col_Salary.cellValueFactory = { cellData =>
    StringProperty(cellData.value.salary)
  }

  table.items = oblist


}

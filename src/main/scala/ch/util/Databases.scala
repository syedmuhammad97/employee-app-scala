package ch.util

import ch.model.{Department, FullTime, PartTime}
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

import java.io.{File, FileInputStream, InputStream}
import java.sql.{Connection, DriverManager, PreparedStatement, ResultSet, SQLException, Statement}

class Databases{

}

object Databases {
  val URL = "jdbc:mysql://localhost:3306/mydb"
  val USER = "root"
  val PASSWORD = ""
  private var result: Int = _

  var connect: Connection = _
  var stmt: Statement = _
  var sql: String = _
  var rs: ResultSet = _

  def getResult: Int = result

  def setResult(result: Int): Unit = this.result = result

  def getRs: ResultSet = rs

  def setRs(rs: ResultSet): Unit = this.rs = rs

  lazy val relativePath: String = {
    val currentWorkingDirectory = new File(".").getAbsolutePath
    val relativePath = currentWorkingDirectory.substring(0, currentWorkingDirectory.lastIndexOf("\\"))
    relativePath
  }

  def FTEmployee(dept: Department, ft: FullTime, file: String): Unit = {
    try {
      connect = DriverManager.getConnection(URL, USER, PASSWORD)
      //!!!shouldnt use absolute path, find a way to use relative path for fis
      //val fis = new FileInputStream(s"C:\\Scala\\ObjectOrientedFinalAssignment\\src\\main\\resources\\ch\\image\\$file")
      val fis = new FileInputStream(s"${relativePath}\\src\\main\\resources\\ch\\image\\${file}")
      sql = "INSERT INTO `full-time` (`id`, `name`, `wages`, `comm`, `totalSalary`, `image`) values (?,?,?,?,?,?)"
      val pstmt = connect.prepareStatement(sql)
      pstmt.setInt(1, ft.getId)
      pstmt.setString(2, ft.getName)
      pstmt.setInt(3, ft.getWages)
      pstmt.setInt(4, ft.getComm)
      pstmt.setInt(5, ft.getTotalSalary)
      pstmt.setBlob(6, fis)
      result = pstmt.executeUpdate()

      connect = DriverManager.getConnection(URL, USER, PASSWORD)
      sql = "INSERT INTO `department`(`department`, `floor`, `Employee_Id`) values (?,?,?)"
      val pstmt2 = connect.prepareStatement(sql)
      pstmt2.setString(1, dept.getDept)
      pstmt2.setString(2, dept.getFloor)
      pstmt2.setInt(3, ft.getId)
      result = pstmt2.executeUpdate()

      println("Record Updated") // just for testing
    } catch {
      case ex: Exception =>
        ex.printStackTrace()
        // put error message here
        val alert = new Alert(AlertType.Error) {
          title = "Error Message"
          headerText = "There is an error with the request"
          contentText = "Ooops, there was an error! Please try again."
        }
        alert.showAndWait()
    }
  }

  def PTEmployee(dept: Department, pt: PartTime, file: String): Unit = {
    try {
      connect = DriverManager.getConnection(URL, USER, PASSWORD)
      val fis = new FileInputStream(s"${relativePath}\\src\\main\\resources\\ch\\image\\${file}")
      sql = "INSERT INTO `part-time`(`id`, `name`, `total_working_hour`, `food_allowance`, `monthly_salary`, `image`) values (?,?,?,?,?,?)"
      val pstmt = connect.prepareStatement(sql)
      pstmt.setInt(1, pt.getId)
      pstmt.setString(2, pt.getName)
      pstmt.setInt(3, pt.getTotalWorkingHour)
      pstmt.setInt(4, pt.getFoodAllowance)
      pstmt.setInt(5, pt.getMonthlySalary)
      pstmt.setBlob(6, fis)
      result = pstmt.executeUpdate()

      connect = DriverManager.getConnection(URL, USER, PASSWORD)
      sql = "INSERT INTO `department`(`department`, `floor`, `Employee_Id`) values (?,?,?)";
      val pstmt2 = connect.prepareStatement(sql)
      pstmt2.setString(1, dept.getDept)
      pstmt2.setString(2, dept.getFloor)
      pstmt2.setInt(3, pt.getId)
      result = pstmt2.executeUpdate()

      println("Record Updated") // just for testing
    } catch {
      case ex: Exception =>
        // put error message here
        val alert = new Alert(AlertType.Error) {
          title = "Error Message"
          headerText = "There is an error with the request"
          contentText = "Ooops, there was an error! Please try again."
        }
        alert.showAndWait()
    }
  }

  def checkID(id: Int): Boolean = {
    var connect: Connection = null
    var stmt: Statement = null
    var rs: ResultSet = null

    try {
      // Step 1: Establish connection
      connect = DriverManager.getConnection(URL, USER, PASSWORD)

      // Step 2: Create statement
      stmt = connect.createStatement()

      // Step 3: Execute SQL
      val sql = s"SELECT * FROM `department` WHERE `Employee_Id` = $id"
      rs = stmt.executeQuery(sql)

      if (rs.next()) {
        true
      } else {
        false
      }
    } catch {
      case e: SQLException =>
        e.printStackTrace()
        false
    } finally {
      // Close resources
      if (rs != null) rs.close()
      if (stmt != null) stmt.close()
      if (connect != null) connect.close()
    }
  }

  def checkIDFull(id: Int): Boolean = {
    val connect: Connection = DriverManager.getConnection(URL, USER, PASSWORD)
    val stmt: Statement = connect.createStatement()
    val sql = s"SELECT * FROM `full-time` WHERE `id` = $id"
    val rs: ResultSet = stmt.executeQuery(sql)

    if (rs.next()) {
      true
    } else {
      false
    }
  }

  def checkIDPart(id: Int): Boolean = {
    val connect: Connection = DriverManager.getConnection(URL, USER, PASSWORD)
    val stmt: Statement = connect.createStatement()
    val sql = s"SELECT * FROM `part-time` WHERE `id` = $id"
    val rs: ResultSet = stmt.executeQuery(sql)

    if (rs.next()) {
      true
    } else {
      false
    }
  }

  def viewEmployee(id: Int): Unit = {
    val sql = s"SELECT * FROM `department` WHERE `id` = $id"
    rs = stmt.executeQuery(sql)
  }

  def viewFTEmployee(id: Int): Unit = {
    val sql = s"SELECT * FROM `full-time` WHERE `id` = $id"
    val ps: PreparedStatement = connect.prepareStatement(sql)
    rs = ps.executeQuery()
  }

  def viewPTEmployee(id: Int): Unit = {
    val sql = s"SELECT * FROM `part-time` WHERE `id` = $id"
    rs = stmt.executeQuery(sql)
  }

  def retrieve(id: Int): InputStream = {
    var is: InputStream = null
    if (checkIDFull(id)) {
      sql = s"SELECT * FROM `full-time` WHERE `id` = $id"
    } else if (checkIDPart(id)) {
      sql = s"SELECT * FROM `part-time` WHERE `id` = $id"
    }
    try {
      val ps: PreparedStatement = connect.prepareStatement(sql)
      rs = ps.executeQuery()
      while (rs.next()) {
        is = rs.getBinaryStream("image")
      }
      println("done2")
    } catch {
      case ex: Exception => ex.printStackTrace()
    }
    is
  }

  def deleteEmployee(id: Int): Unit = {
    val connect: Connection = DriverManager.getConnection(URL, USER, PASSWORD)
    val stmt: Statement = connect.createStatement()

    try {
      val deleteDepartmentSql = s"DELETE FROM `department` WHERE `Employee_Id` = $id"
      val deleteFullTimeSql = s"DELETE FROM `full-time` WHERE `id` = $id"
      val deletePartTimeSql = s"DELETE FROM `part-time` WHERE `id` = $id"

      stmt.executeUpdate(deleteDepartmentSql)
      stmt.executeUpdate(deleteFullTimeSql)
      stmt.executeUpdate(deletePartTimeSql)
    } catch{
      case ex: Exception =>
        ex.printStackTrace()
    }finally {
      stmt.close()
      connect.close()
    }
  }

  def modifyEmployeeFT(dept: Department, ft: FullTime, file: String): Unit = {
    try {
      val connect = DriverManager.getConnection(URL, USER, PASSWORD)
      val fis = new FileInputStream(s"${relativePath}\\src\\main\\resources\\ch\\image\\${file}")

      val sql = "UPDATE `full-time` SET `name` = ?, `wages` = ?, `comm` = ?, `totalSalary` = ?, `image` = ? WHERE `Id` = ? "
      val pstmt = connect.prepareStatement(sql)
      pstmt.setString(1, ft.getName)
      pstmt.setInt(2, ft.getWages)
      pstmt.setInt(3, ft.getComm)
      pstmt.setInt(4, ft.getTotalSalary)
      pstmt.setBlob(5, fis)
      pstmt.setInt(6, ft.getId)
      pstmt.executeUpdate()

      val sql2 = "UPDATE `department` SET `department` = ?, `floor` = ? WHERE `Employee_Id` = ? "
      val pstmt2 = connect.prepareStatement(sql2)
      pstmt2.setString(1, dept.getDept)
      pstmt2.setString(2, dept.getFloor)
      pstmt2.setInt(3, ft.getId)
      pstmt2.executeUpdate()
    } catch {
      case ex: Exception =>
        val alert = new Alert(AlertType.Error)
        alert.setTitle("Error Message")
        alert.setHeaderText("There is an error with the request")
        alert.setContentText("Ooops, there was an error! Please try again.")

        alert.showAndWait()
    }
  }

  def modifyEmployeePT(dept: Department, pt: PartTime, file: String): Unit = {
    try {
      val connect = DriverManager.getConnection(URL, USER, PASSWORD)
      val fis = new FileInputStream(s"${relativePath}\\src\\main\\resources\\ch\\image\\${file}")

      val sql = "UPDATE `part-time` SET `name` = ?, `total_working_hour` = ?, `food_allowance` = ?, `monthly_salary` = ?, `image` = ? WHERE `id` = ? "
      val pstmt = connect.prepareStatement(sql)
      pstmt.setString(1, pt.getName)
      pstmt.setInt(2, pt.getTotalWorkingHour)
      pstmt.setInt(3, pt.getFoodAllowance)
      pstmt.setInt(4, pt.getMonthlySalary)
      pstmt.setBlob(5, fis)
      pstmt.setInt(6, pt.getId)
      pstmt.executeUpdate()

      val sql2 = "UPDATE `department` SET `department` = ?, `floor` = ? WHERE `Employee_Id` = ? "
      val pstmt2 = connect.prepareStatement(sql2)
      pstmt2.setString(1, dept.getDept)
      pstmt2.setString(2, dept.getFloor)
      pstmt2.setInt(3, pt.getId)
      pstmt2.executeUpdate()
    } catch {
      case ex: Exception =>
        val alert = new Alert(AlertType.Error)
        alert.setTitle("Error Message")
        alert.setHeaderText("There is an error with the request")
        alert.setContentText("Ooops, there was an error! Please try again.")

        alert.showAndWait()
    }
  }

  def selectEmployeeID(): Unit = {
    DriverManager.getConnection(URL, USER, PASSWORD).autoClose { connect =>
      val sql = "SELECT * FROM `department` WHERE `Employee_Id`"
      val pstmt: PreparedStatement = connect.prepareStatement(sql)
      val rs: ResultSet = pstmt.executeQuery()

    }
  }

  def selectFTEmployeeID(): Unit = {
    DriverManager.getConnection(URL, USER, PASSWORD).autoClose { connect =>
      val sql = "SELECT * FROM `full-time` WHERE `id`"
      val pstmt: PreparedStatement = connect.prepareStatement(sql)
      val rs: ResultSet = pstmt.executeQuery()

    }
  }

  def selectPTEmployeeID(): Unit = {
    DriverManager.getConnection(URL, USER, PASSWORD).autoClose { connect =>
      val sql = "SELECT * FROM `part-time` WHERE `id`"
      val pstmt: PreparedStatement = connect.prepareStatement(sql)
      val rs: ResultSet = pstmt.executeQuery()

    }
  }

  // automatically close resources
  implicit class AutoCloseableOps[A <: AutoCloseable](resource: A) {
    def autoClose[B](code: A => B): B = {
      try {
        code(resource)
      } finally {
        resource.close()
      }
    }
  }

  def selectFTEmployeeTable(): Unit = {
    val connect: Connection = DriverManager.getConnection(URL, USER, PASSWORD)
    val sql = "SELECT * FROM `department` AS dt LEFT JOIN `full-time` AS ft ON dt.Employee_Id = ft.id WHERE ft.id IS NOT NULL ORDER BY dt.department ASC, dt.Employee_Id ASC"
    val pstmt: PreparedStatement = connect.prepareStatement(sql)
    rs = pstmt.executeQuery()
  }


  def selectEmployeePTTable(): Unit = {
    val connect: Connection = DriverManager.getConnection(URL, USER, PASSWORD)
    val sql = "SELECT * FROM `department` AS dt LEFT JOIN `part-time` AS pt ON dt.Employee_Id = pt.id WHERE pt.id IS NOT NULL ORDER BY dt.department ASC, dt.Employee_Id ASC"
    val pstmt: PreparedStatement = connect.prepareStatement(sql)
    rs = pstmt.executeQuery()
  }

}

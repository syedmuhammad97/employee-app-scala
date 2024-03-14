package ch.model

class Department(var dept: String, var floor: String) {

  def getDept: String = dept

  def setDept(dept: String): Unit = {
    this.dept = dept
  }

  def getFloor: String = floor

  def setFloor(floor: String): Unit = {
    this.floor = floor
  }

}

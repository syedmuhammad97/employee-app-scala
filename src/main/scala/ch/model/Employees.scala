package ch.model

class Employees(var dept : Department, var id: Int, var name: String, var category: String){

  def getName: String = name

  def setName(name: String): Unit = {
    this.name = name
  }

  def getCategory: String = category

  def setCategory(category: String): Unit = {
    this.category = category
  }

  def getId: Int = id

  def setId(id: Int): Unit = {
    this.id = id
  }

  def getDept: Department = dept

  def setDept(dept: Department): Unit = {
    this.dept = dept
  }

}

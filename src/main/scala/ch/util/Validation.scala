package ch.util

object Validation {

  def idValidation(id: Int): Boolean = {
    id >= 1001 && id <= 9999
  }

  def deptValidation(dept: String): Boolean = {
    dept match {
      case "Engineer" | "Marketing" | "MIS" => true
      case _ => false
    }
  }

  def nameValidation(name: String): Boolean = {
    name.matches("[A-Za-z ]+")
  }

  def categoryValidation(category: String): Boolean = {
    category match {
      case "Fulltime" | "fulltime" | "Part-time" | "part-time" => true
      case _ => false
    }
  }

  def wagesValidation(wages: Int): Boolean = {
    wages > 0
  }

  def commValidation(comm: Int): Boolean = {
    comm > 0
  }

  def workingHrValidation(totalWorkingHour: Int): Boolean = {
    totalWorkingHour > 0
  }

  def foodAllowanceValidation(foodAllowance: Int): Boolean = {
    foodAllowance > 0
  }

  def totalSalaryValidation(totalSalary: Int): Boolean = {
    totalSalary > 0
  }

  def monthlySalaryValidation(monthlySalary: Int): Boolean = {
    monthlySalary > 0
  }

  def floorValidation(floor: String): Boolean = {
    floor match {
      case "8th Floor" | "5th Floor" | "3rd Floor" => true
      case _ => false
    }
  }
}

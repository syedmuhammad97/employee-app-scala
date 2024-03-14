package ch.model

import ch.util.Validation

class PartTime(dept: Department, id: Int, name: String, category: String, var totalWorkingHour: Int,
               var foodAllowance: Int)
  extends Employees(dept, id, name, category) {

  private var _totalWorkingHour: Int = totalWorkingHour
  private var _foodAllowance: Int = foodAllowance
  private var _monthlySalary: Int = 0


  def getTotalWorkingHour: Int = _totalWorkingHour

  def setTotalWorkingHour(totalWorkingHour: Int): Unit = {
    if (Validation.workingHrValidation(totalWorkingHour)) {
      _totalWorkingHour = totalWorkingHour
    }
  }

  def getFoodAllowance: Int = _foodAllowance

  def setFoodAllowance(foodAllowance: Int): Unit = {
    if (Validation.foodAllowanceValidation(foodAllowance)) {
      _foodAllowance = foodAllowance
    }
  }

  def getMonthlySalary: Int = {
    _monthlySalary = _foodAllowance + (_totalWorkingHour * 12)
    _monthlySalary
  }

  def setMonthlySalary(monthlySalary: Int): Unit = {
    if (Validation.monthlySalaryValidation(monthlySalary)) {
      _monthlySalary = monthlySalary
    }
  }


}

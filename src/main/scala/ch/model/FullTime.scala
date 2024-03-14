package ch.model

import ch.util.Validation

class FullTime(dept: Department, id: Int, name: String, category: String, private var wages: Int, private var comm: Int)
extends Employees(dept, id, name, category){

  private var _wages: Int = wages
  private var _comm: Int = comm
  private var _totalSalary: Int = 0


  def getWages: Int = _wages

  def setWages(wages: Int): Unit = {
    if (Validation.wagesValidation(wages)) {
      _wages = wages
    }
  }

  def getComm: Int = _comm

  def setComm(comm: Int): Unit = {
    if (Validation.wagesValidation(comm)) {
      _comm = comm
    }
  }

  def getTotalSalary: Int = {
    _totalSalary = _comm + _wages
    _totalSalary
  }

  def setTotalSalary(totalSalary: Int): Unit = {
    if (Validation.totalSalaryValidation(totalSalary)) {
      _totalSalary = totalSalary
    }
  }

}

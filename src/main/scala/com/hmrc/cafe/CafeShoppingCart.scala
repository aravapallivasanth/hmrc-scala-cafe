package com.hmrc.cafe

 /**
 * 
 * User: Vasanth
 * Date: 08/01/17
 */
object CafeShoppingCart {

  val menuPrice = Map("Cola" -> 0.5, "Coffee" -> 1.0, "Cheese Sandwich" -> 2.0, "Steak Sandwich" -> 4.5)

  def getTotalBill(menuList: Option[(List[Item], Boolean)]): BigDecimal = {
    menuList map(i => calculateBill(i._1, i._2)) getOrElse 0.0
  }

  private def calculateBill(menu: List[Item], isChargeApplied: Boolean) : BigDecimal = {
    val originalPrice = menu.map(i => menuPrice(i.name)).sum
    if (isChargeApplied) {
      var serviceChargeAmount = originalPrice * getServiceChargePercentage(menu)
      serviceChargeAmount = if (serviceChargeAmount >= 20) 20 else serviceChargeAmount
      val totalBill = originalPrice + serviceChargeAmount
      return totalBill
    }
    originalPrice
  }

  private def getServiceChargePercentage(menuList: List[Item]): BigDecimal = {
    val isAnyFoodPresent = menuList.contains(Item("Cheese Sandwich"))
    val isHotFoodPresent = menuList.contains(Item("Steak Sandwich"))
    if (isAnyFoodPresent) return 0.1
    if (isHotFoodPresent) return 0.2
    0.0
  }
}

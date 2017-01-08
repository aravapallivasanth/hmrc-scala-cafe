package com.hmrc.cafe

import org.scalatest.{Matchers, FlatSpec}

import scala.collection.mutable

 /**
 * 
 * User: Vasanth
 * Date: 08/01/17
 */
class CafeShoppingCartSpec extends FlatSpec with Matchers {

  "CafeShoppingCart" should " produce total bill of 3.5 for " in {
    val isChargeApplied = false
    val menuList = Option(List(Item("Cola"), Item("Coffee"), Item("Cheese Sandwich")),isChargeApplied)
    CafeShoppingCart.getTotalBill(menuList) shouldBe 3.5
  }

  "CafeShoppingCart" should " produce total bill of 0.0 " in {
    val isChargeApplied = false
    val menuList = Option(List(),isChargeApplied)
    CafeShoppingCart.getTotalBill(menuList) shouldBe 0.0
  }

  "CafeShoppingCart" should " produce total bill of 0.0 for non existing menu " in {
    val menuList = None
    CafeShoppingCart.getTotalBill(menuList) shouldBe 0.0
  }

  "CafeShoppingCart" should " not apply service charge when all items are drink " in {
    val isChargeApplied = true
    val menuList = Option(List(Item("Cola"), Item("Coffee")),isChargeApplied)
    CafeShoppingCart.getTotalBill(menuList) shouldBe 1.5
  }

  "CafeShoppingCart" should " apply 10% service charge when items include any food " in {
    val isChargeApplied = true
    val menuList = Option(List(Item("Cola"), Item("Coffee"), Item("Cheese Sandwich")),isChargeApplied)
    CafeShoppingCart.getTotalBill(menuList) shouldBe 3.85
  }

  "CafeShoppingCart" should " apply 20% service charge when items include any hot food " in {
    val isChargeApplied = true
    val menuList = Option(List(Item("Cola"), Item("Coffee"), Item("Steak Sandwich")),isChargeApplied)
    CafeShoppingCart.getTotalBill(menuList) shouldBe 7.2
  }

  "CafeShoppingCart" should " apply maximum of £20 service charge when items include any hot food " in {
    val drinks = List(Item("Cola"), Item("Coffee"))
    var menuList = mutable.ListBuffer[Item]()
    for (i <- 0 until 30){
      menuList += Item("Steak Sandwich")
    }
    menuList.++=(drinks)
    val isChargeApplied = true
    CafeShoppingCart.getTotalBill(Option(menuList.toList,isChargeApplied)) shouldBe 156.50
  }
}

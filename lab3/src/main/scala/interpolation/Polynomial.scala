//package interpolation
//
//import Polynomial._
//
//
//object Polynomial {
//  implicit class CustomDouble(d: Double) {
//    def X(power: ^): Term = Term(d, power.value)
//  }
//
//  case class Term(var coef: Double, val power: Int) {
//  }
//
//  case class ^(val value: Int)
//
//  def +(terms: Term*): Polynomial = new Polynomial(terms)
//}
//
//class Polynomial(var terms: Seq[Term]) {
//  def this(terms: Term*) = this(for(term <- terms) yield term)
//}
//

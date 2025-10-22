package braid

import org.scalajs.dom

import scala.scalajs.js.annotation._

@main def main(): Unit = {
  val elt = dom.document.getElementById("mount")
  Braid.run(elt)
}

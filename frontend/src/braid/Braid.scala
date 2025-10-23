package braid

import braid.Habit
import com.raquo.laminar.api.L.{_, given}
import org.scalajs.dom

import scala.scalajs.js

object Braid {
  def run(mount: dom.Element): Unit = {
    val model =
      Seq(
        Habit("Work on Braid", 0, Seq()),
        Habit("Exercise", 5, Seq()),
        Habit("Read", 3, Seq(new js.Date(js.Date.now())))
      )

    val app = View.view(model)

    render(mount, app)
  }
}

package braid

import braid.model.Habit
import com.raquo.laminar.api.L.{_, given}
import org.scalajs.dom

object Braid {
  def run(mount: dom.Element): Unit = {
    val model =
      Seq(
        Habit("Work on Braid", 0),
        Habit("Exercise", 5),
        Habit("Read", 3)
      )

    val app = View.view(model)

    render(mount, app)
  }
}

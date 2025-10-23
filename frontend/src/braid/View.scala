package braid

import braid.model.Habit
import com.raquo.laminar.api.L.{_, given}
import com.raquo.laminar.nodes.ReactiveHtmlElement

import scala.scalajs.js

object View {
  def last7Days: Seq[js.Date] = {
    val today = new js.Date(js.Date.now())
    for (i <- -6 to 0) yield {
      val date = new js.Date()
      date.setDate(today.getDate() + i)
      date
    }
  }

  def last7DaysTableHeadings: Seq[Element] =
    last7Days.zipWithIndex
      .map { (date, i) =>
        th(
          className := "px-3 py-4 text-center text-sm font-semibold text-gray-700",
          div(
            date.toLocaleDateString(),
            div(
              className := "text-xs font-normal text-gray-500",
              date.getDate()
            )
          )
        )
      }

  def view(habits: Seq[Habit]): Div =
    div(
      className := "bg-white rounded-lg shadow-lg overflow-hidden",
      div(
        className := "overflow-x-auto",
        table(
          className := "w-full",
          thead(
            className := "bg-gray-50 border-b",
            tr(
              th(
                className := "px-6 py-4 text-left text-sm font-semibold text-gray-700",
                "Habit"
              ),
              th(
                className := "px-4 py-4 text-center text-sm font-semibold text-gray-700",
                "Streak"
              ),
              last7DaysTableHeadings,
              th(className := "px-4 py-4")
            )
          ),
          tbody(className := "divide-y divide-gray-200", habits.map(habitView))
        )
      )
    )

  def habitView(habit: Habit): ReactiveHtmlElement[?] =
    tr(
      className := "hover:bg-gray-50",
      td(className := "px-6 py-4 text-gray-800 font-medium", habit.name),
      td(
        className := "px-4 py-4 text-center",
        span(
          className := "inline-flex items-center px-3 py-1 rounded-full text-sm font-semibold bg-orange-100 text-orange-700",
          s"🔥 ${habit.streak}"
        )
      ),
      last7Days.map((date) =>
        td(
          className := "px-3 py-4 text-center",
          button(className := s"w-8 h-8 rounded-lg transition")
        )
      ),
      td(
        className := "px-4 py-4 text-center",
        button(
          // onClick:={() :=> deleteHabit(habit.id)}
          className := "text-red-500 hover:text-red-700 transition"
        )
      )
    )
}

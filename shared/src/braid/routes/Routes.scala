package braid.routes

import krop.all._

object Routes {
  val home =
    Route(
      Request.get(Path.root),
      Response.ok(Entity.html)
    )

  // This route serves static assets, such as Javascript or stylesheets, from
  // your resource directory.
  val assets =
    Route(
      Request.get(Path / "assets" / Param.separatedString("/")),
      Response.staticResource("braid/assets/")
    )
}

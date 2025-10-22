package braid

import braid.conf.Context
import braid.routes.Routes
import braid.views.html
import cats.effect.ExitCode
import cats.effect.IO
import com.monovore.decline.Opts
import com.monovore.decline.effect.CommandIOApp
import fs2.io.file.{Path => Fs2Path}
import krop.BuildInfo
import krop.all.{_, given}
import krop.tool.cli._

val name = "Braid"

object Main
    extends CommandIOApp(
      name = name,
      header = "An amazing web application built with Krop"
    ) {

  val home =
    Routes.home.handle(() =>
      html.base(name, html.home(name, BuildInfo.kropVersion)).toString
    )

  val assets =
    Routes.assets.passthrough

  val remainingPath =
    Param.separatedString("/").imap(Fs2Path.apply)(_.toString)

  val javascript =
    Route(
      Request.get(Path / "js" / "main.js"),
      Response.staticFile(s"${BuildInfo.frontendPath.toString}/main.js")
    ).passthrough

  val application =
    home.orElse(javascript).orElse(assets).orElse(Application.notFound)

  override def main: Opts[IO[ExitCode]] =
    (Cli.serveOpts.orElse(Cli.migrateOpts)).map {
      case Serve(port) =>
        Context.current.use { _ =>
          ServerBuilder.default
            .withApplication(application)
            .withPort(port)
            .build
            .toIO
            .as(ExitCode.Success)
        }

      case Migrate() => ???
    }
}

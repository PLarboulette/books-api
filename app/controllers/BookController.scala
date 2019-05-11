package controllers

import java.util.UUID
import helpers.Http
import javax.inject._
import models.inputs.BookInput
import play.api.mvc._
import services.BookService

import scala.concurrent.Future
import scala.util.{ Failure, Success, Try }

@Singleton
class BookController @Inject()(cc: ControllerComponents, bookService: BookService)
  extends AbstractController(cc)
  with Http {

  def find(idLibrary: Option[UUID]): Action[AnyContent] = Action.async { _ =>
    ioToFuture(bookService.find(idLibrary))
  }

  def findById(idBook: UUID): Action[AnyContent] = Action.async { implicit request =>
    ioToFuture(bookService.findById(idBook))
  }

  def save: Action[BookInput] = Action(parse.json[BookInput]).async { request =>
    ioToFuture(bookService.save(request.body))
  }

}

package services

import org.scalatest.{ AsyncWordSpec, BeforeAndAfterAll, BeforeAndAfterEach, Matchers }
import play.api.{ Application, Configuration, Play }
import play.api.inject.guice.GuiceApplicationBuilder

class BookServiceSpec extends AsyncWordSpec with Matchers with BeforeAndAfterEach with BeforeAndAfterAll {

  val app: Application = new GuiceApplicationBuilder().build()
  val configuration: Configuration = app.injector.instanceOf[Configuration]

  val userService = app.injector.instanceOf[UserService]
  val libraryServie = app.injector.instanceOf[LibraryService]
  val bookService = app.injector.instanceOf[BookService]

  override def afterAll() = {
    Play.stop(app)
  }

  "BookServiceSpec" should {

    "return a list of books" in {

      assert(1 == 1)

    }

  }
}

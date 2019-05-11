package repositories

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import anorm.SQL
import com.typesafe.config.Config
import models.inputs.BookInput
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{ BeforeAndAfterAll, BeforeAndAfterEach, Inside }
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.db.Database
import play.api.db.evolutions.Evolutions
import play.api.inject.Injector

import scala.concurrent.ExecutionContextExecutor

final class BookRepositorySpec
  extends PlaySpec
  with BeforeAndAfterAll
  with BeforeAndAfterEach
  with GuiceOneAppPerSuite
  with Inside
  with ScalaFutures {

  implicit val system: ActorSystem = ActorSystem("test-system", app.injector.instanceOf[Config])
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContextExecutor = materializer.executionContext

  val injector: Injector = app.injector

  val db: Database = injector.instanceOf[Database]

  override def beforeAll() = {
    db.withConnection { implicit connection =>
      SQL("""DROP SCHEMA public CASCADE""").execute()
      SQL("""CREATE SCHEMA public""").execute()
    }

    Evolutions.cleanupEvolutions(db)
    Evolutions.applyEvolutions(db)
  }

  override def beforeEach(): Unit = {
    db.withConnection { implicit connection =>
      SQL("""TRUNCATE "Book", "Library", "User" CASCADE;""").execute()
    }
  }

  override def afterAll(): Unit = {
    db.shutdown()
  }

  val bookRepository: BookRepository = injector.instanceOf[repositories.BookRepository]

  "BookRepository" should {

    /* "Insert a book not bought yet" in {
      bookRepository.save(
        BookInput(
          "The Lord Of The Rings "
        )
      )
    }*/

    "Delete all books" in {
      db.withConnection { implicit connection =>
        SQL("""
            | DELETE FROM "Book"
          """.stripMargin).execute()
      }

      assert(bookRepository.find(None).isEmpty)
    }
  }

}

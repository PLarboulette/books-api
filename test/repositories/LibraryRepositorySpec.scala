package repositories

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.Config
import fixtures.{ LibraryFixtures, UserFixtures }
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{ BeforeAndAfterAll, BeforeAndAfterEach, Inside }
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.db.Database
import play.api.db.evolutions.Evolutions
import play.api.inject.Injector
import repositories.helpers.{ LibrarySupport, UserSupport }
import anorm.{ SQL, _ }

import scala.concurrent.ExecutionContextExecutor

class LibraryRepositorySpec
  extends PlaySpec
  with BeforeAndAfterAll
  with BeforeAndAfterEach
  with GuiceOneAppPerSuite
  with Inside
  with ScalaFutures
  with LibrarySupport
  with UserFixtures
  with LibraryFixtures {

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

  val userRepository: UserRepository = injector.instanceOf[repositories.UserRepository]
  val libraryRepository: LibraryRepository = injector.instanceOf[LibraryRepository]

  "LibraryRepository" should {

    "save a new library" in {
      val libraryInput = library(userRepository.save(user).get)
      libraryRepository.save(libraryInput)
      db.withConnection { implicit connection =>
        val libraries = SQL"""SELECT * FROM "Library" """.as(libraryRowParser.*)
        assert(libraries.size == 1)
      }
    }

    "save two libraries for a same user but with two different names" in {
      val userCreated = userRepository.save(user)
      val libraryInput1 = library(userCreated.get)
      libraryRepository.save(libraryInput1)
      val libraryInput2 = library(userCreated.get).copy(name = "A second name")
      libraryRepository.save(libraryInput2)

      assert(libraryRepository.find(Some(userCreated.get)).size == 2)
    }

    "doesn't save a library if an existing library with the same name is already linked to an user" in {
      val libraryInput = library(userRepository.save(user).get)
      val insert1 = libraryRepository.save(libraryInput)
      val insert2 = libraryRepository.save(libraryInput) // USe same data for the second library
      assert(insert1.isSuccess)
      assert(insert2.isFailure)
      db.withConnection { implicit connection =>
        val libraries = SQL"""SELECT * FROM "Library" """.as(libraryRowParser.*)
        assert(libraries.size == 1)
      }
    }

    "retrieve a list of libraries" in {
      libraryRepository.save(library(userRepository.save(user).get))
      assert(libraryRepository.find(None).size == 1)
    }

    "retrieve a list of libraries filtered by userId" in {
      val user1 = userRepository.save(user)
      val user2 = userRepository.save(user.copy(login = "Login 2"))
      libraryRepository.save(library(user1.get))
      libraryRepository.save(library(user2.get))

      assert(libraryRepository.find(Some(user1.get)).size == 1)
      assert(libraryRepository.find(Some(user2.get)).size == 1)
      assert(libraryRepository.find(None).size == 2)
    }
  }
}

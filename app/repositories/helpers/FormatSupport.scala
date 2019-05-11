package repositories.helpers

import anorm.{ Column, TypeDoesNotMatch }
import models.Format

trait FormatSupport {

  implicit val columnToFormat: Column[Format] = Column.nonNull[Format] { (value, _) =>
    value match {
      case value: String =>
        Format.withNameOption(value) match {
          case Some(format) =>
            Right(format)
          case None =>
            Left(TypeDoesNotMatch(s"Can not parse Format with $value"))
        }
      case _ =>
        Left(TypeDoesNotMatch(s"Can not parse Format with non string : $value"))
    }
  }

}

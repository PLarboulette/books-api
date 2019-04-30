package models

import enumeratum._

import scala.collection.immutable

sealed trait Format extends EnumEntry

object Format extends Enum[Format] with PlayJsonEnum[Format] {
  override def values: immutable.IndexedSeq[Format] = findValues

  case object EBOOK extends Format
  case object BOOK extends Format
}

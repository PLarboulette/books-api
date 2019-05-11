package fixtures

import java.util.UUID

import models.inputs.LibraryInput

trait LibraryFixtures {

  def library(idUser: UUID) =
    LibraryInput(
      idUser,
      "Library"
    )

}

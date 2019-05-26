# --- !Ups

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE "User"
(
    "idUser" UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    "login" TEXT UNIQUE NOT NULL,
    "password" TEXT NOT NULL,
    "firstName" TEXT,
    "lastName" TEXT,
    "createdAt" TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    "updatedAt" TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    "deletedAt" TIMESTAMPTZ
);

CREATE TABLE "Library"
(
    "idLibrary" UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    "idUser" UUID REFERENCES "User"("idUser"),
    "name" TEXT,
    "createdAt" TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    "updatedAt" TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    "deletedAt" TIMESTAMPTZ,
    UNIQUE ("idUser", "name")
);

CREATE TABLE "Book"
(
    "idBook" UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    "title" TEXT,
    "idLibrary" UUID REFERENCES "Library"("idLibrary"),
    "author" TEXT NOT NULL ,
    "collection" TEXT,
    "status" TEXT,
    "format" TEXT,
    "boughtAt" TIMESTAMPTZ NOT NULL,
    "readAt" TIMESTAMPTZ NOT NULL,
    "rating" INT NOT NULL,
    "createdAt" TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    "updatedAt" TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    "deletedAt" TIMESTAMPTZ
);

# --- !Downs

DROP TABLE "Book";

DROP TABLE "Library";

DROP TABLE "User";

DROP EXTENSION "uuid-ossp";

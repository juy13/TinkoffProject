package ru.tinkoff.mpdback.enums

enum class FileRc (val rc : Int) {

    FileOk(0),
    FileBig(-1),
    FileError(-255)

}
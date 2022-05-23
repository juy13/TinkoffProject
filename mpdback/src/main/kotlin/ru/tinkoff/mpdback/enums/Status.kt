package ru.tinkoff.mpdback.enums

enum class Status(val status : Int) {
    IN_DATA(0),                         // encrypt and put data
    DATA_PROCESS(1),                    // process of prev
    DONE(2),                            //
    OUT_DATA(3),                        // get out data
    OUT_DATA_PREPARE(4),                // prepare data for out
    FILE_IN(5),                         // upload files
    FILE_OUT(6),                        // ready file
    FILE_OUT_PREPARE(7),                // prepare file data for out
    FILE_PROGRESS(8),
    FILE_EXIST(9),                      // if file exist in bd for uploading
    FILE_NOT_EXIST(10),                 // if it's no file in db
    ERROR(255)
}
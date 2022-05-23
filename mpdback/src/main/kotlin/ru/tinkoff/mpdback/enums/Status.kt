package ru.tinkoff.mpdback.enums

enum class Status(val status : Int) {
    IN_DATA(0),                         // encrypt and put data
    DATA_PROCESS(1),                    // process of prev
    DONE(2),                            //
    OUT_DATA(3),                        // get out data
    OUT_DATA_PREPARE(4),                // prepare data for out
    FILE_IN(5),
    FILE_OUT(6),
    FILE_OUT_PREPARE(7),
    FILE_PROGRESS(8),
//    PREPARE(5),
    ERROR(255)
}
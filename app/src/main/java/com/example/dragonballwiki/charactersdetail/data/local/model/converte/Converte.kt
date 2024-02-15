package com.example.dragonballwiki.charactersdetail.data.local.model.converte

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

@ProvidedTypeConverter
class ExampleConverter {
    @TypeConverter
    fun StringToExample(string: String?): ExampleType? {
        ...
    }

    @TypeConverter
    fun ExampleToString(example: ExampleType?): String? {
        ...
    }
}
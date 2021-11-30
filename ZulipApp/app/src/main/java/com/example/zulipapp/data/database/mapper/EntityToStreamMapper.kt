package com.example.zulipapp.data.database.mapper

import androidx.core.graphics.toColorInt
import com.example.zulipapp.data.database.entity.StreamEntity
import com.example.zulipapp.domain.entity.Stream

class EntityToStreamMapper : (List<StreamEntity>) -> List<Stream> {
    override fun invoke(entities: List<StreamEntity>): List<Stream> {
        return entities.map {
            Stream(
                it.streamId,
                it.name,
                it.description,
                it.firstMessageId,
                it.color.toColorInt()
            )
        }
    }
}
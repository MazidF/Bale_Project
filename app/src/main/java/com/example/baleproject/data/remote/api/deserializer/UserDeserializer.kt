package com.example.baleproject.data.remote.api.deserializer

import com.example.baleproject.data.model.User
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

object UserDeserializer : JsonDeserializer<User> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): User = with(json.asJsonObject) {
        User(
            id = this["id"].asString,
            name = this["name"].asString,
            email = this["email"].asString,
            verified = this["verified"].asBoolean,
        )
    }

}
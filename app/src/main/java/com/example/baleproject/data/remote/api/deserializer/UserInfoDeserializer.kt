package com.example.baleproject.data.remote.api.deserializer

import com.example.baleproject.data.model.UserInfo
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

object UserInfoDeserializer : JsonDeserializer<UserInfo> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): UserInfo = with(json.asJsonObject) {
        UserInfo(
            id = this["id"].asString,
            name = this["name"].asString,
            email = this["email"].asString,
            verified = this["verified"].asBoolean,
            accessToken = "",
        )
    }
}
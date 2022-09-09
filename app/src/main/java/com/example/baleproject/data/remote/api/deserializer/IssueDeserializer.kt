package com.example.baleproject.data.remote.api.deserializer

import com.example.baleproject.data.model.Issue
import com.example.baleproject.data.model.Vote
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

object IssueDeserializer : JsonDeserializer<Issue> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Issue = with(json.asJsonObject) {
        Issue(
            id = this["id"].asString,
            title = this["title"].asString,
            description = this["description"].asString,
            reviewed = this["reviewed"].asBoolean,
            vote = Vote.get(
                upVoteCount = this["upVoteCount"].asInt,
                downVoteCount = this["downVoteCount"].asInt,
            ),
            commentsCount = this["commentsCount"].asInt,
            labelIds = this["labels"].asJsonArray.map {
                it.asString
            },
        )
    }
}

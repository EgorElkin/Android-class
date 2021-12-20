package com.example.zulipapp.data.api

import com.example.zulipapp.data.api.entity.message.MessageSendResponse
import com.example.zulipapp.data.api.entity.message.MessagesGetResponse
import com.example.zulipapp.data.api.entity.message.ReactionOperationResponse
import com.example.zulipapp.data.api.entity.stream.StreamsResponse
import com.example.zulipapp.data.api.entity.stream.TopicsInStreamResponse
import com.example.zulipapp.data.api.entity.user.UserResponse
import com.example.zulipapp.data.api.entity.user.UsersResponse
import io.reactivex.Single
import retrofit2.http.*

interface StreamApiService {
    @GET("users/me/subscriptions")
    fun getSubscribedStreams(): Single<StreamsResponse>

    @GET("streams")
    fun getAllStreams(): Single<StreamsResponse>

    @GET("users/me/{stream_id}/topics")
    fun getTopics(@Path("stream_id") streamId: String): Single<TopicsInStreamResponse>
}

interface UserApiService {
    @GET("users")
    fun getAllUsers(): Single<UsersResponse>

    @GET("users/me")
    fun getUserMe(): Single<UserResponse>

    @GET("users/{user_id}")
    fun getUserById(@Path("user_id") userId: String): Single<UsersResponse>

    @GET("users/{user_id}/presence")
    fun getUserPresenceById(@Path("user_id") userId: String): Single<UsersResponse>
}

interface MessageApiService {

    @POST("messages")
    fun postMessage(
        @Query("type") type: String, // stream/private
        @Query("to") streamName: String, // Maximum message size of 10000 bytes
        @Query("topic") topicName: String?, // Maximum length of 60 characters
        @Query("content") content: String
    ): Single<MessageSendResponse>

    @GET("messages")
    fun getMessagesRange(
        @Query("anchor") anchor: String,
        @Query("num_before") numBefore: Int,
        @Query("num_after") numAfter: Int,
        @Query("narrow") narrow: String
    ): Single<MessagesGetResponse>

    @GET("messages")
    fun getMessagesRange(
        @Query("anchor") anchor: Int,
        @Query("num_before") numBefore: Int,
        @Query("num_after") numAfter: Int,
        @Query("narrow") narrow: String
    ): Single<MessagesGetResponse>

    @POST("messages/{message_id}/reactions")
    fun addReaction(
        @Path("message_id") messageId: Int,
        @Query("emoji_name") emojiName: String,
        @Query("emoji_code") emojiCode: String
    ): Single<ReactionOperationResponse>

    @DELETE("messages/{message_id}/reactions")
    fun removeReaction(
        @Path("message_id") messageId: Int,
        @Query("emoji_name") emojiName: String,
        @Query("emoji_code") emojiCode: String
    ): Single<ReactionOperationResponse>
}
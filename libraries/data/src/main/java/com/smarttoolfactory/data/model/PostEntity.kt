package com.smarttoolfactory.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "post")
data class PostEntity(
    @PrimaryKey
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)

/**
 * * Data class that contains [PostStatus] data.
 * [PostEntity.id] is in [PostEntity] class, [PostStatus.postId] is in [PostStatus]
 * both points to same value.
 *
 * * [PostStatus.id] is auto generated by insertion to table.
 *
 * * Index let's this table to be sorted by postId which makes all
 * rows with same postId to be found faster.
 *
 * * Status of the [PostEntity] with [PostEntity.id] or [PostStatus.postId] belong to current user
 * logged in with [PostStatus.userAccountId] or -1 if any user hasn't logged in
 */
@Entity(
    tableName = "post_status",
    indices = [Index(value = ["userAccountId", "postId"])],
    foreignKeys = [
        ForeignKey(
            entity = PostEntity::class,
            parentColumns = ["id"],
            childColumns = ["postId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class PostStatus(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userAccountId: Int = -1,
    val postId: Int,
    val displayCount: Int = 0,
    val isFavorite: Boolean = false
)

/**
 * @Embedded tag is for having nested entities that are contained inside another entity. For
 * instance Songs are embedded inside an Album.
 *
 * @Relation is for having relation between entities based on pairing one or more properties,
 * such as ids. For instance Person with id, having Pets that has userId that is exactly same
 * with each other.
 *
 * * ParentColumn name from [PostEntity] class is matched with entityColumn
 * from [PostStatus.postId]
 */
data class PostAndStatus(

    @Embedded
    val postEntity: PostEntity,

    // 🔥 'id' comes from Post, 'postId' comes from Post. Both are the same ids
    @Relation(parentColumn = "id", entityColumn = "postId")
    var postStatus: PostStatus? = null
)

package com.example.eventapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity("tags_table")
data class Tags(
    @PrimaryKey
    @ColumnInfo(name = "tag_name")
    val name: String,
    @ColumnInfo(name = "tag_color")
    val color: String,
    @ColumnInfo(name = "icon_name")
    val iconName: String
)

//data class TaskWithTagLists(
//    @Embedded val tag: Tags,
//    @Relation(
//        parentColumn = "tag_name",
//        entityColumn = "task_tag_name"
//    )
//    var tasks: List<Task>
//)

@Entity(primaryKeys = ["task_id","tag_name"])
data class TaskTagCrossRef(
    @ColumnInfo(name = "task_id")
    val taskId: Long,
    @ColumnInfo(name = "tag_name")
    val tagName: String
)

data class TagWithTaskLists(
    @Embedded val tag: Tags,
    @Relation(
        parentColumn = "tag_name",
        entityColumn = "task_id",
        associateBy = Junction(TaskTagCrossRef::class)
    )
    var tasks: List<Task>
)

data class TaskWithTags(
    @Embedded val task: Task,
    @Relation(
        parentColumn = "task_id",
        entityColumn = "tag_name",
        associateBy = Junction(TaskTagCrossRef::class)
    )
    var tags: List<Tags>
)
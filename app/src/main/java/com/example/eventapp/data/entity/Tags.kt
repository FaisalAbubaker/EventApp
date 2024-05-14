package com.example.eventapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity("tags_table")
data class Tags(
    @PrimaryKey
    @ColumnInfo(name = "tag_name")
    val name: String,
    @ColumnInfo(name = "tag_color")
    val color: String
)

data class TaskWithTagLists(
    @Embedded val tag: Tags,
    @Relation(
        parentColumn = "tag_name",
        entityColumn = "task_tag_name"
    )
    var tasks: List<Task>
)
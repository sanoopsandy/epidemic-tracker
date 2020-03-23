package `in`.tracker.core.database.entities

import `in`.tracker.core.utils.getISTDateStringFromTime
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "world_stat")
data class WorldStat(
    @Expose
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @Expose
    @SerializedName("new_cases")
    @ColumnInfo(name = "new_cases")
    val new_cases: String,

    @Expose
    @SerializedName("new_deaths")
    @ColumnInfo(name = "new_deaths")
    val new_deaths: String,

    @Expose
    @SerializedName("statistic_taken_at")
    @ColumnInfo(name = "statistic_taken_at")
    val statistic_taken_at: String,

    @Expose
    @SerializedName("total_cases")
    @ColumnInfo(name = "total_cases")
    val total_cases: String,

    @Expose
    @SerializedName("total_deaths")
    @ColumnInfo(name = "total_deaths")
    val total_deaths: String,

    @Expose
    @SerializedName("total_recovered")
    @ColumnInfo(name = "total_recovered")
    val total_recovered: String
) {
    val formatedTimeStamp: String
        get() {
            return statistic_taken_at.getISTDateStringFromTime()
        }
}
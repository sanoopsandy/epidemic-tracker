package `in`.tracker.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "country_stat")
data class CountryStat(
    @Expose
    @SerializedName("active_cases")
    @ColumnInfo(name = "active_cases")
    val active_cases: String,

    @Expose
    @SerializedName("cases")
    @ColumnInfo(name = "cases")
    val cases: String,

    @Expose
    @SerializedName("country_name")
    @ColumnInfo(name = "country_name")
    @PrimaryKey
    val country_name: String,

    @Expose
    @SerializedName("deaths")
    @ColumnInfo(name = "deaths")
    val deaths: String,

    @Expose
    @SerializedName("new_cases")
    @ColumnInfo(name = "new_cases")
    val new_cases: String,

    @Expose
    @SerializedName("new_deaths")
    @ColumnInfo(name = "new_deaths")
    val new_deaths: String,

    @Expose
    @SerializedName("region")
    @ColumnInfo(name = "region")
    val region: String,

    @Expose
    @SerializedName("serious_critical")
    @ColumnInfo(name = "serious_critical")
    val serious_critical: String,

    @Expose
    @SerializedName("total_cases_per_1m_population")
    @ColumnInfo(name = "total_cases_per_1m_population")
    val total_cases_per_1m_population: String,

    @Expose
    @SerializedName("total_recovered")
    @ColumnInfo(name = "total_recovered")
    val total_recovered: String
)
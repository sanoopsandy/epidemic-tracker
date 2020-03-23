package `in`.tracker.covid.network

import `in`.tracker.core.database.entities.CountryStat
import `in`.tracker.core.utils.getISTDateFromTime
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AllCountryStatResponse(
    val countries_stat: List<CountryStat>,
    val statistic_taken_at: String
)

data class CountryHistoryStatResponse(
    @Expose
    @SerializedName("stat_by_country")
    val stat_by_country: List<CountryHistoryStat>
)

data class CountryHistoryStat(

    @Expose
    @SerializedName("new_cases")
    val new_cases: String,

    @Expose
    @SerializedName("new_deaths")
    val new_deaths: String,

    @Expose
    @SerializedName("record_date")
    val record_date: String,

    @Expose
    @SerializedName("total_cases")
    val total_cases: String,

    @Expose
    @SerializedName("total_deaths")
    val total_deaths: String,

    @Expose
    @SerializedName("total_recovered")
    val total_recovered: String
) {
    val formattedTimeStamp: String
        get() {
            return record_date.getISTDateFromTime()
        }
}

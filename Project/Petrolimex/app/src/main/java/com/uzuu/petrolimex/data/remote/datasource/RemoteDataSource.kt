package com.uzuu.petrolimex.data.remote.datasource

import com.uzuu.petrolimex.core.result.ApiResult
import com.uzuu.petrolimex.data.remote.RetrofitProvider
import com.uzuu.petrolimex.data.remote.dto.FuelResponse
import com.uzuu.petrolimex.domain.model.Fuel
import com.uzuu.petrolimex.domain.model.Time
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSource @Inject constructor() {
    suspend fun getData(): ApiResult<FuelResponse> {
        return withContext(Dispatchers.IO) {
            try {

                val doc = org.jsoup.Jsoup.connect(RetrofitProvider.BASE_URL)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get()

                val rows = doc.select(".table-responsive table tbody tr")
                val time = doc.select("article#main p strong").text()

                val fuels = rows.mapNotNull { row ->

                    val name = row.selectFirst("th a")?.text() ?: return@mapNotNull null
                    val prices = row.select("td.text-right")

                    if (prices.size >= 2) {
                        Fuel(
                            name = name,
                            price1 = prices[0].text(),
                            price2 = prices[1].text()
                        )
                    } else null
                }

                ApiResult.Success(
                    FuelResponse(
                        time = Time(time),
                        fuels = fuels
                    )
                )

            } catch (e: Exception) {
                ApiResult.Error(
                    message = e.message ?: "Unknown error",
                    throwable = e
                )
            }
        }
    }
}
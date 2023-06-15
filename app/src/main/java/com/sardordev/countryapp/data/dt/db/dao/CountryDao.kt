package com.sardordev.countryapp.data.dt.db.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.sardordev.countryapp.data.model.CountryData
import com.sardordev.countryapp.data.model.Name
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

@Dao
interface CountryDao {

    @Insert
    suspend fun inser(countryData: CountryData)


    @Delete
    fun delete(countryData: CountryData)

    @Query("select * from countryData")
    fun getcountyDatabase(): LiveData<List<CountryData>>


    @Query("delete  from countryData")
    fun deleteAll()

    @Query("SELECT * FROM countryData LIMIT :pageSize OFFSET :offset")
    fun getEntities(offset: Int, pageSize: Int = 20): List<CountryData>

    @Query("SELECT * FROM countryData LIMIT :limit OFFSET :offset")
    suspend fun getPagedList(limit: Int, offset: Int): List<CountryData>


    @Query("SELECT * FROM countryData WHERE name LIKE '%' || :query || '%' LIMIT :limit OFFSET :offset")
    suspend fun getFilteredData(query: String, limit: Int, offset: Int): List<CountryData>






//    @Query("SELECT * FROM countryData WHERE column_name LIKE '%' || :query || '%' LIMIT :limit OFFSET :offset")

}
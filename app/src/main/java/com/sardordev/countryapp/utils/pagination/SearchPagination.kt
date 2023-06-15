package com.sardordev.countryapp.utils.pagination

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.paging.*
import com.sardordev.countryapp.data.dt.db.dao.CountryDao
import com.sardordev.countryapp.data.model.CountryData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class SearchPagination(private val dao: CountryDao, private var query: String) :
    PagingSource<Int, CountryData>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CountryData> {
        val page = params.key ?: 0
        return try {
            val entity = if (query.isNotBlank()) {
                dao.getFilteredData(query, params.loadSize, page * params.loadSize)
            } else {
                dao.getPagedList(params.loadSize, page * params.loadSize)
            }
            if (page != 0) delay(1000)
            LoadResult.Page(
                data = entity,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entity.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, CountryData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


    fun getFilteredData(query: String): Flow<PagingData<CountryData>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { SearchPagination(dao, query) }
        ).flow
    }




}
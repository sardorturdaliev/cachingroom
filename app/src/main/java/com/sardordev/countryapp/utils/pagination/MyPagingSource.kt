package com.sardordev.countryapp.utils.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sardordev.countryapp.data.dt.db.dao.CountryDao
import com.sardordev.countryapp.data.model.CountryData
import kotlinx.coroutines.delay

class MyPagingSource(private val dao: CountryDao) : PagingSource<Int, CountryData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CountryData> {
        val page = params.key ?: 0
        return try {
            val entity = dao.getPagedList(params.loadSize, page * params.loadSize)
            if (page != 0) delay(1000)
            LoadResult.Page(
                data = entity, prevKey = if (page == 0) null else page - 1,
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


}
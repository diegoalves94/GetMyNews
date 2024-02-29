package me.study.getmynews.di

import android.content.Context
import com.google.common.truth.Truth.assertThat
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import me.study.getmynews.data.dao.NewsDao
import me.study.getmynews.db.NewsDatabase
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DatabaseModuleTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    private lateinit var databaseModule: DatabaseModule
    private lateinit var mockNewsDatabase: NewsDatabase
    private var mockNewsDao: NewsDao = mockk()
    private val context: Context = mockk(relaxed = true)

    @Before
    fun setup() {
        databaseModule = DatabaseModule()
        mockNewsDatabase = databaseModule.provideNewsDatabase(context)
        mockNewsDao = mockNewsDatabase.getNewsDao()
    }

    @Test
    fun `provideNewsDatabase should provide NewsDatabase instance`() {
        // Given
        val newsDatabase = mockNewsDatabase

        // Then
        assertThat(newsDatabase).isNotNull()
    }

    @Test
    fun `provideNewsDao should provide NewsDao instance`() {
        // Given
        val newsDao = databaseModule.provideNewsDao(mockNewsDatabase)

        // Then
        assertThat(newsDao).isEqualTo(mockNewsDao)
    }
}
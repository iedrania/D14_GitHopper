package com.iedrania.githopper.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import android.app.Application
import android.content.Context
import com.iedrania.githopper.database.local.entity.FavoriteUser
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class DetailViewModelTest {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var mockContext: Context
    private lateinit var mockedApplication: Application

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        mockContext = Mockito.mock(Context::class.java)
        mockedApplication = Mockito.mock(Application::class.java)
        Mockito.`when`(mockedApplication.applicationContext).thenReturn(mockContext)
        detailViewModel = DetailViewModel(mockedApplication)
    }

    @Test
    fun delete() {
        detailViewModel.insert(
            FavoriteUser(
                "iedrania", "https://avatars.githubusercontent.com/u/68504848?v=4"
            )
        )

        detailViewModel.delete(
            FavoriteUser(
                "iedrania", "https://avatars.githubusercontent.com/u/68504848?v=4"
            )
        )

        val result = detailViewModel.getFavoriteUserByUsername("iedrania")
        assertEquals(null, result.value?.username)
    }
}
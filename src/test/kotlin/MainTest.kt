package ru.netology
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MainTest {

    @Test
    fun updateExisting() {

        val post = WallService.add(Post(
            0,
            11,
            11,
            123,
            "Первая запись",
            comments = Comments(10),
            likes = Likes(15)
        ))

        val update = post.copy(text = "Первая запись новый текст")

        val result = WallService.update(update)

        assertTrue(result)
    }

    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun updateNonExistingPost () {
        val post = Post(id = 123, text = "Новая запись", ownerId = 1, fromId = 1, date = 111)
        val result = WallService.update(post)
        assertFalse(result)
    }


    @Before
    fun clearBeforeTest2() {
        WallService.clear()
    }


    @Test
    fun addPost() {
        val post = WallService.add(Post(
            0,
            1,
            1,
            12345,
            "Первая запись",
            comments = Comments(10),
            likes = Likes(15)
        ))

        assertNotEquals(0, post.id)

    }

}
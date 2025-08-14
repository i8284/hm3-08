package ru.netology
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MainTest {

    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun addNote_success() {
        val note = NoteService.add("Заголовок", "Текст заметки")
        assertEquals(1, note.id)
        assertEquals("Заголовок", note.title)
    }

    @Test
    fun createComment_success() {
        val note = NoteService.add("Заметка 1", "Текст")
        val comment = NoteService.createComment(note.id, fromId = 101, message = "Комментарий тест")
        assertEquals(1, comment.id)
        assertEquals(note.id, comment.noteId)
        assertEquals("Комментарий тест", comment.message)

    }

    @Test(expected = NoteNotFoundException::class)
    fun createComment_toNonExistingNote_shouldThrow() {
        NoteService.createComment(999, fromId = 100, message = "Комментарий")
    }


    @Test(expected = CommentNotFoundException::class)
    fun deleteComment_nonExisting_shouldThrow() {
        NoteService.deleteComment(999)
    }


    @Test
    fun createComment() {
        val post = WallService.add(Post(1, 1, 1, 123, "Текст поста"))
        val comment = Comment(1, fromId = 1, text = "Комментарий")

        val result = WallService.createComment(post.id, comment)

        assertEquals("Комментарий", result.text)
    }

    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        val comment = Comment(1, fromId = 2, text = "Комментарий")
        WallService.createComment(100, comment)
    }

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



    @Test
    fun updateNonExistingPost () {
        val post = Post(id = 123, text = "Новая запись", ownerId = 1, fromId = 1, date = 111)
        val result = WallService.update(post)
        assertFalse(result)
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
package ru.netology

interface Attachments {
    val type: String
}

data class Photo(
    val id: Int,
    val ownerId: Int,
    val albumId: Int
)

class PhotoAttachment(val photo: Photo) : Attachments {
    override val type = "photo"
}

data class Audio(
    val id: Int,
    val ownerId: Int,
    val artist: String,
    val title: String
)

class AudioAttachment(val audio: Audio) : Attachments {
    override val type = "audio"
}

data class Video(
    val id: Int,
    val ownerId: Int,
    val description: String,
    val duration: Int
)

class VideoAttachment(val video: Video) : Attachments {
    override val type = "video"
}

data class File(
    val id: Int,
    val ownerId: Int,
    val size: Int,
    val ext: String
)

class FileAttachment(val file: File) : Attachments {
    override val type = "file"
}

data class Link(
    val url: String,
    val title: String,
    val caption: String,
    val description: String
)

class LinkAttachment(val link: Link) : Attachments {
    override val type = "link"
}

data class Comments(
    val count: Int = 0,
    val canPost: Boolean = true,
    val groupsCanPost: Boolean = true,
    val canClose: Boolean = true,
    val canOpen: Boolean = true
)

data class Comment(
    val id: Int,
    val fromId: Int,
    val text: String
)

data class Likes(
    val count: Int = 0,
    val userLikes: Boolean = false,
    val canLike: Boolean = false,
    val canPublish: Boolean = false
)

data class Post(
    val id: Int,
    val ownerId: Int,
    val fromId: Int?,
    val date: Int,
    val text: String?,
    val friendsOnly: Boolean = false,
    val isFavorite: Boolean = false,
    val markedAsAds: Boolean = false,
    val canDelete: Boolean = true,
    val postType: String = "post",
    val attachments: List<Attachments> = emptyList(),

    val comments: Comments = Comments(),
    val likes: Likes = Likes()

)

class PostNotFoundException(message: String) : RuntimeException(message)

object WallService {
    private var posts = emptyArray<Post>()
    private var nextId: Int = 1
    private var comments = emptyArray<Comment>()
    private var nextCommentId = 1

    fun createComment(postId: Int, comment: Comment): Comment {
        val postExist = posts.any{it.id == postId}

        if(!postExist) {
            throw PostNotFoundException("Пост с id = $postId не найден")
        }

        val newComment = comment.copy(id = nextCommentId++)
        comments += newComment
        return newComment
    }

    fun clear() {
        posts = emptyArray()
        nextId = 1
        comments = emptyArray()
        nextCommentId = 1

    }

    fun add(post: Post): Post {
        //nextId = nextId + 1
        val newPost = post.copy(
            id = nextId++,
            attachments = post.attachments.toList()
        )
        posts += newPost
        return posts.last()
    }

    fun update(post: Post): Boolean {
        for (i in posts.indices) {
            if (posts[i].id == post.id) {
                posts[i] = post.copy()
                return true
            }
        }
        return false

    }

    fun getAll() = posts.toList()



}

fun main() {
    val photo = Photo(1, 1, 3)
    val video = Video(1, 1, "видео", 30)
    val attachments = listOf<Attachments>(PhotoAttachment(photo), VideoAttachment(video))

    val post = Post(
        0,
        1,
        1,
        123,
        null,
        comments = Comments(10),
        likes = Likes(15)
    )

    val post2 = Post(
        1,
        2,
        2,
        345,
        "Вторая запись",
        attachments = attachments,
        comments = Comments(1, canClose = false),
        likes = Likes(154)
    )

    val addedPost1 = WallService.add(post)
    val addedPost2 = WallService.add(post2)


    try {
        val comment1 = Comment(1, 1, text = "Комментарий к записи")
        val addedComment = WallService.createComment(100, comment1)
        println("Комментарий добавлен: $addedComment")
    } catch (e: PostNotFoundException) {
        println("Ошибка: ${e.message}")
    }

    println("Первая запись: $addedPost1")
    println("Вторая запись: $addedPost2")

    //val allPosts = WallService.getAll()
    //println(allPosts)

    val updated1 = addedPost1.copy(text="Новый текст")
    val result = WallService.update(updated1)

    println("Первая запись обновленная: $updated1")
}
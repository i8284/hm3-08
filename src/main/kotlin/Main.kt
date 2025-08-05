package ru.netology

data class Comments(
    val count: Int = 0,
    val canPost: Boolean = true,
    val groupsCanPost: Boolean = true,
    val canClose: Boolean = true,
    val canOpen: Boolean = true
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


    val comments: Comments = Comments(),
    val likes: Likes = Likes()

)

object WallService {
    private var posts = emptyArray<Post>()
    private var nextId: Int = 1

    fun clear() {
        posts = emptyArray()
        nextId = 1
    }

    fun add(post: Post): Post {
        //nextId = nextId + 1
        val newPost = post.copy(
            id = nextId++
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
        comments = Comments(1, canClose = false),
        likes = Likes(154)
    )

    val addedPost1 = WallService.add(post)
    val addedPost2 = WallService.add(post2)

    println("Первая запись: $addedPost1")
    println("Вторая запись: $addedPost2")

    //val allPosts = WallService.getAll()
    //println(allPosts)

    val updated1 = addedPost1.copy(text="Новый текст")
    val result = WallService.update(updated1)

    println("Первая запись обновленная: $updated1")
}
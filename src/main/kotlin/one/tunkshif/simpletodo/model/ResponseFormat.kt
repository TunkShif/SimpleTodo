package one.tunkshif.simpletodo.model

data class ResponseFormat<T>(
    val code: Int = 200,
    val msg: String = "success",
    val success: Boolean = true,
    val data: T
)
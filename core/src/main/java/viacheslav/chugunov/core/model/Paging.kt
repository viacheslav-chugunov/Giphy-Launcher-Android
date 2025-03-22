package viacheslav.chugunov.core.model

class Paging(
    val got: Int,
    val total: Int
) {

    companion object {
        val EMPTY = Paging(0, 0)
    }

}
import com.example.kotlingithubclient.model.SearchedResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchedUserApi {
    @GET("search/users")
    fun getUsers(@Query("q") username: String): Single<SearchedResult>
}

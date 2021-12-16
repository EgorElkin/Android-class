package com.example.zulipapp.presentation.people

import androidx.fragment.app.Fragment
import com.example.zulipapp.R
import com.example.zulipapp.data.api.NetworkUserDataSource
import com.example.zulipapp.data.api.RetrofitBuilder
import com.example.zulipapp.data.database.LocalUserDataSource
import com.example.zulipapp.data.repository.UserRepositoryImpl
import com.example.zulipapp.domain.usecase.SearchUserUseCase
import com.example.zulipapp.domain.usecase.SearchUserUseCaseImpl
import com.example.zulipapp.presentation.Navigator
import com.example.zulipapp.presentation.base.BasePresenter
import com.example.zulipapp.presentation.people.adapter.UserToItemMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PeoplePresenterImpl(peopleView: PeopleView, private var navigator: Navigator?) : BasePresenter<PeopleView>(peopleView), PeoplePresenter {

    @Inject
    lateinit var  searchUserUseCase: SearchUserUseCase
    private val searchSubject: PublishSubject<String> = PublishSubject.create()

    override fun detachView() {
        super.detachView()
        navigator = null
    }

    override fun viewIsReady() {
        subscribeOnPublisher()

//        searchUserUseCase = SearchUserUseCaseImpl(
//            UserRepositoryImpl(
//                NetworkUserDataSource(RetrofitBuilder.userApiService),
//                LocalUserDataSource((getView() as Fragment).requireActivity().applicationContext)
//            )
//        )

        searchUser(ALL_USERS)
    }

    override fun searchRequestChanged(searchQuery: String) {
        searchUser(searchQuery)
    }

    override fun userSelected(userId: Int) {
        navigator?.showProfile(userId)
    }

    private fun searchUser(searchQuery: String){
        searchSubject.onNext(searchQuery)
        getView()?.showLoading()
    }

    private fun subscribeOnPublisher(){
        val disposable = searchSubject
            .subscribeOn(Schedulers.io())
            .map {
                it.replace(Regex("[^A-Za-zА-Яа-я]"), "") // убираем все символы кроме букв
            }
            .distinctUntilChanged()
            .debounce(500, TimeUnit.MILLISECONDS)
            .switchMap { searchQuery -> searchUserUseCase(searchQuery) }
            .map(UserToItemMapper())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                subscribeOnPublisher()
                emptyList()
            }
            .subscribe({
                getView()?.hideLoading()
                getView()?.showUsers(it)
            },{
                getView()?.hideLoading()
                getView()?.showError(R.string.people_loading_error)
            },{
                getView()?.hideLoading()
            })

        compositeDisposable.add(disposable)
    }

    companion object {
        const val ALL_USERS = ""
    }
}
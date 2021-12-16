package com.example.zulipapp.presentation.profile

import androidx.fragment.app.Fragment
import com.example.zulipapp.R
import com.example.zulipapp.data.api.NetworkUserDataSource
import com.example.zulipapp.data.api.RetrofitBuilder
import com.example.zulipapp.data.database.LocalUserDataSource
import com.example.zulipapp.data.repository.UserRepositoryImpl
import com.example.zulipapp.domain.usecase.*
import com.example.zulipapp.presentation.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfilePresenterImpl(profileView: ProfileView) : BasePresenter<ProfileView>(profileView) , ProfilePresenter{

    @Inject
    lateinit var  getUserUseCase: GetUserByIdUseCase
    @Inject
    lateinit var  getUserPresenceUseCase: GetUserStatusUseCase

    override fun attachView(view: ProfileView){
        super.attachView(view)
//        getUserUseCase = GetUserByIdUseCaseImpl(
//            UserRepositoryImpl(
//                NetworkUserDataSource(RetrofitBuilder.userApiService),
//                LocalUserDataSource((getView() as Fragment).requireContext())
//            )
//        )
//        getUserPresenceUseCase = GetUserStatusUseCaseImpl(
//            UserRepositoryImpl(
//                NetworkUserDataSource((RetrofitBuilder.userApiService)),
//                LocalUserDataSource((getView() as Fragment).requireContext())
//            )
//        )
    }

    override fun viewIsReady(userId: Int) {
        getView()?.showLoading()
        compositeDisposable.add(
            getUserUseCase(userId)
                .subscribeOn(Schedulers.io())
                .toObservable()
                .map(UserToProfileItemMapper())
                .flatMap({ profile ->
                    getUserPresenceUseCase(profile.id).toObservable()
                },{ profile, status ->
                    profile.status = status.status
                    profile
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getView()?.hideLoading()
                    getView()?.showUser(it)
                },{
                    getView()?.hideLoading()
                    getView()?.showError(R.string.profile_loading_error)
                })
        )
    }
}
package com.example.zulipapp.presentation.profile

import com.example.zulipapp.data.Repository
import java.lang.ref.WeakReference
import kotlin.random.Random

class ProfilePresenterImpl : ProfilePresenter{

    private var view: ProfileView? = null

    override fun loadUser() {
        val response = Repository.getUser(Random.nextInt(0, 3))
        if(response.isNullOrEmpty()){
            view?.showError("Не удалось загрузить пользователя, попробуйте еще раз")
        } else {
            view?.showUser(response)
        }
    }

    fun attachView(profileView: ProfileView){
        println("debug: attachView $profileView")
        view = profileView
    }

    fun detachView(profileView: ProfileView){

        if(view == profileView){
            println("debug: detachView: view == profileVIew")
//            view?.clear()
//            view = null
        }
        else {
            println("debug: detachView: view != profileVIew")
        }
    }
}
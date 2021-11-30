package com.example.zulipapp.presentation.base


interface MvpPresenter<V : MvpView> {

    fun attachView(view: V)

    fun detachView()
}
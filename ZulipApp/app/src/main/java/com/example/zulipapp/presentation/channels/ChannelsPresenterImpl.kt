package com.example.zulipapp.presentation.channels

import com.example.zulipapp.App
import com.example.zulipapp.data.api.NetworkStreamDataSource
import com.example.zulipapp.data.api.RetrofitBuilder
import com.example.zulipapp.data.repository.StreamRepositoryImpl
import com.example.zulipapp.di.ChannelsModule
import com.example.zulipapp.di.DaggerActivityComponent
import com.example.zulipapp.di.DaggerChannelsComponent
import com.example.zulipapp.domain.usecase.*
import com.example.zulipapp.presentation.Navigator
import com.example.zulipapp.presentation.base.BasePresenter
import com.example.zulipapp.presentation.channels.adapter.ChannelsItem
import com.example.zulipapp.presentation.channels.adapter.StreamToItemMapper
import com.example.zulipapp.presentation.channels.adapter.TopicToItemMapper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ChannelsPresenterImpl(
    channelsView: ChannelsView,
    private var navigator: Navigator?
) : BasePresenter<ChannelsView>(channelsView), ChannelsPresenter {

    @Inject
    @ChannelsModule.SearchSubscribedQualifier
    lateinit var searchSubscribedStreamsUseCase: SearchStreamUseCase
    @Inject
    @ChannelsModule.SearchAllQualifier
    lateinit var searchAllStreamsUseCase: SearchStreamUseCase
    @Inject
    lateinit var getTopicsUseCase: GetTopicsUseCase
    private val searchSubscribedSubject: PublishSubject<String> = PublishSubject.create()
    private val searchAllSubject: PublishSubject<String> = PublishSubject.create()

        init {
            println("debug: ChannelsPresenter: INIT")
            DaggerChannelsComponent.factory()
                .create(DaggerActivityComponent.factory()
                    .create(((getView() as ChannelsFragment).requireActivity().application as App).appComponent)
                ).inject(this)
//            println("debug: sub=${searchSubscribedStreamsUseCase::class}")
//            println("debug: all=${searchAllStreamsUseCase::class}")
    }

    override fun detachView() {
        super.detachView()
        navigator = null
    }

    override fun viewIsReady() {
        subscribeOnSubscribedPublisher()
        subscribeOnAllPublisher()
//println("debug: sub=${searchSubscribedStreamsUseCase::class}")
//println("debug: all=${searchAllStreamsUseCase::class}")
//        DaggerChannelsComponent.factory()
//            .create(DaggerActivityComponent.factory()
//                .create(((getView() as ChannelsFragment).requireActivity().application as App).appComponent)
//            ).inject(this)


//        searchSubscribedStreamsUseCase = SearchSubscribedStreamUseCaseImpl(
//            StreamRepositoryImpl(
//                NetworkStreamDataSource(RetrofitBuilder.streamApiService),
////                LocalStreamDataSource((getView() as Fragment).requireActivity().applicationContext)
//            )
//        )
//        searchAllStreamsUseCase = SearchAllStreamUseCaseImpl(
//            StreamRepositoryImpl(
//                NetworkStreamDataSource(RetrofitBuilder.streamApiService),
////                LocalStreamDataSource((getView() as Fragment).requireActivity().applicationContext)
//            )
//        )
//        getTopicsUseCase = GetTopicsUseCaseImpl(
//            StreamRepositoryImpl(
//                NetworkStreamDataSource(RetrofitBuilder.streamApiService),
////                LocalStreamDataSource((getView() as Fragment).requireActivity().applicationContext)
//            )
//        )

        searchSubscribedStreams(ALL_STREAMS)
        searchAllStreams(ALL_STREAMS)
//        oneSearchSubscribedStreams(ALL_STREAMS)
//        oneSearchAllStreams(ALL_STREAMS)
    }

    private fun searchSubscribedStreams(searchQuery: String){
        searchSubscribedSubject.onNext(searchQuery)
    }

    private fun searchAllStreams(searchQuery: String){
        searchAllSubject.onNext(searchQuery)
    }

    private fun subscribeOnSubscribedPublisher() {
        var streamsList = emptyList<ChannelsItem.StreamItem>()
        var counter = 0
        val disposable = searchSubscribedSubject
            .subscribeOn(Schedulers.io())
            .distinctUntilChanged()
            .debounce(500, TimeUnit.MILLISECONDS)
            .switchMap { searchQuery -> searchSubscribedStreamsUseCase(searchQuery) }
            .map(StreamToItemMapper())
            .flatMap { streams ->
                streamsList = streams
                Observable.fromIterable(streams)
            }
            .flatMap({stream ->
                println("debug: stream -> topics: ${stream.name}")
                getTopicsUseCase(stream.id).map(TopicToItemMapper())
            }, {stream, topics ->
                topics.map { it.streamName = stream.name }
                streamsList[counter].topics = topics
                counter++
                if(counter == streamsList.size){
                    counter = 0
                    return@flatMap streamsList as List<ChannelsItem>
                } else {
                    return@flatMap emptyList()
                }
//                println("debug: stream + topics: ${stream.name}")
//                topics.map { it.streamName = stream.name }
//                stream.topics = topics
//                stream as ChannelsItem
            })
//            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                println("debug: onSuccess")
                if(list.isNotEmpty()){
                    println("debug: List is Not Empty")
                    getView()?.showSubscribedStreams(list)
                } else {
                    println("debug: List is Empty")
                }
            },{
                println("debug: onError: $it")
            })
        compositeDisposable.add(disposable)
    }

    private fun subscribeOnAllPublisher(){
        var streamsList = emptyList<ChannelsItem.StreamItem>()
        var counter = 0
        val disposable = searchAllSubject
            .subscribeOn(Schedulers.io())
            .distinctUntilChanged()
            .debounce(500, TimeUnit.MILLISECONDS)
            .switchMap { searchQuery -> searchAllStreamsUseCase(searchQuery) }
            .map(StreamToItemMapper())
            .flatMap { streams ->
                streamsList = streams
                Observable.fromIterable(streams)
            }
            .flatMap({stream ->
                println("debug: stream -> topics: ${stream.name}")
                getTopicsUseCase(stream.id).map(TopicToItemMapper())
            }, {stream, topics ->
                topics.map { it.streamName = stream.name }
                streamsList[counter].topics = topics
                counter++
                if(counter == streamsList.size){
                    counter = 0
                    return@flatMap streamsList as List<ChannelsItem>
                } else {
                    return@flatMap emptyList()
                }
//                println("debug: stream + topics: ${stream.name}")
//                topics.map { it.streamName = stream.name }
//                stream.topics = topics
//                stream as ChannelsItem
            })
//            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                println("debug: onSuccess")
                getView()?.showAllStreams(list)
            },{
                println("debug: onError: $it")
            })
        compositeDisposable.add(disposable)
    }

    private fun oneSearchSubscribedStreams(searchQuery: String){
        val disposable = searchSubscribedStreamsUseCase(searchQuery)
            .subscribeOn(Schedulers.io())
            .map(StreamToItemMapper())
            .flatMap { streams -> Observable.fromIterable(streams) }
            .flatMap({stream ->
                println("debug: stream -> topics: ${stream.name}")
                getTopicsUseCase(stream.id).map(TopicToItemMapper())
            }, {stream, topics ->
                println("debug: stream + topics: ${stream.name}")
                topics.map { it.streamName = stream.name }
                stream.topics = topics
                stream as ChannelsItem
            })
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                println("debug: onSuccess")
                getView()?.showSubscribedStreams(list)
            },{
                println("debug: onError: $it")
            })

        compositeDisposable.add(disposable)
    }
    private fun oneSearchAllStreams(searchQuery: String){
        val disposable = searchAllStreamsUseCase(searchQuery)
            .subscribeOn(Schedulers.io())
            .map(StreamToItemMapper())
            .flatMap { streams -> Observable.fromIterable(streams) }
            .flatMap({stream ->
                println("debug: stream -> topics: ${stream.name}")
                getTopicsUseCase(stream.id).map(TopicToItemMapper())
            }, {stream, topics ->
                println("debug: stream + topics: ${stream.name}")
                topics.map { it.streamName = stream.name }
                stream.topics = topics
                stream as ChannelsItem
            })
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                println("debug: onSuccess")
                getView()?.showAllStreams(list)
            },{
                println("debug: onError: $it")
            })

        compositeDisposable.add(disposable)
    }

    // ChannelsPresenter
    override fun searchRequestChanged(searchQuery: String) {
        TODO("Not yet implemented")
    }

    override fun searchButtonClicked(searchQuery: String) {
        searchSubscribedSubject.onNext(searchQuery)
//        oneSearchAllStreams(searchQuery)
//        oneSearchSubscribedStreams(searchQuery)
    }

    override fun allStreamsListSelected(searchQuery: String) {
        searchAllStreams(searchQuery)
    }

    override fun subscribedStreamsListSelected(searchQuery: String) {
        searchSubscribedStreams(searchQuery)
    }

    override fun streamSelected(streamName: String) {
        navigator?.showChat(streamName, null)
    }

    override fun topicSelected(streamName: String, topicName: String) {
        navigator?.showChat(streamName, topicName)
    }

    companion object {
        const val ALL_STREAMS = ""
    }
}
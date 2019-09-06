package com.demo.study.ui.ac.course.playvideo

import android.support.v7.widget.LinearLayoutManager
import com.demo.study.R
import com.demo.study.adapter.CourseCatalogAdapter
import com.demo.study.base.BaseActivity
import com.demo.study.bean.CourseCatalogBean
import com.demo.study.interfaces.IClickItemListener
import com.xiao.nicevideoplayer.NiceVideoPlayer
import com.xiao.nicevideoplayer.NiceVideoPlayerManager
import com.xiao.nicevideoplayer.TxVideoPlayerController
import kotlinx.android.synthetic.main.base_title_layout.*
import kotlinx.android.synthetic.main.play_video_layout.*

/**
 * Created by cheng on 2019/9/4.
 */
class PlayVideoActivity :BaseActivity(), IClickItemListener<Int> {
    private var controller:TxVideoPlayerController?=null
    private val videoList= arrayListOf<CourseCatalogBean>()
    private val courseAdapter by lazy { CourseCatalogAdapter(this@PlayVideoActivity,videoList,this@PlayVideoActivity ) }

    override fun setTitleId(): Int = R.layout.base_title_layout

    override fun setContentId(): Int = R.layout.play_video_layout

    override fun titleString(): String = ""

    override fun initView() {
        super.initView()
        setAdapter()
        initPlayer()
    }

    private fun initPlayer() {
        video_player.setPlayerType(NiceVideoPlayer.TYPE_IJK)
        controller = TxVideoPlayerController(this)
        video_player.setController(controller)
        clickItem(intent.getIntExtra("pos",0))
    }

    private fun setAdapter() {
        val list = intent.getSerializableExtra("list") as ArrayList<CourseCatalogBean>
        this.videoList.addAll(list)
        recyclerView.apply {
            layoutManager= LinearLayoutManager(this@PlayVideoActivity)
            adapter=courseAdapter
        }
    }

    override fun clickItem(t: Int) {
        val bean = videoList[t]
        controller?.playChooseVideo(bean.videoUrl,bean.title)
        base_title_text.setText(bean.title)
        courseAdapter?.setPlaying(t)
    }

    override fun onStop() {
        super.onStop()
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer()
    }

    override fun onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return
        super.onBackPressed()
    }
}
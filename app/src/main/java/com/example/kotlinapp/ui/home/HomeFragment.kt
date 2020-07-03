package com.example.kotlinapp.ui.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinapp.*
import kotlinx.android.synthetic.main.adapter_items.*
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import kotlin.collections.ArrayList
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class HomeFragment : Fragment() {

    private lateinit var mRecycleView: RecyclerView
    private lateinit var mAdapter: RecyclerView.Adapter<*>
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var shoutbox: ArrayList<MessageAPI>



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        shoutbox = ArrayList<MessageAPI>()
        mRecycleView = root.findViewById(R.id.recyclerView) as RecyclerView
        mRecycleView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(activity)
        mAdapter = Adapter(shoutbox)
        mRecycleView.setLayoutManager(mLayoutManager)
        mRecycleView.setAdapter(mAdapter)

        val swipeHandler = object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = mRecycleView.adapter as Adapter
                val currentid = adapter.idOfCurrent(viewHolder.adapterPosition)


                val url = "http://tgryl.pl/shoutbox/message/" + currentid
                var client = OkHttpClient()
                val request = Request.Builder()
                    .url(url)
                    .delete()
                    .build()
                client.newCall(request).enqueue(object : okhttp3.Callback {
                    override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {

                    }

                    override fun onFailure(call: okhttp3.Call, e: IOException) {
                    }
                })
                Handler().postDelayed({
                }, 500)

                (viewHolder.adapterPosition)?.let {
                    Integer.parseInt(
                        it.toString()
                    )
                }?.let { adapter.content.removeAt(it) }
                adapter.notifyDataSetChanged()

            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler).attachToRecyclerView(mRecycleView);

            val retrofit = Retrofit.Builder().baseUrl("http://tgryl.pl/shoutbox/")
                .addConverterFactory(GsonConverterFactory.create()).build()
            val service = retrofit.create(MessageInterface::class.java)
            val call = service.getMessages()
            call!!.enqueue(object : Callback<List<Post?>?> {
                override fun onResponse(
                    call: Call<List<Post?>?>,
                    response: Response<List<Post?>?>
                ) {
                    val list: List<Post?>? = response.body();
                    if (list != null) {
                        list.forEach {
                            if (it != null) {
                                shoutbox.add(MessageAPI(it.content, it.login, it.date, it.id))
                            }
                        }
                        mAdapter.notifyDataSetChanged()
                        mRecycleView.scrollToPosition(list.size - 1)
                    };
                }

                override fun onFailure(call: Call<List<Post?>?>, t: Throwable) {
                    println(t.message)
                }
            })
            return root
        }


}



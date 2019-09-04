package com.rahul.expandablerecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getData()
    }

    fun getData(){
        var url = "http://chandankanya.com/webservice/product.php"
        val queue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.e("saasadsadsad",""+response)
                var strResp = response.toString()
                val jsonObj: JSONObject = JSONObject(strResp)
                var status=jsonObj.getString("code")
                Log.e("qwwwwwwwwww",""+status)
                if (status.equals("200")){
                    val mCat:ArrayList<ItemVO> = ArrayList()
                    var jsonArray=jsonObj.getJSONArray("data")
                    Log.e("xxxxxxxxxxxxxxxxx",""+jsonArray)
                    for (i in 0 until jsonArray.length()){
                        var jsonObject=jsonArray.getJSONObject(i)
                        var catname=jsonObject.getString("catname")

                        var jsonArrayInner=jsonObject.getJSONArray("product")
                        val mSubcat:ArrayList<SubItemVO> = ArrayList()

                        for (j in 0 until jsonArrayInner.length()){
                            val jsonObjectInner=jsonArrayInner.getJSONObject(j)
                            val name=jsonObjectInner.getString("name") as String
                            val Subcat=SubItemVO(name)
                            mSubcat.add(Subcat)
                            Log.e("xxxxxxxxxxxxxxxxx",""+name)
                        }
                        val mainCat=ItemVO(catname,mSubcat)
                        mCat.add(mainCat)
                    }
                    rvList.layoutManager = LinearLayoutManager(this)
                    rvList.adapter = ItemsAdapter(mCat, true)
                }




            },
            Response.ErrorListener {
                Log.e("saasadsadsad",""+it)
            })

        queue.add(request)
        queue.start()
    }
}

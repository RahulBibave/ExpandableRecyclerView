package com.rahul.expandablerecyclerview

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_layout.view.*
import kotlinx.android.synthetic.main.subitem_layout.view.*


class ItemsAdapter(private val itemsList: ArrayList<ItemVO>, private val isExpandable: Boolean) :
    RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
    )

    override fun getItemCount() = itemsList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.bind(itemsList[position])

    inner class ItemViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(itemVO: ItemVO) {
            if (isExpandable) {
                contentItems.visibility = View.GONE
                imgArrow.rotation = 0f
                imgArrow.visibility = View.VISIBLE
            }
            containerView.card.setOnClickListener {
                if (isExpandable) {
                    if (contentItems.visibility == View.VISIBLE) {
                        imgArrow.animate().apply {
                            rotation(0f)
                            start()
                        }
                        fadeText(contentItems, 1f, 0f, 200)
                        contentItems.visibility = View.GONE
                    } else {
                        imgArrow.animate().apply {
                            rotation(180f)
                            start()
                        }
                        contentItems.visibility = View.VISIBLE
                        fadeText(contentItems, 0f, 1f, 700)
                        transformationY(contentItems, -50f, 0f, 200)
                    }
                }
            }

            txtTitle.text = itemVO.title

            itemVO.subItemsList.forEach {
                val view =
                    LayoutInflater.from(containerView.context).inflate(R.layout.subitem_layout,contentItems , false)
                view.txtDescription.text = it.name
                contentItems.addView(view)
            }

        }

        private fun fadeText(view: View, from: Float, to: Float, duration: Long) {
            val fadeIn = AlphaAnimation(from, to)
            fadeIn.interpolator = DecelerateInterpolator()
            fadeIn.duration = duration

            val animation = AnimationSet(false)
            animation.addAnimation(fadeIn)
            view.animation = animation
        }

        private fun transformationY(view: View, from: Float, to: Float, duration: Long) {
            val valueAnimator = ValueAnimator.ofFloat(from, to)
            valueAnimator.interpolator = AccelerateDecelerateInterpolator()
            valueAnimator.duration = duration
            valueAnimator.addUpdateListener {
                val progress = it.animatedValue as Float
                view.translationY = progress
            }
            valueAnimator.start()
        }
    }
}
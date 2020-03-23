package `in`.tracker.core.utils

import `in`.tracker.core.adapter.BaseRecyclerAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.io.File


class BindingUtil {

    companion object {

        @JvmStatic
        @BindingAdapter(value = ["items"])
        fun configureAdapter(recyclerView: RecyclerView, items: List<*>) {
            try {
                val mLayoutManager = LinearLayoutManager(recyclerView.context)
                recyclerView.layoutManager = mLayoutManager
                val adapter = recyclerView.adapter as BaseRecyclerAdapter
                adapter.items = items
                recyclerView.adapter = adapter
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, imageUrl: String?) {
            if (!imageUrl.isNullOrEmpty()) {
                Glide.with(view.context)
                    .load(imageUrl)
                    .into(view)
            }
        }

        @JvmStatic
        @BindingAdapter("android:src")
        fun setImageResource(imageView: ImageView, resource: Int) {
            imageView.setImageResource(resource)
        }

        @JvmStatic
        @BindingAdapter("android:text")
        fun setDoubleText(textView: TextView, value: Double) {
            textView.text = value.toAmountString()
        }

        @JvmStatic
        @BindingAdapter("customTextColor")
        fun setCustomTextColor(textView: TextView, @ColorRes color: Int) {
            textView.setTextColor(ContextCompat.getColor(textView.context, color))
        }

        @JvmStatic
        @BindingAdapter("source", "placeholder")
        fun setImageWithPlaceholder(imageView: ImageView, source: String?, placeholder: Int) {
            source?.let { imageView.scaleType = ImageView.ScaleType.CENTER_CROP }
            Glide.with(imageView.context)
                .load(source?.let { File(it) })
                .placeholder(placeholder)
                .into(imageView)
        }

        @JvmStatic
        @BindingAdapter("android:textAmount")
        fun setDoubleAmountText(textView: TextView, value: Double) {
            textView.text = value.toAmountString()
        }
    }
}
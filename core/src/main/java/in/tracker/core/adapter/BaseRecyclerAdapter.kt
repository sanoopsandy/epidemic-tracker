package `in`.tracker.core.adapter

import `in`.tracker.core.BR
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

// Common adapter to load the recyclerView
class BaseRecyclerAdapter : RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder>() {

    @LayoutRes
    var layoutId: Int = 0

    lateinit var items: List<*>
    var onCustomClickItemListener = fun(view: View, position: Int): Unit = Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, layoutId, parent, false)
        return BaseViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateAdapterItems(newItems: List<*>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class BaseViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ob: Any?) {
            binding.setVariable(BR.model, ob)
            binding.setVariable(BR.handler, this)
            binding.setVariable(BR.position, adapterPosition)
            binding.executePendingBindings()
        }

        fun onCustomClick(view: View, position: Int) {
            onCustomClickItemListener(view, position)
        }
    }

    fun setModel(updates: List<*>) {
        items = updates
    }
}
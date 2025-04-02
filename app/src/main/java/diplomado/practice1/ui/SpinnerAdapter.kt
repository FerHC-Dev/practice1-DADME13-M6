package diplomado.practice1.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import diplomado.practice1.R

class SpinnerAdapter(
    context: Context,
    private val items: Array<String>
) : ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.spinner_item, parent, false)
        }

        val textView: TextView = view!!.findViewById(R.id.spinner_item_text)
        val imageView: ImageView = view.findViewById(R.id.spinner_item_icon)

        // Establece el texto
        textView.text = items[position]
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent) // Usamos el mismo layout para el desplegable
    }
}
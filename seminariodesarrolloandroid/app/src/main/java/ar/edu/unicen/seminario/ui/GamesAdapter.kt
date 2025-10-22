package ar.edu.unicen.seminario.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ar.edu.unicen.seminario.R
import ar.edu.unicen.seminario.ddl.models.ItemGames
import com.bumptech.glide.Glide

class GamesAdapter(
    private val games: List<ItemGames>
) : RecyclerView.Adapter<GamesAdapter.GameViewHolder>() {

    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivGameImage: ImageView = itemView.findViewById(R.id.ivGameImage)
        val tvGameName: TextView = itemView.findViewById(R.id.tvGameName)
        val tvGameReleased: TextView = itemView.findViewById(R.id.tvGameReleased)
        val tvGameRating: TextView = itemView.findViewById(R.id.tvGameRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]
        holder.tvGameName.text = game.name
        holder.tvGameReleased.text = "Fecha: ${game.relased ?: "Sin fecha"}"
        holder.tvGameRating.text = "Rating: ${game.rating ?: 0.0}"

        // Cargar imagen (usando Glide o Coil)
        Glide.with(holder.itemView.context)
            .load(game.backgroundImage) // Asegurate que `imageUrl` exista en tu modelo
            .placeholder(R.drawable.logo_celeste)
            .into(holder.ivGameImage)
    }

    override fun getItemCount(): Int = games.size
}
package com.example.peduli_pangan_versipemmob.adapter 

class RestaurantAdapter(
    private val onRestaurantClick: (Restaurant) -> Unit
) : ListAdapter<Restaurant, RestaurantAdapter.RestaurantViewHolder>(RestaurantDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = ItemRestaurantBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RestaurantViewHolder(
        private val binding: ItemRestaurantBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(restaurant: Restaurant) {
            binding.apply {
                tvRestaurantName.text = restaurant.name
                tvRestaurantAddress.text = restaurant.address
                tvRating.text = restaurant.rating.toString()
                tvDeliveryTime.text = restaurant.deliveryTime
                tvDeliveryFee.text = "Rp ${restaurant.deliveryFee}"

                // Status indicator
                tvStatus.isVisible = !restaurant.isOpen
                tvStatus.text = if (restaurant.isOpen) "Buka" else "Tutup"

                // Categories
                tvCategories.text = restaurant.categories.joinToString(", ")

                if (restaurant.imageUrl.isNotEmpty()) {
                    Glide.with(ivRestaurantImage.context)
                        .load(restaurant.imageUrl)
                        .into(ivRestaurantImage)
                }

                root.setOnClickListener {
                    onRestaurantClick(restaurant)
                }
            }
        }
    }

    private class RestaurantDiffCallback : DiffUtil.ItemCallback<Restaurant>() {
        override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem == newItem
        }
    }
}
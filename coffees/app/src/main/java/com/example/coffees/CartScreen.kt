package com.example.coffees

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun CartScreen(navController: NavHostController? = null) {
    val customFont = FontFamily(Font(R.font.krona))
    var isDelivery by remember { mutableStateOf(true) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF9F8))
            .padding(16.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController?.navigateUp() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Text(
                text = "Order",
                fontSize = 20.sp,
                fontFamily = customFont,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        // Delivery/Pickup Toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { isDelivery = true },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isDelivery) Color(0xFF4AAD4F) else Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Deliver",
                    color = if (isDelivery) Color.White else Color.Black,
                    fontFamily = customFont
                )
            }
            Button(
                onClick = { isDelivery = false },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isDelivery) Color(0xFF4AAD4F) else Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Pick up",
                    color = if (!isDelivery) Color.White else Color.Black,
                    fontFamily = customFont
                )
            }
        }

        // Delivery Address
        if (isDelivery) {
            Text(
                text = "Delivery Address",
                fontSize = 16.sp,
                fontFamily = customFont,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Armenia\n221/5c Hovard, Armenia",
                        fontFamily = customFont,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        // Cart Items
        CartManager.cartItems.forEach { (coffee, quantity) ->
            CartItemCard(coffee = coffee, quantity = quantity)
        }

        // Discounts Applied
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            color = Color.White,
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Discounts Applied",
                    fontFamily = customFont,
                    fontSize = 14.sp
                )
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = "Arrow",
                    tint = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Payment Summary
        Text(
            text = "Payment Summary",
            fontSize = 16.sp,
            fontFamily = customFont,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Price",
                fontFamily = customFont,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = "$${String.format("%.1f", CartManager.getTotal())}",
                fontFamily = customFont,
                fontSize = 14.sp
            )
        }
        if (isDelivery) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Delivery Fee",
                    fontFamily = customFont,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "$${String.format("%.1f", CartManager.getDeliveryFee())}",
                    fontFamily = customFont,
                    fontSize = 14.sp
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total Payment",
                fontFamily = customFont,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$${String.format("%.1f", CartManager.getTotalWithDelivery())}",
                fontFamily = customFont,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Order Button
        Button(
            onClick = {
                // Check if the cart is empty
                if (CartManager.cartItems.isEmpty()) {
                    // Show a Toast message if the cart is empty
                    Toast.makeText(context, "Cannot place an order without products!", Toast.LENGTH_SHORT).show()
                } else {
                    // Clear the cart before navigating to the success screen
                    CartManager.clearCart()

                    // Navigate to the SuccessActivity
                    val intent = Intent(context, SuccessActivity::class.java)
                    context.startActivity(intent)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4AAD4F)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Order",
                fontFamily = customFont,
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}
@Composable
fun CartItemCard(coffee: CoffeeItem, quantity: Int) {
    val customFont = FontFamily(Font(R.font.krona))

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        color = Color.White,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = coffee.image),
                contentDescription = coffee.name,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp)
            ) {
                Text(
                    text = coffee.name,
                    fontFamily = customFont,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = coffee.description,
                    fontFamily = customFont,
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }



            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = { CartManager.removeFromCart(coffee) },
                    modifier = Modifier
                        .size(20.dp)
                        .background(Color(0xFFEEEEEE), RoundedCornerShape(4.dp))
                ) {
                    Text(
                        text = "-",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = quantity.toString(),
                    fontFamily = customFont,
                    fontSize = 14.sp,
                            modifier = Modifier.padding(horizontal = 10.dp) // Add horizontal padding,
                )
                IconButton(
                    onClick = { CartManager.addToCart(coffee) },
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color(0xFF4AAD4F), RoundedCornerShape(4.dp))
                ) {
                    Text(
                        text = "+",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

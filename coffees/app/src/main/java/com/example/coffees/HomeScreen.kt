package com.example.coffees

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) { // Add navController parameter

    var searchText by remember { mutableStateOf("") }
    var selectedCoffee by remember { mutableStateOf<CoffeeItem?>(null) }
    val customFont = FontFamily(Font(R.font.krona))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF010C02))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Hello",
                            color = Color.Gray,
                            fontSize = 18.sp,
                            fontFamily = customFont,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(modifier = Modifier.height(20.dp))


                        Text(
                            text = "Aisha",
                            color = Color.White,
                            fontSize = 25.sp,
                            fontFamily = customFont,
                            fontWeight = FontWeight.Bold,
                            letterSpacing =0.sp
                        )
                    }

                    Surface(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { // Add clickable modifier
                                navController.navigate("profiles") // Navigate to ProfileScreen
                            },
                        color = Color(0xFF4AAD4F)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.gprofile),
                            contentDescription = "Profile",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                        .height(56.dp),
                    color = Color(0x80424242),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        BasicTextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            modifier = Modifier.weight(1f),
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.White,
                                fontSize = 16.sp,
                                fontFamily = customFont
                            ),
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (searchText.isEmpty()) {
                                        Text(
                                            "Search your favor",
                                            color = Color.Gray,
                                            fontSize = 16.sp,
                                            fontFamily = customFont
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Surface(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            color = Color(0xFF4AAD4F)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.filt),
                                contentDescription = "Filter",
                                tint = Color.White,
                                modifier = Modifier
                                    .padding(7.dp)
                                    .size(24.dp)
                            )
                        }
                    }
                }
            }

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                CoffeeGridSection(
                    selectedCoffee = selectedCoffee,
                    onCoffeeSelected = { coffee ->
                        navController.navigate("product/${coffee.name}")
                    }
                )
            }
        }
    }
}

@Composable
private fun CoffeeGridSection(
    selectedCoffee: CoffeeItem?,
    onCoffeeSelected: (CoffeeItem) -> Unit
) {
    val customFont = FontFamily(Font(R.font.krona))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Glow Differently !",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            fontFamily = customFont,
            color = Color.DarkGray,
            modifier = Modifier.padding(vertical = 18.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(coffeeList) { coffee ->
                CoffeeCard(
                    coffee = coffee,
                    onClick = { onCoffeeSelected(coffee) }
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeCard(
    coffee: CoffeeItem,
    onClick: () -> Unit
) {
    val customFont = FontFamily(Font(R.font.krona))
    var isFavorite by remember { mutableStateOf(FavoriteManager.isFavorite(coffee)) }

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
            ) {
                Image(
                    painter = painterResource(id = coffee.image),
                    contentDescription = coffee.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                IconButton(
                    onClick = {
                        if (isFavorite) {
                            FavoriteManager.removeFromFavorites(coffee)
                        } else {
                            FavoriteManager.addToFavorites(coffee)
                        }
                        isFavorite = !isFavorite
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                        .size(30.dp)
                        .background(Color.Transparent.copy(alpha = 0.8f), CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.heart),
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color(0xFF4AAD4F) else Color.White,
                        modifier = Modifier.size(23.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = coffee.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = customFont,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Text(
                    text = coffee.description,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    maxLines = 1,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 5.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = coffee.price,
                        fontFamily = customFont,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    // Add to Cart Button
                    IconButton(
                        onClick = {
                            CartManager.addToCart(coffee) // Add the coffee item to the cart
                        },
                        modifier = Modifier
                            .padding(10.dp)
                            .size(20.dp)
                            .background(Color(0xFF4AAD4F), shape = RoundedCornerShape(10.dp))
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.add),
                            contentDescription = "Add to cart",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}
data class CoffeeItem(
    val name: String,
    val image: Int,
    val price: String,
    val description: String = "",
    val rating: Float = 4.5f,
    val rate: String
)

val coffeeList = listOf(
    CoffeeItem(
        name = "Cleanser",
        image = R.drawable.cleanser,
        price = "$15.8",
        description = "With the Aloevera extra, antioxide clensers sense.",
        rate ="4.6"
    ),
    CoffeeItem(
        name = "Nuxe Organic ",
        image = R.drawable.moisturing,
        price = "$12.4",
        description = "A creamy orgain moisturising cream allow to hydration.",
        rate = "4.8"
    ),
    CoffeeItem(
        name = "Emerald Ampoule",
        image = R.drawable.emerald,
        price = "$8.9",
        description = "Skin1004 Madagascar Cantalla whightier",
        rate = "4.9"
    ),
    CoffeeItem(
        name = "Lavender Mist",
        image = R.drawable.mist,
        price = "$13.5",
        description = "Lavender fragrance mist for pleasent day",
        rate = "5.0"
    ),
    CoffeeItem(
        name = "Lotus Cream",
        image = R.drawable.lotus,
        price = "$13.5",
        description = "Lavender fragrance mist for pleasent day",
        rate = "5.0"

    ),

    CoffeeItem(
        name = "Exphole serum",
        image = R.drawable.exphole,
        price = "$13.5",
        description = "Lavender fragrance mist for pleasent day",
        rate = "5.0"
    ),
)

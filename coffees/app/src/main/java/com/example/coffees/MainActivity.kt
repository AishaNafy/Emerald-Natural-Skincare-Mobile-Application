package com.example.coffees

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
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
import androidx.navigation.compose.*
import com.example.coffees.ui.theme.CoffeesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if the user is logged in
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

        if (!isLoggedIn) {
            // If the user is not logged in, redirect to the SignIn activity
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
            finish() // Finish MainActivity to prevent the user from coming back
        } else {
            // If the user is logged in, proceed to the home screen
            setContent {
                CoffeesTheme {
                    AppNavigation()
                }
            }
        }
    }
}
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // Check if the user is logged in
    val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

    if (!isLoggedIn) {
        // Redirect to SignIn activity if not logged in
        val intent = Intent(context, SignIn::class.java)
        context.startActivity(intent)
    } else {
        // Show the home screen if logged in
        Scaffold(
            bottomBar = { BottomNavigationBar(navController) }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                NavigationGraph(navController)
            }
        }
    }
}

@Composable
fun NavigateToNotification() {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        val intent = Intent(context, NotificationActivity::class.java)
        context.startActivity(intent)
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("favorite") { FavoriteScreen() }
        composable("cart") { CartScreen(navController) }
        composable("profile") { NotificationScreen() }
        composable("profiles") { ProfileScreen() }
        composable("product/{coffeeId}") { backStackEntry ->
            val coffeeId = backStackEntry.arguments?.getString("coffeeId")
            ProductDetailScreen(coffeeId, navController)
        }

        composable("cart") { CartScreen(navController) }
        composable("success") {
            val context = LocalContext.current
            LaunchedEffect(Unit) {
                val intent = Intent(context, SuccessActivity::class.java)
                context.startActivity(intent)
            }
        }
//        composable("profile") { ProfileScreen() }
    }
}

@Composable
fun ProductDetailScreen(coffeeId: String?, navController: NavHostController) {
    val coffee = coffeeList.find { it.name == coffeeId }
    if (coffee == null) {
        Text("Coffee not found!")
        return
    }

    var selectedSize by remember { mutableStateOf("M") }
    val customFont = FontFamily(Font(R.font.krona))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF9F8))
    ) {
        // Top Bar with Back Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Text(
                text = coffee.name,
                fontSize = 20.sp,
                fontFamily = customFont,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        // Coffee Image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = coffee.image),
                contentDescription = coffee.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        }

        // Rating
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Rating",
                tint = Color(0xFFFFD700)
            )
            Text(
                text = coffee.rate,
                fontSize = 16.sp,
                fontFamily = customFont,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        // Description
        Text(
            text = coffee.description,
            fontSize = 14.sp,
            fontFamily = customFont,
            color = Color.Gray,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        // Price and Buy Now Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Price: ",
                    fontSize = 14.sp,
                    fontFamily = customFont,
                    color = Color.Gray
                )
                Text(
                    text = coffee.price,
                    fontSize = 20.sp,
                    fontFamily = customFont,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = {
                    // Add the item to the cart
                    CartManager.addToCart(coffee)

                    // Navigate to the cart screen
                    navController.navigate("cart")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4AAD4F)),
                modifier = Modifier.width(150.dp)
            ) {
                Text(
                    text = "Buy Now",
                    fontFamily = customFont
                )
            }
        }
    }
}

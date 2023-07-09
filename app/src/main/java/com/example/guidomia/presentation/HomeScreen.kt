package com.example.guidomia.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.guidomia.R
import com.example.guidomia.domain.model.Car
import com.example.guidomia.presentation.ui.theme.GuidomiaTheme
import com.example.guidomia.presentation.ui.theme.Orange

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {

    var selectedIndex: Int by remember {
        mutableStateOf(0)
    }

    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "GUIDOMIA")
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu icon.",
                                tint = Color.White
                            )
                        }
                    }
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.tacoma),
                            contentDescription = "Tacoma car image.",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.FillWidth
                        )

                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Tacoma 2021",
                                color = Color.White,
                                style = MaterialTheme.typography.h5
                            )
                            Text(
                                text = "Get yours now",
                                color = Color.White,
                            )
                        }

                    }

                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = MaterialTheme.colors.secondary,
                            contentColor = MaterialTheme.colors.onSecondary,
                            shape = RoundedCornerShape(16.dp),
                            elevation = 16.dp
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Filters",
                                    color = Color.White
                                )

                                OutlinedTextField(
                                    value = state.makeFilterQuery,
                                    onValueChange = { onEvent(HomeEvent.OnMakeFilterQueryChanged(it)) },
                                    maxLines = 1,
                                    placeholder = {
                                        Text(text = "Any Make")
                                    },
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        textColor = Color.Black.copy(alpha = 0.45f),
                                        placeholderColor = Color.Black.copy(alpha = 0.45f),
                                        backgroundColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                OutlinedTextField(
                                    value = state.modelFilterQuery,
                                    onValueChange = { onEvent(HomeEvent.OnModelFilterQueryChanged(it)) },
                                    maxLines = 1,
                                    placeholder = {
                                        Text(text = "Any Model")
                                    },
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        textColor = Color.Black.copy(alpha = 0.45f),
                                        placeholderColor = Color.Black.copy(alpha = 0.45f),
                                        backgroundColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
                itemsIndexed(state.carList) { index, car ->
                    CarItem(
                        car = car,
                        onExpand = { selectedIndex = index },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        isExpanded = index == selectedIndex
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    if (index != state.carList.lastIndex) {
                        Divider(
                            color = MaterialTheme.colors.primary,
                            thickness = 4.dp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun CarItem(
    car: Car,
    onExpand: () -> Unit,
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false
) {

    val customerPriceStr: String = if (car.customerPrice > 1000) {
        val price = car.customerPrice / 1000
        "${price}K"
    } else car.customerPrice.toString()

    Card(
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(16.dp),
        elevation = 16.dp,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .animateContentSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (car.imageId != null) {
                    Image(
                        painter = painterResource(id = car.imageId),
                        contentDescription = "Car image.",
                        modifier = Modifier.size(80.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.Top),
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = "${car.make} ${car.model}",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.alpha(0.45f)
                    )
                    Text(
                        text = "Price: $customerPriceStr",
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier.alpha(0.45f)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        repeat(car.rating) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Star icon.",
                                tint = MaterialTheme.colors.primary
                            )
                        }
                    }
                }

                IconButton(onClick = onExpand) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp
                        else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Expand icon."
                    )
                }
            }

            if (isExpanded) {
                Column(
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Text(
                        text = "Pros:",
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier.alpha(0.45f)
                    )

                    car.prosList.forEach {
                        if (it.isNotBlank()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Canvas(modifier = Modifier.size(8.dp)) {
                                    drawCircle(Orange)
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = it)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Cons:",
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier.alpha(0.45f)
                    )

                    car.consList.forEach {
                        if (it.isNotBlank()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Canvas(modifier = Modifier.size(8.dp)) {
                                    drawCircle(Orange)
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = it)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    GuidomiaTheme {
        HomeScreen(
            state = HomeState(),
            onEvent = {}
        )
    }
}
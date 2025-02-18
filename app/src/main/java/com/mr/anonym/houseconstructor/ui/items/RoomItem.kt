package com.mr.anonym.houseconstructor.ui.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RoomItem(
    primaryColor: Color,
    secondaryColor: Color,
    roomName: String,
    cement: Double,
    cementWithBag:Int,
    sand:Double,
    crushedStone:Double,
    brick: Int,
    length: Double,
    width: Double,
    height:Double,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = primaryColor
        )
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {

            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = roomName,
                    color = secondaryColor,
                    fontSize = 22.sp
                )
                Row {
                    //            Edit room button
                    IconButton(
                        onClick = { onEditClick() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            tint = secondaryColor,
                            contentDescription = "button edit room"
                        )
                    }
//            Delete room button
                    IconButton(
                        onClick = { onDeleteClick() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            tint = secondaryColor,
                            contentDescription = "button edit room"
                        )
                    }
                }
            }
            Text(
                text = "- длина = $length м",
                color = secondaryColor,
                fontSize = 22.sp
            )
            Text(
                text = "- ширина = $width м",
                color = secondaryColor,
                fontSize = 22.sp
            )
            Text(
                text = "- высота = $height м",
                color = secondaryColor,
                fontSize = 22.sp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 10.dp)
            ) {

//                Width
                VerticalDivider(
                    modifier = Modifier
                        .height(height = width.toInt().times(20).dp),
                    thickness = 4.dp,
                    color = secondaryColor
                )
//                length
                Column {
                    HorizontalDivider(
                        modifier = Modifier
                            .width(width = length.toInt().times(20).dp),
                        thickness = 4.dp,
                        color = secondaryColor
                    )
                    Spacer(Modifier.height(width.toInt().times(18.4).dp))
                    HorizontalDivider(
                        modifier = Modifier
                            .width(width = length.toInt().times(20).dp),
                        thickness = 4.dp,
                        color = secondaryColor
                    )
                }
//               Width
                VerticalDivider(
                    modifier = Modifier
                        .height(height = width.toInt().times(20).dp),
                    thickness = 4.dp,
                    color = secondaryColor
                )
            }
            Text(
                text = "Необходимые материалы :",
                color = secondaryColor,
                fontSize = 20.sp
            )
            Text(
                text = "- цемент = $cement м³",
                color = secondaryColor,
                fontSize = 20.sp
            )
            Text(
                text = "- $cementWithBag мешок цемента",
                color = secondaryColor,
                fontSize = 17.sp
            )
            Text(
                text = "- $sand м³ песок",
                color = secondaryColor,
                fontSize = 17.sp
            )
            Text(
                text = "- $crushedStone м³ щебень",
                color = secondaryColor,
                fontSize = 17.sp
            )
            Text(
                text = "- кирпич = $brick шт",
                color = secondaryColor,
                fontSize = 20.sp
            )
        }
    }
    Spacer(Modifier.height(10.dp))
}

@Preview
@Composable
private fun PreviewRoomItem() {
    RoomItem(
        Color.White,
        Color.Black,
        "Кухня",
        1.05,
        cementWithBag = 50,
        sand = 5.0,
        crushedStone = 5.0,
        brick = 1000,
        length = 10.0,
        width = 5.0,
        height = 4.0,
        onEditClick = {},
        onDeleteClick = {})
}
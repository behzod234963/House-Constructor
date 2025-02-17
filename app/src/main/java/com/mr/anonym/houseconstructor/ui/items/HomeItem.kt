package com.mr.anonym.houseconstructor.ui.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mr.anonym.houseconstructor.data.model.HouseEntity

@Composable
fun HomeItem(
    primaryColor: Color,
    secondaryColor: Color,
    tertiaryColor: Color,
    onClick:()->Unit,
    onDeleteClick:()->Unit,
    entity: HouseEntity
) {

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp),
        onClick = { onClick() },
        shape = RoundedCornerShape(7.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = primaryColor
        )
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp)
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
//                Home name
                Text(
                    text = entity.houseName,
                    color = secondaryColor,
                    fontSize = 22.sp
                )
//                Data added
                Text(
                    text = entity.date,
                    color = tertiaryColor,
                    fontSize = 16.sp
                )
            }
            Spacer(Modifier.height(2.dp))
            HorizontalDivider(color = secondaryColor)
            Spacer(Modifier.height(5.dp))
//            Total rooms
            Text(
                text = "Комнаты: ${entity.totalRooms}",
                color = secondaryColor,
                fontSize = 17.sp
            )
            Text(
                text = "Объем потребляемых ресурсов: ",
                color = secondaryColor,
                fontSize = 17.sp
            )
            Text(
                text = "- ${entity.totalCement} м³ цемент",
                color = secondaryColor,
                fontSize = 17.sp
            )
            Text(
                text = "- ${entity.totalCementWithBag} мешок цемента",
                color = secondaryColor,
                fontSize = 17.sp
            )
            Text(
                text = "- ${entity.totalSand} м³ песок",
                color = secondaryColor,
                fontSize = 17.sp
            )
            Text(
                text = "- ${entity.totalCrushedStone} м³ щебень",
                color = secondaryColor,
                fontSize = 17.sp
            )
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "- ${entity.totalBrick} шт кирпич",
                    color = secondaryColor,
                    fontSize = 17.sp
                )
                IconButton(
                    onClick = { onDeleteClick() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        tint = secondaryColor,
                        contentDescription = "button delete home"
                    )
                }
            }
        }
    }
}
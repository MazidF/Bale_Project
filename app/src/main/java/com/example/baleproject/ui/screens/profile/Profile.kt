package com.example.baleproject.ui.screens.profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.baleproject.R
import com.example.baleproject.data.model.UserInfo
import com.example.baleproject.ui.composable.button.BackButton
import com.example.baleproject.ui.composable.image.Image
import com.example.baleproject.ui.composable.wrapper.CurveColumn

@Composable
fun Profile(events: ProfileEvents) {
    ProfileState(events)
}

@Composable
private fun ProfileState(
    events: ProfileEvents,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    ProfileContent(
        userInfo = viewModel.userInfo,
        onEdit = events::navigateToEdit,
        onLogout = {
            viewModel.logout()
            events.onLogoutCompleted()
        },
    )
}

@Composable
private fun ProfileContent(
    userInfo: UserInfo,
    onEdit: () -> Unit,
    onLogout: () -> Unit,
) {
    CurveColumn {
        TopBar(userInfo)
        Spacer(modifier = Modifier.height(100.dp))
        Options(
            onEdit = onEdit,
            onLogout = onLogout,
        )
    }
}

@Composable
private fun TopBar(userInfo: UserInfo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.male_user),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(percent = 50),
                )
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp),
        ) {
            Text(text = userInfo.name, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = userInfo.email, color = Color.Gray)
        }
    }
}

@Composable
fun ColumnScope.Options(
    onEdit: () -> Unit,
    onLogout: () -> Unit,
) {
    Edit(onEdit)
    SeparatorLine()
    Logout(onLogout)
}

@Composable
fun ColumnScope.Edit(onEdit: () -> Unit) {
    OptionItem(
        color = MaterialTheme.colors.onSurface,
        iconId = R.drawable.ic_edit,
        label = stringResource(id = R.string.edit_profile),
        onClick = onEdit,
    )
}

@Composable
fun ColumnScope.Logout(onLogout: () -> Unit) {
    OptionItem(
        color = Color.Red,
        iconId = R.drawable.ic_logout,
        label = stringResource(id = R.string.logout),
        onClick = onLogout,
    )
}

@Composable
private fun ColumnScope.SeparatorLine() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 3.dp),
        thickness = 1.dp,
    )
}

@Composable
private fun ColumnScope.OptionItem(
    @DrawableRes iconId: Int,
    label: String,
    color: Color,
    colorFilter: Color? = null,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp)
    ) {
        Image(id = iconId, colorFilter = colorFilter ?: color)
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = label, color = color, fontSize = 17.sp)
    }
}

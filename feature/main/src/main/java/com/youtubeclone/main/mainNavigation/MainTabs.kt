package com.youtubeclone.main.mainNavigation

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Stable
import com.youtubeclone.designsystem.R
import javax.annotation.concurrent.Immutable

/**
 * 하단 탭 메뉴에 들어갈 아이템 정보
 * @param text  하단 탭에 표시될 텍스트
 * @param resource  하단 탭 위에 띄워질 에셋
 * @param route  선택시 연결될 Screen route
 */

@Immutable
data class MainTabs(
    val text: String,
    @DrawableRes val resource: Int,
    val route: String
) {
    companion object {
        @Stable
        internal val BottomHome: MainTabs = MainTabs(
            "Home",
            resource = R.drawable.home_icon,
            MainScreenRoute.HOME.route
        )

        @Stable
        internal val BottomShorts: MainTabs = MainTabs(
            "Shots",
            R.drawable.shorts_icon,
            MainScreenRoute.SHORTS.route
        )

        @Stable
        internal val BottomSubscriptions: MainTabs = MainTabs(
            "Subscriptions",
            R.drawable.subscriptions_icon,
            MainScreenRoute.SUBSCRIPTIONS.route
        )

        @Stable
        internal val BottomLibrary: MainTabs = MainTabs(
            "Library",
            R.drawable.library_icon,
            MainScreenRoute.LIBRARY.route
        )
    }
}


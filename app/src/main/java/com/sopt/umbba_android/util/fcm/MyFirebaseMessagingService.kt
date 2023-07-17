package com.sopt.umbba_android.util.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.sopt.umbba_android.R
import com.sopt.umbba_android.presentation.qna.QuestionAnswerActivity
import timber.log.Timber

class MyFirebaseMessagingService : FirebaseMessagingService() {
    var isSwitchChecked = MutableLiveData<Boolean>(false)

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.tag("hyeon").e("fcm token : " + token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Timber.tag("hyeon").e(message.notification.toString())
        Timber.tag("hyeon").e(message.data.toString())
        setNotificationOnOff(isSwitchChecked.value!!, message)
    }

    private fun setNotificationOnOff(isSwitchChecked: Boolean, message: RemoteMessage) {
        if (isSwitchChecked) {
            createNotification(message)
        } else {
            Log.e("hyeon", "알림 끈 상태")
        }
    }

    private fun createNotification(message: RemoteMessage) {
        val intent = Intent(this, QuestionAnswerActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        for (key in message.data.keys) {
            intent.putExtra(key, message.data[key])
        }

        val pendingIntent = PendingIntent.getActivity(
            this, (System.currentTimeMillis() / 7).toInt(), intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_MUTABLE
        )

        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            "TestChannelName",
            NotificationManager.IMPORTANCE_DEFAULT // IMPORTANCE_HIGH 면 헤드업 알림이 가능.
        )

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_umbba_logo)
            .setContentTitle("엄빠도 어렸다.")
            .setContentText("알림 내용")
            .setContentIntent(pendingIntent)

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)

        notificationManager.notify(
            1, // 해당 알림의 고유 ID
            builder // 표시할 알림
                .build()
        )
    }

    private fun getFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.tag("hyeon").e(task.exception, "Fetching FCM registration token failed")
                return@OnCompleteListener
            }
            val token = task.result
            Timber.tag("hyeon").e("token is " + token)
        })
    }

    companion object {
        private const val CHANNEL_ID = "channel_id"
        private const val notificationId = 1234
    }
}
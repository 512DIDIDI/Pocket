package com.dididi.pocket.core.util

import android.net.Uri
import android.text.TextUtils
import com.dididi.pocket.core.entity.Message
import com.dididi.pocket.core.entity.User
import com.dididi.pocket.core.fakedata.FakeUser
import com.tencent.imsdk.*
import com.tencent.imsdk.ext.message.TIMMessageExt
import java.io.File
import java.util.*


/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @since 23/05/2019
 * @describe
 */

class MessageUtil {
    companion object {

        const val TAG = "MessageUtil"

        const val GROUP_CREATE = "group_create"
        const val GROUP_DELETE = "group_delete"

        private fun getUserByName(name:String) = FakeUser.getUserByName(name)

        /**
         * 新建文本消息
         * @param message 消息内容
         */
        fun buildTextMessage(message: String) = Message().apply {
            val tempTimMsg = TIMMessage()
            val textElem = TIMTextElem()
            textElem.text = message
            tempTimMsg.addElement(textElem)
            extra = textElem
            date = System.currentTimeMillis()
            isSelf = true
            timMessage = tempTimMsg
            fromUser = getUserByName(TIMManager.getInstance().loginUser)
            type = Message.MSG_TYPE_TEXT
        }

        /**
         * 新建自定义表情消息
         * @param groupId
         * @param faceName 表情名字
         */
        fun buildCustomFaceMessage(groupId: Int, faceName: String) = Message().apply {
            val tempTimMsg = TIMMessage()
            val faceElem = TIMFaceElem()
            faceElem.index = groupId
            faceElem.data = faceName.toByteArray()
            tempTimMsg.addElement(faceElem)
            extra = "[自定义表情]"
            date = System.currentTimeMillis()
            isSelf = true
            timMessage = tempTimMsg
            fromUser = getUserByName(TIMManager.getInstance().loginUser)
            type = Message.MSG_TYPE_CUSTOM_FACE
        }

        /**
         * 新建图片消息
         * @param uri 图片uri
         * @param compressed 是否压缩
         * @param appPhoto 是否是app内拍摄的图片
         */
        fun buildImageMessage(uri: Uri, compressed: Boolean, appPhoto: Boolean): Message? = Message().apply {
            val imageElem = TIMImageElem()
            if (!appPhoto) {
                val copyInfo =
                        ImageUtil.copyImage(uri, Constants.IMAGE_DOWNLOAD_DIR) ?: return null
                imageElem.path = copyInfo.path
                dataPath = copyInfo.path
                imgWidth = copyInfo.width
                imgHeight = copyInfo.height
                dataUri = FileUtil.getUriFromPath(copyInfo.path)
            } else {
                imageElem.path = FileUtil.getPathFromUri(uri)
                val sizes = ImageUtil.getImageSize(uri)
                dataUri = uri
                dataPath = imageElem.path
                imgWidth = sizes[0]
                imgHeight = sizes[1]
            }
            val tempTimMsg = TIMMessage()
            val ext = TIMMessageExt(tempTimMsg)
            ext.setSender(TIMManager.getInstance().loginUser)
            ext.setTimestamp(System.currentTimeMillis())
            if (!compressed) {
                imageElem.level = 0
            }
            tempTimMsg.addElement(imageElem)
            isSelf = true
            timMessage = tempTimMsg
            extra = "[图片]"
            date = System.currentTimeMillis()
            fromUser = getUserByName(TIMManager.getInstance().loginUser)
            type = Message.MSG_TYPE_IMAGE
        }

        /**
         * 新建视频消息
         * @param imgPath 图片路径
         * @param videoPath 视频路径
         * @param width 宽度
         * @param height 高度
         * @param duration 长度
         */
        fun buildVideoMessage(imgPath: String, videoPath: String,
                              width: Int, height: Int, duration: Long): Message = Message().apply {
            val tempMsg = TIMMessage()
            val videoElem = TIMVideoElem()

            val video = TIMVideo()
            video.duaration = duration / 1000
            video.type = "mp4"
            val snapshot = TIMSnapshot()

            snapshot.width = width.toLong()
            snapshot.height = height.toLong()
            videoElem.setSnapshot(snapshot)
            videoElem.setVideo(video)
            videoElem.snapshotPath = imgPath
            videoElem.videoPath = videoPath

            tempMsg.addElement(videoElem)
            val videoUri = Uri.fromFile(File(videoPath))
            isSelf = true
            imgWidth = width
            imgHeight = height
            dataPath = imgPath
            dataUri = videoUri
            timMessage = tempMsg
            extra = "[视频]"
            date = System.currentTimeMillis()
            fromUser = getUserByName(TIMManager.getInstance().loginUser)
            type = Message.MSG_TYPE_VIDEO
        }

        /**
         * 新建语音信息
         * @param recordPath 录音文件路径
         * @param duration 长度
         */
        fun buildAudioMessage(recordPath: String, duration: Int): Message = Message().apply {
            dataPath = recordPath
            val tempTimMsg = TIMMessage()
            val ext = TIMMessageExt(tempTimMsg)
            ext.setSender(TIMManager.getInstance().loginUser)
            ext.setTimestamp(System.currentTimeMillis() / 1000)
            val ele = TIMSoundElem()
            ele.duration = (duration / 1000).toLong()
            ele.path = recordPath
            tempTimMsg.addElement(ele)
            isSelf = true
            timMessage = tempTimMsg
            extra = "[语音]"
            date = System.currentTimeMillis()
            fromUser = getUserByName(TIMManager.getInstance().loginUser)
            type = Message.MSG_TYPE_AUDIO
        }

        /**
         * 新建文件消息
         * @param fileUri 文本路径
         */
        fun buildFileMessage(fileUri: Uri): Message? {
            val filePath = FileUtil.getPathFromUri(fileUri)
            val file = File(filePath)
            if (file.exists()) {
                val message = Message()
                message.dataPath = filePath
                val tempTimMsg = TIMMessage()
                val ele = TIMFileElem()
                val ext = TIMMessageExt(tempTimMsg)
                ext.setSender(TIMManager.getInstance().loginUser)
                ext.setTimestamp(System.currentTimeMillis() / 1000)
                ele.path = filePath
                ele.fileName = file.name
                tempTimMsg.addElement(ele)
                message.isSelf = true
                message.timMessage = tempTimMsg
                message.extra = "[文件]"
                message.date = System.currentTimeMillis()
                message.fromUser = getUserByName(TIMManager.getInstance().loginUser)
                message.type = Message.MSG_TYPE_FILE
                message.status = Message.MSG_STATUS_SENDING
                return message
            }
            return null
        }

        fun buildReadNoticeMessage(peer: String): Message {
            val info = Message()
            info.targetUser = getUserByName(peer)
            info.type = Message.MSG_STATUS_READ
            return info
        }

        fun buildGroupCustomMessage(action: String, message: String): Message {
            val info = Message()
            val tempTimMsg = TIMMessage()
            val ele = TIMCustomElem()
            ele.data = action.toByteArray()
            ele.ext = message.toByteArray()
            tempTimMsg.addElement(ele)
            info.isSelf = true
            info.timMessage = tempTimMsg
            info.extra = message
            info.date = System.currentTimeMillis()
            if (action == GROUP_CREATE) {
                info.type = Message.MSG_TYPE_GROUP_CREATE
            } else if (action == GROUP_DELETE) {
                info.type = Message.MSG_TYPE_GROUP_DELETE
            }
            return info
        }

        fun buildGroupTipsMessage(peer: String, type: Int, message: String): Message {
            val info = Message()
            info.isSelf = true
            info.type = type
            info.targetUser = getUserByName(peer)
            info.extra = message
            info.date = System.currentTimeMillis()
            return info
        }

        fun TIMMessages2Messages(timMessages: List<TIMMessage>?, isGroup: Boolean): List<Message>? {
            if (timMessages == null)
                return null
            val messages = ArrayList<Message>()
            for (i in timMessages.indices) {
                val timMessage = timMessages[i]
                val info = TIMMessage2Message(timMessage, isGroup)
                if (info != null)
                    messages.add(info)
            }
            return messages
        }


        fun TIMMessage2Message(timMessage: TIMMessage?, isGroup: Boolean): Message? {
            if (timMessage == null || timMessage.status() == TIMMessageStatus.HasDeleted)
                return null
            val sender = timMessage.sender
            val msgInfo = Message()
            msgInfo.timMessage = timMessage
            msgInfo.isGroup = isGroup
            msgInfo.msgId = timMessage.msgId
            //获取消息发送方
            if (isGroup) {
                val memberInfo = timMessage.senderGroupMemberProfile
                if (memberInfo != null && !TextUtils.isEmpty(memberInfo.nameCard))
                    msgInfo.fromUser = getUserByName(memberInfo.nameCard)
                else
                    msgInfo.fromUser = getUserByName(sender)
            } else {
                msgInfo.fromUser = getUserByName(sender)
            }
            msgInfo.date = timMessage.timestamp() * 1000
            //获取消息目标方
            msgInfo.targetUser = getUserByName(timMessage.conversation.peer)
            //是否是自己发的消息
            msgInfo.isSelf = sender == TIMManager.getInstance().loginUser
            val conversation = timMessage.conversation
            val conversationType = conversation.type
            if (timMessage.elementCount > 0) {
                val ele = timMessage.getElement(0)

                if (ele == null) {
                    LogUtil.d(TAG, "msg found null elem")
                    return null
                }

                val type = ele.type
                if (type == TIMElemType.Invalid) {
                    LogUtil.d(TAG, "invalid")
                    return null
                }

                if (type == TIMElemType.Custom) {
                    val customElem = ele as TIMCustomElem
                    val data = String(customElem.data)
                    msgInfo.extra = when (data) {
                        GROUP_CREATE -> {
                            msgInfo.type = Message.MSG_TYPE_GROUP_CREATE
                            String(customElem.ext)
                        }
                        GROUP_DELETE -> {
                            msgInfo.type = Message.MSG_TYPE_GROUP_DELETE
                            String(customElem.ext)
                        }
                        else -> "[自定义消息]"
                    }
                } else if (type == TIMElemType.GroupTips) {
                    val groupTips = ele as TIMGroupTipsElem
                    val tipsType = groupTips.tipsType
                    var user = ""
                    if (groupTips.changedGroupMemberInfo.isNotEmpty()) {
                        val ids = groupTips.changedGroupMemberInfo.keys.toTypedArray()
                        for (i in ids.indices) {
                            user += ids[i].toString()
                            if (i != 0)
                                user = "，$user"
                            if (i == 2 && ids.size > 3) {
                                user += "等"
                                break
                            }
                        }

                    } else
                        user = groupTips.opUserInfo.identifier
                    var message = user
                    if (tipsType == TIMGroupTipsType.Join) {
                        msgInfo.type = Message.MSG_TYPE_GROUP_JOIN
                        message += "加入群组"
                    }
                    if (tipsType == TIMGroupTipsType.Quit) {
                        msgInfo.type = Message.MSG_TYPE_GROUP_QUITE
                        message += "退出群组"
                    }
                    if (tipsType == TIMGroupTipsType.Kick) {
                        msgInfo.type = Message.MSG_TYPE_GROUP_KICK
                        message += "被踢出群组"
                    }
                    if (tipsType == TIMGroupTipsType.ModifyGroupInfo) {
                        val modifyList = groupTips.groupInfoList
                        if (modifyList.size > 0) {
                            val modifyInfo = modifyList[0]
                            val modifyType = modifyInfo.type
                            if (modifyType == TIMGroupTipsGroupInfoType.ModifyName) {
                                msgInfo.type = Message.MSG_TYPE_GROUP_MODIFY_NAME
                                message = message + "修改群名称为\"" + modifyInfo.content + "\""
                            } else if (modifyType == TIMGroupTipsGroupInfoType.ModifyNotification) {
                                msgInfo.type = Message.MSG_TYPE_GROUP_MODIFY_NOTICE
                                message += "修改群公告"
                            }
                        }
                    }
                    msgInfo.extra = message
                } else {
                    msgInfo.extra = when (type) {
                        TIMElemType.Text -> {
                            val txtEle = ele as TIMTextElem
                            txtEle.text
                        }
                        TIMElemType.Face -> {
                            val txtEle = ele as TIMFaceElem
                            if (txtEle.index < 1 || txtEle.data == null) {
                                LogUtil.d(TAG, "txtEle data is null or index<1")
                                return null
                            }
                            "[自定义表情]"
                        }
                        TIMElemType.Sound -> {
                            val soundElemEle = ele as TIMSoundElem
                            if (msgInfo.isSelf) {
                                msgInfo.dataPath = soundElemEle.path
                            } else {
                                val path = Constants.RECORD_DOWNLOAD_DIR + soundElemEle.uuid
                                val file = File(path)
                                if (!file.exists()) {
                                    soundElemEle.getSoundToFile(path, object : TIMCallBack {
                                        override fun onError(code: Int, desc: String) {
                                            LogUtil.d("$TAG getSoundToFile", "$code:$desc")
                                        }

                                        override fun onSuccess() {
                                            msgInfo.dataPath = path
                                        }
                                    })
                                } else {
                                    msgInfo.dataPath = path
                                }
                            }
                            "[语音]"
                        }
                        TIMElemType.Image -> {
                            val imageEle = ele as TIMImageElem
                            val localPath = imageEle.path
                            if (msgInfo.isSelf && !TextUtils.isEmpty(localPath)) {
                                msgInfo.dataPath = localPath
                                val size = ImageUtil.getImageSize(localPath)
                                msgInfo.imgWidth = size[0]
                                msgInfo.imgHeight = size[1]
                            } else {
                                val imgs = imageEle.imageList
                                for (i in imgs.indices) {
                                    val img = imgs[i]
                                    if (img.type == TIMImageType.Thumb) {
                                        val path = Constants.IMAGE_DOWNLOAD_DIR + img.uuid
                                        msgInfo.imgWidth = img.width.toInt()
                                        msgInfo.imgHeight = img.height.toInt()
                                        val file = File(path)
                                        if (file.exists()) {
                                            msgInfo.dataPath = path
                                        }
                                    }
                                }
                            }//本地路径为空，可能为更换手机或者是接收的消息
                            "[图片]"
                        }
                        TIMElemType.Video -> {
                            val videoEle = ele as TIMVideoElem
                            if (msgInfo.isSelf && !TextUtils.isEmpty(videoEle.snapshotPath)) {
                                val size = ImageUtil.getImageSize(videoEle.snapshotPath)
                                msgInfo.imgWidth = size[0]
                                msgInfo.imgHeight = size[1]
                                msgInfo.dataPath = videoEle.snapshotPath
                                msgInfo.dataUri = FileUtil.getUriFromPath(videoEle.videoPath)
                            } else {
                                val video = videoEle.videoInfo
                                val videoPath = Constants.VIDEO_DOWNLOAD_DIR + video.uuid
                                val uri = Uri.parse(videoPath)
                                msgInfo.dataUri = uri
                                val snapshot = videoEle.snapshotInfo
                                msgInfo.imgWidth = snapshot.width.toInt()
                                msgInfo.imgHeight = snapshot.height.toInt()
                                val snapPath = Constants.IMAGE_DOWNLOAD_DIR + snapshot.uuid
                                //判断快照是否存在,不存在自动下载
                                if (File(snapPath).exists()) {
                                    msgInfo.dataPath = snapPath
                                }
                            }
                            "[视频]"
                        }
                        TIMElemType.File -> {
                            val fileElem = ele as TIMFileElem
                            val path = Constants.FILE_DOWNLOAD_DIR + fileElem.uuid
                            if (!msgInfo.isSelf) {
                                val file = File(path)
                                if (!file.exists()) {
                                    msgInfo.status = Message.MSG_STATUS_UN_DOWNLOAD
                                } else {
                                    msgInfo.status = Message.MSG_STATUS_DOWNLOADED
                                }
                                msgInfo.dataPath = path
                            } else {
                                if (TextUtils.isEmpty(fileElem.path)) {
                                    fileElem.getToFile(path, object : TIMCallBack {
                                        override fun onError(code: Int, desc: String) {
                                            LogUtil.d("$TAG getToFile", "$code:$desc")
                                        }

                                        override fun onSuccess() {
                                            msgInfo.dataPath = path
                                        }
                                    })
                                } else {
                                    msgInfo.status = Message.MSG_STATUS_SEND_SUCCESS
                                    msgInfo.dataPath = fileElem.path
                                }

                            }
                            "[文件]"
                        }
                        else -> ""
                    }
                    msgInfo.type = TIMElemType2MessageType(type)
                }

            } else {
                LogUtil.d(TAG, "msg elecount is 0")
                return null
            }

            if (timMessage.status() == TIMMessageStatus.HasRevoked) {
                msgInfo.status = Message.MSG_STATUS_REVOKE
                msgInfo.type = Message.MSG_STATUS_REVOKE
            } else {
                if (msgInfo.isSelf) {
                    msgInfo.status = when (timMessage.status()) {
                        TIMMessageStatus.SendFail -> Message.MSG_STATUS_SEND_FAIL
                        TIMMessageStatus.SendSucc -> Message.MSG_STATUS_SEND_SUCCESS
                        TIMMessageStatus.Sending -> Message.MSG_STATUS_SENDING
                        else -> Message.MSG_STATUS_NORMAL
                    }
                }
            }
            return msgInfo
        }

        fun checkMessage(msg: TIMMessage, callBack: TIMCallBack): Boolean {
            if (msg.elementCount > 0) {
                val ele = msg.getElement(0)
                if (ele.type == TIMElemType.Video) {
                    val videoEle = ele as TIMVideoElem
                    val video = videoEle.videoInfo as TIMVideo
                    val snapshot = videoEle.snapshotInfo
                    val snapPath = Constants.IMAGE_DOWNLOAD_DIR + video.uuid
                    //判断快照是否存在,不存在自动下载
                    if (!File(snapPath).exists()) {
                        snapshot.getImage(snapPath, callBack)
                    }
                    return true
                } else if (ele.type == TIMElemType.Image) {
                    val imageEle = ele as TIMImageElem
                    val imgs = imageEle.imageList
                    for (i in imgs.indices) {
                        val img = imgs[i]
                        if (img.type == TIMImageType.Thumb) {
                            val path = Constants.IMAGE_DOWNLOAD_DIR + img.uuid
                            val file = File(path)
                            if (!file.exists()) {
                                img.getImage(path, callBack)
                            }
                            return true
                        }
                    }
                }
                return false

            }
            return true
        }

        private fun TIMElemType2MessageType(type: TIMElemType) =
                when (type) {
                    TIMElemType.Text -> Message.MSG_TYPE_TEXT
                    TIMElemType.Image -> Message.MSG_TYPE_IMAGE
                    TIMElemType.Sound -> Message.MSG_TYPE_AUDIO
                    TIMElemType.Video -> Message.MSG_TYPE_VIDEO
                    TIMElemType.File -> Message.MSG_TYPE_FILE
                    TIMElemType.Location -> Message.MSG_TYPE_LOCATION
                    TIMElemType.Face -> Message.MSG_TYPE_CUSTOM_FACE
                    TIMElemType.GroupTips -> Message.MSG_TYPE_TIPS
                    else -> {
                        -1
                    }
                }
    }
}
package com.cometchat.pro.androiduikit.ComponentFragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.Fragment
import com.cometchat.pro.androiduikit.R
import com.cometchat.pro.androiduikit.databinding.FragmentGroupListBinding
import com.cometchat.pro.constants.CometChatConstants
import com.cometchat.pro.core.CometChat.CallbackListener
import com.cometchat.pro.core.GroupsRequest
import com.cometchat.pro.core.GroupsRequest.GroupsRequestBuilder
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.Group
import com.cometchat.pro.uikit.ui_components.messages.message_list.CometChatMessageListActivity
import com.cometchat.pro.uikit.ui_resources.constants.UIKitConstants
import com.cometchat.pro.uikit.ui_resources.utils.item_clickListener.OnItemClickListener

class GroupListViewFragment : Fragment() {
    var groupBinding: FragmentGroupListBinding? = null
    var grouplist = ObservableArrayList<Group>()
    var groupsRequest: GroupsRequest? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        groupBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cometchat_group_list, container, false)
        groups
        groupBinding!!.setGroupList(grouplist)
        groupBinding!!.cometchatGroupList.setItemClickListener(object : OnItemClickListener<Group?>() {
            override fun OnItemClick(t: Any, position: Int) {
                val group = t as Group
                val intent = Intent(context, CometChatMessageListActivity::class.java)
                intent.putExtra(UIKitConstants.IntentStrings.NAME, group.name)
                intent.putExtra(UIKitConstants.IntentStrings.GROUP_OWNER, group.owner)
                intent.putExtra(UIKitConstants.IntentStrings.GUID, group.guid)
                intent.putExtra(UIKitConstants.IntentStrings.AVATAR, group.icon)
                intent.putExtra(UIKitConstants.IntentStrings.GROUP_TYPE, group.groupType)
                intent.putExtra(UIKitConstants.IntentStrings.TYPE, CometChatConstants.RECEIVER_TYPE_GROUP)
                intent.putExtra(UIKitConstants.IntentStrings.MEMBER_COUNT, group.membersCount)
                intent.putExtra(UIKitConstants.IntentStrings.GROUP_DESC, group.description)
                intent.putExtra(UIKitConstants.IntentStrings.GROUP_PASSWORD, group.password)
                startActivity(intent)
            }
        })
        return groupBinding!!.getRoot()
    }

    private val groups: Unit
        private get() {
            if (groupsRequest == null) {
                groupsRequest = GroupsRequestBuilder().setLimit(30).build()
            }
            groupsRequest!!.fetchNext(object : CallbackListener<List<Group?>?>() {
                override fun onSuccess(groups: List<Group?>?) {
                    groupBinding!!.contactShimmer.stopShimmer()
                    groupBinding!!.contactShimmer.visibility = View.GONE
                    grouplist.addAll(groups!!)
                }

                override fun onError(e: CometChatException) {
                    groupBinding!!.contactShimmer.stopShimmer()
                    groupBinding!!.contactShimmer.visibility = View.GONE
                    e.message?.let { Log.e("onError: ", it) }
                }
            })
        }
}
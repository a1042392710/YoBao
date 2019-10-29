package com.jjz.energy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjz.energy.R;
import com.jjz.energy.entry.commodity.ChildComment;
import com.jjz.energy.entry.commodity.Comment;
import com.jjz.energy.util.DateUtil;
import com.jjz.energy.util.StringUtil;
import com.jjz.energy.util.glide.GlideUtils;

import java.util.Date;
import java.util.List;

/**
 * Author: 陈大帅
 * Desc: 评论与回复列表的适配器
 */

public class CommentExpandAdapter extends BaseExpandableListAdapter {
    private List<Comment> commentBeanList;
    private Context context;

    public CommentExpandAdapter(Context context, List<Comment> commentBeanList)               {
        this.context = context;
        this.commentBeanList = commentBeanList;
    }

    public  List<Comment> getCommentBeanList(){
        return commentBeanList;
    }

    @Override
    public int getGroupCount() {
        return commentBeanList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if(commentBeanList.get(i).getReply_list() == null){
            return 0;
        }else {
            return commentBeanList.get(i).getReply_list().size()>0 ? commentBeanList.get(i).getReply_list().size():0;
        }

    }

    @Override
    public Object getGroup(int i) {
        return commentBeanList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return commentBeanList.get(i).getReply_list().get(i1);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpand, View convertView, ViewGroup viewGroup) {
        final GroupHolder groupHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, viewGroup, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        }else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        //用户头像
        GlideUtils.loadCircleImage(context,commentBeanList.get(groupPosition).getHead_pic(), groupHolder.img_head);
        //用户昵称
        groupHolder.tv_name.setText(commentBeanList.get(groupPosition).getUsername());
        //评论时间
        groupHolder.tv_time.setText(DateUtil.getTimeFormatText(new Date(commentBeanList.get(groupPosition).getAdd_time()*1000)));
        //评论内容
        groupHolder.tv_content.setText(commentBeanList.get(groupPosition).getContent());

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        final ChildHolder childHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_child_comment,viewGroup, false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        }
        else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        ChildComment childComment = commentBeanList.get(groupPosition).getReply_list().get(childPosition);
        //发送方头像
        GlideUtils.loadCircleImage(context,childComment.getFrom_pic(), childHolder.img_head);
        //用户昵称
        childHolder.tv_name.setText(childComment.getFrom_username());
        //评论时间
        childHolder.tv_time.setText( DateUtil.getTimeFormatText(new Date(childComment.getReply_time()*1000)));
        //评论内容
        childHolder.tv_content.setText("回复@"+childComment.getTo_username()+":"+childComment.getContent());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    /**
     * 一级评论
     */
    private class GroupHolder {
        private ImageView img_head;
        private TextView tv_name, tv_content, tv_time;

        //        private ImageView iv_like;
        public GroupHolder(View view) {
            img_head = view.findViewById(R.id.item_img_head);
            tv_content = view.findViewById(R.id.item_tv_comment);
            tv_name = view.findViewById(R.id.item_tv_user_name);
            tv_time = view.findViewById(R.id.item_tv_time);
        }
    }

    /**
     * 二级评论
     */
    private class ChildHolder {
        private ImageView img_head;
        private TextView tv_name, tv_content, tv_time;

        //        private ImageView iv_like;
        public ChildHolder(View view) {
            img_head = view.findViewById(R.id.item_img_head);
            tv_content = view.findViewById(R.id.item_tv_comment);
            tv_name = view.findViewById(R.id.item_tv_user_name);
            tv_time = view.findViewById(R.id.item_tv_time);
//            iv_like = view.findViewById(R.id.comment_item_like);
        }
    }

    /**
     * 刷新数据
     *
     * @param data
     */
    public boolean notifyChangeData(List<Comment> data) {
        if (StringUtil.isListEmpty(data)) {
            commentBeanList.clear();
            notifyDataSetChanged();
            return false;
        }
        commentBeanList.clear();
        commentBeanList.addAll(data);
        notifyDataSetChanged();
        return true;
    }


    /**
     * 加载更多 有数据返回true 无数据返回 false
     * @param data
     */
    public boolean addChangeData(List<Comment> data) {
        if (!StringUtil.isListEmpty(data)&&commentBeanList != null) {
            //只刷新新数据
            int oldSum = data.size();
            commentBeanList.addAll(data);
            notifyDataSetChanged();
            return true;
        }
        return false;
    }
}
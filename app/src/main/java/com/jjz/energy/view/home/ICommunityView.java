package com.jjz.energy.view.home;

import com.jjz.energy.base.IBaseView;
import com.jjz.energy.entry.community.CommunityBean;

/**
 * @Features: 社区
 * @author: create by chenhao on 2019/10/9
 */
public interface ICommunityView extends IBaseView {

    //发布帖子
    default void isPutPostSuc(String data) {}


    //获取帖子列表
    default void isGetPostListSuc(CommunityBean data) {}

    //获取帖子详情
    default void isGetPostDetailsSuc(String data) {}

    //点赞
    default void isPutPostLikeSuc(String data) {}

    //发布评论
    default void isPutPostCommentSuc(String data) {}

    void isFail(String msg, boolean isNetAndServiceError);
}

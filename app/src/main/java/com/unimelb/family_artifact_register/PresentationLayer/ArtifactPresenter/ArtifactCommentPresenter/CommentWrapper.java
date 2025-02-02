package com.unimelb.family_artifact_register.PresentationLayer.ArtifactPresenter.ArtifactCommentPresenter;

import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactComment;
import com.unimelb.family_artifact_register.PresentationLayer.Util.UserInfoWrapper;

/**
 * @author Haichao Song 854035,
 * @time 2019-10-16 18:32:01
 * @description pair comment item with user info to show user avatar and display name is comment.
 */
public class CommentWrapper {

    ArtifactComment artifactComment;

    UserInfoWrapper userInfoWrapper;

    public CommentWrapper(ArtifactComment artifactComment, UserInfoWrapper userInfoWrapper) {
        this.artifactComment = artifactComment;
        this.userInfoWrapper = userInfoWrapper;
    }

    public ArtifactComment getArtifactComment() {
        return artifactComment;
    }

    public void setArtifactComment(ArtifactComment artifactComment) {
        this.artifactComment = artifactComment;
    }

    public UserInfoWrapper getUserInfoWrapper() {
        return userInfoWrapper;
    }

    public void setUserInfoWrapper(UserInfoWrapper userInfoWrapper) {
        this.userInfoWrapper = userInfoWrapper;
    }
}

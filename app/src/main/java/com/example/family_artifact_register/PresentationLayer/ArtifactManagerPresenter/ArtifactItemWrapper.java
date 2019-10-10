package com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter;

import com.example.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;

import java.util.List;

/**
 * a wrapper class that store media's local address
 */
public class ArtifactItemWrapper {

    private ArtifactItem artifactItem;

    private List<String> localMediaList = null;

    public ArtifactItemWrapper(ArtifactItem artifactItem) {
        this.artifactItem = artifactItem;
    }

    public String getPostId() {
        return this.artifactItem.getPostId();
    }

    public String getUid() {
        return this.artifactItem.getUid();
    }

    public int getMediaType() {
        return this.artifactItem.getMediaType();
    }

    public List<String> getLocalMediaDataUrls() {
        return localMediaList;
    }

    public void setLocalMediaDataUrls(List<String> userDeviceMediaUris) { this.localMediaList = userDeviceMediaUris; }

    public String getDescription() {
        return this.artifactItem.getDescription();
    }

    public String getUploadDateTime() {
        return this.artifactItem.getUploadDateTime();
    }

    public String getHappenedDateTime() {
        return this.artifactItem.getHappenedDateTime();
    }

    public int getlikes() {return this.artifactItem.getlikes();}

    public String getLastUpdateDateTime() {
        return this.artifactItem.getLastUpdateDateTime();
    }

    public String getLocationUploadedId() {
        return this.artifactItem.getLocationUploadedId();
    }

    public String getLocationHappenedId() {
        return this.artifactItem.getLocationHappenedId();
    }

    public String getLocationStoredId() {
        return this.artifactItem.getLocationStoredId();
    }

    public String getArtifactTimelineId() {
        return this.artifactItem.getArtifactTimelineId();
    }
}

package com.unimelb.family_artifact_register.PresentationLayer.ArtifactPresenter.ViewArtifactPresenter;

import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactItem;
import com.unimelb.family_artifact_register.FoundationLayer.ArtifactModel.ArtifactTimeline;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class MeFragmentPresenter {

    private List<ArtifactItem> artifacts;
    private IView view;

    public MeFragmentPresenter(IView view) {
        this.view = view;
        // tmp data
        this.artifacts = initArtifacts();

        for (ArtifactItem aItem : this.artifacts) {
//            this.view.addData(((Artifact) aItem).getCreatedDate(), aItem.getDescription(), aItem.getImages(), aItem.getVideos());
        }
    }

    private List<ArtifactItem> initArtifacts() {
        ArtifactTimeline timeline1 = new ArtifactTimeline("timeline 1");

        List<ArtifactItem> res = new ArrayList<>();
//        res.add(new ArtifactItem(0, new ArrayList<>(), new ArrayList<>(), "2019-9-21 16:59:43",
//                new MapLocation(), new MapLocation(), new MapLocation(), timeline1,
//                "description1", "2019-9-21 17:01:04"));
//        res.add(new ArtifactItem(1, new ArrayList<>(), new ArrayList<>(), "2019-9-21 17:02:33",
//                new MapLocation(), new MapLocation(), new MapLocation(), timeline1,
//                "description2", "2019-9-21 17:02:44"));
//        res.add(new ArtifactItem(2, new ArrayList<>(), new ArrayList<>(), "2019-9-21 17:02:33",
//                new MapLocation(), new MapLocation(), new MapLocation(), timeline1,
//                "description2", "2019-9-21 17:02:44"));
//        res.add(new ArtifactItem(3, new ArrayList<>(), new ArrayList<>(), "2019-9-21 17:02:33",
//                new MapLocation(), new MapLocation(), new MapLocation(), timeline1,
//                "description2", "2019-9-21 17:02:44"));
//        res.add(new ArtifactItem(4, new ArrayList<>(), new ArrayList<>(), "2019-9-21 17:02:33",
//                new MapLocation(), new MapLocation(), new MapLocation(), timeline1,
//                "description2", "2019-9-21 17:02:44"));
//        res.add(new ArtifactItem(5, new ArrayList<>(), new ArrayList<>(), "2019-9-21 17:02:33",
//                new MapLocation(), new MapLocation(), new MapLocation(), timeline1,
//                "description2", "2019-9-21 17:02:44"));
//        res.add(new ArtifactItem(6, new ArrayList<>(), new ArrayList<>(), "2019-9-21 17:02:33",
//                new MapLocation(), new MapLocation(), new MapLocation(), timeline1,
//                "description2", "2019-9-21 17:02:44"));
//        res.add(new ArtifactItem(7, new ArrayList<>(), new ArrayList<>(), "2019-9-21 17:02:33",
//                new MapLocation(), new MapLocation(), new MapLocation(), timeline1,
//                "description2", "2019-9-21 17:02:44"));
//        res.add(new ArtifactItem(8, new ArrayList<>(), new ArrayList<>(), "2019-9-21 17:02:33",
//                new MapLocation(), new MapLocation(), new MapLocation(), timeline1,
//                "description2", "2019-9-21 17:02:44"));
//        res.add(new ArtifactItem(9, new ArrayList<>(), new ArrayList<>(), "2019-9-21 17:02:33",
//                new MapLocation(), new MapLocation(), new MapLocation(), timeline1,
//                "description2", "2019-9-21 17:02:44"));
        return res;
    }

    public interface IView {
        void addData(ArtifactItem artifactItem);
    }
}

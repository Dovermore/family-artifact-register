package com.unimelb.family_artifact_register.UI.Util;

import com.unimelb.family_artifact_register.R;


/**
 * @author XuLin Yang 904904,
 * @time 2019-9-14 14:05:49
 * @description activity with predefined app bar with one cancel label at top left corner of the
 * page and direct back to ArtifactManageActivity polymorphism to provide a base activity with an
 * action bar with cancel icon Deprecated because we adapt to fragment-activity design not
 * activity-only design not deleted by open-close principle
 */
@Deprecated
public abstract class BaseCancelToolBarActivity extends BaseActionBarActivity {

    @Override
    public void setTitle(CharSequence title) {
        // https://stackoverflow.com/questions/26486730/in-android-app-toolbar-settitle-method-has-no-effect-application-name-is-shown
        getMyActionBar().setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        // https://stackoverflow.com/questions/26486730/in-android-app-toolbar-settitle-method-has-no-effect-application-name-is-shown
        getMyActionBar().setTitle(titleId);
    }

    /**
     * set left top cancel button icon
     */
    public void setCancelButton() {
        // set the button for the action bar
        getMyActionBar().setHomeAsUpIndicator(R.drawable.common_close_button);
    }
}

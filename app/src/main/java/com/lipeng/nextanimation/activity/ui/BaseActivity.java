package com.lipeng.nextanimation.activity.ui;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by lipeng-ds3 on 2017/12/11.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                FragmentManager manager = getFragmentManager();
                if (manager.getBackStackEntryCount() > 0){
                    manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }else {
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

package com.example.talesweaver;
import android.app.Fragment;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainFragment extends Fragment {

        private GLSurfaceView mGLView = null;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                        Bundle savedInstanceState) {
                Toast.makeText(getActivity(), "MainFragment#onCreateView",
                                Toast.LENGTH_SHORT).show();
                mGLView = new GLSurfaceView(getActivity());
                mGLView.setLayoutParams(new LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
                mGLView.setEGLContextClientVersion(2);
                mGLView.setPreserveEGLContextOnPause(true);
                mGLView.setRenderer(new MainRenderer());
                mGLView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //getActivity().getFragmentManager().beginTransaction()
                        	//                .replace(R.id.container_main, new BattleFragment())
                        	//                  .commit();
                        }
                });
                return mGLView;
        }
}

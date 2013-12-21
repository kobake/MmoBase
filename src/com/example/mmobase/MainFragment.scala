package com.example.mmobase;
import android.app.Fragment;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.Toast;

class MainFragment extends Fragment {

  private lazy val mGLView: GLSurfaceView = new GLSurfaceView(getActivity())

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup,
    savedInstanceState: Bundle) = {
    Toast.makeText(getActivity(), "MainFragment#onCreateView",
      Toast.LENGTH_SHORT).show();
    mGLView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
    //    mGLView.setEGLContextClientVersion(2)
    mGLView.setEGLContextClientVersion(1)
    mGLView.setPreserveEGLContextOnPause(true)
    mGLView.setRenderer(new MainRenderer(getActivity()))
    mGLView
  }
}

package com.yanwenli.prd_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.ar.core.AugmentedImage;
import com.google.ar.core.Frame;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AugmentedImageActivity extends AppCompatActivity {

    private ArFragment arFragment;
    private final Map<AugmentedImage, AugmentedImageNode> augmentedImageMap = new HashMap<>();
    private ImageView fitToScanView;
    private LinearLayout introduction_layout;
    private ArrayList<String> listInfoPages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_augmented_image);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdateFrame);
        fitToScanView = findViewById(R.id.image_view_fit_to_scan);
        fitToScanView.setVisibility(View.VISIBLE);
        introduction_layout = findViewById(R.id.image_intro_layout);
        introduction_layout.setVisibility(View.GONE);
    }

    private void onUpdateFrame(FrameTime frameTime) {
        Frame frame = arFragment.getArSceneView().getArFrame();

        // If there is no frame or ARCore is not tracking yet, just return.
        if (frame == null || frame.getCamera().getTrackingState() != TrackingState.TRACKING) {
            return;
        }

        Collection<AugmentedImage> updatedAugmentedImages = frame.getUpdatedTrackables(AugmentedImage.class);

        for (AugmentedImage augmentedImage : updatedAugmentedImages) {
            switch (augmentedImage.getTrackingState()) {
                case PAUSED:
                    // When an image is in PAUSED state, but the camera is not PAUSED, it has been detected,
                    // but not yet tracked.
                    String text = "Detected Image" + augmentedImage.getIndex();
                    Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

                    break;

                case TRACKING:

                    // Create a new anchor for newly found images.
                    if (!augmentedImageMap.containsKey(augmentedImage)) {
                        AugmentedImageNode node = new AugmentedImageNode(this);
                        node.setImage(augmentedImage);
                        augmentedImageMap.put(augmentedImage, node);
                        arFragment.getArSceneView().getScene().addChild(node);
                        fitToScanView.setVisibility(View.GONE);
                        introduction_layout.setVisibility(View.VISIBLE);

                        if (!augmentedImageMap.isEmpty()) {

                            augmentedImageMap.get(augmentedImage).setOnTapListener((hitTestResult, motionEvent) -> {
//                                Intent intent = new Intent(AugmentedImageActivity.this, InfoActivity.class);
//                                intent.putExtra("imageIndex", augmentedImage.getIndex());
//
//                                startActivity(intent);
                                Toast.makeText(AugmentedImageActivity.this, "Ok: Information page", Toast.LENGTH_SHORT).show();
                            });
                        }

                    }
                    break;

                case STOPPED:
                    augmentedImageMap.remove(augmentedImage);
                    break;
            }
        }
    }
}

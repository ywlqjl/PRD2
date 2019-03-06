package com.yanwenli.prd_2;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.AugmentedImage;
import com.google.ar.core.Frame;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.ExternalTexture;
import com.google.ar.sceneform.rendering.ModelRenderable;
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
    private GridLayout introduction_layout2;
    private TextView txtInfo;
    private TextView txtTitle;
    private TextView txtArtist;
    private TextView txtDate;
    private TextView txtMedium;
    private Button btnMoreInfo;
    private ArrayList<Inuit> listInfoPages = new ArrayList<>();

    @Nullable
    private ModelRenderable videoRenderable;
    private MediaPlayer mediaPlayer;
    // The color to filter out of the video.
    private static final Color CHROMA_KEY_COLOR = new Color(0.1843f, 1.0f, 0.098f);

    // Controls the height of the video in world space.
    private static final float VIDEO_HEIGHT_METERS = 0.85f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_augmented_image2);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdateFrame);
        fitToScanView = findViewById(R.id.image_view_fit_to_scan2);
        fitToScanView.setVisibility(View.VISIBLE);
        introduction_layout = findViewById(R.id.image_intro_layout2);
        introduction_layout.setVisibility(View.GONE);

//        txtInfo = findViewById(R.id.txt_info_image);
        txtTitle = findViewById(R.id.txt_title_image);
        txtArtist = findViewById(R.id.txt_artist_image);
        txtDate = findViewById(R.id.txt_date_image);
        txtMedium = findViewById(R.id.txt_medium_image);

        btnMoreInfo = findViewById(R.id.btn_more_info);

        LoadInuitInfo loadInuitInfo = new LoadInuitInfo();
        listInfoPages = loadInuitInfo.createListInuitInfo();

        btnMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AugmentedImageActivity.this, MoreInfoActivity.class);
                startActivity(intent);
            }
        });

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
                    int index_of_current_image = augmentedImage.getIndex();
                    String text = "Detected Image" + index_of_current_image;
//                    txtInfo.setText(listInfoPages.get(index_of_current_image).getInfo());
                    txtTitle.setText(listInfoPages.get(index_of_current_image).getTitle());
                    txtArtist.setText(listInfoPages.get(index_of_current_image).getArtist());
                    txtDate.setText(listInfoPages.get(index_of_current_image).getDate());
                    txtMedium.setText(listInfoPages.get(index_of_current_image).getMedium());

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

    public void playVideo(Context context) {
        // Create an ExternalTexture for displaying the contents of the video.
        ExternalTexture texture = new ExternalTexture();

        // Create an Android MediaPlayer to capture the video on the external texture's surface.
        mediaPlayer = MediaPlayer.create(this, R.raw.lion_chroma);
        mediaPlayer.setSurface(texture.getSurface());
        mediaPlayer.setLooping(true);

        // Create a renderable with a material that has a parameter of type 'samplerExternal' so that
        // it can display an ExternalTexture. The material also has an implementation of a chroma key
        // filter.
        ModelRenderable.builder()
                .setSource(this, R.raw.chroma_key_video)
                .build()
                .thenAccept(
                        renderable -> {
                            videoRenderable = renderable;
                            renderable.getMaterial().setExternalTexture("videoTexture", texture);
                            renderable.getMaterial().setFloat4("keyColor", CHROMA_KEY_COLOR);
                        })
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load video renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
    }
}

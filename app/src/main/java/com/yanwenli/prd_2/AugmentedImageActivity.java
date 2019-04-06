package com.yanwenli.prd_2;

import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.Frame;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
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
    private TextView txtTitle;
    private TextView txtArtist;
    private TextView txtDate;
    private TextView txtMedium;
    private Button btnMoreInfo;
    private ArrayList<Inuit> listInfoPages = new ArrayList<>();

    private Boolean isFinished = true;

    @Nullable
    private ModelRenderable videoRenderable;
    private MediaPlayer mediaPlayer;
    // The color to filter out of the video.
    private static final Color CHROMA_KEY_COLOR = new Color(0.1843f, 1.0f, 0.098f);

    // Controls the height of the video in world space.
    private static final float VIDEO_HEIGHT_METERS = 0.85f;

    /**
     * onCreate Method, initiation
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        int orientation = getResources().getConfiguration().orientation;
//        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            setContentView(R.layout.activity_augmented_image);
//        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
//            setContentView(R.layout.activity_augmented_image2);
//        }

        setContentView(R.layout.activity_augmented_image2);

        findViews();
        loadInfo();
        addListeners();
    }

    public void findViews() {
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdateFrame);
        fitToScanView = findViewById(R.id.image_view_fit_to_scan);
        fitToScanView.setVisibility(View.VISIBLE);
        introduction_layout = findViewById(R.id.image_intro_layout);
        introduction_layout.setVisibility(View.GONE);

        txtTitle = findViewById(R.id.txt_title_image);
        txtArtist = findViewById(R.id.txt_artist_image);
        txtDate = findViewById(R.id.txt_date_image);
        txtMedium = findViewById(R.id.txt_medium_image);

        btnMoreInfo = findViewById(R.id.btn_more_info);
    }

    public void loadInfo() {

        LoadInuitInfo loadInuitInfo = new LoadInuitInfo();
        listInfoPages = loadInuitInfo.createListInuitInfo();
    }

    public void addListeners() {
        btnMoreInfo.setOnClickListener(v -> {
            Intent intent = new Intent(AugmentedImageActivity.this, MoreInfoActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Image recognition process and node binding according to need
     *
     * @param frameTime frametime got by system
     */
    public void onUpdateFrame(FrameTime frameTime) {
        Frame frame = arFragment.getArSceneView().getArFrame();

        // If there is no frame or ARCore is not tracking yet, just return.
        if (frame == null || frame.getCamera().getTrackingState() != TrackingState.TRACKING) {
            return;
        }

        Collection<AugmentedImage> updatedAugmentedImages = frame.getUpdatedTrackables(AugmentedImage.class);

        gestionState(updatedAugmentedImages);

    }

    /**
     * Play transport introduction video when user click the button "Play".
     *
     * @param context Context of the video
     * @param image   Node of the video
     */
//    public void playVideo(Context context, Anchor anchorParent) {
    public void playVideo(Context context, AugmentedImage image) {
        // Create an ExternalTexture for displaying the contents of the video.
        ExternalTexture texture = new ExternalTexture();

        // Create an Android MediaPlayer to capture the video on the external texture's surface.
        mediaPlayer = MediaPlayer.create(context, R.raw.video_transparent);
        mediaPlayer.setSurface(texture.getSurface());
        mediaPlayer.setLooping(false);


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


//        Anchor anchor = anchorParent.createAnchor();
        Anchor anchorParent = image.createAnchor(image.getCenterPose());
        AnchorNode anchorNode = new AnchorNode(anchorParent);
        anchorNode.setParent(arFragment.getArSceneView().getScene());

        Node videoNode = new Node();
        videoNode.setParent(anchorNode);

        // Set the scale of the node so that the aspect ratio of the video is correct.
        float videoWidth = mediaPlayer.getVideoWidth();
        float videoHeight = mediaPlayer.getVideoHeight();

        //Local position
        Vector3 local_position = new Vector3(0.0f * image.getExtentX(), 0.0f, 0.75f * image.getExtentZ()); //0.5 * videoHeight * VIDEO_HEIGHT_METERS
        videoNode.setLocalPosition(local_position);

        //Local scale
        Vector3 local_vector3 = new Vector3(
                (float) (0.219 * VIDEO_HEIGHT_METERS * (videoWidth / videoHeight)),
                (float) (0.219 * VIDEO_HEIGHT_METERS),
                0.219f);
        videoNode.setLocalScale(local_vector3);

        //Local rotation
        Vector3 rotation = new Vector3(1.0f, 0.0f, 0.0f);
        Quaternion quaternion = new Quaternion(rotation, -90);
        videoNode.setLocalRotation(quaternion);

//        videoNode.setOnTapListener((hitTestResult, motionEvent) -> {
//            mediaPlayer.release();
//        });

        mediaPlayer.setOnCompletionListener(mp -> {
            mediaPlayer.release();
            anchorNode.removeChild(videoNode);
            isFinished = true;
        });


        // Start playing the video when the first node is placed.
        if (!mediaPlayer.isPlaying()) {

            mediaPlayer.start();

            isFinished = false;
            // Wait to set the renderable until the first frame of the  video becomes available.
            // This prevents the renderable from briefly appearing as a black quad before the video plays.
            texture
                    .getSurfaceTexture()
                    .setOnFrameAvailableListener(
                            (SurfaceTexture surfaceTexture) -> {
                                videoNode.setRenderable(videoRenderable);
                                texture.getSurfaceTexture().setOnFrameAvailableListener(null);
                            });

        } else {
            videoNode.setRenderable(videoRenderable);
        }
    }

    /**
     * Gestion of the camera frame state
     *
     * @param updatedAugmentedImages a collection of AugmentedImage
     */
    public void gestionState(Collection<AugmentedImage> updatedAugmentedImages) {

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

                        //augmentedImageMap.clear();

                        AugmentedImageNode node = new AugmentedImageNode(this);
                        node.setImage(augmentedImage);
                        augmentedImageMap.put(augmentedImage, node);
                        arFragment.getArSceneView().getScene().addChild(node);
                        fitToScanView.setVisibility(View.GONE);
                        introduction_layout.setVisibility(View.VISIBLE);

                        if (!augmentedImageMap.isEmpty()) {

                            augmentedImageMap.get(augmentedImage).setOnTapListener((hitTestResult, motionEvent) -> {

//                                playVideo(this, augmentedImageMap.get(augmentedImage).getAnchor());

                                if(isFinished == true){
                                    playVideo(this, augmentedImage);
                                    Toast.makeText(AugmentedImageActivity.this, "Ok: play", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(AugmentedImageActivity.this, "Sorry: a video is playing now, you can't open another!", Toast.LENGTH_SHORT).show();

                                }

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

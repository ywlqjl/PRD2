package com.yanwenli.prd_2;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Session;
import com.google.ar.sceneform.ux.ArFragment;

import java.io.IOException;
import java.io.InputStream;

public class AugmentedImageFragment extends ArFragment {

    private static final String TAG = "AugmentedImageFragment";
    private static final String IMAGE_NAME = "inuit01.png";

    // This is a pre-created database containing the sample image.
    private static final String INUITS_IMAGE_DATABASE = "inuits.imgdb";

    // Augmented image configuration and rendering.
    private static final boolean USE_SINGLE_IMAGE = false;
    private static final double MIN_OPENGL_VERSION = 3.0;


    /**
     * Check versions and devices supports
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Check for Sceneform being supported on this device.  This check will be integrated into
        // Sceneform eventually.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(context, "Sceneform requires Android N or later", Toast.LENGTH_SHORT).show();
        }
        String openGlVersionString = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getDeviceConfigurationInfo().getGlEsVersion();

        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 or later");
            Toast.makeText(context, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * Create view in the scene. We don't use Plane for 3D so we close the plane detection when wo create the view.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view that we created
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        // Turn off the plane discovery since we're only looking for images
        getPlaneDiscoveryController().hide();
        getPlaneDiscoveryController().setInstructionView(null);
        getArSceneView().getPlaneRenderer().setEnabled(false);
        return view;
    }

    /**
     * Create configuration for our session
     *
     * @param session
     * @return
     */
    @Override
    protected Config getSessionConfiguration(Session session) {
        Config config = super.getSessionConfiguration(session);
        if (!setupAugmentedImageDatabase(config, session)) {
            Toast.makeText(getActivity(), "Could not setup augmented image database", Toast.LENGTH_SHORT).show();
        }
        return config;
    }

    /**
     * Load and set configuration for our database.
     *
     * @param config
     * @param session
     * @return
     */
    private boolean setupAugmentedImageDatabase(Config config, Session session) {
        AugmentedImageDatabase augmentedImageDatabase;

        AssetManager assetManager = getContext() != null ? getContext().getAssets() : null;
        if (assetManager == null) {
            Log.e(TAG, "Context is null, cannot intitialize image database.");
            Toast.makeText(getContext(), "Context is null, cannot intitialize image database.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (USE_SINGLE_IMAGE) {
            Bitmap augmentedImageBitmap = loadAugmentedImageBitmap(assetManager);
            if (augmentedImageBitmap == null) {
                return false;
            }

            augmentedImageDatabase = new AugmentedImageDatabase(session);
            augmentedImageDatabase.addImage(IMAGE_NAME, augmentedImageBitmap);
            // If the physical size of the image is known, you can instead use:
            //     augmentedImageDatabase.addImage("image_name", augmentedImageBitmap, widthInMeters);
            // This will improve the initial detection speed. ARCore will still actively estimate the
            // physical size of the image as it is viewed from multiple viewpoints.
        } else {
            // This is an alternative way to initialize an AugmentedImageDatabase instance,
            // load a pre-existing augmented image database.
            try (InputStream is = getContext().getAssets().open(INUITS_IMAGE_DATABASE)) {
                augmentedImageDatabase = AugmentedImageDatabase.deserialize(session, is);
            } catch (IOException e) {
                Log.e(TAG, "IO exception loading augmented image database.", e);
                return false;
            }
        }

        config.setAugmentedImageDatabase(augmentedImageDatabase);
        return true;
    }


    /**
     * Load AugmentedImageBitmap for single database
     * @param assetManager
     * @return
     */
    private Bitmap loadAugmentedImageBitmap(AssetManager assetManager) {
        try (InputStream is = assetManager.open(IMAGE_NAME)) {
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            Log.e(TAG, "IO exception loading augmented image bitmap.", e);
        }
        return null;
    }


}

package com.yanwenli.prd_2;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;

import com.google.ar.core.Anchor;
import com.google.ar.core.AugmentedImage;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;

import java.util.concurrent.CompletableFuture;

public class AugmentedImageNode extends AnchorNode implements Node.OnTapListener, Node.OnTouchListener {


    private static final String TAG = "AugmentedImageNode"; //节点标签
    private AugmentedImage image; //增强的图像
    private static CompletableFuture<ViewRenderable> test;
    private Node cornerNode;
    public Context nodeContext;

    private Anchor anchorParent;


    @RequiresApi(api = Build.VERSION_CODES.N)
    public AugmentedImageNode(Context context) {
        this.nodeContext = context;
        if (test == null) {

            test = ViewRenderable.builder()
                    .setView(context, R.layout.video_play_layout_renderable)
                    .build();
        }
    }

    public AugmentedImage getImage() {
        return image;
    }

    public void setImage(AugmentedImage image) {
        this.image = image;

        if (!test.isDone()) {
            CompletableFuture.allOf(test)
                    .thenAccept((Void aVoid) -> setImage(image))
                    .exceptionally(
                            throwable -> {
                                Log.e(TAG, "Exception loading", throwable);
                                return null;
                            });
        }

        // Set the anchor based on the center of the image.
        anchorParent = image.createAnchor(image.getCenterPose());
        setAnchor(anchorParent);

        Vector3 localPosition = new Vector3();

//        test position
//        localPosition.set(-0.0f, 0.0f, 0.0f );
        localPosition.set(-0.0f * image.getExtentX(), -0.0f, 0.0f * image.getExtentZ());

        /*
        localPosition.set(0.2f * image.getExtentX(), -0.0f , 0.39f * image.getExtentZ());*/
        //TODO:Vérifie si ça marche
        cornerNode = new Node();
        cornerNode.setParent(this);
        cornerNode.setLocalPosition(localPosition);
        Quaternion quaternion = new Quaternion(0.0f, 0.0f * image.getExtentX(), 0.0f, 0.0f * image.getExtentZ());
//        cornerNode.setLocalRotation(quaternion);
        cornerNode.setWorldRotation(quaternion);
        cornerNode.setRenderable(test.getNow(null));


    }


    //2018.
    @Override
    public void onTap(HitTestResult hitTestResult, MotionEvent motionEvent) {
        if (cornerNode == null) {
            return;
        }

        cornerNode.setEnabled(cornerNode.isEnabled());
    }

    public boolean onTouch(HitTestResult hitTestResult, MotionEvent motionEvent) {
        if (cornerNode == null) {
            return false;
        }


        cornerNode.setEnabled(cornerNode.isEnabled());
        return true;
    }
/*
    //这三个方法无需自己定义，无需重写，可直接使用父类中的方法

    public Context getNodeContext() {
        return this.nodeContext;
    }


    public Anchor getAnchorParent() {
        return anchorParent;
    }

    public void setAnchorParent(Anchor anchorParent) {
        this.anchorParent = anchorParent;
    }

    */
}

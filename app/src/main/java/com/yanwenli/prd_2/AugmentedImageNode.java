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
import com.google.ar.sceneform.rendering.ViewRenderable;

import java.util.concurrent.CompletableFuture;

public class AugmentedImageNode extends AnchorNode implements Node.OnTapListener, Node.OnTouchListener {

    private static final String TAG = "AugmentedImageNode"; //节点标签
    private AugmentedImage image; //增强的图像
    private static CompletableFuture<ViewRenderable> point;
    private static CompletableFuture<ViewRenderable> point_left;
    private static CompletableFuture<ViewRenderable> point_right;
    private Node cornerNode;
    private Node cornerNode_left;
    private Node cornerNode_right;
    public Context nodeContext;

    private Anchor anchorParent;


    /**
     * Build renderable images with layout.
     * @param context
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public AugmentedImageNode(Context context) {
        this.nodeContext = context;
        if (point == null) {

            point = ViewRenderable.builder()
                    .setView(context, R.layout.video_play_layout_renderable)
                    .build();
            point_left = ViewRenderable.builder()
                    .setView(context, R.layout.intrest_point_layout_renderable)
                    .build();
            point_right = ViewRenderable.builder()
                    .setView(context, R.layout.intrest_point_layout_renderable2)
                    .build();
        }
    }

    public AugmentedImage getImage() {
        return image;
    }

    /**
     * Load the ViewRenderable points.
     * Define their local positions, angle and the world position.
     * Bind the points with nodes
     * Connect to the parent node.
     * @param image
     */
    public void setImage(AugmentedImage image) {
        this.image = image;

        if (!point.isDone()||!point_left.isDone()||!point_right.isDone()) {
            CompletableFuture.allOf(point,point_left,point_right)
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

        // Create the local position Vector for every point
        Vector3 localPosition = new Vector3();
        Vector3 localPosition2 = new Vector3();
        Vector3 localPosition3 = new Vector3();

        // Centre position: localPosition.set(-0.0f, 0.0f, 0.0f );
        localPosition.set(-0.0f * image.getExtentX(), -0.0f, 0.0f * image.getExtentZ());
        localPosition2.set(-0.5f * image.getExtentX(), -0.0f, 0.7f * image.getExtentZ());
        localPosition3.set(0.5f * image.getExtentX(), -0.0f, -0.2f * image.getExtentZ());

        // Create world position with quaternion for changing local position to world position
        Quaternion quaternion = new Quaternion(0.0f, 0.0f * image.getExtentX(), 0.0f, 0.0f * image.getExtentZ());

        // Create the nodes and set their positions.
        cornerNode = new Node();
        cornerNode.setParent(this);
        cornerNode.setLocalPosition(localPosition);
//        cornerNode.setLocalRotation(quaternion);
        cornerNode.setWorldRotation(quaternion);
        cornerNode.setRenderable(point.getNow(null));

        cornerNode_left = new Node();
        cornerNode_left.setParent(this);
        cornerNode_left.setLocalPosition(localPosition2);
        cornerNode_left.setWorldRotation(quaternion);
        cornerNode_left.setRenderable(point_left.getNow(null));

        cornerNode_right = new Node();
        cornerNode_right.setParent(this);
        cornerNode_right.setLocalPosition(localPosition3);
        cornerNode_right.setWorldRotation(quaternion);
        cornerNode_right.setRenderable(point_right.getNow(null));

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

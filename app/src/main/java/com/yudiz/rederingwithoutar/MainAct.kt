package com.yudiz.rederingwithoutar

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import kotlinx.android.synthetic.main.act_main.*

class MainAct : AppCompatActivity() {

    private lateinit var scene: Scene
    private lateinit var node: Node

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        scene = sceneView.scene
        render(Uri.parse("coffee_cup.sfb"))
    }

    private fun render(uri: Uri) {
        ModelRenderable.builder()
            .setSource(this, uri)
            .build()
            .thenAccept {
                addNode(it)
            }
            .exceptionally {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                return@exceptionally null
            }

    }

    private fun addNode(model: ModelRenderable?) {
        model?.let {
            node = Node().apply {
                setParent(scene)
                localPosition = Vector3(0f, -2f, -7f)
                localScale = Vector3(3f, 3f, 3f)
                renderable = it
            }

            scene.addChild(node)
        }
    }

    override fun onPause() {
        super.onPause()
        sceneView.pause()
    }

    override fun onResume() {
        super.onResume()
        sceneView.resume()
    }
}

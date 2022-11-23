package com.cometchat.pro.androiduikit.ui.fragments

import android.app.Dialog

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Base64
import android.util.Base64.encodeToString
import android.view.*
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.cometchat.pro.androiduikit.LoginActivity
import com.cometchat.pro.androiduikit.MainActivityTrackingNavigation
import com.cometchat.pro.androiduikit.R
import com.cometchat.pro.androiduikit.Views.SessionManager
import com.cometchat.pro.androiduikit.Views.SessionManager.*
import com.cometchat.pro.androiduikit.api.saveToSql
import com.cometchat.pro.androiduikit.db.Run
import com.cometchat.pro.androiduikit.other.Constants.ACTION_PAUSE_SERVICE
import com.cometchat.pro.androiduikit.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.cometchat.pro.androiduikit.other.Constants.ACTION_STOP_SERVICE
import com.cometchat.pro.androiduikit.other.Constants.MAP_ZOOM
import com.cometchat.pro.androiduikit.other.Constants.POLYLINE_COLOR
import com.cometchat.pro.androiduikit.other.Constants.POLYLINE_WIDTH
import com.cometchat.pro.androiduikit.other.TrackingUtility
import com.cometchat.pro.androiduikit.services.Polyline
import com.cometchat.pro.androiduikit.services.TrackingService
import com.cometchat.pro.androiduikit.ui.viewmodels.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.snackbar.Snackbar
import com.stripe.android.stripe3ds2.views.ChallengeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_recommend.view.*
import kotlinx.android.synthetic.main.fragment_tracking.*
import java.io.ByteArrayOutputStream
import java.util.*
import javax.inject.Inject
import kotlin.math.round


const val  CANCEL_TRACKING_DIALOG_TAG = "CancelDialog"

@AndroidEntryPoint
class TrackingFragment: Fragment(R.layout.fragment_tracking) {

    private val viewModel: MainViewModel by viewModels()

    private var isTracking = false
    private var pathPoints = mutableListOf<Polyline>()

    private var map: GoogleMap? = null

    private var curTimeInMillis = 0L

    private var menu: Menu? = null

    @set:Inject
    private var weight = 80f

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)
        btnToggleRun.setOnClickListener{
            toggleRun()
        }

        if(savedInstanceState != null){
            val cancelTrackingDialog = parentFragmentManager.findFragmentByTag(
                    CANCEL_TRACKING_DIALOG_TAG) as CancelTrackingDialog?
            cancelTrackingDialog?.setYesListener {
                stopRun()
            }
        }



        btnFinishRun.setOnClickListener{
            zoomToSeeWholeTrack()
            endRunAndSaveToDb()

            val view = View.inflate(requireContext(), R.layout.dialog_recommend, null)
            val builder = AlertDialog.Builder(requireContext())
            builder.setView(view)
            val dialog = builder.create()

            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.setCancelable(false)

            view.btn_confirm.setOnClickListener {
                findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
                dialog.dismiss()
            }

            view.btn_no.setOnClickListener {
                val intent = Intent(context, com.cometchat.pro.androiduikit.Views.MainActivity::class.java)
                startActivity(intent)
                dialog.dismiss()
            }

            view.btn_challenge.setOnClickListener {
                val intent = Intent(context, com.cometchat.pro.androiduikit.challenges.challenges::class.java)
                startActivity(intent)
                dialog.dismiss()
            }


        }


        mapView.getMapAsync{
            map = it
            addAllPolylines()
        }

        subscribeToObservers()
    }

    private fun subscribeToObservers(){
        TrackingService.isTracking.observe(viewLifecycleOwner, Observer {
            updateTracking(it)
        })
        TrackingService.pathPoints.observe(viewLifecycleOwner, Observer {
            pathPoints = it
            addLatestPolyLine()
            moveCameraToUser()
        })

        TrackingService.timeRunInMillis.observe(viewLifecycleOwner, Observer {
            curTimeInMillis = it
            val formattedTime = TrackingUtility.getFormatedStopWatchTime(curTimeInMillis, true)
            tvTimer.text = formattedTime
        })
    }

    private fun toggleRun(){
        if (isTracking){
            menu?.getItem(0)?.isVisible = true
            sendCommandToService(ACTION_PAUSE_SERVICE)
        }else{
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_tracking_menu, menu)
        this.menu = menu
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        if (curTimeInMillis > 0L){
            this.menu?.getItem(0)?.isVisible = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.miCancelTracking -> {
                showCancelTrackingDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showCancelTrackingDialog(){
        CancelTrackingDialog().apply {
            setYesListener {
                stopRun()
            }
        }.show(parentFragmentManager, CANCEL_TRACKING_DIALOG_TAG)
    }

    private fun stopRun(){
        tvTimer.text = "00:00:00:00"
        sendCommandToService(ACTION_STOP_SERVICE)
        findNavController().navigate(R.id.action_trackingFragment_to_runFragment)
    }

    private fun updateTracking(isTracking: Boolean){
        this.isTracking = isTracking
        if (!isTracking && curTimeInMillis > 0L){
            btnToggleRun.text = "Start"
            btnFinishRun.visibility = View.VISIBLE
        }else if(isTracking){
            btnToggleRun.text ="Stop"
            menu?.getItem(0)?.isVisible = true
            btnFinishRun.visibility = View.GONE
        }
    }

    private fun moveCameraToUser(){
        if(pathPoints.isNotEmpty() && pathPoints.last().isNotEmpty()){
            map?.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                            pathPoints.last().last(),
                            MAP_ZOOM
                    )
            )
        }
    }

    private fun addAllPolylines(){
        for(polyline in pathPoints){
            val polylineOptions = PolylineOptions()
                    .color(POLYLINE_COLOR)
                    .width(POLYLINE_WIDTH)
                    .addAll(polyline)
            map?.addPolyline(polylineOptions)
        }
    }

    private fun zoomToSeeWholeTrack(){
        val bounds = LatLngBounds.Builder()
        for(polyline in pathPoints){
            for(pos in polyline){
                bounds.include(pos)
            }
        }
        map?.moveCamera(
                CameraUpdateFactory.newLatLngBounds(
                        bounds.build(),
                        mapView.width,
                        mapView.height,
                        (mapView.height * 0.05F).toInt()
                )
        )
    }

    private fun endRunAndSaveToDb(){
        map?.snapshot { bmp ->
            var distanceInMeters = 0
            for (polyline in pathPoints){
                distanceInMeters += TrackingUtility.calculatePolylineLength(polyline).toInt()
            }
            val avgSpeed = round((distanceInMeters / 1000f) / (curTimeInMillis / 1000f / 60 / 60) * 10) /10f
            val dateTimestamp = Calendar.getInstance().timeInMillis
            val caloriesBurned = ((distanceInMeters / 1000f) * weight).toInt()
            // saving the data to the database VVV

            val run = Run(bmp, dateTimestamp, avgSpeed, distanceInMeters, curTimeInMillis, caloriesBurned)
            viewModel.insertRun(run)


            // makes bitmap to Base 64 encoding (png) string
            val img = bmp.bmpToString(bmp)

            var sessionManager: SessionManager = SessionManager(context) //TINITIGNAN KUNG MAY SESSION
            var uid = sessionManager.userDetail[UNIQUE_ID]
            saveToSql.saveToSQL(img, dateTimestamp.toString(), avgSpeed, distanceInMeters, curTimeInMillis.toString(), caloriesBurned, uid)

            Snackbar.make(
                    requireActivity().findViewById(R.id.rootView),
                    "Saved Succesfully",
                    Snackbar.LENGTH_LONG
            ).show()
            stopRun()

        }
    }

    private fun addLatestPolyLine(){
        if(pathPoints.isNotEmpty() && pathPoints.last().size > 1){
            val preLastLatLng = pathPoints.last()[pathPoints.last().size - 2]
            val lastLatLng = pathPoints.last().last()
            val polylineOptions = PolylineOptions()
                    .color(POLYLINE_COLOR)
                    .width(POLYLINE_WIDTH)
                    .add(preLastLatLng)
                    .add(lastLatLng)
            map?.addPolyline(polylineOptions)
        }
    }

    private fun sendCommandToService(action: String)=
            Intent(requireContext(), TrackingService::class.java).also{
                it.action = action
                requireContext().startService(it)
            }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

}

private fun Bitmap?.bmpToString(bitmap: Bitmap?): String? {
    val baos = ByteArrayOutputStream()
    bitmap?.compress(Bitmap.CompressFormat.PNG, 100, baos)
    val imageBytes = baos.toByteArray()
    return encodeToString(imageBytes, Base64.DEFAULT)
}

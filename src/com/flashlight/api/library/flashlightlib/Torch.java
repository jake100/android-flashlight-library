package com.flashlight.api.library.flashlightlib;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;

public class Torch 
{
    private Camera camera;
    public boolean isFlashOn;
    private Parameters params;
    public Torch()
    {
    	getCamera();
    }
    public boolean supports(Context context)
    {
    	PackageManager pm = context.getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return false;
        }
        return true;
    }
    private void getCamera() 
    {
        if (camera == null)
        {
            try 
            {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {}
        }
    }
    public void pause()
    {
    	params.setFlashMode(Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);
    }
    public void stop()
    {
    	camera.release();
    }
	public void toggleFlash()
	{
		if (isFlashOn) 
		{
    		turnOffFlash();
    	} else 
    	{
    		turnOnFlash();
    	}
		
	}
    public void turnOffFlash() {
      if (isFlashOn) 
      {
          if (camera == null || camera.getParameters() == null) 
          {
              return;
          }
           
          params = camera.getParameters();
          params.setFlashMode(Parameters.FLASH_MODE_OFF);
          camera.setParameters(params);
          camera.startPreview();
          isFlashOn = false;
      }
  }
    public void turnOnFlash() 
    {
      if (!isFlashOn)
      {
          if (camera == null || camera.getParameters() == null)
          {
              return;
          }
           
          params = camera.getParameters();
          params.setFlashMode(Parameters.FLASH_MODE_TORCH);
          camera.setParameters(params);
          camera.stopPreview();
          isFlashOn = true;
      }
   
  }
}

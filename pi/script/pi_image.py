import RPi.GPIO as GPIO
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BOARD)
GPIO.setup(10, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
from datetime import datetime
from picamera import PiCamera
from time import sleep
import os

import pyrebase

firebaseConfig = {
    'apiKey': "ZUS4WmV3g6Lc0yHl5J2K02ck68lhMQ9ePkd4mDKR",
    'authDomain': "smartfridgeapp-5d119.firebaseapp.com",
    'databaseURL': "https://smartfridgeapp-5d119-default-rtdb.firebaseio.com",
    'projectId': "smartfridgeapp-5d119",
    'storageBucket': "smartfridgeapp-5d119.appspot.com",
    'appId': "1:192763750287:android:fc5e40aa2ed3668caf8dfa"

}

firebase = pyrebase.initialize_app(firebaseConfig)

storage = firebase.storage()

camera = PiCamera()

while True:
  try:
    if GPIO.input(10) == GPIO.HIGH:
        print("pushed")
        now = datetime.now()
        dt = now.strftime("%d%m%Y%H:%M:%S")
        name = dt+".jpg"
        camera.capture(name)
        print(name+" saved")
        storage.child(name).put(name)
        print("Image sent")
        os.remove(name)
        print("File Removed")
        sleep(2)


  except:
        camera.close()
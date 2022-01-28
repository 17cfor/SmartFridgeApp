
import time
import board
import busio as io
import adgitafruit_mlx90614
import pyrebase

i2c = io.I2C(board.SCL, board.SDA, frequency=100000)
mlx = adafruit_mlx90614.MLX90614(i2c)

config = {
  "apiKey": "ZUS4WmV3g6Lc0yHl5J2K02ck68lhMQ9ePkd4mDKR",
  "authDomain": "smartfridgeapp-5d119.firebaseapp.com",
  "databaseURL": "https://smartfridgeapp-5d119-default-rtdb.firebaseio.com",
  "storageBucket": "smartfridgeapp-5d119.appspot.com"
}

firebase = pyrebase.initialize_app(config)
db = firebase.database()

print("Send Data to Firebase Using Raspberry Pi")
print("—————————————-")
print()

while True:
  groceryString = "{}".format(mlx.grocery_list)

  groceryNumber = 0

  if groceryString:
    groceryNumber =+ groceryNumber

  # groceryNumber = "{:.0f}".format(mlx.grocery_number)

  groceryItem = float(groceryString)
  # itemNumber = float(groceryNumber)

  print("Grocery Item: {}".format(groceryString))
  print("Item number: {} °C".format(groceryNumber))
  print()

  data = {
    "item": groceryItem,
    "id": groceryNumber,
  }

  db.child("fridge").child("1-fridgeGroceryList").push(data)

  time.sleep(2)
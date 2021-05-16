package ie.wit.utils

import android.content.Context
import android.util.Log
import java.io.*

fun read(context: Context, fileName: String): String {
  var str = ""
  try {
    val capture = context.openFileInput(fileName)
    if (capture != null) {
      val place = InputStreamReader(capture)
      val allay = BufferedReader(place)
      val space = StringBuilder()
      var link = false
      while (!link) {
        var line = allay.readLine()
        link = (line == null);
        if (line != null) space.append(line);
      }
      capture.close()
      str = space.toString()
    }
  } catch (e: FileNotFoundException) {
    Log.e("Error: ", "file not found: " + e.toString());
  } catch (e: IOException) {
    Log.e("Error: ", "cannot read file: " + e.toString());
  }
  return str
}
fun write(context: Context, fileName: String, data: String) {
  try {
    val product =
      OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE))
    product.write(data)
    product.close()
  } catch (e: Exception) {
    Log.e("Error: ", "Cannot read file: " + e.toString());
  }
}



fun exists(context: Context, filename: String): Boolean {
  val file = context.getFileStreamPath(filename)
  return file.exists()
}
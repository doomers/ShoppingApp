package doomers.doomerskart;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class LoginDetails extends AppCompatActivity {
    int REQUEST_CAMERA = 1;
    int SELECT_FILE;
    ImageView ivImage;
    RadioGroup radiosexgroup;
    RadioButton radiosexbutton;
    EditText fnametext, lnametext, emailtext;
    Toolbar toolbar;


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_details);
        toolbar=(Toolbar)findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        fnametext = (EditText) findViewById(R.id.editText);
        lnametext = (EditText) findViewById(R.id.editText2);
        emailtext = (EditText) findViewById(R.id.editText4);

        ivImage = (ImageView) findViewById(R.id.imageView2);
        Button button = (Button) findViewById(R.id.submitbuton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radiosexgroup = (RadioGroup) findViewById(R.id.radiosexgroup);
                int selectid = radiosexgroup.getCheckedRadioButtonId();
                radiosexbutton = (RadioButton) findViewById(selectid);
                //storing data in extermnal storage
                String state = Environment.getExternalStorageState();

                if (Environment.MEDIA_MOUNTED.equals(state)) {//is there is internal storage or sd card mounted
                    //this statement will return true

                    //the root dicectory of storage

                    File root = Environment.getExternalStorageDirectory();
                    //change the directory to the for that we want to choose to save data of our apps in
                    //it may be created or not.
                    File dir = new File(root.getAbsolutePath() + "/APPDATA");


                    if (!dir.exists()) {
                        dir.mkdir();
                    }

                    File file = new File(dir, "name.txt");

                    String Message = fnametext.getText().toString() + " ";
                    Message += lnametext.getText().toString();

                    System.out.println(Message);

                    try {
                        FileOutputStream out = new FileOutputStream(file);
                        //this will write the message into mymessage.txt
                        out.write(Message.getBytes());
                        out.flush();
                        out.close();

                        //Toast.makeText(getApplicationContext(), "MESSAGE SAVED", Toast.LENGTH_LONG).show();
                        //these are the exceptions handlers
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                    File file1 = new File(dir, "email.txt");

                    Message = emailtext.getText().toString();

                    System.out.println(Message);
                    try {
                        FileOutputStream out = new FileOutputStream(file1);
                        //this will write the message into mymessage.txt
                        out.write(Message.getBytes());
                        out.flush();
                        out.close();


                        //these are the exceptions handlers
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


            }
        });


    }
    public void save(View view){

            final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginDetails.this);
            builder.setCancelable(true);


            builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (items[item].equals("Take Photo")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_CAMERA);
                    } else if (items[item].equals("Choose from Library")) {
                        Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(
                                Intent.createChooser(intent, "Select File"),
                                SELECT_FILE);
                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                File root = Environment.getExternalStorageDirectory();
                //change the directory to the for that we want to choose to save data of our apps in
                //it may be created or not.
                File dir = new File(root.getAbsolutePath() + "/APPDATA");

                File destination = new File(dir, "me.png");

                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ivImage.setImageBitmap(thumbnail);

            } else if (requestCode == SELECT_FILE) {
                File root=Environment.getExternalStorageDirectory();
                File dir = new File(root.getAbsolutePath()+"/APPDATA");
                if(!dir.exists()){dir.mkdir();}
                File file =new File(dir,"mypics.png");
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null,
                        null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();

                String selectedImagePath = cursor.getString(column_index);

                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);
                try {
                    FileOutputStream out =new FileOutputStream(file);

                    bm.compress(Bitmap.CompressFormat.PNG,100,out);
                    out.flush();
                    out.close();

                    Toast.makeText(getApplicationContext(), "MESSAGE SAVED", Toast.LENGTH_LONG).show();
                    //these are the exceptions handlers
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ivImage.setImageBitmap(bm);
            }
        }

    }


}
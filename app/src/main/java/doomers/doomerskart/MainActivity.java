package doomers.doomerskart;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getnavTitle();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.app_open, R.string.app_closed);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }


    public void getnavTitle() {
        String retrievename;
        File root = Environment.getExternalStorageDirectory();
        File dir = new File(root + "/Appdata");
        File file = new File(dir, "name.txt");
        try {
            FileInputStream in = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            while ((retrievename = bufferedReader.readLine()) != null) {
                stringBuffer.append(retrievename + "\n");
                TextView textview;
                textview = (TextView) findViewById(R.id.nametextview);
                textview.setText(retrievename);
            }

            System.out.println("MESSAGE IS"+retrievename);
            // mainActivity.setextview(retrievename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        root = Environment.getExternalStorageDirectory();
        dir = new File(root + "/Appdata");
        File file1 =new File(dir,"email.txt");
        try {
            String retreivemail;
            FileInputStream in1 = new FileInputStream(file1);
            InputStreamReader inputStreamReader1 = new InputStreamReader(in1);
            BufferedReader bufferedReader1 = new BufferedReader(inputStreamReader1);
            StringBuffer stringBuffer1 = new StringBuffer();
            while ((retreivemail = bufferedReader1.readLine()) != null) {
                stringBuffer1.append(retreivemail + "\n");
                TextView emailtextview;
                emailtextview = (TextView) findViewById(R.id.emailtextview);
                emailtextview.setText(retreivemail);
            }System.out.println("MESSAGE IS"+retreivemail);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File imagefile =new File(dir,"mypics.png");
        if(imagefile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imagefile.getAbsolutePath());
            ImageView imgView = (ImageView)findViewById(R.id.imageView);
            imgView.setImageBitmap(getCircleBitmap(myBitmap));
        } else {
            Log.d("IMAGE_FILE", "File doesn't exist");
        }
    }
    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        int id = menuItem.getItemId();
        if (id == R.id.nav_camera) {
            setTitle("CAMERA");

        }
        if (id == R.id.nav_gallery) {
            setTitle("GALLERY");
        }
        if (id == R.id.nav_discussion) {
            setTitle("DISCUSSSION");
        }
        if (id == R.id.nav_slideshow) {
            setTitle("SLIDESHOW");
        }
        if (id == R.id.nav_manage) {
            setTitle("MANAGE");
        }
        if (id == R.id.nav_send) {
            setTitle("SEND");
        }
        if (id == R.id.nav_share) {
            setTitle("SHARE");
        }
        if (id == R.id.nav_login) {
            startActivity(new Intent(this, LoginDetails.class));
        }
        return true;
    }
}

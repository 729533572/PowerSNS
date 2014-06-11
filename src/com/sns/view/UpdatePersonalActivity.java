package com.sns.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.powersns.R;
import com.sns.service.buttonlistener.UpdatePersonalButtonListener;

public class UpdatePersonalActivity extends Activity{
	
	public Button btn_submit,btn_cancel;
	public EditText et_username,et_age,et_mood;
	public RadioGroup radioGroup;
	public RadioButton radio_man,radio_wuman;
	public ImageButton imgbtn_open,imabtn_camera,imgbtn_upload;
	public ImageView photo_face;
	public String UID,AGE,GENDER,NAME,MOOD;
	public Bitmap bbm;
	public String sex = null;
	//public String 
	public Bitmap bitmap1;
	String dates;//�r�g��ʽ
	
	public void init(){
		btn_submit = (Button)findViewById(R.id.update_submit);
		btn_cancel = (Button)findViewById(R.id.update_cancel);
		
		imgbtn_open = (ImageButton)findViewById(R.id.imageB_open);
		imabtn_camera = (ImageButton)findViewById(R.id.imageB_camera);
		imgbtn_upload = (ImageButton)findViewById(R.id.imageB_upload);
		
		et_username = (EditText)findViewById(R.id.update_username);
		et_age = (EditText)findViewById(R.id.update_age);
		et_mood = (EditText)findViewById(R.id.update_mood);
		radioGroup = (RadioGroup)findViewById(R.id.sexgroup);
		radio_man = (RadioButton)findViewById(R.id.man);
		radio_wuman = (RadioButton)findViewById(R.id.wuman);
		
		et_username = (EditText)findViewById(R.id.update_username);
		et_age = (EditText)findViewById(R.id.update_age);
		et_mood = (EditText)findViewById(R.id.update_mood);
		
		photo_face = (ImageView)findViewById(R.id.img_face);
		
		Bundle extras = getIntent().getExtras();
		UID = extras.getString("UID");
		AGE = extras.getString("AGE");
		GENDER = extras.getString("GENDER");
		NAME = extras.getString("NAME");
		MOOD = extras.getString("MOOD");
		bbm = (Bitmap) extras.get("bbm");
		
		btn_submit.setOnClickListener(new UpdatePersonalButtonListener(this));
		btn_cancel.setOnClickListener(new UpdatePersonalButtonListener(this));
		imgbtn_open.setOnClickListener(new UpdatePersonalButtonListener(this));
		imabtn_camera.setOnClickListener(new UpdatePersonalButtonListener(this));
		imgbtn_upload.setOnClickListener(new UpdatePersonalButtonListener(this));
		photo_face.setImageBitmap(bbm);
		SetSome();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_updatemess_layout);
		init();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void SetSome(){
		et_username.setText(NAME);
		et_age.setText(AGE);
		et_mood.setText(MOOD);
System.out.println("GENDER------->"+GENDER);
		if(GENDER.equals("��")){
System.out.println("�У�������");
			radio_man.setChecked(true);
		}if(GENDER.equals("Ů")){
System.out.println("Ů��������");
			radio_wuman.setChecked(true);
		}else if(!GENDER.equals("��")&&!GENDER.equals("Ů")){
			radio_man.setChecked(false);
			radio_wuman.setChecked(false);
		}
	}
	
	public String getSex(){
	     if(radio_man.isChecked()){
	    	 sex = "��";
	     }if(radio_wuman.isChecked()){
	    	 sex = "Ů";
	     }
System.out.println("sex------------------------------------------>"+sex);		 
		return sex;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK && requestCode == 0) {
			boolean sdStatus = Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED);
System.out.println("SD���Ƿ����" + sdStatus);
			
			bitmap1 = (Bitmap) data.getExtras().get("data");// ��ȡ������ص����ݣ���ת��ΪBitmapͼƬ��ʽ
			FileOutputStream b = null;
			SimpleDateFormat si = new SimpleDateFormat("HH.mm.ss");
			dates = si.format(new Date());

			File file = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/myImage/");

			if (!file.exists())
				file.mkdirs();

			String fileName = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/myImage/" + dates + ".jpg";
			// System.out.println(file.exists());
			byte[] b2 = bitmap1.toString().getBytes();

			try {
				// fil
				b = new FileOutputStream(fileName);
				// ����ͼƬ��ʽ
				bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, b);
				b.write(b2);
				b.flush();
				b.close();
			} catch (FileNotFoundException e) {

				e.printStackTrace();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			photo_face.setImageBitmap(bitmap1);

		}

		ContentResolver resolver = getContentResolver();

		if (requestCode == 1) {
			Uri uri = data.getData();
			try {
				bitmap1 = MediaStore.Images.Media.getBitmap(resolver, uri);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			photo_face.setImageBitmap(bitmap1);

		}
	
	
	
}}

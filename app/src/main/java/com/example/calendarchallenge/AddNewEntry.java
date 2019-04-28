package com.example.calendarchallenge;

        import android.app.DatePickerDialog;
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.net.Uri;
        import android.provider.SyncStateContract;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Base64;
        import android.view.View;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Toast;

        import com.google.android.gms.auth.api.signin.internal.Storage;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.OnProgressListener;
        import com.google.firebase.storage.StorageReference;
        import com.google.firebase.storage.UploadTask;

        import java.io.ByteArrayOutputStream;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Locale;
        import java.util.UUID;

public class AddNewEntry extends AppCompatActivity {

    FirebaseStorage storage;
    StorageReference storageReference;
    StorageReference imageReference;

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;

    Bitmap pictureBitmap;
    Calendar myCalendar = Calendar.getInstance();
    EditText dateInput;

    EditText titleText;
    EditText detailsText;

    String eventTitle;
    String eventDetails;

    Button addEntryButton;

    public AddNewEntry() {
        this.myCalendar = myCalendar;
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateInput.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_entry);

        Intent camIntent = getIntent();
        pictureBitmap = camIntent.getParcelableExtra("picture");

        ImageView picture = findViewById(R.id.pictureView);
        picture.setImageBitmap(pictureBitmap);

        dateInput = (EditText) findViewById(R.id.date);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddNewEntry.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        addEntryButton = (Button) findViewById(R.id.addEntry);
        addEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEntryFunction();
            }
        });

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    private void addEntryFunction() {
        uploadImage();
        Intent goBackToCalendarIntent = new Intent(this, CalendarActivity.class);
        startActivity(goBackToCalendarIntent);
    }

    private void uploadImage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        pictureBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://ecen-489-final-project.appspot.com");
        final StorageReference imagesRef = storageRef.child("images/image1.jpg");

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Task<Uri> downloadUrl = imagesRef.getDownloadUrl();
                // Do what you want
            }
        });
    }
}

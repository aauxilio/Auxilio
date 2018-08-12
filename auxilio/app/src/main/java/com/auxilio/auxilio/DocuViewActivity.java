package com.auxilio.auxilio;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.auxilio.auxilio.data.AffidivitApplication;
import com.auxilio.auxilio.data.ChildInformation;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DocuViewActivity extends AppCompatActivity {

    private AffidivitApplication affidivitApplication;

    private static final String AUTHORITY="com.commonsware.android.cp.v4file";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docu_view);

        affidivitApplication = DataUtils.getAffidivitApplication().build();

        File f=new File(getFilesDir(), "affidavitFormTemplate.pdf");

        if (!f.exists()) {
            AssetManager assets=getAssets();

            try {
                copy(assets.open("affidavitFormTemplate.pdf"), f);
            }
            catch (IOException e) {
                Log.e("FileProvider", "Exception copying from assets", e);
            }
        }

        try {
            OutputStream outputStream = new FileOutputStream(f);
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);

            document.open();
            setChildInfo(document);
            document.close();
            viewPdf(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void setChildInfo(Document document) throws DocumentException {
        ChildInformation childInformation = affidivitApplication.getChildInformation();

        document.add(new Paragraph("Full Name: " + childInformation.getFirstName() + " " + childInformation.getLastName()));
        document.add(new Paragraph("DOB: " + childInformation.getDob()));
    }

    private void viewPdf(File f) {
        Intent i=
                new Intent(Intent.ACTION_VIEW,
                        FileProvider.getUriForFile(this, AUTHORITY, f));

        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(i);
        finish();
    }

    static private void copy(InputStream in, File dst) throws IOException {
        FileOutputStream out=new FileOutputStream(dst);
        byte[] buf=new byte[1024];
        int len;

        while ((len=in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }

        in.close();
        out.close();
    }
}

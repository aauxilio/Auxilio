package com.auxilio;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.auxilio.data.AffidivitApplication;
import com.auxilio.data.ChildInformation;
import com.auxilio.data.Person;
import com.itextpdf.text.Chunk;
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
import java.util.List;

public class DocuViewActivity extends AppCompatActivity {

    private AffidivitApplication affidivitApplication;

    private static final String AUTHORITY="com.commonsware.android.cp.v4file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docu_view);

        startRetrieveDoc();
    }

    private void startRetrieveDoc() {
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
            setParentInfo(document);
            addRelatives(document);
            addMainBody(document);
            document.close();
            viewPdf(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void addMainBody(Document document) throws DocumentException {

        Person person = affidivitApplication.getParent();
        ChildInformation childInformation = affidivitApplication.getChildInformation();
        if (person == null) {
            return;
        }

        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

        document.add(new Paragraph("I, " + person.getFirstName() + " " + person.getLastName() + " hereby declare to take " +
                "on the full responsibility, guardianship and support for "
                + childInformation.getFirstName() + " " + childInformation.getLastName()
                + " (Child's full name) stay in the United State as (her/his) legal guardian and " +
                "supervisor until the age of eighteen."));

        document.add(Chunk.NEWLINE);

        document.add(new Paragraph("I further declare that all communication and supervision of her stay in the United States shall be my sole" +
                "responsibility"));

        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("As guardian, I shall be the primary contact in the United States and shall take all legal and financial" +
                "responsibilities for the supervision of (her/his) stay."));


        document.add(new Paragraph("Sincerely,"));
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

        document.add(new Paragraph(person.getFirstName() + " " + person.getLastName()));
        document.add(new Paragraph("(Print Name)"));
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

        document.add(new Paragraph("State of California"));
        document.add(new Paragraph("County of Monterey"));

    }

    private void setChildInfo(Document document) throws DocumentException {
        ChildInformation childInformation = affidivitApplication.getChildInformation();

        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Full Name: " + childInformation.getFirstName() + " " + childInformation.getLastName()));
        document.add(new Paragraph("DOB: " + childInformation.getDob()));
    }

    private void setParentInfo(Document document) throws DocumentException {
        Person parent = affidivitApplication.getParent();

        if (parent == null) {
            return;
        }

        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Parent"));
            document.add(new Paragraph("Full Name: " + parent.getFirstName() + parent.getLastName()));
        document.add(new Paragraph("Phone Number: " + parent.getPhoneNumber()));
        document.add(new Paragraph("Address: " + parent.getAddress()));
    }

    private void addRelatives(Document document) throws DocumentException {
        List<Person> relatives = affidivitApplication.getRelatives();
        for (Person person : relatives) {
            addRelative(document, person);
        }
    }

    private void addRelative(Document document, @Nullable Person person) throws DocumentException {

        if (person == null) {
            return;
        }

        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Guardian"));
        document.add(new Paragraph("Full Name: " + person.getFirstName() + person.getLastName()));
        document.add(new Paragraph("Phone Number: " + person.getPhoneNumber()));
        document.add(new Paragraph("Address: " + person.getAddress()));
    }

    private void viewPdf(File f) {

        Uri file = Uri.fromFile(f);

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

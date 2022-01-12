package com.example.registor.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.VerifiedInputEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.registor.R;
import com.example.registor.model.DetailItem;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class FoldingContactListAdapter extends ArrayAdapter<DetailItem> {

    HashSet<Integer> integers = new HashSet<>();
    ArrayList<DetailItem> arrayList;
    Context context;

    public FoldingContactListAdapter(@NonNull Context context, ArrayList<DetailItem> arrayList) {
        super(context, 0, arrayList);

        this.arrayList = arrayList;
        this.context = context;
    }
    public FoldingContactListAdapter(@NonNull Context context, List<DetailItem> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        DetailItem item = getItem(position);

        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;

        if (cell == null){

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            cell = (FoldingCell) inflater.inflate(R.layout.foldingadapter_layout, parent, false);

            viewHolder.txt_name = cell.findViewById(R.id.fname);
            viewHolder.txt_phno = cell.findViewById(R.id.phone);
            viewHolder.mainProfileImage = cell.findViewById(R.id.profile_image);
            viewHolder.header_name = cell.findViewById(R.id.fheader_name);
            viewHolder.contentRequestBtn = cell.findViewById(R.id.content_request_btn);
            viewHolder.fathername = cell.findViewById(R.id.content_from_address_2);
            viewHolder.age = cell.findViewById(R.id.foldingage);
            viewHolder.mobilenumber = cell.findViewById(R.id.mobilenumber);
            viewHolder.phonenumber = cell.findViewById(R.id.foldingphonenumber);
            viewHolder.education = cell.findViewById(R.id.foldingwork);
            viewHolder.city = cell.findViewById(R.id.foldingcity);
            viewHolder.address = cell.findViewById(R.id.foldingaddress);
            viewHolder.email = cell.findViewById(R.id.foldingemail);
            viewHolder.birthdate = cell.findViewById(R.id.foldingbirthDate);
            viewHolder.name = cell.findViewById(R.id.foldingname);
            viewHolder.profileImage = cell.findViewById(R.id.content_avatar);
            viewHolder.groupName = cell.findViewById(R.id.content_GroupName2);
            viewHolder.groupNo = cell.findViewById(R.id.content_groupNo2);
            viewHolder.editBtn = cell.findViewById(R.id.editBtn);
            viewHolder.cast = cell.findViewById(R.id.foldingcast);
            viewHolder.school = cell.findViewById(R.id.foldingschool);
            viewHolder.referancePersonName = cell.findViewById(R.id.foldingreferancePersonName);
            viewHolder.referancePersonNo = cell.findViewById(R.id.foldingreferancePersonNo);
            viewHolder.area = cell.findViewById(R.id.foldingarea);

            cell.setTag(viewHolder);

        }else {

            if (integers.contains(position)){

                cell.unfold(true);

            }else {

                cell.fold(true);

            }
            viewHolder = (ViewHolder) cell.getTag();

        }

        if (null == item)
            return cell;

        viewHolder.contentRequestBtn.setOnClickListener(v -> {

            Intent intent1 = new Intent(Intent.ACTION_CALL);
            intent1.setData(Uri.parse("tel: " + arrayList.get(position).getPhno()));

            if (ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            context.startActivity(intent1);

        });

        viewHolder.editBtn.setOnClickListener(v -> {

            String userId;

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Edit Detail");

            TextInputEditText groupNo, name, fatherName, phoneNumber, whatsappNumber
                    , cast, referancePersonName, referancePersonNumber, school, city, address, area, education;

            AutoCompleteTextView groupName, birthDate, age;

            ImageView imageView;

            MaterialButton updateBtn;

            View view = LayoutInflater.from(context).inflate(R.layout.updatedailog_layout, null);

            builder.setView(view);

            imageView = view.findViewById(R.id.URegistration_profile_Image);
            groupName = view.findViewById(R.id.UgroupName);
            groupNo = view.findViewById(R.id.UgroupNo);
            name = view.findViewById(R.id.Uname);
            fatherName = view.findViewById(R.id.UfatherName);
            phoneNumber = view.findViewById(R.id.Uphonenumber);
            whatsappNumber = view.findViewById(R.id.Uwhatsappnumber);
            birthDate = view.findViewById(R.id.UbirthDate);
            age = view.findViewById(R.id.Uage);
            cast = view.findViewById(R.id.Ucast);
            referancePersonName = view.findViewById(R.id.Ureference);
            referancePersonNumber = view.findViewById(R.id.Ureferencepersonnumber);
            school = view.findViewById(R.id.Uschool);
            city = view.findViewById(R.id.Ucity);
            address = view.findViewById(R.id.Uaddress);
            area = view.findViewById(R.id.Uarea);
            education = view.findViewById(R.id.Ueducation);
            updateBtn = view.findViewById(R.id.UpdateBtn);

            groupName.setText(arrayList.get(position).getGroupName());
            groupNo.setText(arrayList.get(position).getGroupNo());
            name.setText(arrayList.get(position).getName());
            fatherName.setText(arrayList.get(position).getFatherName());
            phoneNumber.setText(arrayList.get(position).getPhno());
            whatsappNumber.setText(arrayList.get(position).getWhatsappNo());
            birthDate.setText(arrayList.get(position).getBirthDate());
            age.setText(arrayList.get(position).getAge());
            cast.setText(arrayList.get(position).getCast());
            referancePersonName.setText(arrayList.get(position).getReferancePersonName());
            referancePersonNumber.setText(arrayList.get(position).getReferancePersonNo());
            school.setText(arrayList.get(position).getSchool());
            city.setText(arrayList.get(position).getCity());
            address.setText(arrayList.get(position).getAddress());
            area.setText(arrayList.get(position).getArea());
            education.setText(arrayList.get(position).getEducation());

            Picasso.with(context).load(arrayList.get(position).getProfileImage()).into(imageView);

            imageView.setOnClickListener(v1 -> {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                ((Activity) context).startActivityForResult(intent, 101);

            });

            userId = arrayList.get(position).getUserId();

            Log.e(TAG, "cast: " + arrayList.get(position).getCast());

            updateBtn.setOnClickListener(v1 -> {

                String GroupName, GroupNo, Name, FatherName, PhoneNumber, WhatsappNumber, BirthDate, Age
                        , Cast, ReferancePersonName, ReferancePersonNumber, School, City, Address, Area, Education;

                GroupName = groupName.getText().toString();
                GroupNo = groupNo.getText().toString();
                Name = name.getText().toString();
                FatherName = fatherName.getText().toString();
                PhoneNumber = phoneNumber.getText().toString();
                WhatsappNumber = whatsappNumber.getText().toString();
                BirthDate = birthDate.getText().toString();
                Age = age.getText().toString();
                Cast = cast.getText().toString();
                ReferancePersonName = referancePersonName.getText().toString();
                ReferancePersonNumber = referancePersonNumber.getText().toString();
                School = school.getText().toString();
                City = city.getText().toString();
                Address = address.getText().toString();
                Area = area.getText().toString();
                Education = education.getText().toString();

                Map<String, Object> user = new HashMap<>();
                user.put("groupNo", GroupNo);
                user.put("name", Name);
                user.put("fatherName", FatherName);
                user.put("phoneNumber", PhoneNumber);
                user.put("whatsappNumber", WhatsappNumber);
                user.put("birthDate", BirthDate);
                user.put("age", Age);
                user.put("cast", Cast);
                user.put("referancePersonName", ReferancePersonName);
                user.put("referancePersonNumber", ReferancePersonNumber);
                user.put("school", School);
                user.put("city", City);
                user.put("address", Address);
                user.put("area", Area);
                user.put("education", Education);

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
                reference.child(arrayList.get(position).getGroupName()).child(userId).updateChildren(user);

            });

            builder.show();

        });

        viewHolder.txt_name.setText(arrayList.get(position).getName());
        viewHolder.txt_phno.setText(arrayList.get(position).getPhno());
        viewHolder.header_name.setText(arrayList.get(position).getName());

        viewHolder.fathername.setText(arrayList.get(position).getFatherName());
        viewHolder.age.setText(arrayList.get(position).getAge());
        viewHolder.mobilenumber.setText(arrayList.get(position).getPhno());
        viewHolder.phonenumber.setText(arrayList.get(position).getPhno());
        viewHolder.city.setText(arrayList.get(position).getCity());
        viewHolder.address.setText(arrayList.get(position).getAddress());
        viewHolder.email.setText(arrayList.get(position).getWhatsappNo());
        viewHolder.birthdate.setText(arrayList.get(position).getBirthDate());
        viewHolder.name.setText(arrayList.get(position).getName());
        viewHolder.education.setText(arrayList.get(position).getEducation());
        viewHolder.groupNo.setText(arrayList.get(position).getGroupNo());
        viewHolder.groupName.setText(arrayList.get(position).getGroupName());
        viewHolder.school.setText(arrayList.get(position).getSchool());
        viewHolder.cast.setText(arrayList.get(position).getCast());
        viewHolder.area.setText(arrayList.get(position).getArea());
        viewHolder.referancePersonNo.setText(arrayList.get(position).getReferancePersonNo());
        viewHolder.referancePersonName.setText(arrayList.get(position).getReferancePersonName());

        Picasso.with(context).load(arrayList.get(position).getProfileImage()).into(viewHolder.profileImage);
        Picasso.with(context).load(arrayList.get(position).getProfileImage()).into(viewHolder.mainProfileImage);

        return cell;
    }

    public void registerToggle(int position) {
        if (integers.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        integers.remove(position);
    }

    public void registerUnfold(int position) {
        integers.add(position);
    }

    private static class ViewHolder {

        TextView txt_name, txt_buss, txt_phno, contentRequestBtn, header_name, fathername, age, mobilenumber, phonenumber, education, city, address, email
                , birthdate, name, groupNo, groupName, editBtn, area, school, referancePersonName, referancePersonNo, cast;

        CircleImageView profileImage, mainProfileImage;

    }
}

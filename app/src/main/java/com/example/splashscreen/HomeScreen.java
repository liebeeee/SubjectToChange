package com.example.splashscreen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.splashscreen.databinding.ActivityHomeScreenBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeScreen extends AppCompatActivity implements OnItemsClick{
    ActivityHomeScreenBinding binding;
    private ExpensesAdapter expensesAdapter;
    Intent intent;
    private long income = 0, expense = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.lgreen)));
        //Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color=\"#FDFFAE\">MoneyMinder</font>"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.moneybg);


        expensesAdapter = new ExpensesAdapter(this, this);
        binding.recycle.setAdapter(expensesAdapter);
        binding.recycle.setLayoutManager(new LinearLayoutManager(this));

        intent = new Intent(this,HomeScreen.class);

        binding.addIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type", "Income");
                startActivity(intent);
            }
        });

        binding.addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type", "Expense");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please");
        progressDialog.setMessage("Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            FirebaseAuth.getInstance()
                    .signInAnonymously()
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(HomeScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
@Override
protected void onResume() {
        super.onResume();
        income = 0; expense = 0;
        getData();
}

    public void onClick(ExpenseModel expenseModel){
        intent.putExtra("model", expenseModel);
        startActivity(intent);
    }
    private void getData(){
        FirebaseFirestore
                .getInstance()
                .collection("expenses")
                .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        expensesAdapter.clear();
                        List<DocumentSnapshot> dsList=queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot ds:dsList){
                            ExpenseModel expenseModel = ds.toObject(ExpenseModel.class);
                            if (expenseModel.getType().equals("Income")) {
                                income += expenseModel.getAmount();
                            }else {
                                expense += expenseModel.getAmount();
                            }
                            expensesAdapter.add(expenseModel);
                        }
                        setUpGraph();
                    }
                });
    }

    private void setUpGraph() {
        List<PieEntry> pieEntries = new ArrayList<>();
        List<Integer> colorsList = new ArrayList<>();
        if (income != 0){
            pieEntries.add(new PieEntry(income, "Income"));
            colorsList.add(getResources().getColor(R.color.lgreen));
        }
        if (income != 0){
            pieEntries.add(new PieEntry(expense, "Income"));
            colorsList.add(getResources().getColor(R.color.yells));
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntries, String.valueOf(income = expense));
        PieData = new PieData
    }

    @Override
    public void OnClick(ExpenseModel expenseModel) {
        intent.putExtra("model", expenseModel);
        startActivity(intent);
    }
}
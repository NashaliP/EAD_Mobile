package com.example.ead.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.ead.R;
import com.example.ead.adapter.ProductsListAdapter;
import com.example.ead.models.ProductModel;
import com.example.ead.network.ApiClient;
import com.example.ead.services.ProductService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreActivity extends AppCompatActivity {
    private RecyclerView exploreRecyclerView;
    private ProductsListAdapter productsListAdapter;
    private List<ProductModel> productList;
    private List<ProductModel> filteredProductList;
    private SearchView idSVExplore;
    private Button filterButton, sortButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        // Setup Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.shop); // Set default selected item

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.shop:
                        startActivity(new Intent(getApplicationContext(), ShoppingActivity.class));
                        overridePendingTransition(0, 0);

                    case R.id.explore:
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        // Initialize RecyclerView and set GridLayoutManager
        exploreRecyclerView = findViewById(R.id.ExploreRecyclerView);
        productList = new ArrayList<>();
        filteredProductList = new ArrayList<>();  // Initialize filtered list
        productsListAdapter = new ProductsListAdapter(this, filteredProductList);

        // Set up the RecyclerView to use a GridLayoutManager with 2 columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2); // 2 columns
        exploreRecyclerView.setLayoutManager(gridLayoutManager);
        exploreRecyclerView.setAdapter(productsListAdapter);

        idSVExplore = findViewById(R.id.idSVExplore);
        sortButton = findViewById(R.id.sortButton);
        filterButton = findViewById(R.id.filterButton);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilterBottomSheet();
            }
        });

        // Set up the listener for Sort Button
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSortBottomSheet();
            }
        });


        // Fetch all products from the API
        fetchAllProducts();

        // Set up search functionality
        setupSearchView();
    }

    private void fetchAllProducts() {
        ProductService productService = ApiClient.getRetrofitInstance().create(ProductService.class);

        Call<List<ProductModel>> call = productService.getAllProducts();
        call.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productList.clear();
                    productList.addAll(response.body());
                    filteredProductList.addAll(productList);
                    productsListAdapter.notifyDataSetChanged();
                } else {
                    Log.e("ExploreActivity", "Response not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                Log.e("ExploreActivity", "Error fetching products", t);
            }
        });
    }

    private void setupSearchView() {
        idSVExplore.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // No action needed on submit, but can handle if needed
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter products as the user types
                filterProducts(newText);
                return true;
            }
        });
    }

    private void filterProducts(String query) {
        // Clear the filtered list first
        filteredProductList.clear();

        if (query.isEmpty()) {
            // If query is empty, show all products
            filteredProductList.addAll(productList);
        } else {
            // Filter based on the query
            for (ProductModel product : productList) {
                if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredProductList.add(product);
                }
            }
        }

        // Notify the adapter about the changes
        productsListAdapter.notifyDataSetChanged();
    }

    // Method to open the Bottom Sheet Dialog for sorting options
    private void openSortBottomSheet() {
        // Create a BottomSheetDialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ExploreActivity.this);

        // Inflate the layout for the bottom sheet
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.bottom_sheet_sort, (ViewGroup) findViewById(R.id.buttonContainer), false);

        // Set the bottom sheet view to the dialog
        bottomSheetDialog.setContentView(bottomSheetView);

        // Set the height of the bottom sheet to half the screen
        bottomSheetView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        bottomSheetDialog.getBehavior().setPeekHeight((int) (getResources().getDisplayMetrics().heightPixels * 0.5));

        // Get the RadioGroup and handle selection
        RadioGroup radioGroupSort = bottomSheetView.findViewById(R.id.radioGroupSort);
        radioGroupSort.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
//                    case R.id.radioRecentlyAdded:
//                        selectedOption = "Recently Added";
//                        break;
                    case R.id.radioPriceLowToHigh:
                        // Sort products by price in ascending order (low to high)
                        sortProductsByPrice(true);
                        break;
                    case R.id.radioPriceHighToLow:
                        // Sort products by price in descending order (high to low)
                        sortProductsByPrice(false);
                        break;
//                    case R.id.radioMostRated:
//                        selectedOption = "Most Rated";
//                        break;
                }

                // Notify adapter about changes
                productsListAdapter.notifyDataSetChanged();

                // Dismiss the bottom sheet after selection
                bottomSheetDialog.dismiss();
            }
        });

        // Show the bottom sheet
        bottomSheetDialog.show();
    }

    // Method to sort products by price
    private void sortProductsByPrice(boolean ascending) {
        if (ascending) {
            // Sort in ascending order (Price: Low to High)
            Collections.sort(filteredProductList, (product1, product2) -> Double.compare(product1.getPrice(), product2.getPrice()));
        } else {
            // Sort in descending order (Price: High to Low)
            Collections.sort(filteredProductList, (product1, product2) -> Double.compare(product2.getPrice(), product1.getPrice()));
        }

        // After sorting, notify the adapter to update the displayed products
        productsListAdapter.notifyDataSetChanged();
    }

    private void openFilterBottomSheet() {
        // Create a BottomSheetDialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ExploreActivity.this);

        // Inflate the layout for the bottom sheet (assuming you have a bottom_sheet_filter layout)
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.bottom_sheet_filter, (ViewGroup) findViewById(R.id.buttonContainer), false);

        // Set the bottom sheet view to the dialog
        bottomSheetDialog.setContentView(bottomSheetView);

        // Initialize views in the bottom sheet
        SeekBar priceRangeSeekBar = bottomSheetView.findViewById(R.id.priceRangeSeekBar);
        TextView priceRangeTxt = bottomSheetView.findViewById(R.id.priceRangeTxt);
        CheckBox checkBoxZara = bottomSheetView.findViewById(R.id.checkBoxZara);
        CheckBox checkBoxLunaGrace = bottomSheetView.findViewById(R.id.checkBoxLunaGrace);
        CheckBox checkBoxElysianWardrobe = bottomSheetView.findViewById(R.id.checkBoxElysianWardrobe);
        RadioGroup radioGroupRatings = bottomSheetView.findViewById(R.id.radioGroupRatings);
        Button buttonApplyFilter = bottomSheetView.findViewById(R.id.buttonApplyFilter);

//        priceRangeSeekBar.setMax(1000);

        priceRangeSeekBar.setProgress(50);
        // Update price range text dynamically
        priceRangeTxt.setText("0 - 200");
        priceRangeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                priceRangeTxt.setText("0 - " + progress + "$");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Apply filter button click listener
        buttonApplyFilter.setOnClickListener(v -> {
            applyFilters(priceRangeSeekBar.getProgress(),
                    checkBoxZara.isChecked(),
                    checkBoxLunaGrace.isChecked(),
                    checkBoxElysianWardrobe.isChecked(),
                    radioGroupRatings);
                    bottomSheetDialog.dismiss();
        });

        // Show the bottom sheet
        bottomSheetDialog.show();
    }

    // Method to apply filters based on user selection
    private void applyFilters(int maxPrice, boolean isZaraChecked, boolean isLunaGraceChecked, boolean isElysianWardrobeChecked, RadioGroup radioGroupRatings) {
        filteredProductList.clear();

        // Prepare a list of selected vendors
        List<String> selectedVendorIds = new ArrayList<>();
        if (isZaraChecked) selectedVendorIds.add("66f718a3fa3dcafbbe19fd09"); // Zara ID
        if (isLunaGraceChecked) selectedVendorIds.add("66fa5b18a613796e6c547726"); // Luna Grace ID
        if (isElysianWardrobeChecked)
            selectedVendorIds.add("66fad18f1c568fb28aed53bb"); // Elysian Wardrobe ID

        // Get selected rating
        int selectedRatingId = radioGroupRatings.getCheckedRadioButtonId();
        int minRating = 0; // Default if none selected
        if (selectedRatingId == R.id.radio4StarsAndUp) {
            minRating = 4;
        } else if (selectedRatingId == R.id.radio3StarsAndUp) {
            minRating = 3;
        } else if (selectedRatingId == R.id.radio2StarsAndUp) {
            minRating = 2;
        }

        // Filter products based on price, vendorId, and rating
        for (ProductModel product : productList) {
            boolean matchesPrice = product.getPrice() <= maxPrice;
            boolean matchesVendor = selectedVendorIds.isEmpty() || selectedVendorIds.contains(product.getVendorId());
            boolean matchesRating = product.getRating() >= minRating;

            if (matchesPrice && matchesVendor && matchesRating) {
                filteredProductList.add(product);
            }
        }

        // Notify adapter of changes
        productsListAdapter.notifyDataSetChanged();
    }

}
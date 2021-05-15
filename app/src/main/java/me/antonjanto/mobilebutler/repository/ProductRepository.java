package me.antonjanto.mobilebutler.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import me.antonjanto.mobilebutler.model.Product;

public interface ProductRepository
{
     LiveData<List<Product>> getAllProducts();
}

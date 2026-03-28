package com.soumya.expense_tracker_backend.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.soumya.expense_tracker_backend.constant.TransactionType;
import com.soumya.expense_tracker_backend.entity.Category;
import com.soumya.expense_tracker_backend.repository.CategoryRepository;

@Component
public class CategorySeeder implements CommandLineRunner {

  private final CategoryRepository repo;

  public CategorySeeder(CategoryRepository repo) {
    this.repo = repo;
  }

  @Override
  public void run(String... args) {

    seedIfNotExists("Salary", TransactionType.INCOME);
    seedIfNotExists("Business", TransactionType.INCOME);
    seedIfNotExists("Freelance", TransactionType.INCOME);
    seedIfNotExists("Investment", TransactionType.INCOME);
    seedIfNotExists("Refund", TransactionType.INCOME);
    seedIfNotExists("Other Income", TransactionType.INCOME);

    seedIfNotExists("Food", TransactionType.EXPENSE); // groceries + dining
    seedIfNotExists("Transport", TransactionType.EXPENSE); // fuel, cab, public transport
    seedIfNotExists("Housing", TransactionType.EXPENSE); // rent + maintenance
    seedIfNotExists("Utilities", TransactionType.EXPENSE); // electricity, water, internet
    seedIfNotExists("Entertainment", TransactionType.EXPENSE); // movies, concerts, subscriptions
    seedIfNotExists("Healthcare", TransactionType.EXPENSE); // doctor, pharmacy
    seedIfNotExists("Shopping", TransactionType.EXPENSE); // clothing, electronics
    seedIfNotExists("Education", TransactionType.EXPENSE); // tuition, books, courses
    seedIfNotExists("Insurance", TransactionType.EXPENSE); // health, life premiums
    seedIfNotExists("Personal Care", TransactionType.EXPENSE); // haircut, makeup, skincare
    seedIfNotExists("Debt Payment", TransactionType.EXPENSE); // credit card, loans
    seedIfNotExists("Miscellaneous", TransactionType.EXPENSE);
  }

  private void seedIfNotExists(String name, TransactionType type) {

    boolean exists = repo.existsByNameAndIsDefaultTrue(name);

    if (!exists) {
      repo.save(create(name, type));
    }
  }

  private Category create(String name, TransactionType type) {
    Category c = new Category();
    c.setName(name);
    c.setType(type);
    c.setIsDefault(true);
    return c;
  }
}